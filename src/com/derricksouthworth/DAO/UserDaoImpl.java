package com.derricksouthworth.DAO;

import com.derricksouthworth.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for User Objects
 *
 * @author derrick.southworth
 */

public class UserDaoImpl {
    /**
     * Handles READ task for CRUD on all User Objects
     * @return
     * @throws SQLException
     * @throws ParseException
     * @throws ClassNotFoundException
     */
    public static ObservableList<User> getAllUsers() throws SQLException, ParseException, ClassNotFoundException {
        DBConnect.makeConnection();
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sqlStatement = "select * FROM user";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int userID = result.getInt("userid");
            String userName = result.getString("userName");
            String password = result.getString("password");
            Calendar createDate = stringToCalendar(result.getString("createDate"));
            String createdBy = result.getString("createdBy");
            Calendar lastUpdate = stringToCalendar(result.getString("lastUpdate"));
            String lastUpdateBy = result.getString("lastUpdateBy");
            User userResult = new User(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdateBy);
            allUsers.add(userResult);
        }
        DBConnect.closeConnection();
        return allUsers;
    }

    public static void updateUser() {

    }

    public static void deleteUser() {

    }

    public static void addUser() {

    }
}
