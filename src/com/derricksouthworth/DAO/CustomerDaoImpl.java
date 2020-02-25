package com.derricksouthworth.DAO;

import com.derricksouthworth.model.Appointment;
import com.derricksouthworth.model.Customer;
import com.derricksouthworth.utilities.TimeFiles;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

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
    private static ObservableList<Appointment> appointmentsIn15 = FXCollections.observableArrayList();
    private static ObservableList<ObservableList> report;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMER METHODS
    //******************************************************************************************************************

    /**
     * Handles CREATE task for given Customer Object
     * @param customer
     * @param cityID
     * @throws SQLException
     */
    public static void addCustomer(Customer customer, int cityID) throws SQLException {
        int maxAddressID = getMaxAddressID();
        PreparedStatement addressStatement = null;
        PreparedStatement customerStatement = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            addressStatement = conn.prepareStatement(Query.INSERT_ADDRESS);
            addressStatement.setInt(1, maxAddressID);
            addressStatement.setString(2, customer.getAddress());
            addressStatement.setString(3, customer.getAddress2());
            addressStatement.setInt(4, cityID);
            addressStatement.setString(5, customer.getPostalCode());
            addressStatement.setString(6, customer.getPhone());
            addressStatement.setString(7, currentUser);
            addressStatement.setString(8, currentUser);
            addressStatement.executeUpdate();

            customerStatement = conn.prepareStatement(Query.INSERT_CUSTOMER);
            customerStatement.setInt(1, customer.getCustomerID());
            customerStatement.setString(2, customer.getCustomerName());
            customerStatement.setInt(3, maxAddressID);
            customerStatement.setString(4, currentUser);
            customerStatement.setString(5, currentUser);
            customerStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Customer Addition failed: " + e.getMessage());

            // close database resources
        } finally {
            if (addressStatement != null) {
                addressStatement.close();
            }
            if (customerStatement != null) {
                customerStatement.close();
            }
            conn.setAutoCommit(true);
        }
    }


    /**
     * Handles READ task for all Customer Objects
     * @return
     * @throws SQLException
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
     * @throws SQLException
     */
    public static Customer getCustomer(String customerName) throws SQLException {
        Statement statement = null;
        String sqlStatement = String.format("%s%s\"", Query.QUERY_GET_CUSTOMER, customerName);
        ResultSet result = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.createStatement();
            result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                return buildCustomer(result, customerName);
            }

        } catch (SQLException | ParseException e) {
            System.out.println("Unable to retrieve customer record: " + e.getMessage());

            // close jdbc resources
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
     * Handles UPDATE task for given Customer Object
     * @param customer
     * @throws SQLException
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        PreparedStatement statement = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.prepareStatement(Query.UPDATE_CUSTOMER);
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

        } catch (SQLException e) {
            System.out.println("Customer Update failed: " + e.getMessage());

            // close jdbc resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
        }
    }

    /**
     * Handles DELETE task for given Customer Object
     * @param customerID
     */
    public static void deleteCustomer(int customerID) throws SQLException {
        PreparedStatement statement = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.prepareStatement(Query.DELETE_CUSTOMER);
            statement.setInt(1, customerID);

            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("Customer record was deleted successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Customer Record Delete failed: " + e.getMessage());

            // close jdbc resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
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
            System.out.println("Unable to add appointment: " + e.getMessage());

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
     * @param sortBy
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

        // constrain query to the coming week or month
        if(sortBy == Query.SORT_BY_WEEK) appointmentEnd = LocalDate.now().plusWeeks(1);
        if(sortBy == Query.SORT_BY_MONTH) appointmentEnd = LocalDate.now().plusMonths(1);

        // clear contents of list to avoid adding appointments more than once
        Customer.getCustomerAppointments().clear();

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            // apply query constraints if given
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
            System.out.println("Unable to retrieve appointments: " + e.getMessage());
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
     * Get a list of appointments scheduled to start in the next 15 minutes
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsWithinFifteenMinutes() throws SQLException {
        Statement statement = null;
        String sqlStatement = Query.GET_APPOINTMENTS_WITHIN_FIFTEEN_MINUTES;
        ResultSet result = null;

        appointmentsIn15.clear();

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.createStatement();
            result = statement.executeQuery(sqlStatement);

            while (result.next()) {
                int appointmentID = result.getInt(Query.COLUMN_APPOINTMENT_ID);
                String customerName = result.getString(Query.COLUMN_CUSTOMER_NAME);
                int userID = result.getInt(Query.COLUMN_USER_ID);
                String location = result.getString(Query.COLUMN_LOCATION);
                String contact = result.getString(Query.COLUMN_CONTACT);
                String type = result.getString(Query.COLUMN_TYPE);
                String start = result.getString(Query.COLUMN_START);
                String end = result.getString(Query.COLUMN_END);
                Calendar createDate = stringToCalendar(result.getString(Query.COLUMN_CREATE_DATE));
                String createdBy = result.getString(Query.COLUMN_CREATED_BY);
                Calendar lastUpdate = stringToCalendar(result.getString(Query.COLUMN_LAST_UPDATE));
                String lastUpdateBy = result.getString(Query.COLUMN_LAST_UPDATE_BY);

                Appointment appointment = new Appointment(appointmentID, customerName, userID, location,
                        contact, type, start, end, createDate, createdBy, lastUpdate, lastUpdateBy);

                appointmentsIn15.add(appointment);
            }

            return appointmentsIn15;

        } catch (SQLException | ParseException e) {
            System.out.println("Unable to get appointments coming up in 15 minutes: " + e.getMessage());

            // close jdbc resources and commit transaction
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
     * Handle UPDATE task for given customer appointment
     * @param appointment
     * @param customerID
     * @throws SQLException
     */
    public static void updateAppointment(Appointment appointment, int customerID) throws SQLException {
        PreparedStatement updateAppointment = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            updateAppointment = conn.prepareStatement(Query.UPDATE_APPOINTMENT);
            updateAppointment.setInt(1, customerID);
            updateAppointment.setInt(2, currentUserID);
            updateAppointment.setString(3, appointment.getLocation());
            updateAppointment.setString(4, appointment.getContact());
            updateAppointment.setString(5, appointment.getType());
            updateAppointment.setString(6, appointment.getStart());
            updateAppointment.setString(7, appointment.getEnd());
            updateAppointment.setString(8, currentUser);
            updateAppointment.setInt(9, appointment.getAppointmentID());

            int rowsUpdated = updateAppointment.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Appointment was updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Appointment Update failed: " + e.getMessage());

            // close jdbc resources
        } finally {
            if (updateAppointment != null) {
                updateAppointment.close();
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
            System.out.println("Unable to delete appointment");

            // close jdbc resources
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

    /**
     * Query database for given fields and populate table view
     * @param tblReports
     * @param query
     * @throws SQLException
     */
    public static void getReportData(TableView tblReports, String query) throws SQLException {
        PreparedStatement getData = null;
        String sqlStatement = query;
        ResultSet result = null;
        report = FXCollections.observableArrayList();

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            getData = conn.prepareStatement(sqlStatement);
            result = getData.executeQuery();

            // dynamically add table columns
            for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(result.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tblReports.getColumns().addAll(col);
            }

            // populate table rows
            while (result.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    row.add(result.getString(i));
                }
                report.add(row);
            }

            tblReports.setItems(report);

        } catch (SQLException e) {
            System.out.println("Unable to generate report: " + e.getMessage());

            // close jdbc resources
        } finally {
            if (getData != null) {
                getData.close();
            }
            if (result != null) {
                result.close();
            }
            conn.setAutoCommit(true);
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY METHODS
    //******************************************************************************************************************

    /**
     * Query to get new max ID for Address Table
     * @return
     * @throws SQLException
     */
    private static int getMaxAddressID() throws SQLException {
        int maxID = 0;
        Statement statement = null;
        String sqlStatement = Query.QUERY_MAX_ID_FROM_ADDRESS;
        ResultSet result = null;

        try {
            // Avoid committing before transaction is complete
            conn.setAutoCommit(false);

            statement = conn.createStatement();
            result = statement.executeQuery(sqlStatement);
            if(result.next()) {
                maxID = result.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Unable to get max address ID: " + e.getMessage());

            // close jdbc resources
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
            conn.setAutoCommit(true);
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
