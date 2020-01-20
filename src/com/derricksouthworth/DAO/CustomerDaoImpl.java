package com.derricksouthworth.DAO;

import com.derricksouthworth.model.City;
import com.derricksouthworth.model.Customer;
import com.derricksouthworth.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.DAO.CityDaoImpl.getCity;
import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;
import static com.derricksouthworth.DAO.DBConnect.conn;

/**
 * Implement CRUD for Customer Objects
 *
 * @author derrick.southworth
 */

public class CustomerDaoImpl {
    /**
     * Handles READ task for all Customer Objects
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException, ParseException {
        DBConnect.makeConnection();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM customer";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int customerID = result.getInt("customerID");
            String customerName = result.getString("customerName");
            String address = result.getString("address");
            String address2 = result.getString("address2");
            City city = getCity(result.getString("city"));
            String postalCode = result.getString("postalCode");
            String phone = result.getString("phone");
            Calendar createDate = stringToCalendar(result.getString("createDate"));
            String createdBy = result.getString("createdBy");
            Calendar lastUpdate = stringToCalendar(result.getString("lastUpdate"));
            String lastUpdateBy = result.getString("lastUpdateBy");
            Customer customerResult = new Customer(customerID, customerName, address, address2, city, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy);
            allCustomers.add(customerResult);
        }
        DBConnect.closeConnection();
        return allCustomers;
    }

    /**
     * Handles UPDATE task for given Customer Object
     * @param customer
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        DBConnect.makeConnection();
        String sqlStatement = "UPDATE customer " +
                "SET customerName = " + customer.getCustomerName() + ", "  +
                "address = " + customer.getAddress() + ", " +
                "address2 = " + customer.getAddress2() + ", " +
                "city = " + customer.getCity().toString() + ", " +
                "postalCode = " + customer.getPostalCode() + ", " +
                "phone = " + customer.getPhone() + ", " +
                "createDate = " + customer.getCreateDate() + ", " +
                "createdBy = " + customer.getCreatedBy() + ", " +
                "lastUpdate = NOW(), " +
                "lastUpdateBy = test" + //TODO set lastUpdateBy to current user
                "WHERE customerId = " + customer.getCustomerID();
        Query.makeQuery(sqlStatement);
        DBConnect.closeConnection();
    }

    /**
     * Handles DELETE task for given Customer Object
     * @param customer
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        DBConnect.makeConnection();
        String sqlStatement = "DELETE FROM customer WHERE customerId = '" + customer.getCustomerID() + "'";
        Query.makeQuery(sqlStatement);
        DBConnect.closeConnection();
    }

    public static void addCustomer() {

    }
}
