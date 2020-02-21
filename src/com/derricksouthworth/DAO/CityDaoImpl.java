package com.derricksouthworth.DAO;

import com.derricksouthworth.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for City Objects
 *
 * @author derrick.southworth
 */

public class CityDaoImpl {

    private static Connection conn = DBConnect.getInstance().getConn();
    private static String currentUser = DBConnect.getCurrentUser().getUserName();
    private static ObservableList<City> allCities = FXCollections.observableArrayList();
    /**
     * Take results from ResultSet and Construct City
     * @param result
     * @param cityName
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    private static City buildCity(ResultSet result, String cityName) throws SQLException, ParseException {
        int cityID = result.getInt(Query.COLUMN_CITY_ID);
        String country = result.getString(Query.COLUMN_COUNTRY_NAME);
        Calendar createDate = stringToCalendar(result.getString(Query.COLUMN_CREATE_DATE));
        String createdBy = result.getString(Query.COLUMN_CREATED_BY);
        Calendar lastUpdate = stringToCalendar(result.getString(Query.COLUMN_LAST_UPDATE));
        String lastUpdateBy = result.getString(Query.COLUMN_LAST_UPDATE_BY);
        City city = new City(cityID, cityName, country, createDate, createdBy, lastUpdate, lastUpdateBy);
        return city;
    }

    /**
     * Handles READ task for CRUD on all City Objects
     * @return
     * @throws SQLException
     */
    public static ObservableList<City> getAllCities() throws SQLException {
        // clear list to avoid adding cities more than once
        allCities.clear();
        Statement statement = null;
        String sqlStatement = Query.QUERY_GET_ALL_CITIES;
        ResultSet result = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.createStatement();
            result = statement.executeQuery(sqlStatement);
            while(result.next()) {
                String cityName = result.getString(Query.COLUMN_CITY_NAME);
                allCities.add(buildCity(result, cityName));
            }

            return allCities;

        } catch (SQLException | ParseException e) {
            System.out.println("Unable to retrieve cities: " + e.getMessage());
            return null;

            // close all database resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
            conn.setAutoCommit(true);
        }
    }

    /**
     * Handles READ task for CRUD on a desired City Object
     * @param cityName
     * @throws SQLException
     * @return
     */
    public static City getCity(String cityName) throws SQLException {
        Statement statement = null;
        String sqlStatement = String.format("%s%s\"", Query.QUERY_GET_CITY, cityName);
        ResultSet result = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.createStatement();
            result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                return buildCity(result, cityName);
            }

        } catch (ParseException | SQLException e) {
            e.printStackTrace();

            // close all database resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
            conn.setAutoCommit(true);
        }

        return null;
    }

    /**
     * Handles DELETE task for CRUD for given City Object
     * @param city
     * @throws SQLException
     */
    public static void deleteCity(City city) throws SQLException {
        PreparedStatement statement = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.prepareStatement(Query.DELETE_CITY);
            statement.setInt(1, city.getCityID());

            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("City record was deleted successfully.");
            }

        } catch (SQLException e) {
            System.out.println("City Record Delete failed: " + e.getMessage());

            // close all database resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
        }
    }

    /**
     * Handles CREATE task for given City Object
     * @param city
     * @param countryID
     * @throws SQLException
     */
    public static void addCity(City city, int countryID) throws SQLException {
        PreparedStatement statement = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.prepareStatement(Query.INSERT_CITY);
            statement.setInt(1, city.getCityID());
            statement.setString(2, city.getCityName());
            statement.setInt(3, countryID);
            statement.setString(4, currentUser);
            statement.setString(5, currentUser);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to create city: " + e.getMessage());

            // close all database resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
        }
    }
}
