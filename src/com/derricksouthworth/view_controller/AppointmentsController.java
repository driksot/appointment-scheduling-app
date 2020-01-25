package com.derricksouthworth.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentsController {

    @FXML
    private Button btnAddAppointment;

    @FXML
    private Button btnUpdateAppointment;

    @FXML
    private Button btnRemoveAppointment;

    @FXML
    private TableView<?> tblCustomer;

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

}

