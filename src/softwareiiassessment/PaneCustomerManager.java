package softwareiiassessment;

import java.util.ArrayList;
import java.util.Collections;
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
 * A Pane to manage customer
 *
 * @author lesaun
 */
public class PaneCustomerManager extends GridPane {
    private final Text headerText = new Text("Customers");
    private final Button addBtn = new Button("Add");
    private final Button modBtn = new Button("Modify");
    private final Button delBtn = new Button("Delete");
    private final Button canBtn = new Button("Cancel");
    private TableView<ORMCustomer> tableView;

    PaneCustomerManager(SQLAPI api) {

        // Set button widths
        this.addBtn.setPrefWidth(71);
        this.modBtn.setPrefWidth(71);
        this.delBtn.setPrefWidth(71);
        this.canBtn.setPrefWidth(71);

        constructTableView(api);

        // Add width constraint for columns
        ArrayList<Integer> colWidths = new ArrayList<>();
        colWidths.add(15);
        colWidths.addAll(Collections.nCopies(19, 29));
        colWidths.add(15);
        for (int colWidth : colWidths) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            getColumnConstraints().add(column);
        }

        // Add height constraint for rows
        for (int rowHeight : new int[]{15, 23, 6, 160, 6, 26, 24}) {
            RowConstraints row = new RowConstraints(rowHeight);
            getRowConstraints().add(row);
        }

        // Add items to grid
        add(headerText, 1, 1, 3, 1);
        add(tableView, 1, 3, 19, 1);
        add(addBtn, 8, 5, 3, 1);
        add(modBtn, 11, 5, 3, 1);
        add(delBtn, 14, 5, 3, 1);
        add(canBtn, 17, 5, 3, 1);
    }

    /**
     * Construct customer table view
     * 
     * @param api
     */
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

    /**
     * Set add button handler
     *
     * @param handler add button handler to set
     */
    public final void setAddBtnEvent(EventHandler<ActionEvent> handler) {
        this.addBtn.setOnAction(handler);
    }

    /**
     * Set mod button handler
     *
     * @param handler mod button handler to set
     */
    public final void setModBtnEvent(EventHandler<ActionEvent> handler) {
        this.modBtn.setOnAction(handler);
    }

    /**
     * Set delete customer button handler
     *
     * @param handler delete customer button handler
     */
    public final void setDelBtnEvent(EventHandler<ActionEvent> handler) {
        this.delBtn.setOnAction(handler);
    }

    /**
     * Set cancel button handler
     *
     * @param handler cancel button handler
     */
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.canBtn.setOnAction(handler);
    }

    /**
     * Set customers on table view
     *
     * @param customers customer to set on table view
     */
    public void updateCustomers(ObservableList<ORMCustomer> customers) {
        tableView.getItems().clear();
        tableView.setItems(customers);
    }

    /**
     * Return current customer selection
     *
     * @return current customer selection
     */
    public ORMCustomer getSelectedCustomer() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
