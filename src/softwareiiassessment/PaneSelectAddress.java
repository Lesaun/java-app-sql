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
 * Select address pane
 *
 * @author Lesaun
 */
public class PaneSelectAddress extends GridPane {
    private final Text headerText = new Text("Select Address");
    private final Button selBtn = new Button("Select");
    private final Button canBtn = new Button("Cancel");
    private TableView<ORMAddress> tableView = new TableView<>();

    PaneSelectAddress(SQLAPI api) {

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

    /**
     * Construct the addresses table view
     * 
     * @param api 
     */
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
     * Sets the cancel button handler
     *
     * @param handler handler to set
     */
    public final void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.canBtn.setOnAction(handler);
    }

    /**
     * Sets select button handler
     *
     * @param handler select button handler
     */
    public final void setSelectBtnEvent(EventHandler<ActionEvent> handler) {
        this.selBtn.setOnAction(handler);
    }

    /**
     * Return the selected address
     *
     * @return the selected address
     */
    public ORMAddress getSelectedAddress() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
