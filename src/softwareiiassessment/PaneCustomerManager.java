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
public class PaneCustomerManager extends GridPane {
    private final Button addBtn;
    private final Button modBtn;
    private final Button delBtn;
    private final Button canBtn;
    private TableView<ORMCustomer> tableView;

    PaneCustomerManager(SQLAPI api) {

        // Construct add button
        this.addBtn = new Button();
        this.addBtn.setText("Add");
        this.addBtn.setPrefWidth(71);
       
        // Constuct modify button
        this.modBtn = new Button();
        this.modBtn.setText("Modify");
        this.modBtn.setPrefWidth(71);
        
        // Constuct delete button
        this.delBtn = new Button();
        this.delBtn.setText("Delete");
        this.delBtn.setPrefWidth(71);        
        
        // Constuct delete button
        this.canBtn = new Button();
        this.canBtn.setText("Cancel");
        this.canBtn.setPrefWidth(71);          

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
        this.add(new Text("Customers"), 1, 1, 3, 1);
        this.add(this.tableView, 1, 3, 19, 1);
        this.add(this.addBtn, 8, 5, 3, 1);
        this.add(this.modBtn, 11, 5, 3, 1);
        this.add(this.delBtn, 14, 5, 3, 1);
        this.add(this.canBtn, 17, 5, 3, 1);
    }
    
    private void constructTableView(SQLAPI api) {
        this.tableView = new TableView<>();
        
        TableColumn<ORMCustomer, String> activeCol = new TableColumn("Active");
        activeCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(data.getValue().getActive() ? "yes" : "no");
        });

        TableColumn customerNameCol = new TableColumn("Name");
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<ORMAddress, String>("customerName"));

        TableColumn<ORMCustomer, String> phoneCol = new TableColumn("Phone");
        phoneCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(api.getAddressById(data.getValue()
                    .getAddressId()).getPhone());
        });

        TableColumn<ORMCustomer, String> postalCodeCol = new TableColumn("Postal Code");
        postalCodeCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(api.getAddressById(data.getValue()
                    .getAddressId()).getPostalCode());
        });
        
        tableView.setItems(api.getCustomers());
        tableView.getColumns().addAll(activeCol, customerNameCol, phoneCol, postalCodeCol);
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public final void setAddBtnEvent(EventHandler<ActionEvent> handler) {
        this.addBtn.setOnAction(handler);
    }

    public final void setModBtnEvent(EventHandler<ActionEvent> handler) {
        this.modBtn.setOnAction(handler);
    }

    public final void setDelBtnEvent(EventHandler<ActionEvent> handler) {
        this.delBtn.setOnAction(handler);
    }

    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.canBtn.setOnAction(handler);
    }
    
    public void updateCustomers(ObservableList<ORMCustomer> customers) {
        tableView.getItems().clear();
        tableView.setItems(customers);
    }

    public ORMCustomer getSelectedCustomer() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
