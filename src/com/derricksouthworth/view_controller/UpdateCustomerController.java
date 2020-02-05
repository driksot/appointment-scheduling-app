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
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CityDaoImpl.getAllCities;
import static com.derricksouthworth.DAO.CustomerDaoImpl.getAllCustomers;

public class UpdateCustomerController implements Initializable {

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
    private static Customer customerToUpdate;

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UI CONTROL METHODS
    //******************************************************************************************************************

    @FXML
    void addCity(ActionEvent event) {

    }

    @FXML
    void cancelUpdateCustomer(ActionEvent event) {

    }

    /**
     * Update customer record in database with given changes
     * @param event
     * @throws IOException
     */
    @FXML
    void updateCustomer(ActionEvent event) throws IOException {
        int customerID = Integer.parseInt(txtCustomerID.getText());
        String customerName = txtCustomerName.getText();
        String address = txtAddress.getText();
        String address2 = txtAddress2.getText();
        String city = cmbCity.getSelectionModel().getSelectedItem();
        String postalCode = txtPostalCode.getText();
        String phone = txtPhone.getText();
        // TODO validate input
        Customer customer = new Customer(customerID, customerName, address, address2, city, postalCode, phone);

        boolean isValid = true;
        if(isValid) {
            CustomerDaoImpl.updateCustomer(customer);
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
        ObservableList<City> allCities = getAllCities();
        ObservableList<String> result = FXCollections.observableArrayList();
        for (City city : allCities) {
            result.add(city.getCityName());
        }
        return result;
    }

    /**
     * Setter to get Customer object to fill form
     * @param customerToUpdate
     */
    public static void setCustomerToUpdate(Customer customerToUpdate) {
        UpdateCustomerController.customerToUpdate = customerToUpdate;
    }

    /**
     * Initialize with given Customer pre-set and populate city combo box
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtCustomerID.setText(Integer.toString(customerToUpdate.getCustomerID()));
        txtCustomerName.setText(customerToUpdate.getCustomerName());
        txtAddress.setText(customerToUpdate.getAddress());
        txtAddress2.setText(customerToUpdate.getAddress2());
        cmbCity.getSelectionModel().select(customerToUpdate.getCity());
        txtPostalCode.setText(customerToUpdate.getPostalCode());
        txtPhone.setText(customerToUpdate.getPhone());

        cmbCity.setItems(cities);
    }
}
