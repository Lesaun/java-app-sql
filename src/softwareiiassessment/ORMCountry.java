package softwareiiassessment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Object Relational Map of Country
 *
 * @author Lesaun
 */
public class ORMCountry {
    private final SimpleIntegerProperty countryId;
    private final SimpleStringProperty country;
    private final SimpleStringProperty createDate;
    private final SimpleStringProperty createdBy;
    private final SimpleIntegerProperty lastUpdate;
    private final SimpleStringProperty lastUpdateBy;

    ORMCountry(int countryId, String country,
               String createDate, String createdBy,
               int lastUpdate, String lastUpdateBy) {
        this.countryId = new SimpleIntegerProperty(countryId);
        this.country = new SimpleStringProperty(country);

        this.createDate = new SimpleStringProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleIntegerProperty(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    /**
     * Returns the country id
     *
     * @return
     */
    public int getCountryId() {
        return countryId.get();
    }

    /**
     * Sets the country id
     *
     * @param countryId country id to set
     */
    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    /**
     * Returns the country name
     * 
     * @return the country name
     */
    public String getCountry() {
        return country.get();
    }

    /**
     * Sets the country name
     * 
     * @param country country name to set
     */
    public void setCountry(String country) {
        this.country.set(country);
    }

    /**
     * Returns the create date
     * 
     * @return set the create date
     */
    public String getCreateDate() {
        return createDate.get();
    }

    /**
     * Sets the create date
     * 
     * @param createDate the create date to set
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
     * Returns the last update by user
     * 
     * @return Sets the last update by user
     */
    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    /**
     * Sets the last update by user
     * 
     * @param lastUpdateBy the last update by user to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }
}
