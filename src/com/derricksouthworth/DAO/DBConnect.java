package com.derricksouthworth.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Setup database connection info
 * Manage connecting to and disconnecting from database
 *
 * @author derrick.southworth
 */

public class DBConnect {
    //Connection String
    //Server name:  3.227.166.251
    //Database name:  U04aFY
    //Username:  U04aFY
    //Password:  53688186170

    static Connection conn;
    private static final String DATABASE_NAME = "U04aFY";
    private static final String DB_URL = "jdbc:mysql://3.227.166.251/" + DATABASE_NAME;
    private static final String USERNAME = "U04aFY";
    private static final String PASSWORD = "53688186170";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private PreparedStatement queryAllCustomers;
    private PreparedStatement queryAllCities;

    // Connect to database
    public boolean makeConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            queryAllCustomers = conn.prepareStatement(Query.QUERY_GET_ALL_CUSTOMERS);
            queryAllCities = conn.prepareStatement(Query.QUERY_GET_ALL_CITIES);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    // Disconnect from database
    public void closeConnection() {
        try {
            if(queryAllCustomers != null) {
                queryAllCustomers.close();
            }
            if(queryAllCities != null) {
                queryAllCities.close();
            }
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public static Connection getConn() {
        return conn;
    }

    private static DBConnect instance = new DBConnect();

    private DBConnect() {

    }

    public static DBConnect getInstance() {
        return instance;
    }
}
