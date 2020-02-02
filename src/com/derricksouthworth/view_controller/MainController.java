package com.derricksouthworth.view_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 *
 * @author derrick.southworth
 */

public class MainController {

    @FXML
    private BorderPane borderpane;

    @FXML
    private Button btnAppointments;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnExit;

    @FXML
    void appointments(MouseEvent event) {
//        btn_appointments.setStyle("-fx-background-color: #E5DFDF;" + "-fx-text-fill: #1D4D4F;");
//        btn_customers.setStyle("-fx-background-color: #1D4D4F;" + "-fx-text-fill: #E5DFDF");
//        btn_reports.setStyle("-fx-background-color: #1D4D4F;" + "-fx-text-fill: #E5DFDF");
        loadUI("Appointments");
    }

    @FXML
    void customers(MouseEvent event) {
//        btn_customers.setStyle("-fx-background-color: #E5DFDF;" + "-fx-text-fill: #1D4D4F");
//        btn_appointments.setStyle("-fx-background-color: #1D4D4F;" + "-fx-text-fill: #E5DFDF");
//        btn_reports.setStyle("-fx-background-color: #1D4D4F;" + "-fx-text-fill: #E5DFDF");
        loadUI("Customers");
    }

    @FXML
    void exit(MouseEvent event) {

    }

    @FXML
    void reports(MouseEvent event) {
        loadUI("Reports");
    }

    /**
     * Set the given fxml file to the center of the Main border pane
     * @param ui
     */
    private void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(ui + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderpane.setCenter(root);
    }

}

