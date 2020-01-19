package com.derricksouthworth.model;

import java.util.Calendar;

/**
 *
 * @author derrick.southworth
 */

public class City {
    private int cityID;
    private String cityName;
    private Country country;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    /**
     * Constructor
     * @param cityID
     * @param cityName
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public City(int cityID, String cityName, Country country, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    // Getters

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public Country getCountry() {
        return country;
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

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountry(Country country) {
        this.country = country;
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
