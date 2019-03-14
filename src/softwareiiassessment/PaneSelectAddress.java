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
public class PaneSelectAddress extends GridPane {
    private final Button selBtn;
    private final Button canBtn;
    private TableView<ORMAddress> tableView = new TableView<>();
    

    /**
     * Constructs the pane with given title and TableView
     * 
     * @param customers
     */
    PaneSelectAddress(SQLAPI api) {
       
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

        TableColumn address1Col = new TableColumn("Address 1");
        address1Col.setCellValueFactory(
                new PropertyValueFactory<ORMAddress, String>("address"));

        TableColumn address2Col = new TableColumn("Address 2");
        address2Col.setCellValueFactory(
                new PropertyValueFactory<ORMAddress, String>("address2"));

        TableColumn<ORMAddress, String> cityNameCol = new TableColumn("City");
        cityNameCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(api.getCityById(data.getValue()
                    .getCityId()).getCity());
        });

        TableColumn<ORMAddress, String> countryNameCol = new TableColumn("Country");
        countryNameCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    api.getCountryById(api.getCityById(data.getValue()
                            .getCityId()).getCountryId()).getCountry());
        });

        TableColumn postalCol = new TableColumn("Postal");
        postalCol.setCellValueFactory(
                new PropertyValueFactory<ORMAddress, String>("postalCode"));

        TableColumn phoneCol = new TableColumn("Phone");
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<ORMAddress, String>("phone"));

        tableView.setItems(api.getAddresses());
        tableView.getColumns().addAll(address1Col, address2Col, cityNameCol,
                countryNameCol, postalCol, phoneCol);
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
    
    public ORMAddress getSelectedAddress() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
