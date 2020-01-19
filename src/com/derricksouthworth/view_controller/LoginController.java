package com.derricksouthworth.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author derrick.southworth
 */

public class LoginController {

    @FXML
    private Label lblLogInTitle;

    @FXML
    private Label lblUserName;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblPassword;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    void submitSignIn(ActionEvent event) {

    }

}
