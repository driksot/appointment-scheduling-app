package com.derricksouthworth.DAO;

import com.derricksouthworth.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for Country Objects
 *
 * @author derrick.southworth
 */

public class CountryDaoImpl {

    private static Connection conn = DBConnect.getInstance().getConn();
    private static String currentUser = DBConnect.getCurrentUser().getUserName();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    private static Country buildCountry(ResultSet result, String countryName) throws SQLException, ParseException {
        int countryID = result.getInt(Query.COLUMN_COUNTRY_ID);
        Calendar createDate = stringToCalendar(result.getString(Query.COLUMN_CREATE_DATE));
        String createdBy = result.getString(Query.COLUMN_CREATED_BY);
        Calendar lastUpdate = stringToCalendar(result.getString(Query.COLUMN_LAST_UPDATE));
        String lastUpdateBy = result.getString(Query.COLUMN_LAST_UPDATE_BY);
        Country country = new Country(countryID, countryName, createDate, createdBy, lastUpdate, lastUpdateBy);
        return country;
    }

    public static ObservableList<Country> getAllCountries() {
        allCountries.clear();
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = Query.QUERY_GET_ALL_COUNTRIES;
            ResultSet result = statement.executeQuery(sqlStatement);
            while(result.next()) {
                String countryName = result.getString(Query.COLUMN_COUNTRY_NAME);
                allCountries.add(buildCountry(result, countryName));
            }
            return allCountries;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Country getCountry(String countryName) {
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = String.format("%s%s\"", Query.QUERY_GET_COUNTRY, countryName);
            ResultSet result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                return buildCountry(result, countryName);
            }
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateCountry() {

    }

    public static void deleteCountry() {

    }

    public static void addCountry(Country country) throws SQLException {
        PreparedStatement statement = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.prepareStatement(Query.INSERT_COUNTRY);
            statement.setInt(1, country.getCountryID());
            statement.setString(2, country.getCountryName());
            statement.setString(3, currentUser);
            statement.setString(4, currentUser);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to create country: " + e.getMessage());

            // close all database resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
        }
    }
}
