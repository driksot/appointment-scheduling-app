package com.derricksouthworth.view_controller;

import com.derricksouthworth.DAO.CityDaoImpl;
import com.derricksouthworth.model.City;
import com.derricksouthworth.model.Country;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.derricksouthworth.DAO.CityDaoImpl.getAllCities;
import static com.derricksouthworth.DAO.CountryDaoImpl.*;

/**
 *
 * @author derrick.southworth
 */

public class AddCityController implements Initializable {

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  FORM VARIABLES
    //******************************************************************************************************************

    @FXML
    private JFXTextField txtCityID;
    @FXML
    private JFXTextField txtCityName;
    @FXML
    private JFXTextField txtCountry;
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
    //  UI CONTROL METHODS
    //******************************************************************************************************************

    @FXML
    void addCity(ActionEvent event) throws IOException {
        Country country = getCountry(txtCountry.getText());

        int countryID = getAllCountries().size() + 1;
        String countryName = txtCountry.getText();

        if (country == null) {
            Country addCountry = new Country(countryID, countryName);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ERROR adding city");
            alert.setHeaderText("Country not found");
            alert.setContentText("Would you like to add this country?");

            ButtonType buttonTypeNewCountry = new ButtonType("Add Country");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeNewCountry, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeNewCountry) {
                try {
                    addCountry(addCountry);
                } catch (SQLException e) {
                    System.out.println("Error adding country: " + e.getMessage());
                }

                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("add_city.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("add_city.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } else {
            int cityID = Integer.parseInt(txtCityID.getText());
            String cityName = txtCityName.getText();

            City city = new City(cityID, cityName, countryName);

            if(isValidCity(city)) {
                try {
                    CityDaoImpl.addCity(city, country.getCountryID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    @FXML
    void cancelAddCity(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Cancellation");
        alert.setContentText("Are you sure you want to cancel adding city?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            System.out.println("Cancel clicked.");
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //  UTILITY METHODS
    //******************************************************************************************************************

    private boolean isValidCity(City city) {
        String errorMessage = "";

        if (city.getCityName() == null) {
            errorMessage += "Please enter a city. \n";
        }
        if (city.getCountry() == null) {
            errorMessage += "Please enter a country. \n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding city");
            alert.setHeaderText("Invalid city information");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            txtCityID.setText(Integer.toString(getAllCities().size() + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
