package softwareiiassessment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * A Pane for modifying or adding parts to the inventory
 *
 * @author lesaun
 */
public class CustomerCreateModifyPane extends GridPane {
    private final RadioButton activeRadio = new RadioButton("Active");
    private final RadioButton disabledRadio = new RadioButton("Disabled");
    private final ToggleGroup radioGroup = new ToggleGroup();
    private boolean active = true;
    
    private final TextField idTextField = new TextField();
    private final TextField nameTextField = new TextField();
    
    private final Button addressAdd = new Button("Add");
    private final Button addressEdit = new Button("Edit");
    private final Button addressSelect = new Button("Select");
    
    private Address address;
    private final Text address1 = new Text("");
    private final Text address2 = new Text("");
    private final Text cityCountry = new Text("");
    private final Text postalCode = new Text("");
    private final Text phone = new Text("");

    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");
    
    

    /**
     * Constructs the Pane
     */
    public CustomerCreateModifyPane() {
        // Setup toggle group
        activeRadio.setId("active");
        disabledRadio.setId("disabled");
        activeRadio.setToggleGroup(radioGroup);
        disabledRadio.setToggleGroup(radioGroup);
        activeRadio.setSelected(true);

        // Add prompt text to text fields
        idTextField.setPromptText("Auto Gen - Disabled");
        nameTextField.setPromptText("Customer Name");
        
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
        for (int row_height : new int[]{15,50,50,50,50,40,40,40,40,40,50,15}) {
            RowConstraints row = new RowConstraints(row_height);
            this.getRowConstraints().add(row);
        }

        // Add controls/fields onto grid pane
        this.add(new Text("Customer"), 1, 1, 2, 1);
        this.add(this.activeRadio, 3, 1, 2, 1);
        this.add(this.disabledRadio, 5, 1, 2, 1);
        this.add(new Text("ID"), 2, 2, 1, 1);
        this.add(idTextField, 4, 2, 3, 1);
        this.add(new Text("Name"), 2, 3, 1, 1);
        this.add(nameTextField, 4, 3, 3, 1);
        this.add(new Text("Address"), 2, 4, 1, 1);
        this.add(addressAdd, 5, 4, 1, 1);
        this.add(addressEdit, 6, 4, 1, 1);
        this.add(addressSelect, 7, 4, 2, 1);
        this.add(address1, 2, 5, 3, 1);
        this.add(address2, 2, 6, 3, 1);
        this.add(cityCountry, 2, 7, 3, 1);
        this.add(postalCode, 2, 8, 3, 1);
        this.add(phone, 2, 9, 3, 1);   
        this.add(saveBtn, 6, 10, 2, 1);
        this.add(cancelBtn, 8, 10, 2, 1);
        
        //this.setGridLinesVisible(true);

        // Add event to change variable text between in-house and outsourced
        radioGroup.selectedToggleProperty().addListener((ov, ot, new_toggle) -> {
            RadioButton selectedBtn = (RadioButton) new_toggle;
            if (selectedBtn.getId().equals("disabled")) {
                active = false;
            } else {
                active = true;
            }
        });
    }
    
    public void setAddress(Address address, City city, Country country) {
        this.address = address;
        this.address1.setText(address.getAddress());
        this.address2.setText(address.getAddress2());
        this.cityCountry.setText(city.getCity() + ", " + country.getCountry());
        this.postalCode.setText(address.getPostalCode());
        this.phone.setText(address.getPhone());
    }
    
    public Address getAddress() {
        return address;
    }

    /**
     * Check if fields contain a valid customer
     * 
     * @return if customer is valid
     */
    public boolean hasValidCustomer() {
        return true;
    }
    
    /**
     * Displays errors with current fields
     */
    public void displayErrors() {

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
    public final void setAddressAddEvent(EventHandler<ActionEvent> handler) {
        this.addressAdd.setOnAction(handler);
    }
    
    /**
     * Sets the event handler for the address edit button
     * 
     * @param handler the event handler for the cancel button
     */
    public final void setAddressEditEvent(EventHandler<ActionEvent> handler) {
        this.addressEdit.setOnAction(handler);
    }
  
    /**
     * Sets the event handler for the address select button
     * 
     * @param handler the event handler for the cancel button
     */
    public final void setAddressSelectEvent(EventHandler<ActionEvent> handler) {
        this.addressSelect.setOnAction(handler);
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
    public String getNameTextField() {
        return nameTextField.getText();
    }

    /**
     * Sets the value in the name Text Field
     * 
     * @param name the value to set in ID Text Field
     */
    public void setNameTextField(String name) {
        nameTextField.setText(name);
    }

    /**
     * Returns if customer is active
     * 
     * @return if customer is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets customer to active or disabled
     * 
     * @param active if customer is active
     */
    public void setActive(boolean active) {      
        if (active) {
            activeRadio.setSelected(true);
        } else {
            disabledRadio.setSelected(true);
        }

        this.active = active;
    }
    
    
}

