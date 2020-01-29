package com.derricksouthworth.DAO;

import com.derricksouthworth.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.DAO.CityDaoImpl.getCity;
import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for Customer Objects
 *
 * @author derrick.southworth
 */

public class CustomerDaoImpl {
    /**
     * Take results from ResultSet and Construct Customer
     * @param result
     * @param customerName
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    private static Customer buildCustomer(ResultSet result, String customerName) throws SQLException, ParseException {
        int customerID = result.getInt(Query.COLUMN_CUSTOMER_ID);
        String address = result.getString(Query.COLUMN_ADDRESS);
        String address2 = result.getString(Query.COLUMN_ADDRESS_2);
        String city = result.getString(Query.COLUMN_CITY_NAME);
        String postalCode = result.getString(Query.COLUMN_POSTAL_CODE);
        String phone = result.getString(Query.COLUMN_PHONE);
        Calendar createDate = stringToCalendar(result.getString(Query.COLUMN_CREATE_DATE));
        String createdBy = result.getString(Query.COLUMN_CREATED_BY);
        Calendar lastUpdate = stringToCalendar(result.getString(Query.COLUMN_LAST_UPDATE));
        String lastUpdateBy = result.getString(Query.COLUMN_LAST_UPDATE_BY);
        Customer customerResult = new Customer(customerID, customerName, address, address2, city, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy);
        return customerResult;
    }

    /**
     * Handles READ task for all Customer Objects
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, ParseException {
        DBConnect.makeConnection();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = Query.QUERY_GET_ALL_CUSTOMERS;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            String customerName = result.getString(Query.COLUMN_CUSTOMER_NAME);
            allCustomers.add(buildCustomer(result, customerName));
        }
        DBConnect.closeConnection();
        return allCustomers;
    }

    /**
     * Handles READ task for desired Customer Object
     * @param customerName
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public static Customer getCustomer(String customerName) throws SQLException, ParseException {
        DBConnect.makeConnection();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        StringBuilder sb = new StringBuilder(Query.QUERY_GET_CUSTOMER);
        sb.append(customerName);
        sb.append("\"");
        String sqlStatement = sb.toString();
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            return buildCustomer(result, customerName);
        }
        DBConnect.closeConnection();
        return null;
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

    /**
     * Handles CREATE task for given Customer Object
     * @param customer
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void addCustomer(Customer customer, int cityID) throws SQLException, ClassNotFoundException, ParseException {
        DBConnect.makeConnection();
        String sqlStatementAddress = "INSERT INTO address " +
                "SET addressId = '" + getAllCustomers().size() + 1 + "', " +
                "address = '" + customer.getAddress() + "', " +
                "address2 = '" + customer.getAddress2() + "', " +
                "cityId = '" + cityID + "', " +
                "postalCode = '" + customer.getPostalCode() + "', " +
                "phone = '" + customer.getPhone() + "', " +
                "createDate = NOW(), " +
                "createdBy = 'user', " +
                "lastUpdate = NOW(), " +
                "lastUpdateBy = 'user'";
        Query.makeQuery(sqlStatementAddress);
        String sqlStatementCustomer = "INSERT INTO customer " +
                "SET customerId = '" + customer.getCustomerID() + "', " +
                "customerName = '" + customer.getCustomerName() + "', " +
                "addressId = '" + getAllCustomers().size() + 1 + "', " +
                "active = 1, " +
                "createDate = NOW(), " +
                "createdBy = 'user', " +
                "lastUpdate = NOW(), " +
                "lastUpdateBy = 'user'";
        Query.makeQuery(sqlStatementCustomer);
        DBConnect.closeConnection();
    }
}
