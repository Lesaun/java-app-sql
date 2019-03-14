package softwareiiassessment;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 * A Pane for modifying or adding parts to the inventory
 *
 * @author lesaun
 */
public class PaneAddressCreateModify extends GridPane {
    
    private final TextField idTextField = new TextField();

    private final TextField address1 = new TextField();
    private final TextField address2 = new TextField();
    private final TextField postalCode = new TextField();
    private final TextField phone = new TextField();
    
    private final ComboBox<ORMCountry> countryDropDown = new ComboBox<>();
    private final ComboBox<ORMCity> cityDropDown = new ComboBox<>();
       
    private final Text cityCountry = new Text("");

    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");
    
    private boolean citySelected = false;

    public PaneAddressCreateModify(SQLAPI api) {

        // Add prompt text to text fields
        idTextField.setPromptText("Auto Gen - Disabled");
        address1.setPromptText("Address Line 1");
        address2.setPromptText("Address Line 2");
        postalCode.setPromptText("Postal Code");
        phone.setPromptText("Phone");
        
        // Disable id text field
        idTextField.setDisable(true);
        
        countryDropDown.setItems(api.getCountries());
        countryDropDown.setConverter(new StringConverter<ORMCountry>() {
            @Override
            public String toString(ORMCountry country) {
                return country.getCountry();
            }

            @Override
            public ORMCountry fromString(String string) {
                return countryDropDown.getItems().stream().filter(ap -> 
                    ap.getCountry().equals(string)).findFirst().orElse(null);
            }
        });
        countryDropDown.valueProperty().addListener((ov, t, ti) -> {
            cityDropDown.getItems().clear();
            cityDropDown.setItems(api.getCountryCities(ti.getCountryId()));
        });

        cityDropDown.setConverter(new StringConverter<ORMCity>() {
            @Override
            public String toString(ORMCity city) {
                return city.getCity();
            }

            @Override
            public ORMCity fromString(String string) {
                return cityDropDown.getItems().stream().filter(ap -> 
                    ap.getCity().equals(string)).findFirst().orElse(null);
            }
        });


        // Add width constraint for columns
        ColumnConstraints leftMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(leftMarginCol);

        for (int i = 0; i < 9; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
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
        this.add(new Text("Country"), 2, 5, 1, 1);
        this.add(countryDropDown, 4, 5, 3, 1);
        this.add(new Text("City"), 2, 6, 3, 1);
        this.add(cityDropDown, 4, 6, 3, 1);
        this.add(new Text("Postal"), 2, 7, 1, 1);
        this.add(postalCode, 4, 7, 3, 1);
        this.add(new Text("Phone"), 2, 8, 1, 1);
        this.add(phone, 4, 8, 3, 1);   
        this.add(saveBtn, 6, 9, 2, 1);
        this.add(cancelBtn, 8, 9, 2, 1);
        
        //this.setGridLinesVisible(true);
    }
    
    public void setCity(ORMCity city) {
        this.cityDropDown.setValue(city);
    }
    
    public void setCountry(ORMCountry country) {
        this.countryDropDown.setValue(country);
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
    
    public ORMCity getSelectedCity() {
        return cityDropDown.getSelectionModel().getSelectedItem();
    }
    
    public ORMCountry getSelectedCountry() {
        return countryDropDown.getSelectionModel().getSelectedItem();
    }
    public boolean isCitySelected() {
        return citySelected;
    }
}
