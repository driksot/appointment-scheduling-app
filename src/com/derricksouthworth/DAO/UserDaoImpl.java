package com.derricksouthworth.DAO;

import com.derricksouthworth.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author derrick.southworth
 */

public class UserDaoImpl {
    private static Connection conn = DBConnect.getInstance().getConn();
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Boolean login(String userName, String password) {
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = Query.buildQueryUser(userName, password);
            ResultSet result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                currentUser = new User(result.getInt(Query.COLUMN_USER_ID),
                        result.getString(Query.COLUMN_USER_NAME),
                        result.getString(Query.COLUMN_USER_PASSWORD));
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
