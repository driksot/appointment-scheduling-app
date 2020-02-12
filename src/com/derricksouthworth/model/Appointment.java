package com.derricksouthworth.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author derrick.southworth
 */

public class Appointment {
    private int appointmentID;
    private String customer;
    private int userID;
    private String location;
    private String contact;
    private String type;
    private String start;
    private String end;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    /**
     * Constructor
     * @param appointmentID
     * @param customer
     * @param userID
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
    public Appointment(int appointmentID, String customer, int userID, String location, String contact, String type, String start, String end, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.userID = userID;
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

    /**
     * Constructor
     * @param appointmentID
     * @param customer
     * @param userID
     * @param location
     * @param contact
     * @param type
     * @param start
     * @param end
     */
    public Appointment(int appointmentID, String customer, int userID, String location, String contact, String type, String start, String end) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.userID = userID;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    // Getters

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getCustomer() {
        return customer;
    }

    public int getUserID() {
        return userID;
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

    public String getStart() {
        return start;
    }

    public String getEnd() {
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

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
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
