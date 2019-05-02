package softwareiiassessment;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class ORMUser {
    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty userName;

    ORMUser(int userId, String userName) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
    }

    public int getUserId() {
        return userId.get();
    }

    public String getUserName() {
        return userName.get();
    }
}
