package com.derricksouthworth.DAO;

import com.derricksouthworth.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

    private static Logger logger = Logger.getLogger("ScheduleLog.txt");

    private static User currentUser;

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

    public static Boolean login(String userName, String password) throws SQLException {
        Statement statement = null;
        String sqlStatement = Query.buildQueryUser(userName, password);
        ResultSet result = null;

        try {
            conn.setAutoCommit(false);

            statement = conn.createStatement();
            result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                currentUser = new User(result.getInt(Query.COLUMN_USER_ID),
                        result.getString(Query.COLUMN_USER_NAME),
                        result.getString(Query.COLUMN_USER_PASSWORD));
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Unable to log in: " + e.getMessage());
            return false;
        } finally {
            createLogFile();
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
            conn.setAutoCommit(true);
        }
        return false;
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

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void createLogFile() {
        try {
            FileHandler fh = new FileHandler("ScheduleLog.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            logger.addHandler(fh);
        } catch (IOException | SecurityException e) {
            System.out.println("Error creating log file: " + e.getMessage());
            e.printStackTrace();
        }

        if (currentUser != null) {
            logger.log(Level.INFO, "LOGIN: User {0} logged in", getCurrentUser().getUserName());
        } else {
            logger.log(Level.INFO, "UNSUCCESSFUL LOGIN.");
        }
    }
}
