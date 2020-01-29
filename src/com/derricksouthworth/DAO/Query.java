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

    public static final String TABLE_CUSTOMER = "customer";
    public static final String COLUMN_CUSTOMER_ID = "customerId";
    public static final String COLUMN_CUSTOMER_NAME = "customerName";
    public static final String COLUMN_ACTIVE = "active";

    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ID = "addressId";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_2 = "address2";
    public static final String COLUMN_POSTAL_CODE = "postalCode";
    public static final String COLUMN_PHONE = "phone";

    public static final String TABLE_CITY = "city";
    public static final String COLUMN_CITY_ID = "cityId";
    public static final String COLUMN_CITY_NAME = "city";

    public static final String TABLE_COUNTRY = "country";
    public static final String COLUMN_COUNTRY_ID = "countryId";
    public static final String COLUMN_COUNTRY_NAME = "country";

    public static final String COLUMN_CREATE_DATE = "createDate";
    public static final String COLUMN_CREATED_BY = "createdBy";
    public static final String COLUMN_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String QUERY_GET_CUSTOMER =
            "SELECT " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID + ", " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS + ", " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_2 + ", " +
                    TABLE_CITY + "." + COLUMN_CITY_NAME + ", " +
                    TABLE_ADDRESS + "." + COLUMN_POSTAL_CODE + ", " +
                    TABLE_ADDRESS + "." + COLUMN_PHONE + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_CREATE_DATE + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_CREATED_BY + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE_BY + " FROM " +
                    TABLE_CUSTOMER + " INNER JOIN " + TABLE_ADDRESS + " ON " +
                    TABLE_CUSTOMER + "." + COLUMN_ADDRESS_ID + " = " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID + " INNER JOIN " +
                    TABLE_CITY + " ON " + TABLE_ADDRESS + "." + COLUMN_CITY_ID + " = " +
                    TABLE_CITY + "." + COLUMN_CITY_ID + " WHERE " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + " = \"";

    public static final String QUERY_GET_ALL_CUSTOMERS =
            "SELECT " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + ", " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS + ", " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_2 + ", " +
                    TABLE_CITY + "." + COLUMN_CITY_NAME + ", " +
                    TABLE_ADDRESS + "." + COLUMN_POSTAL_CODE + ", " +
                    TABLE_ADDRESS + "." + COLUMN_PHONE + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_CREATE_DATE + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_CREATED_BY + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE_BY + " FROM " +
                    TABLE_CUSTOMER + " INNER JOIN " + TABLE_ADDRESS + " ON " +
                    TABLE_CUSTOMER + "." + COLUMN_ADDRESS_ID + " = " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID + " INNER JOIN " +
                    TABLE_CITY + " ON " + TABLE_ADDRESS + "." + COLUMN_CITY_ID + " = " +
                    TABLE_CITY + "." + COLUMN_CITY_ID;

    public static final String QUERY_GET_ALL_CITIES =
            "SELECT " + TABLE_CITY + "." + COLUMN_CITY_ID + ", " +
                    TABLE_CITY + "." + COLUMN_CITY_NAME + ", " +
                    TABLE_COUNTRY + "." + COLUMN_COUNTRY_NAME + ", " +
                    TABLE_CITY + "." + COLUMN_CREATE_DATE + ", " +
                    TABLE_CITY + "." + COLUMN_CREATED_BY + ", " +
                    TABLE_CITY + "." + COLUMN_LAST_UPDATE + ", " +
                    TABLE_CITY + "." + COLUMN_LAST_UPDATE_BY + " FROM " +
                    TABLE_CITY + " INNER JOIN " + TABLE_COUNTRY + " ON " +
                    TABLE_CITY + "." + COLUMN_COUNTRY_ID + " = " +
                    TABLE_COUNTRY + "." + COLUMN_COUNTRY_ID;

    public static final String QUERY_GET_CITY =
            "SELECT " + TABLE_CITY + "." + COLUMN_CITY_ID + ", " +
                    TABLE_COUNTRY + "." + COLUMN_COUNTRY_NAME + ", " +
                    TABLE_CITY + "." + COLUMN_CREATE_DATE + ", " +
                    TABLE_CITY + "." + COLUMN_CREATED_BY + ", " +
                    TABLE_CITY + "." + COLUMN_LAST_UPDATE + ", " +
                    TABLE_CITY + "." + COLUMN_LAST_UPDATE_BY + " FROM " +
                    TABLE_CITY + " INNER JOIN " + TABLE_COUNTRY + " ON " +
                    TABLE_CITY + "." + COLUMN_COUNTRY_ID + " = " +
                    TABLE_COUNTRY + "." + COLUMN_COUNTRY_ID + " WHERE " +
                    TABLE_CITY + "." + COLUMN_CITY_NAME + " = \"";

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
