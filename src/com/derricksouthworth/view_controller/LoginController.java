package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.UserDaoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author derrick.southworth
 */

public class LoginController {

    @FXML
    private Label lblLogInTitle;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField pwdPassword;

    @FXML
    private JFXButton btnSignIn;

    /**
     * Verify that user name/ password pair exist in database prior to loading Main.fxml
     * @param event
     * @throws IOException
     */
    @FXML
    private void submitSignIn(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = pwdPassword.getText();
        boolean isValid = UserDaoImpl.login(userName, password);
        if(isValid) {
            Stage stage = (Stage) btnSignIn.getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login failed!");
            alert.setHeaderText("Login failed!");
            alert.setContentText("Incorrect username or password!");
            alert.showAndWait();
        }
    }

}
