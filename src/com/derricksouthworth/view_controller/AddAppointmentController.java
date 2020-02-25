package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.CustomerDaoImpl;
import com.derricksouthworth.DAO.DBConnect;
import com.derricksouthworth.model.Appointment;
import com.derricksouthworth.model.Customer;
import com.derricksouthworth.utilities.TimeFiles;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CustomerDaoImpl.getCustomer;
import static com.derricksouthworth.utilities.TimeFiles.stringToCalendar;

/**
 *
 * @author derrick.southworth
 */

public class AddAppointmentController implements Initializable {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  FORM VARIABLES
    //******************************************************************************************************************

    @FXML
    private JFXTextField txtAppointmentID;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtLocation;
    @FXML
    private JFXTextField txtContact;
    @FXML
    private JFXTextField txtType;
    @FXML
    private JFXComboBox<String> cmbDuration;
    @FXML
    private JFXTimePicker timeStartTime;
    @FXML
    private JFXDatePicker dateStartDate;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnSubmit;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  LABELS
    //******************************************************************************************************************

    @FXML
    private Label lblAddAppointment;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UI CONTROL METHODS
    //******************************************************************************************************************

    /**
     * Discard form and return to main.fxml
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelAddAppointment(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void submitAddAppointment(ActionEvent event) throws SQLException, IOException, ParseException {
        Customer customer = getCustomer(txtCustomerName.getText());

        int duration = (cmbDuration.getSelectionModel().getSelectedIndex() + 1) * 15;

        if (dateStartDate.getValue() == null || timeStartTime.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Date or time not selected");
            alert.setContentText("Please select a date and time.");

            alert.showAndWait();

        } else {
            String startTime = dateStartDate.getValue().format(TimeFiles.DATE_FORMATTER) + " " + timeStartTime.getValue().format(TimeFiles.TIME_FORMATTER);
            String endTime = dateStartDate.getValue().format(TimeFiles.DATE_FORMATTER) + " " + timeStartTime.getValue().plusMinutes(duration).format(TimeFiles.TIME_FORMATTER);

            if (customer == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("ERROR adding appointment");
                alert.setHeaderText("Invalid customer");
                alert.setContentText("Please input the name of an existing customer or create a new record.");

                ButtonType buttonTypeNewCustomer = new ButtonType("New Customer");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeNewCustomer, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeNewCustomer) {
                    Stage stage = (Stage) btnSubmit.getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("add_customer.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                } else {
                    Stage stage = (Stage) btnSubmit.getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("add_appointment.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            } else {
                int appointmentID = Integer.parseInt(txtAppointmentID.getText());

                String customerName = customer.getCustomerName();
                int userID = DBConnect.getCurrentUser().getUserID();
                String location = txtLocation.getText();
                String contact = txtContact.getText();
                String type = txtType.getText();
                String start = TimeFiles.timeToUTC(startTime);
                String end = TimeFiles.timeToUTC(endTime);

                Appointment addAppointment = new Appointment(appointmentID, customerName, userID, location, contact, type,
                        start, end);

                if (CustomerDaoImpl.isOverlappingAppointmentTime(contact, stringToCalendar(start), stringToCalendar(end))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error adding appointment");
                    alert.setHeaderText(contact + " has another appointment at this time.");
                    alert.setContentText("Please select another time or consultant.");

                    alert.showAndWait();

                } else if (isValidAppointment(addAppointment)) {
                    CustomerDaoImpl.addAppointment(addAppointment, customer.getCustomerID());
                    Stage stage = (Stage) btnSubmit.getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY METHODS
    //******************************************************************************************************************

    private boolean isValidAppointment(Appointment appointment) {
        String errorMessage = "";

        // validate customer name
        if (!(appointment.getCustomer().matches("[a-zA-z]+([ '-][a-zA-Z]+)*"))) {
            errorMessage += "Please enter a valid customer name. \n";
        }
        if (appointment.getCustomer().length() == 0 || appointment.getCustomer() == null) {
            errorMessage += "Please enter a customer's name. \n";
        }

        // validate location
        if (appointment.getLocation().length() == 0 || appointment.getLocation() == null) {
            errorMessage += "Please enter a location. \n";
        }

        // validate contact
        if (appointment.getContact().length() == 0 || appointment.getContact() == null) {
            errorMessage += "Please enter a contact. \n";
        }

        // validate type
        if (appointment.getType().length() == 0 || appointment.getType() == null) {
            errorMessage += "Please enter an appointment type. \n";
        }

        // validate duration
        if (cmbDuration.getSelectionModel().isEmpty()) {
            errorMessage += "Please select an appointment duration. \n";
        }

        // validate date
        if (dateStartDate.getValue() == null) {
            errorMessage += "Please select an appointment date. \n";
        }

        // validate time
        if (timeStartTime.getValue() == null) {
            errorMessage += "Please select an appointment start time. \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding appointment");
            alert.setHeaderText("Invalid appointment information");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbDuration.getItems().addAll("15 minutes", "30 minutes", "45 minutes", "60 minutes");
        try {
            txtAppointmentID.setText(Integer.toString(MainController.getAllAppointments(null).size() + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
