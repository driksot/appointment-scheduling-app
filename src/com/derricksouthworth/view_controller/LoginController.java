package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.DBConnect;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author derrick.southworth
 */

public class LoginController implements Initializable {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  CONTROL VARIABLES
    //******************************************************************************************************************

    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField pwdPassword;
    @FXML
    private JFXButton btnSignIn;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  LABELS
    //******************************************************************************************************************

    @FXML
    private Label lblSignIn;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  OTHER GLOBAL VARIABLES
    //******************************************************************************************************************

    Locale userLocale;
    ResourceBundle rb;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  METHODS
    //******************************************************************************************************************

    /**
     * Verify that user name/ password pair exist in database prior to loading main.fxml
     * @param event
     * @throws IOException
     */
    @FXML
    void submitSignIn(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = pwdPassword.getText();
        boolean isValid = false;
        try {
            isValid = DBConnect.login(userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(isValid) {
            Stage stage = (Stage) btnSignIn.getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("logInErrorTitle"));
            alert.setHeaderText(rb.getString("logInErrorHeader"));
            alert.setContentText(rb.getString("logInErrorContent"));
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userLocale = Locale.getDefault();
        this.rb = ResourceBundle.getBundle("com/derricksouthworth/utilities/Login", this.userLocale);

        lblSignIn.setText(this.rb.getString("signInLabel"));
        txtUserName.setPromptText(this.rb.getString("usernamePrompt"));
        pwdPassword.setPromptText(this.rb.getString("passwordPrompt"));
        btnSignIn.setText(this.rb.getString("submitButton"));
    }
}
