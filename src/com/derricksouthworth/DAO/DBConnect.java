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
    private static final String databaseName = "U04aFY";
    private static final String DB_URL = "jdbc:mysql://3.227.166.251/" + databaseName;
    private static final String username = "U04aFY";
    private static final String password = "53688186170";
    private static final String driver = "com.mysql.jdbc.Driver";

    // Connect to database
    public static void makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection Successful");
    }

    // Disconnect from database
    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("Connection Closed");
    }

    public static Connection getConn() {
        return conn;
    }
}
