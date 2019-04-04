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


public class PaneCustomerCreateModify extends GridPane {
    private final RadioButton activeRadio = new RadioButton("Active");
    private final RadioButton disabledRadio = new RadioButton("Disabled");
    private final ToggleGroup radioGroup = new ToggleGroup();
    private boolean active = true;
    
    private final TextField idTextField = new TextField();
    private final TextField nameTextField = new TextField();
    
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
    

    public PaneCustomerCreateModify() {
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
    
    public void setAddress(ORMAddress address, ORMCity city, ORMCountry country) {
        this.address = address;
        this.address1.setText(address.getAddress());
        this.address2.setText(address.getAddress2());
        this.cityCountry.setText(city.getCity() + ", " + country.getCountry());
        this.postalCode.setText(address.getPostalCode());
        this.phone.setText(address.getPhone());
    }
    
    public ORMAddress getAddress() {
        return address;
    }

    public boolean hasValidCustomer() {
        return true;
    }
    
    public void displayErrors() {

    }

    public final void setSaveBtnEvent(EventHandler<ActionEvent> handler) {
        this.saveBtn.setOnAction(handler);
    }
    
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.cancelBtn.setOnAction(handler);
    }

    public final void setAddressAddEvent(EventHandler<ActionEvent> handler) {
        this.addressAdd.setOnAction(handler);
    }
    
    public final void setAddressEditEvent(EventHandler<ActionEvent> handler) {
        this.addressEdit.setOnAction(handler);
    }
  
    public final void setAddressSelectEvent(EventHandler<ActionEvent> handler) {
        this.addressSelect.setOnAction(handler);
    }
    
    public void setIdTextField(String id) {
        idTextField.setText(id);
    }

    public String getNameTextField() {
        return nameTextField.getText();
    }

    public void setNameTextField(String name) {
        nameTextField.setText(name);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {      
        if (active) {
            activeRadio.setSelected(true);
        } else {
            disabledRadio.setSelected(true);
        }

        this.active = active;
    }    
}

