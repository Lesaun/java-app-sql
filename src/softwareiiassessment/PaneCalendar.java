package softwareiiassessment;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
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


public class PaneCalendar extends GridPane {
    private TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

    private final Text headerText = new Text("Calendar");
    private final RadioButton weekRadio = new RadioButton("Week");
    private final RadioButton monthRadio = new RadioButton("Month");
    private final ToggleGroup radioGroup = new ToggleGroup();

    private final SQLAPI api;
    private LocalDateTime currentDate;
    private final Text calLocation;

    private final Button calPrev = new Button("<");
    private final Button calNext = new Button(">");

    private final Button manageCustomersBtn = new Button("Manage Customers");

    private final Button addBtn = new Button("Add");
    private final Button modBtn = new Button("Modify");
    private final Button delBtn = new Button("Delete");
    private final Button logBtn = new Button("Logout");
    private TableView<ORMAppointment> tableView;

    PaneCalendar(SQLAPI api, LocalDateTime startDate) {
        // Setup toggle group
        weekRadio.setId("week");
        monthRadio.setId("month");
        weekRadio.setToggleGroup(radioGroup);
        monthRadio.setToggleGroup(radioGroup);
        weekRadio.setSelected(true);

        // assign api to object
        this.api = api;

        // Set widths on butons
        manageCustomersBtn.setPrefWidth(125);
        addBtn.setPrefWidth(71);
        modBtn.setPrefWidth(71);
        delBtn.setPrefWidth(71);
        logBtn.setPrefWidth(71);

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
                calLocation.setText(currentDate.getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH));
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
                calLocation.setText(currentDate.getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH));
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
                calLocation.setText(currentDate.getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            }
        });

        // Add width constraint for columns
        ArrayList<Integer> colWidths = new ArrayList<>();
        colWidths.add(15);
        colWidths.addAll(Collections.nCopies(25, 29));
        colWidths.add(15);
        for (int colWidth : colWidths) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            getColumnConstraints().add(column);
        }

        // Add height constraint for rows
        for (int rowHeight : new int[]{15, 23, 6, 26, 6, 260, 6, 26, 24}) {
            RowConstraints row = new RowConstraints(rowHeight);
            getRowConstraints().add(row);
        }

        add(headerText, 1, 1, 3, 1);
        add(weekRadio, 1, 3, 2, 1);
        add(monthRadio, 3, 3, 2, 1);
        add(calLocation, 10, 3, 3, 1);
        add(calPrev, 24, 3, 2, 1);
        add(calNext, 25, 3, 2, 1);
        add(tableView, 1, 5, 25, 1);
        add(manageCustomersBtn, 1, 7, 5, 1);
        add(addBtn, 13, 7, 3, 1);
        add(modBtn, 16, 7, 3, 1);
        add(delBtn, 19, 7, 3, 1);
        add(logBtn, 23, 7, 3, 1);
    }

    private void constructTableView() {
        this.tableView = new TableView<>();

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn contactCol = new TableColumn("Contact");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn locationCol = new TableColumn("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<ORMAppointment, String> startCol = new TableColumn("Start Time");
        startCol.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getStart().toString()));

        tableView.getColumns().addAll(titleCol, typeCol, contactCol,
                                      locationCol, startCol);
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
