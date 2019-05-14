package softwareiiassessment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Object Relational Map of Address
 *
 * @author Lesaun
 */
public class ORMAddress {
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

    ORMAddress(int addressId, String address, String address2, int cityId,
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

    /**
     * Returns the address id
     * 
     * @return the address id
     */
    public int getAddressId() {
        return addressId.get();
    }

    /**
     * Sets the address id
     *
     * @param addressId address id to set
     */
    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }

    /**
     * Returns the address line 1
     * 
     * @return address line 1
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Set the address line 1
     * 
     * @param address address line 1 to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Returns address line 2
     *
     * @return address line 2
     */
    public String getAddress2() {
        return address2.get();
    }

    /**
     * Sets the address line 2
     * 
     * @param address2 the address line 2
     */
    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    /**
     * Returns city id
     * 
     * @return the city id
     */
    public int getCityId() {
        return cityId.get();
    }

    /**
     * Sets the city id
     * 
     * @param cityId the city id to set
     */
    public void setCityId(int cityId) {
        this.cityId.set(cityId);
    }

    /**
     * Returns the postal code
     * 
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode.get();
    }

    /**
     * Sets the postal code
     * 
     * @param postalCode postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    /**
     * Returns the phone number
     * 
     * @return the phone number
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * Sets the phone number
     *
     * @param phone phone number to set
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    /**
     * Returns the create date
     * 
     * @return the create date to set
     */
    public String getCreateDate() {
        return createDate.get();
    }

    /**
     * Sets the create date
     * @param createDate the create date to set 
     */
    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    /**
     * Returns the create by date
     * 
     * @return the create by date
     */
    public String getCreatedBy() {
        return createdBy.get();
    }

    /**
     * Sets the create by date
     *
     * @param createdBy create by date
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    /**
     * Returns the last update time
     * 
     * @return the last update time
     */
    public int getLastUpdate() {
        return lastUpdate.get();
    }

    /**
     * Sets the last update time
     * 
     * @param lastUpdate last update time to set
     */
    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    /**
     * Returns the last update user
     * 
     * @return the last update user
     */
    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    /**
     * Sets the last update user
     * 
     * @param lastUpdateBy last update user to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }
}
