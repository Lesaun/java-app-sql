package softwareiiassessment;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Object Relational Map of Customer
 *
 * @author Lesaun
 */
public class ORMCustomer {
    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty customerName;
    private final SimpleIntegerProperty addressId;
    private final SimpleBooleanProperty active;
    private final SimpleStringProperty createDate;
    private final SimpleStringProperty createdBy;
    private final SimpleIntegerProperty lastUpdate;
    private final SimpleStringProperty lastUpdateBy;

    ORMCustomer(int customerId, String customerName, int addressId,
                boolean active, String createDate, String createdBy,
                int lastUpdate, String lastUpdateBy) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.addressId = new SimpleIntegerProperty(addressId);
        this.active = new SimpleBooleanProperty(active);

        this.createDate = new SimpleStringProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleIntegerProperty(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    /**
     * Returns the customer id
     * 
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId.get();
    }

    /**
     * Sets the customer id
     *
     * @param customerId the customer id to set
     */
    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    /**
     * Returns the customer name
     * 
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName.get();
    }

    /**
     * Sets the customer name
     * 
     * @param customerName customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
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
     * Returns the active status
     * 
     * @return the active status
     */
    public boolean getActive() {
        return active.get();
    }

    /**
     * Sets the active status
     *
     * @param active active status to set
     */
    public void setActive(boolean active) {
        this.active.set(active);
    }

    /**
     * Returns the create date
     * 
     * @return the create date
     */
    public String getCreateDate() {
        return createDate.get();
    }

    /**
     * Sets the create date
     *
     * @param createDate create date to set
     */
    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    /**
     * Returns the create by user
     * 
     * @return the create by user
     */
    public String getCreatedBy() {
        return createdBy.get();
    }

    /**
     * Sets the create by user
     *
     * @param createdBy create by user to set
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
     * Returns last update user
     * 
     * @return last update user
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
