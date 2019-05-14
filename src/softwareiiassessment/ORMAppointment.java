package softwareiiassessment;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Object Relational Map of Appointment
 *
 * @author Lesaun
 */
public class ORMAppointment {
    private final SimpleIntegerProperty appointmentId;
    private final SimpleIntegerProperty customerId;
    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty type;
    private final SimpleStringProperty description;
    private final SimpleStringProperty location;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty url;
    private LocalDateTime start;
    private LocalDateTime end;

    private final SimpleStringProperty createDate;
    private final SimpleStringProperty createdBy;
    private final SimpleIntegerProperty lastUpdate;
    private final SimpleStringProperty lastUpdateBy;

    ORMAppointment(int appointmentId, int customerId, int userId, String title, String type,
            String description, String location, String contact, String url,
            LocalDateTime start, LocalDateTime end, String createDate,
            String createdBy, int lastUpdate, String lastUpdateBy) {
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.userId = new SimpleIntegerProperty(userId);
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.url = new SimpleStringProperty(url);
        this.start = start;
        this.end = end;

        this.createDate = new SimpleStringProperty(createDate);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = new SimpleIntegerProperty(lastUpdate);
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    /**
     * Returns the appointment id
     * 
     * @return the appointment id
     */
    public int getAppointmentId() {
        return appointmentId.get();
    }

    /**
     * Sets the appointment id
     *
     * @param addressId the appointment id to set
     */
    public void setAppointmentId(int addressId) {
        this.appointmentId.set(addressId);
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
     * @param createDate the create date to set
     */
    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
    }

    /**
     * Returns the create user 
     * 
     * @return the create user 
     */
    public String getCreatedBy() {
        return createdBy.get();
    }

    /**
     * Sets the create user
     * 
     * @param createdBy the create user to set
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
     * @param lastUpdate the last update time to set
     */
    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }
    
    /**
     * Returns the user id
     * 
     * @return the user id
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Sets the user id
     * 
     * @param userId the user id to set
     */
    public void setUserId(int userId) {
        this.userId.set(userId);
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
     * @param lastUpdateBy the last update user to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
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
     * @param customerId customer id to set
     */
    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    /**
     * Returns the title
     * 
     * @return the title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Sets the title
     * 
     * @param title title to set
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Returns the type
     * 
     * @return the type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Sets the type
     * 
     * @param type type to set
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Returns the description
     * 
     * @return the description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Sets the description
     * 
     * @param description description to set
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Returns the location
     * 
     * @return the location
     */
    public String getLocation() {
        return location.get();
    }

    /**
     * Sets the location
     * 
     * @param location location to set
     */
    public void setLocation(String location) {
        this.location.set(location);
    }

    /**
     * Returns the contact
     * 
     * @return the contact
     */
    public String getContact() {
        return contact.get();
    }

    /**
     * Sets the contact
     * 
     * @param contact contact to set
     */
    public void setContact(String contact) {
        this.contact.set(contact);
    }

    /**
     * Returns the url
     * 
     * @return the url
     */
    public String getUrl() {
        return url.get();
    }

    /**
     * Sets the url
     * 
     * @param url url to set
     */
    public void setUrl(String url) {
        this.url.set(url);
    }

    /**
     * Returns the start
     * 
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the start
     * 
     * @param start start to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Returns the end
     * 
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the end
     * 
     * @param end end to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
