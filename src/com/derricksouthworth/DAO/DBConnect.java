package com.derricksouthworth.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
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

    // Connect to database
    public static boolean makeConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    // Disconnect from database
    public static void closeConnection() {
        try {
            if(conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public static Connection getConn() {
        return conn;
    }
}
