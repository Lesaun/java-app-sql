package softwareiiassessment;

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

    public PaneAddressCreateModify(SQLAPI api) {

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
        add(postalField, 4, 7, 3, 1);
        add(phonePrompt, 2, 8, 1, 1);
        add(phoneField, 4, 8, 3, 1);
        add(saveBtn, 6, 9, 2, 1);
        add(cancelBtn, 8, 9, 2, 1);
    }

    public final void setSaveBtnEvent(EventHandler<ActionEvent> handler) {
        this.saveBtn.setOnAction(handler);
    }

    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.cancelBtn.setOnAction(handler);
    }

    public ORMCity getSelectedCity() {
        return cityDropDown.getSelectionModel().getSelectedItem();
    }

    public void setCity(ORMCity city) {
        this.cityDropDown.setValue(city);
    }

    public ORMCountry getSelectedCountry() {
        return countryDropDown.getSelectionModel().getSelectedItem();
    }

    public void setCountry(ORMCountry country) {
        this.countryDropDown.setValue(country);
    }

    public void setIdTextField(String id) {
        idField.setText(id);
    }

    public String getAddress1TextField() {
        return address1Field.getText();
    }

    public void setAddress1TextField(String address1) {
        this.address1Field.setText(address1);
    }

    public String getAddress2TextField() {
        return address2Field.getText();
    }

    public void setAddress2TextField(String address2) {
        this.address2Field.setText(address2);
    }

    public String getPostalTextField() {
        return postalField.getText();
    }

    public void setPostalTextField(String postalCode) {
        this.postalField.setText(postalCode);
    }

    public String getPhoneTextField() {
        return phoneField.getText();
    }

    public void setPhoneTextField(String phone) {
        this.phoneField.setText(phone);
    }
}
