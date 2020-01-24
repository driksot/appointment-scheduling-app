package com.derricksouthworth.DAO;

import com.derricksouthworth.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for Country Objects
 *
 * @author derrick.southworth
 */

public class CountryDaoImpl {
    public static ObservableList<Country> getAllCountries() throws SQLException, ClassNotFoundException {
        DBConnect.makeConnection();
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        DBConnect.closeConnection();
        return allCountries;
    }

    public static Country getCountry(String countryName) throws SQLException, ClassNotFoundException, ParseException {
        DBConnect.makeConnection();
        String sqlStatement = "SELECT * FROM country WHERE country = '" + countryName + "'";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int countryID = result.getInt("countryId");
            Calendar createDate = stringToCalendar(result.getString("createDate"));
            String createdBy = result.getString("createdBy");
            Calendar lastUpdate = stringToCalendar(result.getString("lastUpdate"));
            String lastUpdateBy = result.getString("lastUpdateBy");
            Country countryResult = new Country(countryID, countryName, createDate, createdBy, lastUpdate, lastUpdateBy);
            return countryResult;
        }
        DBConnect.closeConnection();
        return null;
    }

    public static void updateCountry() {

    }

    public static void deleteCountry() {

    }

    public static void addCountry() {

    }
}
