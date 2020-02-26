package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.CustomerDaoImpl;
import com.derricksouthworth.DAO.Query;
import com.derricksouthworth.model.Appointment;
import com.derricksouthworth.model.Customer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CustomerDaoImpl.*;

/**
 *
 * @author derrick.southworth
 */

public class MainController implements Initializable {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  MAIN NAVIGATION
    //******************************************************************************************************************

    @FXML
    private BorderPane bpaneMain;

    @FXML
    private JFXButton btnAppointmentsPane;
    @FXML
    private JFXButton btnCustomersPane;
    @FXML
    private JFXButton btnReportsPane;
    @FXML
    private JFXButton btnExitApp;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  APPOINTMENTS CONTROLS
    //******************************************************************************************************************

    @FXML
    private VBox vBoxAppointments;

    @FXML
    private JFXButton btnAddAppointment;
    @FXML
    private JFXButton btnUpdateAppointment;
    @FXML
    private JFXButton btnDeleteAppointment;
    @FXML
    private JFXButton btnAppointmentByWeek;
    @FXML
    private JFXButton btnAppointmentByMonth;
    @FXML
    private JFXButton btnAppointmentByAll;
    @FXML
    private TableView<Appointment> tblAppointments;
    @FXML
    private TableColumn<Appointment, String> colAppointmentCustomer;
    @FXML
    private TableColumn<Appointment, String> colAppointmentType;
    @FXML
    private TableColumn<Appointment, String> colAppointmentLocation;
    @FXML
    private TableColumn<Appointment, String> colAppointmentContact;
    @FXML
    private TableColumn<Appointment, LocalDateTime> colAppointmentStart;
    @FXML
    private TableColumn<Appointment, LocalDateTime> colAppointmentEnd;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMERS CONTROLS
    //******************************************************************************************************************

    @FXML
    private VBox vBoxCustomers;

    @FXML
    private JFXButton btnAddCustomer;
    @FXML
    private JFXButton btnUpdateCustomer;
    @FXML
    private JFXButton btnDeleteCustomer;
    @FXML
    private TableView<Customer> tblCustomers;
    @FXML
    private TableColumn<Customer, Integer> colCustomerID;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableColumn<Customer, String> colCustomerAddress;
    @FXML
    private TableColumn<Customer, String> colCustomerAddress2;
    @FXML
    private TableColumn<Customer, String> colCustomerCity;
    @FXML
    private TableColumn<Customer, String> colCustomerPostalCode;
    @FXML
    private TableColumn<Customer, String> colCustomerPhone;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  REPORTS CONTROLS
    //******************************************************************************************************************

    @FXML
    private VBox vBoxReports;

    @FXML
    private TableView<String> tblReports;

    @FXML
    private JFXButton btnReportAppointmentByMonth;
    @FXML
    private JFXButton btnReportConsultantSchedule;
    @FXML
    private JFXButton btnReportCustomerAppointmentTotal;
    @FXML
    private JFXButton btnGenerateReport;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  LABELS
    //******************************************************************************************************************

    @FXML
    private Label lblAppointmentTitle;
    @FXML
    private Label lblAppointmentSubtitle;
    @FXML
    private Label lblAppointmentsSortBy;

    @FXML
    private Label lblCustomerTitle;
    @FXML
    private Label lblCustomerSubtitle;

    @FXML
    private Label lblReportsTitle;
    @FXML
    private Label lblReportsSubtitle;
    @FXML
    private JFXTextArea txtShowReport;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  OTHER GLOBAL VARIABLES
    //******************************************************************************************************************

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsWithin15Minutes = FXCollections.observableArrayList();

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  NAVIGATION UI CONTROL METHODS
    //******************************************************************************************************************

    @FXML
    void loadAppointmentsPane(ActionEvent event) {
        vBoxAppointments.toFront();
    }

    @FXML
    void loadCustomersPane(ActionEvent event) {
        vBoxCustomers.toFront();
    }

    @FXML
    void loadReportsPane(ActionEvent event) {
        vBoxReports.toFront();
    }

    @FXML
    void exitApp(ActionEvent event) {

    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  APPOINTMENTS UI CONTROL METHODS
    //******************************************************************************************************************

    /**
     * Open add_appointment.fxml stage in new window
     * @param event
     * @throws IOException
     */
    @FXML
    void addAppointment(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddAppointment.getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("add_appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        if(tblAppointments.getSelectionModel().getSelectedItem() != null) {
            Appointment appointment = tblAppointments.getSelectionModel().getSelectedItem();
            UpdateAppointmentController.setAppointmentToUpdate(appointment);

            Stage stage = (Stage) btnUpdateAppointment.getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("update_appointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unable to update record.");
            alert.setHeaderText("Failed to update appointment record.");
            alert.setContentText("Please select a valid appointment.");
            alert.showAndWait();
        }
    }

    /**
     * Remove selected appointment from database
     * @param actionEvent
     */
    @FXML
    public void deleteAppointment(ActionEvent actionEvent) {
        int appointmentID = tblAppointments.getSelectionModel().getSelectedItem().getAppointmentID();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Appointment Deletion?");
        alert.setContentText("Are you sure you want to delete this appointment?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Appointment deletion confirmed.");
            try {
                CustomerDaoImpl.deleteAppointment(appointmentID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadAppointmentTable(null);
        } else {
            System.out.println("Canceled appointment deletion.");
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMERS UI CONTROL METHODS
    //******************************************************************************************************************

    /**
     * Open add_customer.fxml stage in new window
     * @param event
     * @throws IOException
     */
    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("add_customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Update customer on database with given information
     * @param event
     * @throws IOException
     */
    @FXML
    void updateCustomer(ActionEvent event) throws IOException {
        if(tblCustomers.getSelectionModel().getSelectedItem() != null) {
            Customer customer = tblCustomers.getSelectionModel().getSelectedItem();
            UpdateCustomerController.setCustomerToUpdate(customer);

            Stage stage = (Stage) btnUpdateCustomer.getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("update_customer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unable to update record.");
            alert.setHeaderText("Failed to update customer record.");
            alert.setContentText("Please select a valid customer record.");
            alert.showAndWait();
        }
    }

    /**
     * Remove selected Customer from database
     * @param event
     */
    @FXML
    void deleteCustomer(ActionEvent event) {
        int customerID = tblCustomers.getSelectionModel().getSelectedItem().getCustomerID();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Customer Record Deletion?");
        alert.setContentText("Are you sure you want to delete this customer record?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Customer record deletion confirmed.");
            try {
                CustomerDaoImpl.deleteCustomer(customerID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadCustomerTable();
        } else {
            System.out.println("Canceled customer record deletion.");
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  REPORTS UI CONTROL METHODS
    //******************************************************************************************************************

    @FXML
    void generateReport(ActionEvent event) {

    }

    @FXML
    void showReportAppointmentByMonth(ActionEvent event) {
        tblReports.getColumns().clear();
        try {
            CustomerDaoImpl.getReportData(tblReports, Query.QUERY_GET_APPOINTMENT_TYPE_BY_MONTH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showReportConsultantSchedule(ActionEvent event) {
        tblReports.getColumns().clear();
        try {
            CustomerDaoImpl.getReportData(tblReports, Query.QUERY_GET_CONSULTANT_SCHEDULES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showReportCustomerAppointmentTotal(ActionEvent event) {
        tblReports.getColumns().clear();
        try {
            CustomerDaoImpl.getReportData(tblReports, Query.QUERY_GET_CUSTOMER_APPOINTMENT_TOTALS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  APPOINTMENT UTILITY METHODS
    //******************************************************************************************************************

    /**
     * Populate Appointments table from database in given time frame
     * @param sortBy
     */
    private void loadAppointmentTable(String sortBy) {
        tblAppointments.getItems().clear();
        colAppointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colAppointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAppointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colAppointmentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAppointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colAppointmentStart.setSortType(TableColumn.SortType.ASCENDING);
        colAppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        try {
            if (sortBy == "15 minutes") {
                tblAppointments.setItems(getAppointmentsWithinFifteenMinutes());
            } else {
                tblAppointments.setItems(getAllAppointments(sortBy));
            }
            tblAppointments.getSortOrder().add(colAppointmentStart);
        } catch (SQLException e) {
            System.out.println("Unable to populate appointment table: " + e.getMessage());
        }
    }

    /**
     * Alert user to appointment coming up within 15 minutes and populate table with those
     * appointments, if no such appointments, no alert and populate table with all appointments
     * @throws SQLException
     */
    private void upcomingAppointmentNotification() throws SQLException {
        appointmentsWithin15Minutes = CustomerDaoImpl.getAppointmentsWithinFifteenMinutes();
        if (appointmentsWithin15Minutes.size() != 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment.");
            alert.setHeaderText("You have an appointment.");
            alert.setContentText("Number of appointments in next 15 minutes: " +
                    appointmentsWithin15Minutes.size() + "\n Please review your appointments.");
            alert.showAndWait();

            loadAppointmentTable("15 minutes");
        } else {
            loadAppointmentTable(null);
        }
    }

    /**
     * Populate Appointments table by first getting all customers
     * and then all appointments associated with each customer
     * @return
     */
    public static ObservableList<Appointment> getAllAppointments(String sortBy) throws SQLException {
        if (getAllCustomers() == null) {
            return null;
        }
        allAppointments.clear();
        // Loop through all Customer objects
        for(Customer customer: getAllCustomers()) {
            // Find every appointment for given customer and add to allAppointments
            allAppointments.addAll(getCustomerAppointments(customer, sortBy));

        }
        return allAppointments;
    }

    /**
     * Show appointment in the upcoming month
     * @param event
     */
    @FXML
    void loadTableAppointmentsByMonth(ActionEvent event) {
        loadAppointmentTable(Query.SORT_BY_MONTH);
    }

    /**
     * Show appointments in the upcoming week
     * @param event
     */
    @FXML
    void loadTableAppointmentsByWeek(ActionEvent event) {
        loadAppointmentTable(Query.SORT_BY_WEEK);
    }

    /**
     * Show all appointments
     * @param event
     */
    @FXML
    void loadTableAppointmentsByAll(ActionEvent event) {
        loadAppointmentTable(null);
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMER UTILITY METHODS
    //******************************************************************************************************************

    /**
     * Populate Customer table using getAllCustomers from CustomerDaoImpl
     */
    private void loadCustomerTable() {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            tblCustomers.setItems(getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  INITIALIZE
    //******************************************************************************************************************

    /**
     * Initialize and populate tables
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBoxAppointments.toFront();
        loadCustomerTable();
        try {
            upcomingAppointmentNotification();
        } catch (SQLException e) {
            System.out.println("Unable to populate appointment table: " + e.getMessage());
        }
    }
}

