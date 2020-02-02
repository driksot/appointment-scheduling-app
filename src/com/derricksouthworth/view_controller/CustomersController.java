package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.CustomerDaoImpl;
import com.derricksouthworth.model.City;
import com.derricksouthworth.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CityDaoImpl.getAllCities;
import static com.derricksouthworth.DAO.CustomerDaoImpl.getAllCustomers;

/**
 *
 * @author derrick.southworth
 */

public class CustomersController implements Initializable {

    // Main Customer Controls
    @FXML
    private VBox vboxCustomerMain;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button btnUpdateCustomer;
    @FXML
    private Button btnRemoveCustomer;
    @FXML
    private TableView<Customer> tblCustomers;
    @FXML
    private TableColumn<Customer, Integer> colCustomerID;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableColumn<Customer, String> colCustomerAddress1;
    @FXML
    private TableColumn<Customer, String> colCustomerAddress2;
    @FXML
    private TableColumn<Customer, String> colCustomerCity;
    @FXML
    private TableColumn<Customer, String> colCustomerCountry;
    @FXML
    private TableColumn<Customer, String> colCustomerPostalCode;
    @FXML
    private TableColumn<Customer, String> colCustomerPhone;

    // Add Customer Controls
    @FXML
    private VBox vboxAddCustomer;
    @FXML
    private Button btnConfirmAddCustomer;
    @FXML
    private Button btnCancelAddCustomer;
    @FXML
    private Label lblCustomerID;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblAddress2;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblPostalCode;
    @FXML
    private Label lblPhone;
    @FXML
    private TextField txtAddCustomerID;
    @FXML
    private TextField txtAddCustomerName;
    @FXML
    private TextField txtAddCustomerAddress;
    @FXML
    private TextField txtAddCustomerAddress2;
    @FXML
    private ComboBox<String> cmbAddCustomerCity;
    @FXML
    private Button btnAddCity;
    @FXML
    private TextField txtAddCustomerPostalCode;
    @FXML
    private TextField txtAddCustomerPhone;

    // Update Customer Controls
    @FXML
    private VBox vboxModifyCustomer;
    @FXML
    private Button btnConfirmModCustomer;
    @FXML
    private Button btnCancelModCustomer;
    @FXML
    private Label lblCustomerIDMod;
    @FXML
    private Label lblCustomerNameMod;
    @FXML
    private Label lblAddressMod;
    @FXML
    private Label lblAddress2Mod;
    @FXML
    private Label lblCityMod;
    @FXML
    private Label lblPostalCodeMod;
    @FXML
    private Label lblPhoneMod;
    @FXML
    private TextField txtModCustomerID;
    @FXML
    private TextField txtModCustomerName;
    @FXML
    private TextField txtModCustomerAddress;
    @FXML
    private TextField txtModCustomerAddress2;
    @FXML
    private TextField txtModCustomerPostalCode;
    @FXML
    private TextField txtModCustomerPhone;
    @FXML
    private ComboBox<String> cmbModCustomerCity;
    @FXML
    private Button btnModCity;

    // Global variables
    private ObservableList<String> cities = getCities();

    //******************************************************************************************************************
    //
    //******************************************************************************************************************

    @FXML
    void cancelModCustomer(ActionEvent event) {
        loadMain();
    }

    @FXML
    void confirmModCustomer(ActionEvent event) {
        int customerID = Integer.parseInt(txtModCustomerID.getText());
        String customerName = txtModCustomerName.getText();
        String address = txtModCustomerAddress.getText();
        String address2 = txtModCustomerAddress2.getText();
        String city = cmbModCustomerCity.getSelectionModel().getSelectedItem();
        int cityID = cmbModCustomerCity.getSelectionModel().getSelectedIndex() + 1;
        String postalCode = txtModCustomerPostalCode.getText();
        String phone = txtModCustomerPhone.getText();
        // TODO validate input
        Customer customer = new Customer(customerID, customerName, address, address2, city, postalCode, phone);
        CustomerDaoImpl.updateCustomer(customer);
        loadMainTable();
    }

    @FXML
    void addCity(ActionEvent event) {

    }

    @FXML
    void addCustomer(ActionEvent event) {
        loadAdd();
        txtAddCustomerID.setText(Integer.toString(getAllCustomers().size() + 1));
    }

    @FXML
    void removeCustomer(ActionEvent event) {
        int customerID = tblCustomers.getSelectionModel().getSelectedItem().getCustomerID();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Customer Record Deletion?");
        alert.setContentText("Are you sure you want to delete this customer record?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Customer record deletion confirmed.");
            CustomerDaoImpl.deleteCustomer(customerID);
            loadMainTable();
        } else {
            System.out.println("Canceled customer record deletion.");
        }
    }

    @FXML
    void updateCustomer(ActionEvent event) {
        Customer customer = tblCustomers.getSelectionModel().getSelectedItem();
        txtModCustomerID.setText(Integer.toString(customer.getCustomerID()));
        txtModCustomerName.setText(customer.getCustomerName());
        txtModCustomerAddress.setText(customer.getAddress());
        txtModCustomerAddress2.setText(customer.getAddress2());
        cmbModCustomerCity.getSelectionModel().select(customer.getCity());
        txtModCustomerPostalCode.setText(customer.getPostalCode());
        txtModCustomerPhone.setText(customer.getPhone());
        loadModify();
    }

    @FXML
    void cancelAddCustomer(ActionEvent event) {
        loadMain();
    }

    @FXML
    void confirmAddCustomer(ActionEvent event) {
        int customerID = Integer.parseInt(txtAddCustomerID.getText());
        String customerName = txtAddCustomerName.getText();
        String address = txtAddCustomerAddress.getText();
        String address2 = txtAddCustomerAddress2.getText();
        String city = cmbAddCustomerCity.getSelectionModel().getSelectedItem();
        int cityID = cmbAddCustomerCity.getSelectionModel().getSelectedIndex() + 1;
        String postalCode = txtAddCustomerPostalCode.getText();
        String phone = txtAddCustomerPhone.getText();
        // TODO validate input
        Customer customer = new Customer(customerID, customerName, address, address2, city, postalCode, phone);
        CustomerDaoImpl.addCustomer(customer, cityID);
        loadMainTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMainTable();
    }

    private void loadMainTable() {
        loadMain();
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress1.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tblCustomers.setItems(getAllCustomers());
    }

    private void loadMain() {
        vboxCustomerMain.toFront();
    }

    private void loadAdd() {
        vboxAddCustomer.toFront();
        cmbAddCustomerCity.setItems(cities);
    }

    private void loadModify() {
        vboxModifyCustomer.toFront();
        cmbModCustomerCity.setItems(cities);
    }

    private ObservableList<String> getCities() {
        ObservableList<City> allCities = getAllCities();
        ObservableList<String> result = FXCollections.observableArrayList();
        for (City city : allCities) {
            result.add(city.getCityName());
        }
        return result;
    }
}

