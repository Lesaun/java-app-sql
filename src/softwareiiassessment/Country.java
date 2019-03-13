/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiassessment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author v-leharv
 */
public class Country {
    private final SimpleIntegerProperty countryId;
    private final SimpleStringProperty country;
    private final SimpleStringProperty createDate;
    private final SimpleStringProperty createdBy;
    private final SimpleIntegerProperty lastUpdate;
    private final SimpleStringProperty lastUpdateBy;

    Country(int countryId, String country,
            String createDate, String createdBy, 
            int lastUpdate, String lastUpdateBy) {
        this.countryId = new SimpleIntegerProperty(countryId);
        this.country = new SimpleStringProperty(country);
        
        this.createDate = new SimpleStringProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleIntegerProperty(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    public int getCountryId() {
        return countryId.get();
    }

    public void getCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
    
    public String getCreateDate() {
        return createDate.get();
    }

    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public int getLastUpdate() {
        return lastUpdate.get();
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }
}
        

