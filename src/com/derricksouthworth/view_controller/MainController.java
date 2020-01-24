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
    private Button btn_appointments;

    @FXML
    private Button btn_customers;

    @FXML
    private Button btn_reports;

    @FXML
    private Button btn_exit;

    @FXML
    void appointments(MouseEvent event) {
        loadUI("Appointments");
    }

    @FXML
    void customers(MouseEvent event) {
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

