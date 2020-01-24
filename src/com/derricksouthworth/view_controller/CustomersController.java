package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.CustomerDaoImpl;
import com.derricksouthworth.DAO.DBConnect;
import com.derricksouthworth.model.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    void addCustomer(MouseEvent event) {

    }

    @FXML
    void removeCustomer(MouseEvent event) {

    }

    @FXML
    void updateCustomer(MouseEvent event) {

    }

    public void populateCustomerTable() throws ParseException, SQLException, ClassNotFoundException {
        tblCustomers.setItems(getAllCustomers());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}

