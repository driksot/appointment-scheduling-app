package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.DBConnect;
import com.derricksouthworth.DAO.Query;
import com.derricksouthworth.DAO.UserDaoImpl;
import com.derricksouthworth.model.User;
import com.derricksouthworth.schedulingapp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private void submitSignIn(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtUserName.getText();
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
