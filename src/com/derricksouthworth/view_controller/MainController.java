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
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CustomerDaoImpl.getAllCustomers;
import static com.derricksouthworth.DAO.CustomerDaoImpl.getCustomerAppointments;

public class MainController implements Initializable {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  MAIN CONTAINERS
    //******************************************************************************************************************

    @FXML
    private Tab tabAppointments;
    @FXML
    private Tab tabCustomers;
    @FXML
    private Tab tabReports;
    @FXML
    private Tab tabExit;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  APPOINTMENTS CONTROLS
    //******************************************************************************************************************

    @FXML
    private JFXButton btnAddAppointment;
    @FXML
    private JFXButton btnAppointmentByWeek;
    @FXML
    private JFXButton btnAppointmentByMonth;
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
    private TableColumn<Appointment, Calendar> colAppointmentStart;
    @FXML
    private TableColumn<Appointment, Calendar> colAppointmentEnd;
    @FXML
    private GridPane gridCalendar;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CUSTOMERS CONTROLS
    //******************************************************************************************************************

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
    private JFXButton btnReportAppointmentByMonth;
    @FXML
    private JFXButton btnReportConsultantSchedule;
    @FXML
    private JFXButton btnReportLastWeekEdits;
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

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UI CONTROL METHODS
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
    void loadTableAppointmentsByMonth(ActionEvent event) {
        loadAppointmentTable(Query.SORT_BY_MONTH);
    }

    @FXML
    void loadTableAppointmentsByWeek(ActionEvent event) {
        loadAppointmentTable(Query.SORT_BY_WEEK);
    }

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
            CustomerDaoImpl.deleteCustomer(customerID);
            loadCustomerTable();
        } else {
            System.out.println("Canceled customer record deletion.");
        }
    }

    @FXML
    void generateReport(ActionEvent event) {

    }

    @FXML
    void showReportAppointmentByMonth(ActionEvent event) {

    }

    @FXML
    void showReportConsultantSchedule(ActionEvent event) {

    }

    @FXML
    void showReportLastWeekEdits(ActionEvent event) {

    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY METHODS
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

    private void loadAppointmentTable(String sortBy) {
        tblAppointments.getItems().clear();
        colAppointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colAppointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAppointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colAppointmentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAppointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colAppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        try {
            tblAppointments.setItems(getAllAppointments(sortBy));
        } catch (SQLException e) {
            e.printStackTrace();
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
        // Loop through all Customer objects
        for(Customer customer: getAllCustomers()) {
            // Find every appointment for given customer and add to allAppointments
            allAppointments.addAll(getCustomerAppointments(customer, sortBy));

        }
        return allAppointments;
    }

    /**
     * Initialize and populate tables
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCustomerTable();
        loadAppointmentTable(null);
    }
}

