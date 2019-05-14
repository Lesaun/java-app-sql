package softwareiiassessment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Object Relational Map of City
 *
 * @author Lesaun
 */
public class ORMCity {
    private final SimpleIntegerProperty cityId;
    private final SimpleStringProperty city;
    private final SimpleIntegerProperty countryId;

    ORMCity(int cityId, String city, int countryId, 
            String createDate, String createdBy, 
            int lastUpdate, String lastUpdateBy) {
        this.cityId = new SimpleIntegerProperty(cityId);
        this.city = new SimpleStringProperty(city);
        this.countryId = new SimpleIntegerProperty(countryId);
    }
 
    /**
     * Returns the city Id
     *
     * @return the city Id
     */
    public int getCityId() {
        return cityId.get();
    }

    /**
     * Sets the city Id
     * 
     * @param cityId city id to set
     */
    public void setCityId(int cityId) {
        this.cityId.set(cityId);
    }

    /**
     * Returns the city name
     *
     * @return the city name
     */
    public String getCity() {
        return city.get();
    }

    /**
     * Sets the city name
     * 
     * @param city city name to set
     */
    public void setCity(String city) {
        this.city.set(city);
    }

    /**
     * Returns the city Id
     *
     * @return the country id
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
}
