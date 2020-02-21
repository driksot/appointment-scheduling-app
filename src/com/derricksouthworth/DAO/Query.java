package com.derricksouthworth.DAO;

/**
 * Construct sql database queries
 *
 * @author derrick.southworth
 */

public class Query {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  TABLE and COLUMN CONSTANTS
    //******************************************************************************************************************

    // USER
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_USER_NAME = "userName";
    public static final String COLUMN_USER_PASSWORD = "password";

    // CUSTOMER
    public static final String TABLE_CUSTOMER = "customer";
    public static final String COLUMN_CUSTOMER_ID = "customerId";
    public static final String COLUMN_CUSTOMER_NAME = "customerName";
    public static final String COLUMN_CUSTOMER_ACTIVE = "active";

    // ADDRESS
    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ID = "addressId";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_2 = "address2";
    public static final String COLUMN_POSTAL_CODE = "postalCode";
    public static final String COLUMN_PHONE = "phone";

    // CITY
    public static final String TABLE_CITY = "city";
    public static final String COLUMN_CITY_ID = "cityId";
    public static final String COLUMN_CITY_NAME = "city";

    // COUNTRY
    public static final String TABLE_COUNTRY = "country";
    public static final String COLUMN_COUNTRY_ID = "countryId";
    public static final String COLUMN_COUNTRY_NAME = "country";

    // APPOINTMENT
    public static final String TABLE_APPOINTMENT = "appointment";
    public static final String COLUMN_APPOINTMENT_ID = "appointmentId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_END = "end";

    // DATABASE CREATE/EDIT
    public static final String COLUMN_CREATE_DATE = "createDate";
    public static final String COLUMN_CREATED_BY = "createdBy";
    public static final String COLUMN_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    // SORT
    public static final String SORT_BY_WEEK = "week";
    public static final String SORT_BY_MONTH = "month";

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMER QUERIES
    //******************************************************************************************************************

    // Customer CREATE
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

    // Customer READ all
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

    // Customer READ
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

    // Customer UPDATE
    public static final String UPDATE_CUSTOMER =
            "UPDATE " + TABLE_CUSTOMER + " INNER JOIN " + TABLE_ADDRESS + " ON " +
                    TABLE_CUSTOMER + "." + COLUMN_ADDRESS_ID + " = " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID + " SET " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_2 + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_POSTAL_CODE + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_PHONE + " = ?, " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE + " = NOW(), " +
                    TABLE_CUSTOMER + "." + COLUMN_LAST_UPDATE_BY + " = ?, " +
                    TABLE_ADDRESS + "." + COLUMN_LAST_UPDATE + " = NOW(), " +
                    TABLE_ADDRESS + "." + COLUMN_LAST_UPDATE_BY + " = ?" + " WHERE " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID + " = ?";

    // Customer DELETE
    public static final String DELETE_CUSTOMER =
            "DELETE " + TABLE_CUSTOMER + ", " + TABLE_ADDRESS +
                    " FROM " + TABLE_CUSTOMER + " INNER JOIN " + TABLE_ADDRESS + " ON " +
                    TABLE_CUSTOMER + "." + COLUMN_ADDRESS_ID + " = " +
                    TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID + " WHERE " +
                    COLUMN_CUSTOMER_ID + " = ?";

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  APPOINTMENT QUERIES
    //******************************************************************************************************************

    // Appointment CREATE
    public static final String INSERT_APPOINTMENT =
            "INSERT INTO " + TABLE_APPOINTMENT + " SET " +
                    COLUMN_APPOINTMENT_ID + " = ?, " +
                    COLUMN_CUSTOMER_ID + " = ?, " +
                    COLUMN_USER_ID + " = ?, " +
                    COLUMN_TITLE + " = 'not needed', " +
                    COLUMN_DESCRIPTION + " = 'not needed', " +
                    COLUMN_LOCATION + " = ?, " +
                    COLUMN_CONTACT + " = ?, " +
                    COLUMN_TYPE + " = ?, " +
                    COLUMN_URL + " = 'not needed', " +
                    COLUMN_START + " = ?, " +
                    COLUMN_END + " = ?, " +
                    COLUMN_CREATE_DATE + " = NOW(), " +
                    COLUMN_CREATED_BY + " = ?, " +
                    COLUMN_LAST_UPDATE + " = NOW(), " +
                    COLUMN_LAST_UPDATE_BY + " = ?";

    // Appointment READ all sorted
    public static final String GET_SORTED_CUSTOMER_APPOINTMENTS =
            "SELECT * FROM " + TABLE_APPOINTMENT + " WHERE " +
                    COLUMN_CUSTOMER_ID + " = ? AND " + COLUMN_START +
                    " >= ? AND " + COLUMN_START + " <= ?";

    // Appointment READ by Customer
    public static final String GET_CUSTOMER_APPOINTMENTS =
            "SELECT * FROM " + TABLE_APPOINTMENT + " WHERE " +
                    COLUMN_CUSTOMER_ID + " = ?";

    public static final String UPDATE_APPOINTMENT =
            "UPDATE " + TABLE_APPOINTMENT + " SET " +
                    COLUMN_CUSTOMER_ID + " = ?, " +
                    COLUMN_USER_ID + " = ?, " +
                    COLUMN_LOCATION + " = ?, " +
                    COLUMN_CONTACT + " = ?, " +
                    COLUMN_TYPE + " = ?, " +
                    COLUMN_START + " = ?, " +
                    COLUMN_END + " = ?, " +
                    COLUMN_LAST_UPDATE + " = NOW(), " +
                    COLUMN_LAST_UPDATE_BY + " = ? WHERE " +
                    COLUMN_APPOINTMENT_ID + " = ?";

    // Appointment DELETE
    public static final String DELETE_APPOINTMENT =
            "DELETE FROM " + TABLE_APPOINTMENT + " WHERE " +
                    COLUMN_APPOINTMENT_ID + " = ?";

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CITY QUERIES
    //******************************************************************************************************************

    // City Address CREATE
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

    // City CREATE
    public static final String INSERT_CITY =
            "INSERT INTO " + TABLE_CITY + " SET " +
                    COLUMN_CITY_ID + " = ?, " +
                    COLUMN_CITY_NAME + " = ?, " +
                    COLUMN_COUNTRY_ID + " = ?, " +
                    COLUMN_CREATE_DATE + " = NOW(), " +
                    COLUMN_CREATED_BY + " = ?, " +
                    COLUMN_LAST_UPDATE + " = NOW(), " +
                    COLUMN_LAST_UPDATE_BY + " = ?";

    // City READ all
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

    // City READ
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

    // City DELETE
    public static final String DELETE_CITY =
            "DELETE FROM " + TABLE_CITY + " WHERE " +
                    COLUMN_CITY_ID + " = ?";

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  COUNTRY QUERIES
    //******************************************************************************************************************

    // Country CREATE
    public static final String INSERT_COUNTRY =
            "INSERT INTO " + TABLE_COUNTRY + " SET " +
                    COLUMN_COUNTRY_ID + " = ?, " +
                    COLUMN_COUNTRY_NAME + " = ?, " +
                    COLUMN_CREATE_DATE + " = NOW(), " +
                    COLUMN_CREATED_BY + " = ?, " +
                    COLUMN_LAST_UPDATE + " = NOW(), " +
                    COLUMN_LAST_UPDATE_BY + " = ?";

    // Country READ all
    public static final String QUERY_GET_ALL_COUNTRIES =
            "SELECT * FROM " + TABLE_COUNTRY;

    // Country READ
    public static final String QUERY_GET_COUNTRY =
            "SELECT * FROM " + TABLE_COUNTRY + " WHERE " +
                    COLUMN_COUNTRY_NAME + " = \"";

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  REPORT QUERIES
    //******************************************************************************************************************

    // Grab record of each appointment type by month from database
    public static final String QUERY_GET_APPOINTMENT_TYPE_BY_MONTH =
            "SELECT " + COLUMN_TYPE + " as 'Appointment Type', MONTHNAME(" + COLUMN_START + ") as 'Month', " +
                    "COUNT(*) as 'Total' FROM " + TABLE_APPOINTMENT + " GROUP BY " +
                    COLUMN_TYPE + ", MONTH(" + COLUMN_START + ")";

    // Grab record of appointments assigned to each consultant
    public static final String QUERY_GET_CONSULTANT_SCHEDULES =
            "SELECT " + TABLE_APPOINTMENT + "." + COLUMN_CONTACT + ", " +
                    TABLE_APPOINTMENT + "." + COLUMN_TYPE + ", " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + ", " +
                    TABLE_APPOINTMENT + "." + COLUMN_START + ", " +
                    TABLE_APPOINTMENT + "." + COLUMN_END +
                    " FROM " + TABLE_APPOINTMENT + " JOIN " + TABLE_CUSTOMER + " ON " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID + " = " +
                    TABLE_APPOINTMENT + "." + COLUMN_CUSTOMER_ID +
                    " GROUP BY " + TABLE_APPOINTMENT + "." + COLUMN_CONTACT + ", " +
                    "MONTH(" + COLUMN_START + "), " + COLUMN_START;

    // Grab record of number of appointments each customer has
    public static final String QUERY_GET_CUSTOMER_APPOINTMENT_TOTALS =
            "SELECT " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME +
                    " as 'Customer', COUNT(*) as 'Total' FROM " +
                    TABLE_CUSTOMER + " JOIN " + TABLE_APPOINTMENT + " ON " +
                    TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID + " = " +
                    TABLE_APPOINTMENT + "." + COLUMN_CUSTOMER_ID +
                    " GROUP BY " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY QUERIES
    //******************************************************************************************************************

    // Get max Address ID from Address table
    public static final String QUERY_MAX_ID_FROM_ADDRESS =
            "SELECT MAX(" + COLUMN_ADDRESS_ID + ") FROM " + TABLE_ADDRESS;

    // User READ to validate login and track user edits
    public static String buildQueryUser(String userName, String password) {
        return "SELECT * FROM " + TABLE_USER + " WHERE " +
                COLUMN_USER_NAME + " = '" + userName + "' AND " +
                COLUMN_USER_PASSWORD + " = '" + password + "'";
    }
}
