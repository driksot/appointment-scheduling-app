package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.CustomerDaoImpl;
import com.derricksouthworth.DAO.Query;
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

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CityDaoImpl.getAllCities;
import static com.derricksouthworth.DAO.CustomerDaoImpl.getAllCustomers;

/**
 *
 * @author derrick.southworth
 */

public class CustomersController implements Initializable {

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

    private ObservableList<String> cities;

    {
        try {
            cities = getCities();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addCity(ActionEvent event) {

    }

    @FXML
    void addCustomer(ActionEvent event) throws SQLException, ParseException {
        loadAdd();
        txtAddCustomerID.setText(Integer.toString(getAllCustomers().size() + 1));
    }

    @FXML
    void removeCustomer(ActionEvent event) {

    }

    @FXML
    void updateCustomer(ActionEvent event) {

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
        try {
            CustomerDaoImpl.addCustomer(customer, cityID);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        loadMainTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMainTable();
    }

    private void loadMainTable() {
        loadMain();
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>(Query.COLUMN_CUSTOMER_NAME));
        colCustomerAddress1.setCellValueFactory(new PropertyValueFactory<>(Query.COLUMN_ADDRESS));
        colCustomerAddress2.setCellValueFactory(new PropertyValueFactory<>(Query.COLUMN_ADDRESS_2));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>(Query.COLUMN_CITY_NAME));
        colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>(Query.COLUMN_POSTAL_CODE));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>(Query.COLUMN_PHONE));

        try {
            tblCustomers.setItems(getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void loadMain() {
        vboxCustomerMain.toFront();
    }

    private void loadAdd() {
        vboxAddCustomer.toFront();
        cmbAddCustomerCity.setItems(cities);
    }

    private ObservableList<String> getCities() throws ParseException, SQLException {
        ObservableList<City> allCities = getAllCities();
        ObservableList<String> result = FXCollections.observableArrayList();
        for (City city : allCities) {
            result.add(city.getCityName());
        }
        return result;
    }
}

