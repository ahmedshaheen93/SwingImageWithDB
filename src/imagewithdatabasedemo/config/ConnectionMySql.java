/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagewithdatabasedemo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author lts
 */
public class ConnectionMySql {

    private String url = "jdbc:mysql://localhost/";
    private String db = "customerInfo";
    private String driver = "com.mysql.jdbc.Driver";
    private String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
    private static Connection connection;

    private static final ConnectionMySql CONNECTION_MY_SQL = new ConnectionMySql();

    private ConnectionMySql() {
        try {
            // Load the JDBC driver
            Class.forName(driver);
            connection = DriverManager.getConnection(url + db + unicode, "root", "root");
//            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * @return the connection
     */
    public static Connection getConnection() {
        return connection;
    }

}
