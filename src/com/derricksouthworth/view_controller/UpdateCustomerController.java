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

/**
 *
 * @author derrick.southworth
 */

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

    /**
     * Open add_city.fxml stage in new window
     * @param event
     * @throws IOException
     */
    @FXML
    void addCity(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddCity.getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("add_customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Discard form and return to main.fxml
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelUpdateCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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

        if(isValidCustomer(customer)) {
            try {
                CustomerDaoImpl.updateCustomer(customer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
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
     * Setter to get Customer object to fill form
     * @param customerToUpdate
     */
    public static void setCustomerToUpdate(Customer customerToUpdate) {
        UpdateCustomerController.customerToUpdate = customerToUpdate;
    }

    private boolean isValidCustomer(Customer customer) {
        String errorMessage = "";

        // validate customer name
        if (!(customer.getCustomerName().matches("[a-zA-z]+([ '-][a-zA-Z]+)*"))) {
            errorMessage += "Please enter a valid customer name. \n";
        }
        if (customer.getCustomerName().length() == 0 || customer.getCustomerName() == null) {
            errorMessage += "Please enter a customer's name. \n";
        }

        // validate address
        if (!(customer.getAddress().matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-A]+)"))) {
            errorMessage += "Please enter a valid address. \n";
        }
        if (customer.getAddress().length() == 0 || customer.getAddress() == null) {
            errorMessage += "Please enter an address. \n";
        }

        // validate phone number
        if (!(customer.getPhone().matches("[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}"))) {
            errorMessage += "Please enter a valid phone number. \n";
        }
        if (customer.getPhone().length() == 0 || customer.getPhone() == null) {
            errorMessage += "Please enter a phone number. \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding customer");
            alert.setHeaderText("Invalid customer information");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
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

