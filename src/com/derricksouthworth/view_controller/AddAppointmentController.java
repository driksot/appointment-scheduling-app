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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CustomerDaoImpl.getCustomer;

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
    private JFXComboBox<String> cmbType;
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
    void submitAddAppointment(ActionEvent event) throws SQLException, IOException {
        Customer customer = getCustomer(txtCustomerName.getText());
        if(customer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR adding appointment");
            alert.setHeaderText("Invalid customer");
            alert.setContentText("Please input the name of an existing customer.");
            alert.showAndWait();
        } else {
            int appointmentID = Integer.parseInt(txtAppointmentID.getText());

            String customerName = customer.getCustomerName();
            int userID = DBConnect.getCurrentUser().getUserID();
            String location = txtLocation.getText();
            String contact = txtContact.getText();
            String type = cmbType.getSelectionModel().getSelectedItem();
            String start = timeStartTime.getValue().format(TimeFiles.DATE_TIME_FORMATTER);
            String end = timeStartTime.getValue().plusMinutes(30).format(TimeFiles.DATE_TIME_FORMATTER);

            Appointment addAppointment = new Appointment(appointmentID, customerName, userID, location, contact, type,
                    start, end);

            boolean isValid = true;
            if(isValid) {
                CustomerDaoImpl.addAppointment(addAppointment, customer.getCustomerID());
                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Unable to add record.");
                alert.setHeaderText("Failed to add appointment.");
                alert.setContentText("Invalid entry.");
                alert.showAndWait();
            }
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY METHODS
    //******************************************************************************************************************

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            txtAppointmentID.setText(Integer.toString(MainController.getAllAppointments(null).size() + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
