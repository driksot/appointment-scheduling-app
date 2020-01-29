package com.derricksouthworth.view_controller;

import com.derricksouthworth.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CustomerDaoImpl.getAllCustomers;

public class AppointmentsController implements Initializable {

    @FXML
    private Button btnAddAppointment;

    @FXML
    private Button btnUpdateAppointment;

    @FXML
    private Button btnRemoveAppointment;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TableColumn<Customer, Integer> colCustomerID;

    @FXML
    private TableColumn<Customer, String> colCustomerName;

    @FXML
    private TableView<?> tblMonthlyAppointments;

    @FXML
    private TableColumn<?, ?> colMAppointmentType;

    @FXML
    private TableColumn<?, ?> colMAppointmentLocation;

    @FXML
    private TableColumn<?, ?> colMAppointmentContact;

    @FXML
    private TableColumn<?, ?> colMAppointmentStart;

    @FXML
    private TableColumn<?, ?> colMAppointmentEnd;

    @FXML
    private TableView<?> tblWeeklyAppointments;

    @FXML
    private TableColumn<?, ?> colWAppointmentType;

    @FXML
    private TableColumn<?, ?> colWAppointmentLocation;

    @FXML
    private TableColumn<?, ?> colWAppointmentContact;

    @FXML
    private TableColumn<?, ?> colWAppointmentStart;

    @FXML
    private TableColumn<?, ?> colWAppointmentEnd;

    @FXML
    void addAppointment(ActionEvent event) {

    }

    @FXML
    void removeAppointment(ActionEvent event) {

    }

    @FXML
    void updateAppointment(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        try {
            tblCustomer.setItems(getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

