package com.derricksouthworth.DAO;

import com.derricksouthworth.model.City;
import com.derricksouthworth.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.DAO.CountryDaoImpl.getCountry;
import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for City Objects
 *
 * @author derrick.southworth
 */

public class CityDaoImpl {
    /**
     * Handles READ task for CRUD on all City Objects
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    public static ObservableList<City> getAllCities() throws SQLException, ClassNotFoundException, ParseException {
        DBConnect.makeConnection();
        ObservableList<City> allCities = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM city";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int cityID = result.getInt("cityId");
            String cityName = result.getString("cityName");
            Country country = getCountry(result.getString("country"));
            Calendar createDate = stringToCalendar(result.getString("createDate"));
            String createdBy = result.getString("createdBy");
            Calendar lastUpdate = stringToCalendar(result.getString("lastUpdate"));
            String lastUpdateBy = result.getString("lastUpdateBy");
            City cityResult = new City(cityID, cityName, country, createDate, createdBy, lastUpdate, lastUpdateBy);
            allCities.add(cityResult);
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
    public static City getCity(String cityName) throws SQLException, ClassNotFoundException, ParseException {
        DBConnect.makeConnection();
        String sqlStatement = "SELECT * FROM city WHERE cityName = '" + cityName + "'";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int cityID = result.getInt("cityId");
            Country country = getCountry(result.getString("country"));
            Calendar createDate = stringToCalendar(result.getString("createDate"));
            String createdBy = result.getString("createdBy");
            Calendar lastUpdate = stringToCalendar(result.getString("lastUpdate"));
            String lastUpdateBy = result.getString("lastUpdateBy");
            City cityResult = new City(cityID, cityName, country, createDate, createdBy, lastUpdate, lastUpdateBy);
            return cityResult;
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

    public static void addCity() {

    }
}
