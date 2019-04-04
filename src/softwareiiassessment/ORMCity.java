package softwareiiassessment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


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
}
