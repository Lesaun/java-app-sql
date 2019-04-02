package softwareiiassessment;

import java.util.Calendar;
import java.util.Date;
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
    private final RadioButton weekRadio = new RadioButton("Week");
    private final RadioButton monthRadio = new RadioButton("Month");
    private final ToggleGroup radioGroup = new ToggleGroup();
    private boolean weekView = true;

    private final Button calPrev;
    private final Button calNext;

    private final Button manageCustomersBtn;

    private final Button addBtn;
    private final Button modBtn;
    private final Button delBtn;
    private final Button logBtn;
    private TableView<ORMAppointment> tableView;
    

    /**
     * Constructs the pane with given title and TableView
     * 
     * @param customers
     */
    PaneCalendar(SQLAPI api, Date startDate) {
        // Setup toggle group
        weekRadio.setId("week");
        monthRadio.setId("month");
        weekRadio.setToggleGroup(radioGroup);
        monthRadio.setToggleGroup(radioGroup);
        weekRadio.setSelected(true);
        
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

        constructTableView(api);
        
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(startDate);
        int startWeek = currentDate.get(Calendar.WEEK_OF_YEAR);
        int startYear = currentDate.get(Calendar.YEAR);
        setTableViewToWeekOfYear(api, startWeek, startYear);
        
        calPrev.setOnAction(e -> {
            if (weekRadio.isSelected()) {
                currentDate.add(Calendar.DATE, -7);
                int tstartWeek = currentDate.get(Calendar.WEEK_OF_YEAR);
                int tstartYear = currentDate.get(Calendar.YEAR);
                setTableViewToWeekOfYear(api, tstartWeek, tstartYear);
            } else {
                currentDate.add(Calendar.MONTH, -1);
                int tstartWeek = currentDate.get(Calendar.MONTH);
                int tstartYear = currentDate.get(Calendar.YEAR);
                setTableViewToMonthOfYear(api, tstartWeek, tstartYear);
            }
        });
        
        calNext.setOnAction(e -> {
            if (weekRadio.isSelected()) {
                currentDate.add(Calendar.DATE, 7);
                int tstartWeek = currentDate.get(Calendar.WEEK_OF_YEAR);
                int tstartYear = currentDate.get(Calendar.YEAR);
                setTableViewToWeekOfYear(api, tstartWeek, tstartYear);
            } else {
                currentDate.add(Calendar.MONTH, 1);
                int tstartWeek = currentDate.get(Calendar.MONTH);
                int tstartYear = currentDate.get(Calendar.YEAR);
                setTableViewToMonthOfYear(api, tstartWeek, tstartYear);
            }
        });
        
        radioGroup.selectedToggleProperty().addListener((ov, ot, new_toggle) -> {
            RadioButton selectedBtn = (RadioButton) new_toggle;
            if (selectedBtn.getId().equals("week")) {
                int tstartWeek = currentDate.get(Calendar.WEEK_OF_YEAR);
                int tstartYear = currentDate.get(Calendar.YEAR);
                setTableViewToWeekOfYear(api, tstartWeek, tstartYear);
            } else {
                int tstartWeek = currentDate.get(Calendar.MONTH);
                int tstartYear = currentDate.get(Calendar.YEAR);
                setTableViewToMonthOfYear(api, tstartWeek, tstartYear);
            }
        });
        
        // Add items to grid
        this.add(new Text("Calendar"), 1, 1, 3, 1);
        this.add(this.weekRadio, 1, 3, 2, 1);
        this.add(this.monthRadio, 3, 3, 2, 1);
        this.add(this.calPrev, 24, 3, 2, 1);
        this.add(this.calNext, 25, 3, 2, 1);
        this.add(this.tableView, 1, 5, 25, 1);
        this.add(this.manageCustomersBtn, 1, 7, 5, 1);
        this.add(this.addBtn, 13, 7, 3, 1);
        this.add(this.modBtn, 16, 7, 3, 1);
        this.add(this.delBtn, 19, 7, 3, 1);
        this.add(this.logBtn, 23, 7, 3, 1);
    }
    
    private void constructTableView(SQLAPI api) {
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

    private void setTableViewToWeekOfYear(SQLAPI api, int week, int year) {
        setTableItems(api.getAppointmentsInWeek(week, year));
    }
    
    private void setTableViewToMonthOfYear(SQLAPI api, int month, int year) {
        setTableItems(api.getAppointmentsByMonth(month, year));
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
    public final void setLogoutBtnEvent(EventHandler<ActionEvent> handler) {
        this.logBtn.setOnAction(handler);
    }
    
    public final void setCustomerManagerBtnEvent(EventHandler<ActionEvent> handler) {
        this.manageCustomersBtn.setOnAction(handler);
    }
}
