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
 * Pane to create and modify customers
 * 
 * @author Lesaun
 */
public class PaneCustomerCreateModify extends GridPane {
    private final RadioButton activeRadio = new RadioButton("Active");
    private final RadioButton disabledRadio = new RadioButton("Disabled");
    private final ToggleGroup radioGroup = new ToggleGroup();
    private boolean active = true;

    private final Text headerText = new Text("Customer");
    private final Text idPrompt = new Text("ID");
    private final TextField idTextField = new TextField();
    private final Text namePrompt = new Text("Name");
    private final TextField nameTextField = new TextField();

    private final Text addressPrompt = new Text("Address");
    private final Button addressAdd = new Button("Add");
    private final Button addressEdit = new Button("Edit");
    private final Button addressSelect = new Button("Select");

    private ORMAddress address;
    private final Text address1 = new Text("");
    private final Text address2 = new Text("");
    private final Text cityCountry = new Text("");
    private final Text postalCode = new Text("");
    private final Text phone = new Text("");

    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");

    
    PaneCustomerCreateModify() {
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

        // Add event to change variable text between in-house and outsourced
        radioGroup.selectedToggleProperty().addListener((ov, ot, new_toggle) -> {
            RadioButton selectedBtn = (RadioButton) new_toggle;
            if (selectedBtn.getId().equals("disabled")) {
                active = false;
            } else {
                active = true;
            }
        });

        // Add width constraint for columns
        for (int colWidth : new int[]{15,50,50,50,50,50,50,50,50,50,15}) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            this.getColumnConstraints().add(column);
        }

        // Add height constraint for rows
        for (int rowHeight : new int[]{15,40,40,40,40,40,40,40,40,40,40,15}) {
            RowConstraints row = new RowConstraints(rowHeight);
            this.getRowConstraints().add(row);
        }

        // Add controls/fields onto grid pane
        add(headerText, 1, 1, 2, 1);
        add(activeRadio, 3, 1, 2, 1);
        add(disabledRadio, 5, 1, 2, 1);
        add(idPrompt, 2, 2, 1, 1);
        add(idTextField, 4, 2, 3, 1);
        add(namePrompt, 2, 3, 1, 1);
        add(nameTextField, 4, 3, 3, 1);
        add(addressPrompt, 2, 4, 1, 1);
        add(addressAdd, 5, 4, 1, 1);
        add(addressEdit, 6, 4, 1, 1);
        add(addressSelect, 7, 4, 2, 1);
        add(address1, 2, 5, 3, 1);
        add(address2, 2, 6, 3, 1);
        add(cityCountry, 2, 7, 3, 1);
        add(postalCode, 2, 8, 3, 1);
        add(phone, 2, 9, 3, 1);
        add(saveBtn, 6, 10, 2, 1);
        add(cancelBtn, 8, 10, 2, 1);
    }

    /**
     * Sets address of customer
     *
     * @param address address of customer
     * @param city city of customer
     * @param country country of customer
     */
    public void setAddress(ORMAddress address, ORMCity city, ORMCountry country) {
        this.address = address;
        this.address1.setText(address.getAddress());
        this.address2.setText(address.getAddress2());
        this.cityCountry.setText(city.getCity() + ", " + country.getCountry());
        this.postalCode.setText(address.getPostalCode());
        this.phone.setText(address.getPhone());
    }

    /**
     * Returns the address of customer
     *
     * @return the address of customer
     */
    public ORMAddress getAddress() {
        return address;
    }

    /**
     * Sets the save button event handler
     * 
     * @param handler save button event handler
     */
    public final void setSaveBtnEvent(EventHandler<ActionEvent> handler) {
        this.saveBtn.setOnAction(handler);
    }

    /**
     * Sets the cancel button handler
     *
     * @param handler cancel button handler to set
     */
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.cancelBtn.setOnAction(handler);
    }

    /**
     * Sets the address add handler
     *
     * @param handler sets the address add handler
     */
    public final void setAddressAddEvent(EventHandler<ActionEvent> handler) {
        this.addressAdd.setOnAction(handler);
    }

    /**
     * Sets the address edit button handler
     *
     * @param handler address edit button handler to set
     */
    public final void setAddressEditEvent(EventHandler<ActionEvent> handler) {
        this.addressEdit.setOnAction(handler);
    }

    /**
     * Sets the address select button handler
     *
     * @param handler address select button handler
     */
    public final void setAddressSelectEvent(EventHandler<ActionEvent> handler) {
        this.addressSelect.setOnAction(handler);
    }

    /**
     * Sets the customer id
     *
     * @param id customer id to set
     */
    public void setIdTextField(String id) {
        idTextField.setText(id);
    }

    /**
     * Return the customer name
     *
     * @return the customer name
     */
    public String getNameTextField() {
        return nameTextField.getText();
    }

    /**
     * Sets the customer name
     *
     * @param name the customer name to set
     */
    public void setNameTextField(String name) {
        nameTextField.setText(name);
    }

    /**
     * Return if customer is active
     *
     * @return if customer is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set customer active or disabled
     *
     * @param active customer active boolean
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
