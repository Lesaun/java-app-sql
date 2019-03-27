package softwareiiassessment;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * A Pane for modifying or adding parts to the inventory
 *
 * @author lesaun
 */
public class PaneAppointmentCreateModify extends GridPane {
    
    private final TextField idTextField = new TextField();

    private final TextField title = new TextField();
    private final TextArea description = new TextArea();
    private final TextArea location = new TextArea();
    private final TextArea contact = new TextArea();
    private final TextField url = new TextField();
    
    private final DatePicker startDate = new DatePicker();
    private final ComboBox<String> startHour = new ComboBox();
    private final ComboBox<String> startMinute = new ComboBox();

    private final ComboBox<String> endHour = new ComboBox();
    private final ComboBox<String> endMinute = new ComboBox();
       
    private final Button selectBtn = new Button("Select");
    private final Text customerName = new Text("");

    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");
    
    private boolean customerSelected = false;

    public PaneAppointmentCreateModify(SQLAPI api) {

        // Add prompt text to text fields
        idTextField.setPromptText("Auto Gen - Disabled");
        
        title.setPromptText("Title");
        url.setPromptText("URL");

        // Disable id text field
        idTextField.setDisable(true);
        
        startHour.setItems(FXCollections.observableArrayList(
                new String[]{"08", "09", "10", "11", "12",
                             "01", "02", "03", "04", "05"}));
        
        startMinute.setItems(FXCollections.observableArrayList(
                Arrays.stream(IntStream.rangeClosed(0, 59).toArray())
                    .mapToObj(n -> String.format("%02d", n))
                        .collect(Collectors.toList())));
        
        endHour.setItems(FXCollections.observableArrayList(
                new String[]{"08", "09", "10", "11", "12",
                             "01", "02", "03", "04", "05"}));

        endMinute.setItems(FXCollections.observableArrayList(
                Arrays.stream(IntStream.rangeClosed(0, 59).toArray())
                    .mapToObj(n -> String.format("%02d", n))
                        .collect(Collectors.toList())));

        // Add width constraint for columns
        ColumnConstraints leftMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(leftMarginCol);

        for (int i = 0; i < 9; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
            this.getColumnConstraints().add(column);
        }

        ColumnConstraints rightMarginCol = new ColumnConstraints(15);
        this.getColumnConstraints().add(rightMarginCol);

        // Add width constraint for columns
        RowConstraints topMargin = new RowConstraints(15);
        this.getRowConstraints().add(topMargin);

        for (int i = 0; i < 17; i++) {
            RowConstraints row = new RowConstraints(50);
            this.getRowConstraints().add(row);
        }

        RowConstraints bottomMargin = new RowConstraints(15);
        this.getRowConstraints().add(bottomMargin);
       
        // Add height constraint for rows

        // Add controls/fields onto grid pane
        this.add(new Text("Appointment"), 1, 1, 2, 1);
        this.add(new Text("ID"), 2, 2, 1, 1);
        this.add(idTextField, 4, 2, 3, 1);
        this.add(new Text("Title"), 2, 3, 1, 1);
        this.add(title, 4, 3, 3, 1);
        this.add(new Text("Description"), 2, 4, 1, 1);
        this.add(description, 2, 5, 5, 2);
        this.add(new Text("Location"), 2, 7, 1, 1);
        this.add(location, 2, 8, 5, 2);
        this.add(new Text("Contact"), 2, 10, 3, 1);
        this.add(contact, 2, 11, 5, 2);
        this.add(new Text("Start Date"), 2, 13, 1, 1);
        this.add(startDate, 4, 13, 3, 1);
        this.add(new Text("Start Time"), 2, 14, 1, 1);
        //this.add(start, 4, 14, 3, 1);
        this.add(new Text("End Time"), 2, 15, 1, 1);
        //this.add(url, 4, 16, 3, 1);
        this.add(new Text("URL"), 2, 16, 1, 1);
        this.add(url, 4, 16, 3, 1);
        this.add(saveBtn, 6, 17, 2, 1);
        this.add(cancelBtn, 8, 17, 2, 1);
        
        //this.setGridLinesVisible(true);
    }
}