package softwareiiassessment;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
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
public class PaneCalendar extends GridPane {
    private TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

    private final RadioButton weekRadio = new RadioButton("Week");
    private final RadioButton monthRadio = new RadioButton("Month");
    private final ToggleGroup radioGroup = new ToggleGroup();
    private boolean weekView = true;

    private final SQLAPI api;
    private LocalDateTime currentDate;
    private final Text calLocation;

    private final Button calPrev;
    private final Button calNext;

    private final Button manageCustomersBtn;

    private final Button addBtn;
    private final Button modBtn;
    private final Button delBtn;
    private final Button logBtn;
    private TableView<ORMAppointment> tableView;

    PaneCalendar(SQLAPI api, LocalDateTime startDate) {
        // Setup toggle group
        weekRadio.setId("week");
        monthRadio.setId("month");
        weekRadio.setToggleGroup(radioGroup);
        monthRadio.setToggleGroup(radioGroup);
        weekRadio.setSelected(true);

        this.api = api;

        // Construct button
        this.calPrev = new Button();
        this.calPrev.setText("<");

        // Construct button
        this.calNext = new Button();
        this.calNext.setText(">");

        // Construct add button
        this.manageCustomersBtn = new Button();
        this.manageCustomersBtn.setText("Manage Customers");
        this.manageCustomersBtn.setPrefWidth(125);

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
        this.logBtn = new Button();
        this.logBtn.setText("Logout");
        this.logBtn.setPrefWidth(71);          

        // Add width constraint for columns
        ColumnConstraints leftMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(leftMarginCol);

        for (int i = 0; i < 25; i++) {
            ColumnConstraints column = new ColumnConstraints(29);//29);
            this.getColumnConstraints().add(column);    
        }

        ColumnConstraints rightMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(rightMarginCol);

        // Add height constraint for rows
        for (int row_height : new int[]{15, 23, 6, 26, 6, 260, 6, 26, 24}) {
            RowConstraints row = new RowConstraints(row_height);
            this.getRowConstraints().add(row);
        }
        
        constructTableView();
        currentDate = startDate;
        calLocation = new Text(
                currentDate.with(DayOfWeek.MONDAY).toLocalDate().toString() +
                " - " +
                currentDate.with(DayOfWeek.FRIDAY).toLocalDate().toString());
        setTableViewToWeekOfYear(currentDate.get(woy), currentDate.getYear());

        calPrev.setOnAction(e -> {
            if (weekRadio.isSelected()) {
                currentDate = currentDate.minusDays(7);
                setTableViewToWeekOfYear(currentDate.get(woy),
                                         currentDate.getYear());
                calLocation.setText(currentDate.with(DayOfWeek.MONDAY).toLocalDate().toString() +
                " - " +
                currentDate.with(DayOfWeek.FRIDAY).toLocalDate().toString());
            } else {
                currentDate = currentDate.minusMonths(1);
                setTableViewToMonthOfYear(currentDate.getMonthValue(),
                                         currentDate.getYear());
            }
        });
        
        calNext.setOnAction(e -> {
            if (weekRadio.isSelected()) {
                currentDate = currentDate.plusDays(7);
                setTableViewToWeekOfYear(currentDate.get(woy),
                                         currentDate.getYear());
                calLocation.setText(currentDate.with(DayOfWeek.MONDAY).toLocalDate().toString() +
                " - " +
                currentDate.with(DayOfWeek.FRIDAY).toLocalDate().toString());
            } else {
                currentDate = currentDate.plusMonths(1);
                setTableViewToMonthOfYear(currentDate.getMonthValue(),
                                         currentDate.getYear());
            }
        });
        
        radioGroup.selectedToggleProperty().addListener((ov, ot, new_toggle) -> {
            RadioButton selectedBtn = (RadioButton) new_toggle;
            if (selectedBtn.getId().equals("week")) {
                setTableViewToWeekOfYear(currentDate.get(woy),
                                         currentDate.getYear());
                calLocation.setText(currentDate.with(DayOfWeek.MONDAY).toLocalDate().toString() +
                " - " +
                currentDate.with(DayOfWeek.FRIDAY).toLocalDate().toString());
            } else {
                setTableViewToMonthOfYear(currentDate.getMonthValue(),
                                         currentDate.getYear());
            }
        });
        
        // Add items to grid
        this.add(new Text("Calendar"), 1, 1, 3, 1);
        this.add(this.weekRadio, 1, 3, 2, 1);
        this.add(this.monthRadio, 3, 3, 2, 1);
        this.add(this.calLocation, 10, 3, 3, 1);
        this.add(this.calPrev, 24, 3, 2, 1);
        this.add(this.calNext, 25, 3, 2, 1);
        this.add(this.tableView, 1, 5, 25, 1);
        this.add(this.manageCustomersBtn, 1, 7, 5, 1);
        this.add(this.addBtn, 13, 7, 3, 1);
        this.add(this.modBtn, 16, 7, 3, 1);
        this.add(this.delBtn, 19, 7, 3, 1);
        this.add(this.logBtn, 23, 7, 3, 1);
        
        this.setGridLinesVisible(true);
    }
    
    private void constructTableView() {
        this.tableView = new TableView<>();

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn contactCol = new TableColumn("Contact");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn locationCol = new TableColumn("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<ORMAppointment, String> startCol = new TableColumn("Start Time");
        startCol.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getStart().toString()));

        tableView.getColumns().addAll(titleCol, contactCol, locationCol, startCol);
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    private void setTableItems(ObservableList<ORMAppointment> items) {
        this.tableView.getItems().clear();
        this.tableView.setItems(items);
        this.tableView.refresh();
    }

    private void setTableViewToWeekOfYear(int week, int year) {
        setTableItems(api.getAppointmentsInWeek(week, year));
    }
    
    private void setTableViewToMonthOfYear(int month, int year) {
        setTableItems(api.getAppointmentsByMonth(month, year));
    }
    
    public void refreshCalendar() {
        if (weekRadio.isSelected()) {
            setTableViewToWeekOfYear(currentDate.get(woy),
                                     currentDate.getYear());
        } else {
            setTableViewToMonthOfYear(currentDate.getMonthValue(),
                                     currentDate.getYear());
        }
    }
    
    public ORMAppointment getSelectedAppointment() {
        return tableView.getSelectionModel().getSelectedItem();
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

    public final void setLogoutBtnEvent(EventHandler<ActionEvent> handler) {
        this.logBtn.setOnAction(handler);
    }
    
    public final void setCustomerManagerBtnEvent(EventHandler<ActionEvent> handler) {
        this.manageCustomersBtn.setOnAction(handler);
    }
}
