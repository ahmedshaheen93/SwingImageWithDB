/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagewithdatabasedemo.model;

import imagewithdatabasedemo.config.ConnectionMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lts
 */
public class CustomerDAO {

    private final static String INSERT = "insert into customer(name,image) values(?,?)";
    private final static String FIND_ALL = "select * from customer";
    private final static String FIND_BY_ID = "select * from customer where id = ?";
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CustomerDAO() {
        connection = ConnectionMySql.getConnection();
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setImage(resultSet.getBytes("image"));
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customers;

    }

    public Customer findById(int id) {
        try {
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Customer customer = null;
            while (resultSet.next()) {
                customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setImage(resultSet.getBytes("image"));
            }
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int save(Customer customer) {

        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setBytes(2, customer.getImage());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    id = Integer.valueOf(resultSet.getString(i));
                }
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
