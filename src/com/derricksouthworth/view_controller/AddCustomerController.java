package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.CustomerDaoImpl;
import com.derricksouthworth.model.City;
import com.derricksouthworth.model.Customer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import static com.derricksouthworth.DAO.CityDaoImpl.getAllCities;
import static com.derricksouthworth.DAO.CustomerDaoImpl.getAllCustomers;

public class AddCustomerController implements Initializable {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  FORM VARIABLES
    //******************************************************************************************************************

    @FXML
    private JFXTextField txtCustomerID;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtAddress2;
    @FXML
    private JFXComboBox<String> cmbCity;
    @FXML
    private JFXButton btnAddCity;
    @FXML
    private JFXTextField txtPostalCode;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnSubmit;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  LABELS
    //******************************************************************************************************************

    @FXML
    private Label lblAddCustomer;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  OTHER GLOBAL VARIABLES
    //******************************************************************************************************************

    private ObservableList<String> cities = getCities();

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UI CONTROL METHODS
    //******************************************************************************************************************

    @FXML
    void addCity(ActionEvent event) {

    }

    /**
     * Discard form and return to main.fxml
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelAddCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Create Customer object with given values and add to database
     * @param event
     */
    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        int customerID = Integer.parseInt(txtCustomerID.getText());
        String customerName = txtCustomerName.getText();
        String address = txtAddress.getText();
        String address2 = txtAddress2.getText();
        String city = cmbCity.getSelectionModel().getSelectedItem();
        int cityID = cmbCity.getSelectionModel().getSelectedIndex() + 1;
        String postalCode = txtPostalCode.getText();
        String phone = txtPhone.getText();
        // TODO validate input
        Customer customer = new Customer(customerID, customerName, address, address2, city, postalCode, phone);

        boolean isValid = true;
        if(isValid) {
            try {
                CustomerDaoImpl.addCustomer(customer, cityID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unable to add record.");
            alert.setHeaderText("Failed to add customer record.");
            alert.setContentText("Invalid entry.");
            alert.showAndWait();
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY METHODS
    //******************************************************************************************************************

    /**
     * Create a list of all city names to populate combo box
     * @return
     */
    private ObservableList<String> getCities() {
        ObservableList<City> allCities = null;
        try {
            allCities = getAllCities();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> result = FXCollections.observableArrayList();
        for (City city : allCities) {
            result.add(city.getCityName());
        }
        return result;
    }

    /**
     * Initialize with next ID index pre-set and populate city combo box
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            txtCustomerID.setText(Integer.toString(getAllCustomers().size() + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cmbCity.setItems(cities);
    }
}

