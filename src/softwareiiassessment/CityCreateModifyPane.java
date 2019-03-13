package softwareiiassessment;

import javafx.collections.ObservableList;
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
class CityCreateModifyPane extends GridPane {
    
    private final TextField idTextField = new TextField();
    private final TextField cityName = new TextField();
    private final ComboBox<Country> countryDropDown = new ComboBox<>();
    
    private final Button countryAdd = new Button("Add");
    private final Button countryEdit = new Button("Edit");
    
    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");


    /**
     * Constructs the Pane
     */
    CityCreateModifyPane(ObservableList<Country> countries) {

        // Add prompt text to text fields
        idTextField.setPromptText("Auto Gen - Disabled");
        cityName.setPromptText("City Name");

        // Disable id text field
        idTextField.setDisable(true);

        // set countries on dropdown, and 
        countryDropDown.setItems(countries);
        countryDropDown.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(String string) {
                return countryDropDown.getItems().stream().filter(ap -> 
                    ap.getCountry().equals(string)).findFirst().orElse(null);
            }
        });

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
        for (int row_height : new int[]{15,50,50,50,50,50,50,15}) {
            RowConstraints row = new RowConstraints(row_height);
            this.getRowConstraints().add(row);
        }

        // Add controls/fields onto grid pane
        this.add(new Text("City"), 1, 1, 2, 1);
        this.add(new Text("ID"), 2, 2, 1, 1);
        this.add(idTextField, 4, 2, 3, 1);
        this.add(new Text("Name"), 2, 3, 1, 1);
        this.add(cityName, 4, 3, 3, 1);
        this.add(new Text("Country"), 2, 4, 1, 1);
        this.add(countryAdd, 4, 4, 1, 1);
        this.add(countryEdit, 5, 4, 1, 1);
        this.add(countryDropDown, 2, 5, 3, 1);
        this.add(saveBtn, 6, 6, 2, 1);
        this.add(cancelBtn, 8, 6, 2, 1);
        
        //this.setGridLinesVisible(true);
    }
    
    /**
     *
     * @param country
     */
    public void setCountry(Country country) {
        this.countryDropDown.setValue(country);
    }

    public Country getCountry() {
        return countryDropDown.getValue();
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
    public final void setCountryAddEvent(EventHandler<ActionEvent> handler) {
        this.countryAdd.setOnAction(handler);
    }
    
    /**
     * Sets the event handler for the address edit button
     * 
     * @param handler the event handler for the cancel button
     */
    public final void setCountryEditEvent(EventHandler<ActionEvent> handler) {
        this.countryEdit.setOnAction(handler);
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
        return cityName.getText();
    }

    /**
     * Sets the value in the name Text Field
     * 
     * @param address1 the value to set in ID Text Field
     */
    public void setNameTextField(String cityName) {
        this.cityName.setText(cityName);
    }
    
    public void updateCountries(ObservableList<Country> countries) {
        countryDropDown.getItems().clear();
        countryDropDown.setItems(countries);
    }
    
    public boolean isCountrySelected() {
        return !countryDropDown.getSelectionModel().isEmpty();
    }
}

