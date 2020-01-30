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
     * @throws SQLException
     * @throws ParseException
     */
    public static Customer getCustomer(String customerName) throws SQLException, ParseException {
        DBConnect.getInstance().makeConnection();
        StringBuilder sb = new StringBuilder(Query.QUERY_GET_CUSTOMER);
        sb.append(customerName);
        sb.append("\"");
        String sqlStatement = sb.toString();
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()) {
            return buildCustomer(result, customerName);
        }
        DBConnect.getInstance().closeConnection();
        return null;
    }

    /**
     * Handles UPDATE task for given Customer Object
     * @param customer
     */
    public static void updateCustomer(Customer customer, int addressID) {
        try {
            PreparedStatement ps = conn.prepareStatement(Query.UPDATE_CUSTOMER);
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, addressID);
            ps.setString(3, currentUser);
            ps.setInt(4, customer.getCustomerID());
            ps.executeUpdate();
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
            PreparedStatement ps = conn.prepareStatement(Query.DELETE_CUSTOMER);
            ps.setInt(1, customerID);

            int rowsDeleted = ps.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("A customer was deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Customer Delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles CREATE task for given Customer Object
     * @param customer
     */
    public static void addCustomer(Customer customer, int cityID) {
        DBConnect.getInstance().makeConnection();
        StringBuilder sbAddress = new StringBuilder(Query.buildQueryAddAddress(getAllCustomers().size() + 1,
                customer.getAddress(), customer.getAddress2(), cityID, customer.getPostalCode(), customer.getPhone()));
        sbAddress.append(Query.COLUMN_CREATE_DATE + " = NOW(), ");
        sbAddress.append(Query.COLUMN_CREATED_BY + " = '" + currentUser + "', ");
        sbAddress.append(Query.COLUMN_LAST_UPDATE + " = NOW(), ");
        sbAddress.append(Query.COLUMN_LAST_UPDATE_BY + " = '" + currentUser + "'");
        Query.makeQuery(sbAddress.toString());
        StringBuilder sbCustomer = new StringBuilder(Query.buildQueryAddCustomer(customer.getCustomerID(),
                customer.getCustomerName(), getAllCustomers().size() + 1));
        sbCustomer.append(Query.COLUMN_CREATE_DATE + " = NOW(), ");
        sbCustomer.append(Query.COLUMN_CREATED_BY + " = '" + currentUser + "', ");
        sbCustomer.append(Query.COLUMN_LAST_UPDATE + " = NOW(), ");
        sbCustomer.append(Query.COLUMN_LAST_UPDATE_BY + " = '" + currentUser + "'");
        Query.makeQuery(sbCustomer.toString());
        DBConnect.getInstance().closeConnection();
    }
}
