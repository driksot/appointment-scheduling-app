package com.derricksouthworth.DAO;

import com.derricksouthworth.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;

import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 * Implement CRUD for Customer Objects
 *
 * @author derrick.southworth
 */

public class CustomerDaoImpl {

    private static Connection conn = DBConnect.getInstance().getConn();
    private static String currentUser = UserDaoImpl.getCurrentUser().getUserName();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

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
        Customer customer = new Customer(customerID, customerName, address, address2, city, postalCode, phone,
                createDate, createdBy, lastUpdate, lastUpdateBy);
        return customer;
    }

    /**
     * Handles READ task for all Customer Objects
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {
        allCustomers.clear();
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = Query.QUERY_GET_ALL_CUSTOMERS;
            ResultSet result = statement.executeQuery(sqlStatement);
            while(result.next()) {
            String customerName = result.getString(Query.COLUMN_CUSTOMER_NAME);
            allCustomers.add(buildCustomer(result, customerName));
            }
            result.close();
            statement.close();
            return allCustomers;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handles READ task for desired Customer Object
     * @param customerName
     * @return
     */
    public static Customer getCustomer(String customerName) {
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = String.format("%s%s\"", Query.QUERY_GET_CUSTOMER, customerName);
            ResultSet result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                return buildCustomer(result, customerName);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Handles UPDATE task for given Customer Object
     * @param customer
     */
    public static void updateCustomer(Customer customer) {
        try {
            PreparedStatement statement = conn.prepareStatement(Query.UPDATE_CUSTOMER);
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getAddress2());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhone());
            statement.setString(6, currentUser);
            statement.setString(7, currentUser);
            statement.setInt(8, customer.getCustomerID());

            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Customer record was updated successfully.");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Customer Update failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles DELETE task for given Customer Object
     * @param customerID
     */
    public static void deleteCustomer(int customerID) {
        try {
            PreparedStatement statement = conn.prepareStatement(Query.DELETE_CUSTOMER);
            statement.setInt(1, customerID);

            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("Customer record was deleted successfully.");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Customer Record Delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles CREATE task for given Customer Object
     * @param customer
     */
    public static void addCustomer(Customer customer, int cityID) {
        int maxAddressID = getMaxAddressID();
        try {
            PreparedStatement addressStatement = conn.prepareStatement(Query.INSERT_ADDRESS);
            addressStatement.setInt(1, maxAddressID);
            addressStatement.setString(2, customer.getAddress());
            addressStatement.setString(3, customer.getAddress2());
            addressStatement.setInt(4, cityID);
            addressStatement.setString(5, customer.getPostalCode());
            addressStatement.setString(6, customer.getPhone());
            addressStatement.setString(7, currentUser);
            addressStatement.setString(8, currentUser);
            addressStatement.executeUpdate();

            PreparedStatement customerStatement = conn.prepareStatement(Query.INSERT_CUSTOMER);
            customerStatement.setInt(1, customer.getCustomerID());
            customerStatement.setString(2, customer.getCustomerName());
            customerStatement.setInt(3, maxAddressID);
            customerStatement.setString(4, currentUser);
            customerStatement.setString(5, currentUser);
            customerStatement.executeUpdate();

            addressStatement.close();
            customerStatement.close();
        } catch (SQLException e) {
            System.out.println("Customer Addition failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Query to get new max ID for Address Table
     * @return
     */
    private static int getMaxAddressID() {
        int maxID = 0;
        String sqlStatement = Query.QUERY_MAX_ID_FROM_ADDRESS;
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                maxID = result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Unable to get max address ID: " + e.getMessage());
            e.printStackTrace();
        }
        return maxID + 1;
    }
}
