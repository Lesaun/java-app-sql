/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiassessment;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author v-leharv
 */
public class Address {
    private final SimpleIntegerProperty addressId;
    private final SimpleStringProperty address;
    private final SimpleStringProperty address2;
    private final SimpleIntegerProperty cityId;
    private final SimpleStringProperty postalCode;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty createDate;
    private final SimpleStringProperty createdBy;
    private final SimpleIntegerProperty lastUpdate;
    private final SimpleStringProperty lastUpdateBy;

    Address(int addressId, String address, String address2, int cityId, 
            String postalCode, String phone, String createDate, 
            String createdBy, int lastUpdate, String lastUpdateBy) {
        this.addressId = new SimpleIntegerProperty(addressId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.cityId = new SimpleIntegerProperty(cityId);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        
        this.createDate = new SimpleStringProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleIntegerProperty(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }
 
    public int getAddressId() {
        return addressId.get();
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getAddress2() {
        return address2.get();
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    public int getCityId() {
        return cityId.get();
    }

    public void setCityId(int cityId) {
        this.cityId.set(cityId);
    }
    
    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }
    
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
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
        

