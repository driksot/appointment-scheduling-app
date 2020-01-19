package com.derricksouthworth.model;

import java.util.Calendar;

/**
 *
 * @author derrick.southworth
 */

public class Appointment {
    private int appointmentID;
    private Customer customer;
    private User user;
    private String location;
    private String contact;
    private String type;
    private Calendar start;
    private Calendar end;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    /**
     * Constructor
     * @param appointmentID
     * @param customer
     * @param user
     * @param location
     * @param contact
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public Appointment(int appointmentID, Customer customer, User user, String location, String contact, String type, Calendar start, Calendar end, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.user = user;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    // Getters

    public int getAppointmentID() {
        return appointmentID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public User getUser() {
        return user;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public Calendar getStart() {
        return start;
    }

    public Calendar getEnd() {
        return end;
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

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public void setEnd(Calendar end) {
        this.end = end;
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
}
