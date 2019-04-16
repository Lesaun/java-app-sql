package softwareiiassessment;

import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.property.SimpleStringProperty;
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
public class PaneSelectCustomer extends GridPane {
    private final Text headerText = new Text("Select Customer");
    private final Button selBtn = new Button("Select");
    private final Button canBtn = new Button("Cancel");
    private TableView<ORMCustomer> tableView = new TableView<>();

    PaneSelectCustomer(SQLAPI api) {

        // Set button widths
        this.canBtn.setPrefWidth(71);
        this.selBtn.setPrefWidth(71);

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
            this.getRowConstraints().add(row);
        }

        constructTableView(api);

        // Add items to grid
        this.add(headerText, 1, 1, 3, 1);
        this.add(tableView, 1, 3, 19, 1);
        this.add(canBtn, 14, 5, 3, 1);
        this.add(selBtn, 17, 5, 3, 1);
    }

    private void constructTableView(SQLAPI api) {
       this.tableView = new TableView<>();

        TableColumn<ORMCustomer, String> activeCol = new TableColumn("Active");
        activeCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(data.getValue().getActive() ? "yes" : "no");
        });

        TableColumn customerNameCol = new TableColumn("Name");
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));

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

    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.canBtn.setOnAction(handler);
    }

    public final void setSelectBtnEvent(EventHandler<ActionEvent> handler) {
        this.selBtn.setOnAction(handler);
    }

    public ORMCustomer getSelectedCustomer() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
