package com.derricksouthworth.DAO;

import com.derricksouthworth.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for City Objects
 *
 * @author derrick.southworth
 */

public class CityDaoImpl {
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
        City cityResult = new City(cityID, cityName, country, createDate, createdBy, lastUpdate, lastUpdateBy);
        return cityResult;
    }

    /**
     * Handles READ task for CRUD on all City Objects
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    public static ObservableList<City> getAllCities() throws SQLException, ParseException {
        DBConnect.makeConnection();
        ObservableList<City> allCities = FXCollections.observableArrayList();
        String sqlStatement = Query.QUERY_GET_ALL_CITIES;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            String cityName = result.getString(Query.COLUMN_CITY_NAME);
            allCities.add(buildCity(result, cityName));
        }
        DBConnect.closeConnection();
        return allCities;
    }

    /**
     * Handles READ task for CRUD on a desired City Object
     * @param cityName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    public static City getCity(String cityName) throws SQLException, ParseException {
        DBConnect.makeConnection();
        City cityResult;
        StringBuilder sb = new StringBuilder(Query.QUERY_GET_CITY);
        sb.append(cityName);
        sb.append("\"");
        String sqlStatement = sb.toString();
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            return buildCity(result, cityName);
        }
        DBConnect.closeConnection();
        return null;
    }

    public static void updateCity() {

    }

    /**
     * Handles DELETE task for CRUD for given City Object
     * @param city
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteCity(City city) throws SQLException, ClassNotFoundException {
        DBConnect.makeConnection();
        String sqlStatement = "DELETE FROM city WHERE cityId = '" + city.getCityID() + "'";
        Query.makeQuery(sqlStatement);
        DBConnect.closeConnection();
    }

    public static void addCity(City city, int countryID) throws SQLException, ClassNotFoundException, ParseException {
        DBConnect.makeConnection();
        String sqlStatement = "INSERT INTO city " +
                "SET cityId = '" + getAllCities().size() + 1 + "', " +
                "city = '" + city.getCityName() + "', " +
                "countryId = " + countryID + "', " +
                "createDate = NOW(), " +
                "createdBy = user, " +
                "lastUpdate = NOW(), " +
                "lastUpdateBy = user";
        Query.makeQuery(sqlStatement);
        DBConnect.closeConnection();
    }
}
