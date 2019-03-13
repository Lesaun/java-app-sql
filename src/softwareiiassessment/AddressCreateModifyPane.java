package softwareiiassessment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * A Pane for modifying or adding parts to the inventory
 *
 * @author lesaun
 */
public class AddressCreateModifyPane extends GridPane {
    
    private final TextField idTextField = new TextField();

    private final TextField address1 = new TextField();
    private final TextField address2 = new TextField();
    private final TextField postalCode = new TextField();
    private final TextField phone = new TextField();
    
    private final Button cityAdd = new Button("Add");
    private final Button cityEdit = new Button("Edit");
    private final Button citySelect = new Button("Select");
    
    private City selectedCity;
    private Country selectedCountry;
    
    private final Text cityCountry = new Text("");

    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");
    
    private boolean citySelected = false;
    
    

    /**
     * Constructs the Pane
     */
    public AddressCreateModifyPane() {

        // Add prompt text to text fields
        idTextField.setPromptText("Auto Gen - Disabled");
        address1.setPromptText("Address Line 1");
        address2.setPromptText("Address Line 2");
        postalCode.setPromptText("Postal Code");
        phone.setPromptText("Phone");
        
        // Disable id text field
        idTextField.setDisable(true);

        // Add width constraint for columns
        ColumnConstraints leftMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(leftMarginCol);

        for (int i = 0; i < 9; i++) {
            ColumnConstraints column = new ColumnConstraints(50);//29);
            this.getColumnConstraints().add(column);    
        }

        ColumnConstraints rightMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(rightMarginCol);

        // Add height constraint for rows
        for (int row_height : new int[]{15,50,50,50,50,50,50,50,50,50,15}) {
            RowConstraints row = new RowConstraints(row_height);
            this.getRowConstraints().add(row);
        }

        // Add controls/fields onto grid pane
        this.add(new Text("Address"), 1, 1, 2, 1);
        this.add(new Text("ID"), 2, 2, 1, 1);
        this.add(idTextField, 4, 2, 3, 1);
        this.add(new Text("Address 1"), 2, 3, 1, 1);
        this.add(address1, 4, 3, 3, 1);
        this.add(new Text("Address 2"), 2, 4, 1, 1);
        this.add(address2, 4, 4, 3, 1);
        this.add(new Text("City"), 2, 5, 1, 1);
        this.add(cityAdd, 4, 5, 1, 1);
        this.add(cityEdit, 5, 5, 1, 1);
        this.add(citySelect, 6, 5, 2, 1);
        this.add(cityCountry, 2, 6, 3, 1);
        this.add(new Text("Postal"), 2, 7, 1, 1);
        this.add(postalCode, 4, 7, 3, 1);
        this.add(new Text("Phone"), 2, 8, 1, 1);
        this.add(phone, 4, 8, 3, 1);   
        this.add(saveBtn, 6, 9, 2, 1);
        this.add(cancelBtn, 8, 9, 2, 1);
        
        //this.setGridLinesVisible(true);
    }
    
    public void setCityCountry(String cityCountry) {
        this.cityCountry.setText(cityCountry);
    }

    /**
     * Sets the event handler for the save button
     * 
     * @param handler the event handler for the save button
     */
    public final void setSaveBtnEvent(EventHandler<ActionEvent> handler) {
        this.saveBtn.setOnAction(handler);
    }
    
    /**
     * Sets the event handler for the cancel button
     * 
     * @param handler the event handler for the cancel button
     */
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.cancelBtn.setOnAction(handler);
    }

    /**
     * Sets the event handler for the address add button
     * 
     * @param handler the event handler for the save button
     */
    public final void setCityAddEvent(EventHandler<ActionEvent> handler) {
        this.cityAdd.setOnAction(handler);
    }
    
    /**
     * Sets the event handler for the address edit button
     * 
     * @param handler the event handler for the cancel button
     */
    public final void setCityEditEvent(EventHandler<ActionEvent> handler) {
        this.cityEdit.setOnAction(handler);
    }
  
    /**
     * Sets the event handler for the address select button
     * 
     * @param handler the event handler for the cancel button
     */
    public final void setCitySelectEvent(EventHandler<ActionEvent> handler) {
        this.citySelect.setOnAction(handler);
    }
    
    /**
     * Sets the value in the ID Text Field
     * 
     * @param id the value to set in ID Text Field
     */
    public void setIdTextField(String id) {
        idTextField.setText(id);
    }

    /**
     * Returns the string value in the name Text Field
     * 
     * @return the string value in the name Text Field
     */
    public String getAddress1TextField() {
        return address1.getText();
    }

    /**
     * Sets the value in the name Text Field
     * 
     * @param address1 the value to set in ID Text Field
     */
    public void setAddress1TextField(String address1) {
        this.address1.setText(address1);
    }

    /**
     * Returns the string value in the name Text Field
     * 
     * @return the string value in the name Text Field
     */
    public String getAddress2TextField() {
        return address2.getText();
    }

    /**
     * Sets the value in the name Text Field
     * 
     * @param address2 the value to set in ID Text Field
     */
    public void setAddress2TextField(String address2) {
        this.address2.setText(address2);
    }
    
        /**
     * Returns the string value in the name Text Field
     * 
     * @return the string value in the name Text Field
     */
    public String getPostalTextField() {
        return postalCode.getText();
    }

    /**
     * Sets the value in the name Text Field
     * 
     * @param postalCode the value to set in ID Text Field
     */
    public void setPostalTextField(String postalCode) {
        this.postalCode.setText(postalCode);
    }
    
    /**
     * Returns the string value in the name Text Field
     * 
     * @return the string value in the name Text Field
     */
    public String getPhoneTextField() {
        return phone.getText();
    }

    /**
     * Sets the value in the name Text Field
     * 
     * @param phone the value to set in ID Text Field
     */
    public void setPhoneTextField(String phone) {
        this.phone.setText(phone);
    }
    
    public City getSelectedCity() {
        return selectedCity;
    }
    
    public Country getSelectedCountry() {
        return selectedCountry;
    }
    
    public void setSelectedCityCountry(City city, Country country) {
        this.citySelected = true;
        this.selectedCity = city;
        this.selectedCountry = country;
        this.setCityCountry(city.getCity() + ", " + country.getCountry());
    }
    
    public boolean isCitySelected() {
        return citySelected;
    }
}
