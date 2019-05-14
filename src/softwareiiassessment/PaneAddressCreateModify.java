package softwareiiassessment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 * Pane to create and modify addresses
 *
 * @author Lesaun
 */
public class PaneAddressCreateModify extends GridPane {

    private final Text headerText = new Text("Address");
    private final Text idPrompt = new Text("ID");
    private final TextField idField = new TextField();
    private final Text address1Prompt = new Text("Address 1");
    private final TextField address1Field = new TextField();
    private final Text address2Prompt = new Text("Address 2");
    private final TextField address2Field = new TextField();
    private final Text postalPrompt = new Text("Postal");
    private final TextField postalField = new TextField();
    private final Text phonePrompt = new Text("Phone");
    private final TextField phoneField = new TextField();

    private final Text countryPrompt = new Text("Country");
    private final ComboBox<ORMCountry> countryDropDown = new ComboBox<>();
    private final Text cityPrompt = new Text("City");
    private final ComboBox<ORMCity> cityDropDown = new ComboBox<>();

    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");

    PaneAddressCreateModify(SQLAPI api) {

        // Add prompt text to text fields
        idField.setPromptText("Auto Gen - Disabled");
        address1Field.setPromptText("Address Line 1");
        address2Field.setPromptText("Address Line 2");
        postalField.setPromptText("Postal Code");
        phoneField.setPromptText("Phone");

        // Disable id text field
        idField.setDisable(true);

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
        for (int colWidth : new int[]{15,50,50,50,50,50,50,50,50,50,15}) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            this.getColumnConstraints().add(column);
        }

        // Add height constraint for rows
        for (int rowHeight : new int[]{15,50,50,50,50,50,50,50,50,50,15}) {
            RowConstraints row = new RowConstraints(rowHeight);
            this.getRowConstraints().add(row);
        }

        // Add controls/fields onto grid pane
        add(headerText, 1, 1, 2, 1);
        add(idPrompt, 2, 2, 1, 1);
        add(idField, 4, 2, 3, 1);
        add(address1Prompt, 2, 3, 1, 1);
        add(address1Field, 4, 3, 3, 1);
        add(address2Prompt, 2, 4, 1, 1);
        add(address2Field, 4, 4, 3, 1);
        add(countryPrompt, 2, 5, 1, 1);
        add(countryDropDown, 4, 5, 3, 1);
        add(cityPrompt, 2, 6, 3, 1);
        add(cityDropDown, 4, 6, 3, 1);
        add(postalPrompt, 2, 7, 1, 1);
        add(postalField, 3, 7, 2, 1);
        add(phonePrompt, 2, 8, 1, 1);
        add(phoneField, 3, 8, 2, 1);
        add(saveBtn, 6, 9, 2, 1);
        add(cancelBtn, 8, 9, 2, 1);
    }

    /**
     * Show postal error message
     */
    public void showPostalError() {
        Text postalErrorText = new Text("must be 5 digits");
        postalErrorText.setFill(Color.RED);
        add(postalErrorText, 5, 7, 3, 1);
    }

    /**
     * Show postal error message
     */
    public void showPhoneError() {
        Text postalErrorText = new Text("format must be 9999999999 or 999-999-9999");
        postalErrorText.setFill(Color.RED);
        add(postalErrorText, 5, 8, 3, 1);
    }

    /**
     * Sets hander for save button
     * 
     * @param handler handler to set
     */
    public final void setSaveBtnEvent(EventHandler<ActionEvent> handler) {
        this.saveBtn.setOnAction(handler);
    }

    /**
     * Sets hander for cancel button
     * 
     * @param handler handler to set
     */
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.cancelBtn.setOnAction(handler);
    }

    /**
     * Return selected city
     * 
     * @return selected city
     */
    public ORMCity getSelectedCity() {
        return cityDropDown.getSelectionModel().getSelectedItem();
    }

    /**
     * Sets city name
     * 
     * @param city city name to set
     */
    public void setCity(ORMCity city) {
        this.cityDropDown.setValue(city);
    }

    /**
     * Returns selected country
     *
     * @return selected country
     */
    public ORMCountry getSelectedCountry() {
        return countryDropDown.getSelectionModel().getSelectedItem();
    }

    /**
     * Sets country
     * 
     * @param country country to set
     */
    public void setCountry(ORMCountry country) {
        this.countryDropDown.setValue(country);
    }

    /**
     * Sets address id
     *
     * @param id address id to set
     */
    public void setIdTextField(String id) {
        idField.setText(id);
    }

    /**
     * Returns address line 1
     *
     * @return address line 1
     */
    public String getAddress1TextField() {
        return address1Field.getText();
    }

    /**
     * Sets address line 1
     *
     * @param address1 address line 1 to set
     */
    public void setAddress1TextField(String address1) {
        this.address1Field.setText(address1);
    }

    /**
     * Returns address line 2
     *
     * @return address line 2
     */
    public String getAddress2TextField() {
        return address2Field.getText();
    }

    /**
     * Sets address line 2
     * 
     * @param address2 address line 2 to set
     */
    public void setAddress2TextField(String address2) {
        this.address2Field.setText(address2);
    }

    /**
     * Return the postal code
     *
     * @return the postal code
     */
    public String getPostalTextField() {
        return postalField.getText();
    }

    /**
     * Sets the postal code
     *
     * @param postalCode postal code to set
     */
    public void setPostalTextField(String postalCode) {
        this.postalField.setText(postalCode);
    }

    /**
     * Checks if postal code is valid
     *
     * @return is postal code is valid
     */
    public boolean isValidPostalCode() {
        boolean retVal = false;

        if (postalField.getText().matches("\\d{5}")) {
            retVal = true;
        }

        return retVal;
    }


    /**
     * Returns the phone number
     * 
     * @return the phone number
     */
    public String getPhoneTextField() {
        return phoneField.getText();
    }

    /**
     * Check if valid phone number
     *
     * @return is phone valid
     */
    public boolean isValidPhone() {
        boolean retVal = false;

        if (phoneField.getText().matches("\\d{10}")) retVal = true;
        if (phoneField.getText().matches("\\d{3}-\\d{3}-\\d{4}"))
            retVal = true;

        return retVal;
    }

    /**
     * Sets the phone number
     *
     * @param phone phone number to set
     */
    public void setPhoneTextField(String phone) {
        this.phoneField.setText(phone);
    }
}
