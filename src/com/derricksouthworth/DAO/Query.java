package com.derricksouthworth.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Construct sql database queries
 *
 * @author derrick.southworth
 */

public class Query {

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_USER_NAME = "userName";
    public static final String COLUMN_USER_PASSWORD = "password";

    public static final String TABLE_CUSTOMER = "customer";
    public static final String COLUMN_CUSTOMER_ID = "customerId";
    public static final String COLUMN_CUSTOMER_NAME = "customerName";
    public static final String COLUMN_CUSTOMER_ACTIVE = "active";

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

    public static final String UPDATE_CUSTOMER =
            "UPDATE " + TABLE_CUSTOMER + ", " + TABLE_ADDRESS + " SET " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_2 + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_POSTAL_CODE + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_PHONE + " = ?, " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE + " = NOW(), " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE_BY + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_LAST_UPDATE + " = NOW(), " +
                    TABLE_ADDRESS + "." + COLUMN_LAST_UPDATE_BY + " = ?, " + " WHERE " +
                    TABLE_CUSTOMER + "." + COLUMN_ADDRESS_ID + " = " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID + " AND " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID + " = ?";

    public static final String DELETE_CUSTOMER =
            "DELETE " + TABLE_CUSTOMER + ", " + TABLE_ADDRESS +
                    " FROM " + TABLE_CUSTOMER + " INNER JOIN " + TABLE_ADDRESS + " ON " +
                    TABLE_CUSTOMER + "." + COLUMN_ADDRESS_ID + " = " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID + " WHERE " +
                    COLUMN_CUSTOMER_ID + " = ?";

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

    public static final String QUERY_GET_ALL_COUNTRIES =
            "SELECT * FROM " + TABLE_COUNTRY;

    public static final String INSERT_ADDRESS =
            "INSERT INTO " + TABLE_ADDRESS + " SET " +
                    COLUMN_ADDRESS_ID + " = ?, " +
                    COLUMN_ADDRESS + " = ?, " +
                    COLUMN_ADDRESS_2 + " = ?, " +
                    COLUMN_CITY_ID + " = ?, " +
                    COLUMN_POSTAL_CODE + " = ?, " +
                    COLUMN_PHONE + " = ?, " +
                    COLUMN_CREATE_DATE + " = NOW(), " +
                    COLUMN_CREATED_BY + " = ?, " +
                    COLUMN_LAST_UPDATE + " = NOW(), " +
                    COLUMN_LAST_UPDATE_BY + " = ?";

    public static final String INSERT_CUSTOMER =
            "INSERT INTO " + TABLE_CUSTOMER + " SET " +
                    COLUMN_CUSTOMER_ID + " = ?, " +
                    COLUMN_CUSTOMER_NAME + " = ?, " +
                    COLUMN_ADDRESS_ID + " = ?, " +
                    COLUMN_CUSTOMER_ACTIVE + " = 1, " +
                    COLUMN_CREATE_DATE + " = NOW(), " +
                    COLUMN_CREATED_BY + " = ?, " +
                    COLUMN_LAST_UPDATE + " = NOW(), " +
                    COLUMN_LAST_UPDATE_BY + " = ?";

    public static final String QUERY_MAX_ID_FROM_ADDRESS =
            "SELECT MAX(" + COLUMN_ADDRESS_ID + ") FROM " + TABLE_ADDRESS;

    public static String buildQueryUser(String userName, String password) {
        return "SELECT * FROM " + TABLE_USER + " WHERE " +
                COLUMN_USER_NAME + " = '" + userName + "' AND " +
                COLUMN_USER_PASSWORD + " = '" + password + "'";
    }
}
