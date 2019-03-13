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
public class City {
    private final SimpleIntegerProperty cityId;
    private final SimpleStringProperty city;
    private final SimpleIntegerProperty countryId;
    private final SimpleStringProperty createDate;
    private final SimpleStringProperty createdBy;
    private final SimpleIntegerProperty lastUpdate;
    private final SimpleStringProperty lastUpdateBy;

    City(int cityId, String city, int countryId, 
            String createDate, String createdBy, 
            int lastUpdate, String lastUpdateBy) {
        this.cityId = new SimpleIntegerProperty(cityId);
        this.city = new SimpleStringProperty(city);
        this.countryId = new SimpleIntegerProperty(countryId);
        
        this.createDate = new SimpleStringProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleIntegerProperty(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }
 
    public int getCityId() {
        return cityId.get();
    }

    public void setCityId(int cityId) {
        this.cityId.set(cityId);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public int getCountryId() {
        return countryId.get();
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
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
     

