package softwareiiassessment;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

/**
 * Pane to view calendar
 *
 * @author Lesaun
 */
public class PaneCalendar extends GridPane {
    private TemporalField woy =
        WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

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
    private final Button reportsBtn = new Button("Reports");

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
        
        // Display the current date range
        currentDate = startDate;
        calLocation = new Text(
                currentDate.with(DayOfWeek.MONDAY).toLocalDate().toString() +
                " - " +
                currentDate.with(DayOfWeek.FRIDAY).toLocalDate().toString());
        setTableViewToWeekOfYear(currentDate.get(woy), currentDate.getYear());

        // handle prev button
        calPrev.setOnAction(e -> {
            if (weekRadio.isSelected()) {
                currentDate = currentDate.minusDays(7);
                setTableViewToWeekOfYear(currentDate.get(woy),
                                         currentDate.getYear());
                calLocation.setText(currentDate.with(DayOfWeek.MONDAY)
                                    .toLocalDate().toString() + " - " +
                currentDate.with(DayOfWeek.FRIDAY).toLocalDate().toString());
            } else {
                currentDate = currentDate.minusMonths(1);
                setTableViewToMonthOfYear(currentDate.getMonthValue(),
                                         currentDate.getYear());
                calLocation.setText(currentDate.getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            }
        });
        
        // handle next button
        calNext.setOnAction(e -> {
            if (weekRadio.isSelected()) {
                currentDate = currentDate.plusDays(7);
                setTableViewToWeekOfYear(currentDate.get(woy),
                                         currentDate.getYear());
                calLocation.setText(currentDate.with(DayOfWeek.MONDAY)
                                    .toLocalDate().toString() + " - " +
                currentDate.with(DayOfWeek.FRIDAY).toLocalDate().toString());
            } else {
                currentDate = currentDate.plusMonths(1);
                setTableViewToMonthOfYear(currentDate.getMonthValue(),
                                         currentDate.getYear());
                calLocation.setText(currentDate.getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            }
        });

        // handle month / week toggle
        // justification for lamda: simplifies code for toggle
        radioGroup.selectedToggleProperty().addListener((ov, ot, newToggle) -> {
            RadioButton selectedBtn = (RadioButton) newToggle;
            if (selectedBtn.getId().equals("week")) {
                setTableViewToWeekOfYear(currentDate.get(woy),
                                         currentDate.getYear());
                calLocation.setText(currentDate.with(DayOfWeek.MONDAY)
                                    .toLocalDate().toString() + " - " +
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
        add(reportsBtn, 6, 7, 5, 1);
        add(addBtn, 13, 7, 3, 1);
        add(modBtn, 16, 7, 3, 1);
        add(delBtn, 19, 7, 3, 1);
        add(logBtn, 23, 7, 3, 1);
    }

    /**
     * Construct calendar table view
     */
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

        TableColumn<ORMAppointment, String> startCol = new TableColumn("Local Start Time");
        startCol.setCellValueFactory(data -> {
            ZonedDateTime utcTime = data.getValue().getStart()
                                                   .atZone(ZoneId.of("UTC"));
            ZonedDateTime localTime = utcTime.withZoneSameInstant(
                                                        ZoneId.systemDefault());
            String formattedTime = localTime.format(
                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

            return new SimpleStringProperty(formattedTime);
        });

        tableView.getColumns().addAll(titleCol, typeCol, contactCol,
                                      locationCol, startCol);
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Set table view items
     */
    private void setTableItems(ObservableList<ORMAppointment> items) {
        this.tableView.getItems().clear();
        this.tableView.setItems(items);
        this.tableView.refresh();
    }

    /**
     * Set table view to show appointments week and year
     * 
     * @param week week of year to set calendar to
     * @param year year to set calendar to
     */
    private void setTableViewToWeekOfYear(int week, int year) {
        setTableItems(api.getAppointmentsInWeek(week, year));
    }

    /**
     * Set table view to show appointments in month and year
     * 
     * @param month month of year to set calendar to
     * @param year year to set calendar to
     */
    private void setTableViewToMonthOfYear(int month, int year) {
        setTableItems(api.getAppointmentsByMonth(month, year));
    }

    /**
     * Refresh calendar items
     */
    public void refreshCalendar() {
        if (weekRadio.isSelected()) {
            setTableViewToWeekOfYear(currentDate.get(woy),
                                     currentDate.getYear());
        } else {
            setTableViewToMonthOfYear(currentDate.getMonthValue(),
                                     currentDate.getYear());
        }
    }

    /**
     * Return the selected appointment
     * 
     * @return select appointment
     */
    public ORMAppointment getSelectedAppointment() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    /**
     * Sets the add button handler
     *
     * @param handler add button handler to set
     */
    public final void setAddBtnEvent(EventHandler<ActionEvent> handler) {
        this.addBtn.setOnAction(handler);
    }

    /**
     * Sets the modify button handler to set
     *
     * @param handler modify button handler to set
     */
    public final void setModBtnEvent(EventHandler<ActionEvent> handler) {
        this.modBtn.setOnAction(handler);
    }

    /**
     * Sets the delete button handler
     *
     * @param handler delete button handler to set
     */
    public final void setDelBtnEvent(EventHandler<ActionEvent> handler) {
        this.delBtn.setOnAction(handler);
    }

    /**
     * Sets the logout button handler
     *
     * @param handler logout button handler to set
     */
    public final void setLogoutBtnEvent(EventHandler<ActionEvent> handler) {
        this.logBtn.setOnAction(handler);
    }
    
    /**
     * Sets the reports button handler
     *
     * @param handler
     */
    public final void setReportsBtnEvent(EventHandler<ActionEvent> handler) {
        this.reportsBtn.setOnAction(handler);
    }

    /**
     * Sets the customer manager button handler event
     *
     * @param handler customer manager button handler event
     */
    public final void setCustomerManagerBtnEvent(EventHandler<ActionEvent> handler) {
        this.manageCustomersBtn.setOnAction(handler);
    }
}
