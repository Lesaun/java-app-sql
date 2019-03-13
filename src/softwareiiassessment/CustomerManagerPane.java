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
public class CustomerManagerPane extends GridPane {
    private final Button addBtn;
    private final Button modBtn;
    private final Button delBtn;
    private final Button canBtn;
    private TableView<Customer> tableView;
    

    /**
     * Constructs the pane with given title and TableView
     * 
     * @param customers
     */
    CustomerManagerPane(SQLAPI api) {

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
        
        TableColumn<Customer, String> activeCol = new TableColumn("Active");
        activeCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(data.getValue().getActive() ? "yes" : "no");
        });

        TableColumn customerNameCol = new TableColumn("Name");
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<Address, String>("customerName"));

        TableColumn<Customer, String> phoneCol = new TableColumn("Phone");
        phoneCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(api.getAddressById(data.getValue()
                    .getAddressId()).getPhone());
        });

        TableColumn<Customer, String> postalCodeCol = new TableColumn("Postal Code");
        postalCodeCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(api.getAddressById(data.getValue()
                    .getAddressId()).getPostalCode());
        });
        
        tableView.setItems(api.getCustomers());
        tableView.getColumns().addAll(activeCol, customerNameCol, phoneCol, postalCodeCol);
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Set handler for add button event
     * 
     * @param handler handler for add button event
     */
    public final void setAddBtnEvent(EventHandler<ActionEvent> handler) {
        this.addBtn.setOnAction(handler);
    }
    
    /**
     * Set handler for mod button event
     * 
     * @param handler handler for mod button event
     */
    public final void setModBtnEvent(EventHandler<ActionEvent> handler) {
        this.modBtn.setOnAction(handler);
    }
    
    /**
     * Set handler for delete button event
     * 
     * @param handler handler for delete button event
     */
    public final void setDelBtnEvent(EventHandler<ActionEvent> handler) {
        this.delBtn.setOnAction(handler);
    }
    
    /**
     * Set handler for delete button event
     * 
     * @param handler handler for delete button event
     */
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.canBtn.setOnAction(handler);
    }
    
    public void updateCustomers(ObservableList<Customer> customers) {
        tableView.getItems().clear();
        tableView.setItems(customers);
    }

    public Customer getSelectedCustomer() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
