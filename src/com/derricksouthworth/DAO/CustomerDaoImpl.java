package com.derricksouthworth.DAO;

import com.derricksouthworth.model.Appointment;
import com.derricksouthworth.model.Customer;
import com.derricksouthworth.utilities.TimeFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;

import static com.derricksouthworth.utilities.TimeFiles.*;

/**
 * Implement CRUD for Customer Objects
 *
 * @author derrick.southworth
 */

public class CustomerDaoImpl {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  GLOBAL VARIABLES
    //******************************************************************************************************************

    private static Connection conn = DBConnect.getInstance().getConn();
    private static String currentUser = DBConnect.getCurrentUser().getUserName();
    private static int currentUserID = DBConnect.getCurrentUser().getUserID();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMER METHODS
    //******************************************************************************************************************

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
     * Handles READ task for all Customer Objects
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        Statement statement = null;
        String sqlStatement = Query.QUERY_GET_ALL_CUSTOMERS;
        ResultSet result = null;

        // clear contents of list to avoid adding a customer more than once
        allCustomers.clear();
        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.createStatement();

            result = statement.executeQuery(sqlStatement);
            while(result.next()) {
                String customerName = result.getString(Query.COLUMN_CUSTOMER_NAME);
                allCustomers.add(buildCustomer(result, customerName));
            }
            return allCustomers;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
            // close all jdbc resources
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

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  APPOINTMENT METHODS
    //******************************************************************************************************************

    /**
     * Handles CREATE task for appointments associated with give customer
     * @param appointment
     * @param customerID
     * @throws SQLException
     */
    public static void addAppointment(Appointment appointment, int customerID) throws SQLException {
        PreparedStatement addAppointment = null;
        String sqlStatement = Query.INSERT_APPOINTMENT;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            addAppointment = conn.prepareStatement(sqlStatement);
            addAppointment.setInt(1, appointment.getAppointmentID());
            addAppointment.setInt(2, customerID);
            addAppointment.setInt(3, currentUserID);
            addAppointment.setString(4, appointment.getLocation());
            addAppointment.setString(5, appointment.getContact());
            addAppointment.setString(6, appointment.getType());
            addAppointment.setString(7, appointment.getStart());
            addAppointment.setString(8, appointment.getEnd());
            addAppointment.setString(9, currentUser);
            addAppointment.setString(10, currentUser);

            addAppointment.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // close all jdbc resources
        } finally {
            if (addAppointment != null) {
                addAppointment.close();
            }
            conn.setAutoCommit(true);
        }
    }

    /**
     * Handles READ task for appointments associated with give customer
     * @param customer
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getCustomerAppointments(Customer customer, String sortBy) throws SQLException {
        PreparedStatement getAppointments = null;
        String sqlStatement = Query.GET_CUSTOMER_APPOINTMENTS;
        String sortedStatement = Query.GET_SORTED_CUSTOMER_APPOINTMENTS;
        ResultSet result = null;
        LocalDate appointmentStart = LocalDate.now();
        LocalDate appointmentEnd = null;
        if(sortBy == Query.SORT_BY_WEEK) appointmentEnd = LocalDate.now().plusWeeks(1);
        if(sortBy == Query.SORT_BY_MONTH) appointmentEnd = LocalDate.now().plusMonths(1);

        // clear contents of list to avoid adding appointments more than once
        Customer.getCustomerAppointments().clear();

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            if(appointmentEnd != null) {
                getAppointments = conn.prepareStatement(sortedStatement);
                getAppointments.setInt(1, customer.getCustomerID());
                getAppointments.setDate(2, Date.valueOf(appointmentStart));
                getAppointments.setDate(3, Date.valueOf(appointmentEnd));
            } else {
                getAppointments = conn.prepareStatement(sqlStatement);
                getAppointments.setInt(1, customer.getCustomerID());
            }

            result = getAppointments.executeQuery();
            while(result.next()) {
                int appointmentID = result.getInt(Query.COLUMN_APPOINTMENT_ID);
                int userID = result.getInt(Query.COLUMN_USER_ID);
                String location = result.getString(Query.COLUMN_LOCATION);
                String contact = result.getString(Query.COLUMN_CONTACT);
                String type = result.getString(Query.COLUMN_TYPE);
                String start = TimeFiles.timeToLocal(result.getString(Query.COLUMN_START));
                String end = TimeFiles.timeToLocal(result.getString(Query.COLUMN_END));
                Calendar createDate = stringToCalendar(result.getString(Query.COLUMN_CREATE_DATE));
                String createdBy = result.getString(Query.COLUMN_CREATED_BY);
                Calendar lastUpdate = stringToCalendar(result.getString(Query.COLUMN_LAST_UPDATE));
                String lastUpdateBy = result.getString(Query.COLUMN_LAST_UPDATE_BY);
                Appointment appointmentResult = new Appointment(appointmentID, customer.getCustomerName(), userID,
                        location, contact, type, start, end, createDate, createdBy, lastUpdate, lastUpdateBy);
                Customer.addCustomerAppointment(appointmentResult);
            }
            return Customer.getCustomerAppointments();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
            // close all jdbc resources
        } finally {
            if (getAppointments != null) {
                getAppointments.close();
            }
            if (result != null) {
                result.close();
            }
            conn.setAutoCommit(true);
        }
    }

    /**
     * Handles DELETE task for given Appointment Object
     * @param appointmentID
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {
        PreparedStatement deleteAppointment = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            deleteAppointment = conn.prepareStatement(Query.DELETE_APPOINTMENT);
            deleteAppointment.setInt(1, appointmentID);

            int rowsDeleted = deleteAppointment.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("Appointment was deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (deleteAppointment != null) {
                deleteAppointment.close();
            }
            conn.setAutoCommit(true);
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMER AND APPOINTMENT REPORT METHODS
    //******************************************************************************************************************

    public static String getAppointmentTypeByMonth() throws SQLException {
        PreparedStatement getAppointments = null;
        String sqlStatement = Query.QUERY_GET_APPOINTMENT_TYPE_BY_MONTH;
        StringBuilder appointmentTypeByMonth = new StringBuilder();
        ResultSet result = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            getAppointments = conn.prepareStatement(sqlStatement);
            result = getAppointments.executeQuery();
            while (result.next()) {
                appointmentTypeByMonth.append(String.format("%1$-55s %2$-55s %3$d \n",
                        result.getString("Month"),
                        result.getString("Appointment Type"),
                        result.getInt("Total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (getAppointments != null) {
                getAppointments.close();
            }
            if (result != null) {
                result.close();
            }
            conn.setAutoCommit(true);
        }
        return appointmentTypeByMonth.toString();
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY METHODS
    //******************************************************************************************************************

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
}
