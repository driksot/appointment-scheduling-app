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
    private static String currentUser = UserDaoImpl.getCurrentUser().getUserName();
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
     */
    public static ObservableList<City> getAllCities() {
        allCities.clear();
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = Query.QUERY_GET_ALL_CITIES;
            ResultSet result = statement.executeQuery(sqlStatement);
            while(result.next()) {
                String cityName = result.getString(Query.COLUMN_CITY_NAME);
                allCities.add(buildCity(result, cityName));
            }
            result.close();
            statement.close();
            return allCities;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handles READ task for CRUD on a desired City Object
     * @param cityName
     * @return
     */
    public static City getCity(String cityName) {
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = String.format("%s%s\"", Query.QUERY_GET_CITY, cityName);
            ResultSet result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                return buildCity(result, cityName);
            }
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Handles DELETE task for CRUD for given City Object
     * @param city
     */
    public static void deleteCity(City city) {
        try {
            PreparedStatement statement = conn.prepareStatement(Query.DELETE_CITY);
            statement.setInt(1, city.getCityID());

            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("City record was deleted successfully.");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("City Record Delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles CREATE task for given City Object
     * @param city
     * @param countryID
     */
    public static void addCity(City city, int countryID) {
        try {
            PreparedStatement statement = conn.prepareStatement(Query.INSERT_CITY);
            statement.setInt(1, city.getCityID());
            statement.setString(2, city.getCityName());
            statement.setInt(3, countryID);
            statement.setString(4, currentUser);
            statement.setString(5, currentUser);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to create city: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
