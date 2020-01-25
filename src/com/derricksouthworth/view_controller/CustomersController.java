package com.derricksouthworth.view_controller;

import com.derricksouthworth.model.Customer;
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
    private TextField txtAddCustomerCity;

    @FXML
    private TextField txtAddCustomerPostalCode;

    @FXML
    private TextField txtAddCustomerPhone;

    @FXML
    void addCustomer(ActionEvent event) {
        loadAdd();
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMain();
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress1.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCustomerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            tblCustomers.setItems(getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
    }
}

