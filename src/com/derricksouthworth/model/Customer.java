package com.derricksouthworth.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Calendar;

/**
 *
 * @author derrick.southworth
 */

public class Customer {
    private static ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
    private int customerID;
    private String customerName;
    private String address;
    private String address2;
    private String city;
    private String postalCode;
    private String phone;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    /**
     * Constructor
     * @param customerID
     * @param customerName
     * @param address
     * @param address2
     * @param city
     * @param postalCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public Customer(int customerID, String customerName, String address, String address2, String city, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Constructor
     * @param customerID
     * @param customerName
     * @param address
     * @param address2
     * @param city
     * @param postalCode
     * @param phone
     */
    public Customer(int customerID, String customerName, String address, String address2, String city, String postalCode, String phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    // Add Appointment Object to customerAppointments list
    public static void addCustomerAppointment(Appointment appointment) {
        customerAppointments.add(appointment);
    }

    // Remove Appointment Object from customerAppointments list
    public void removeCustomerAppointment(Appointment appointment) {
        customerAppointments.remove(appointment);
    }

    // Lookup a specific Appointment buy Customer or appointmentID in customerAppointments list
    public Appointment lookupCustomerAppointment(String searchItem) {
        for(Appointment a:customerAppointments) {
            if(a.getCustomer().contains(searchItem) || Integer.toString(a.getAppointmentID()).equals(searchItem)) return a;
        }
        return null;
    }

    // Getters

    public static ObservableList<Appointment> getCustomerAppointments() {
        return customerAppointments;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    // Setters

    public void setCustomerAppointments(ObservableList<Appointment> customerAppointments) {
        this.customerAppointments = customerAppointments;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public static boolean isValidCustomerName(String customerName) {
        if(!customerName.matches("[A-Z]+([ '-][a-zA-Z]+)*")) return false;
        if(customerName.length() == 0) return false;
        return true;
    }
}
