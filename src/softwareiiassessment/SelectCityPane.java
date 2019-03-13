package softwareiiassessment;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * A Pane with TableView and controls
 * 
 * @author lesaun
 */
public class SelectCityPane extends GridPane {
    private final Button selBtn;
    private final Button canBtn;
    private TableView<City> tableView = new TableView<>();
    

    /**
     * Constructs the pane with given title and TableView
     * 
     * @param customers
     */
    SelectCityPane(SQLAPI api) {
       
        // Constuct delete button
        this.canBtn = new Button();
        this.canBtn.setText("Cancel");
        this.canBtn.setPrefWidth(71);        
        
        // Constuct delete button
        this.selBtn = new Button();
        this.selBtn.setText("Select");
        this.selBtn.setPrefWidth(71);          

        // Add width constraint for columns
        ColumnConstraints leftMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(leftMarginCol);

        for (int i = 0; i < 19; i++) {
            ColumnConstraints column = new ColumnConstraints(29);//29);
            this.getColumnConstraints().add(column);    
        }

        ColumnConstraints rightMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(rightMarginCol);
        
        
        // Add height constraint for rows
        for (int row_height : new int[]{15, 23, 6, 160, 6, 26, 24}) {
            RowConstraints row = new RowConstraints(row_height);
            this.getRowConstraints().add(row);
        }
        
        constructTableView(api);

        // Add items to grid
        this.add(new Text("Cities"), 1, 1, 3, 1);
        this.add(this.tableView, 1, 3, 19, 1);
        this.add(this.canBtn, 14, 5, 3, 1);
        this.add(this.selBtn, 17, 5, 3, 1);
    }
    
    private void constructTableView(SQLAPI api) {
        TableColumn cityNameCol = new TableColumn("City");
        cityNameCol.setCellValueFactory(
                new PropertyValueFactory<City, String>("city"));
        
        TableColumn<City, String> countryNameCol = new TableColumn("Country");
        countryNameCol.setCellValueFactory(data ->
            new SimpleStringProperty(api.getCountryById(data.getValue().getCountryId()).getCountry()));

        tableView.setItems(api.getCities());
        tableView.getColumns().addAll(cityNameCol, countryNameCol);
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * Set handler for cancel button event
     * 
     * @param handler handler for cancel button event
     */
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.canBtn.setOnAction(handler);
    }
    
    /**
     * Set handler for select button event
     * 
     * @param handler handler for select button event
     */
    public final void setSelectBtnEvent(EventHandler<ActionEvent> handler) {
        this.selBtn.setOnAction(handler);
    }
    
    public City getSelectedCity() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
