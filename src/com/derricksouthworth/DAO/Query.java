package com.derricksouthworth.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static com.derricksouthworth.DAO.DBConnect.conn;

/**
 * Construct sql database queries
 *
 * @author derrick.southworth
 */

public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet result;

    // Execute query or update depending on the first word of given query
    public static void makeQuery(String q) {
        query = q;
        try {
            statement = conn.createStatement();
            if(query.toLowerCase().startsWith("select")) {
                result = statement.executeQuery(q);
            } else if(query.toLowerCase().startsWith("delete") ||
                    query.toLowerCase().startsWith("update") ||
                    query.toLowerCase().startsWith("insert")) {
                statement.executeUpdate(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public static ResultSet getResult() {
        return result;
    }
}
