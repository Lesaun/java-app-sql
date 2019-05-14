package softwareiiassessment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Object Relational Map of User
 *
 * @author Lesaun
 */
public class ORMUser {
    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty userName;

    ORMUser(int userId, String userName) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
    }

    /**
     * Returns user id
     * 
     * @return user id
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Returns user name
     * 
     * @return user name
     */
    public String getUserName() {
        return userName.get();
    }
}
