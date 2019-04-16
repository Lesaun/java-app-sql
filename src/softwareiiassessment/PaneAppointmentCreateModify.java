package softwareiiassessment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class PaneAppointmentCreateModify extends GridPane {

    private final Text headerText = new Text("Appointment");
    private final Text idPrompt = new Text("ID");
    private final TextField idTextField = new TextField();
    private final Text titlePrompt = new Text("Title");
    private final TextField title = new TextField();
    private final Text typePrompt = new Text("Type");
    private final TextField type = new TextField();
    private final Text descriptionPrompt = new Text("Description");
    private final TextArea description = new TextArea();
    private final Text locationPrompt = new Text("Location");
    private final TextArea location = new TextArea();
    private final Text contactPrompt = new Text("Contact");
    private final TextArea contact = new TextArea();
    private final Text urlPrompt = new Text("URL");
    private final TextField url = new TextField();
    private final Text errorText = new Text("");

    private final Text startDPrompt = new Text("Start Date");
    private final DatePicker startDate = new DatePicker();
    private final Text startTPrompt = new Text("Start Time");
    private final ComboBox<String> startHour = new ComboBox();
    private final ComboBox<String> startMinute = new ComboBox();

    private final Text endTPrompt = new Text("End Time");
    private final ComboBox<String> endHour = new ComboBox();
    private final ComboBox<String> endMinute = new ComboBox();

    private final Text customerPrompt = new Text("Customer");
    private final Button selectBtn = new Button("Select");
    private final Text customerName = new Text("");
    private ORMCustomer customer;

    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");

    private boolean customerSelected = false;

    PaneAppointmentCreateModify(SQLAPI api) {

        // Add prompt text to text fields
        idTextField.setPromptText("Auto Gen - Disabled");
        title.setPromptText("Title");
        url.setPromptText("URL");

        // Disable id text field
        idTextField.setDisable(true);

        // Set color of error text
        errorText.setFill(Color.RED);

        String[] hours = new String[]{"08am", "09am", "10am", "11am", "12pm",
                                      "01pm", "02pm", "03pm", "04pm", "05pm"};

        startDate.setDayCellFactory(dayCel -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                                    date.getDayOfWeek() == DayOfWeek.SATURDAY);
            }
        });

        startHour.setItems(FXCollections.observableArrayList(hours));
        startMinute.setItems(FXCollections.observableArrayList(
                Arrays.stream(IntStream.rangeClosed(0, 59).toArray())
                    .mapToObj(n -> String.format("%02d", n))
                        .collect(Collectors.toList())));

        startHour.valueProperty().addListener((ov, t, ti) ->
            setEndLowTime(ti,
                startMinute.getSelectionModel().getSelectedItem(),
                null));
        startMinute.valueProperty().addListener((ov, t, ti) ->
            setEndLowTime(startHour.getSelectionModel().getSelectedItem(),
                ti,
                null));
        endHour.valueProperty().addListener((ov, t, ti) -> {
            if (ti != null)
                setEndLowTime(startHour.getSelectionModel().getSelectedItem(),
                    startMinute.getSelectionModel().getSelectedItem(),
                    ti);
        });


        // Add width constraint for columns
        for (int colWidth : new int[]{15,50,50,50,50,50,50,50,50,50,15}) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            getColumnConstraints().add(column);
        }

        // Add height constraint for row
        ArrayList<Integer> rowHeights = new ArrayList<>();
        rowHeights.add(15);
        rowHeights.addAll(Collections.nCopies(20, 30));
        rowHeights.add(15);

        for (Integer rowHeight : rowHeights) {
            RowConstraints row = new RowConstraints(rowHeight);
            getRowConstraints().add(row);
        }

        // Add controls/fields onto grid pane
        add(headerText, 1, 1, 2, 1);
        add(idPrompt, 2, 2, 1, 1);
        add(idTextField, 4, 2, 3, 1);
        add(titlePrompt, 2, 3, 1, 1);
        add(title, 4, 3, 3, 1);
        add(typePrompt, 2, 4, 1, 1);
        add(type, 4, 4, 3, 1);
        add(customerPrompt, 2, 5, 1, 1);
        add(selectBtn, 4, 5, 1, 1);
        add(customerName, 2, 6, 1, 1);
        add(descriptionPrompt, 2, 7, 1, 1);
        add(description, 2, 8, 5, 2);
        add(locationPrompt, 2, 10, 1, 1);
        add(location, 2, 11, 5, 2);
        add(contactPrompt, 2, 13, 3, 1);
        add(contact, 2, 14, 5, 2);
        add(startDPrompt, 2, 16, 1, 1);
        add(startDate, 4, 16, 3, 1);
        add(startTPrompt, 2, 17, 1, 1);
        add(startHour, 4, 17, 2, 1);
        add(startMinute, 6, 17, 2, 1);
        add(endTPrompt, 2, 18, 1, 1);
        add(endHour, 4, 18, 2, 1);
        add(endMinute, 6, 18, 2, 1);
        add(urlPrompt, 2, 19, 1, 1);
        add(url, 4, 19, 3, 1);
        add(errorText, 2, 20, 2, 1);
        add(saveBtn, 6, 20, 2, 1);
        add(cancelBtn, 8, 20, 2, 1);
    }

    private void setEndLowTime(String start_hour, String start_minute,
                               String end_hour) {
        int ehour = 0;

        if (end_hour == null) {
            endHour.getItems().clear();
        } else {
            ehour = Integer.parseInt(end_hour.substring(0, 2));
        }

        endMinute.getItems().clear();

        if (start_hour == null || start_minute == null)
            return;

        String[] hours = new String[]{"08am", "09am", "10am", "11am", "12pm",
                                      "01pm", "02pm", "03pm", "04pm", "05pm"};

        int shour = Integer.parseInt(start_hour.substring(0, 2));
        int smin = Integer.parseInt(start_minute.substring(0, 2));

        if (shour < 7) {
            endHour.setItems(FXCollections.observableArrayList(
                Arrays.copyOfRange(hours, shour + 5, 10)));
        } else {
            endHour.setItems(FXCollections.observableArrayList(
                Arrays.copyOfRange(hours, shour - 8, 10)));
        }

        if (end_hour == null || ehour != shour) {
            endMinute.setItems(FXCollections.observableArrayList(
                Arrays.stream(IntStream.rangeClosed(0, 59).toArray())
                    .mapToObj(n -> String.format("%02d", n))
                        .collect(Collectors.toList())));
        } else {
            endMinute.setItems(FXCollections.observableArrayList(
                Arrays.stream(IntStream.rangeClosed(smin, 59).toArray())
                    .mapToObj(n -> String.format("%02d", n))
                        .collect(Collectors.toList())));
        }
    }

    public final void setSelectBtnEvent(EventHandler<ActionEvent> handler) {
        this.selectBtn.setOnAction(handler);
    }

    public void setCustomer(ORMCustomer customer) {
        customerName.setText(customer.getCustomerName());
        this.customer = customer;
        customerSelected = true;
    }

    public ORMCustomer getCustomer() {
        if (!customerSelected)
            return null;
        return customer;
    }

    public void setErrorText(String errorText) {
        this.errorText.setText(errorText);
    }

    public void setSaveBtnEvent(EventHandler<ActionEvent> handler) {
        this.saveBtn.setOnAction(handler);
    }

    public void setCancelBtnEvent(EventHandler<ActionEvent> handler) {
        this.cancelBtn.setOnAction(handler);
    }

    public String getIdTextField() {
        return idTextField.getText();
    }

    public void setIdTextField(String id) {
        this.idTextField.setText(id);
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public String getType() {
        return type.getText();
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    public String getDescription() {
        return description.getText();
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public String getLocation() {
        return location.getText();
    }

    public void setLocation(String location) {
        this.location.setText(location);
    }

    public String getContact() {
        return contact.getText();
    }

    public void setContact(String contact) {
        this.contact.setText(contact);
    }

    public String getUrl() {
        return url.getText();
    }

    public void setUrl(String url) {
        this.url.setText(url);
    }

    public LocalDateTime getStart() {
        LocalDate startDateOnly = startDate.getValue();
        int shour;

        if (startHour.getValue().contains("pm") && !startHour.getValue().contains("12")) {
            shour = Integer.parseInt(startHour.getValue().substring(0, 2)) + 12;
        } else {
            shour = Integer.parseInt(startHour.getValue().substring(0, 2));
        }

        return startDateOnly.atTime(shour,
               Integer.parseInt(startMinute.getValue()),
               00);
    }

    public void setStart(LocalDateTime start) {
        startDate.setValue(start.toLocalDate());
        String hour;

        if (start.getHour() < 12) {
            if (start.getHour() < 10) {
                hour = String.format("0%dam", start.getHour());
            } else {
                hour = String.format("%dam", start.getHour());
            }
        } else if (start.getHour() == 12) {
            hour = String.format("%dam", start.getHour());
        } else {
            hour = String.format("%dpm", start.getHour() - 12);
        }

        startHour.setValue(hour);
        startMinute.setValue(String.format("%02d", start.getMinute()));
    }

    public LocalDateTime getEnd() {
         LocalDate startDateOnly = startDate.getValue();
        int ehour;

        if (endHour.getValue().contains("pm") && !endHour.getValue().contains("12")) {
            ehour = Integer.parseInt(endHour.getValue().substring(0, 2)) + 12;
        } else {
            ehour = Integer.parseInt(endHour.getValue().substring(0, 2));
        }

        return startDateOnly.atTime(ehour,
               Integer.parseInt(endMinute.getValue()),
               00);
    }

    public void setEnd(LocalDateTime end) {
        String hour;

        if (end.getHour() < 12) {
            if (end.getHour() < 10) {
                hour = String.format("0%dam", end.getHour());
            } else {
                hour = String.format("%dam", end.getHour());
            }
        } else if (end.getHour() == 12) {
            hour = String.format("%dam", end.getHour());
        } else {
            hour = String.format("0%dpm", end.getHour() - 12);
        }

        endHour.setValue(hour);
        endMinute.setValue(String.format("%02d", end.getMinute()));
    }

    public boolean isCustomerSelected() {
        return customerSelected;
    }

    public void setCustomerSelected(boolean customerSelected) {
        this.customerSelected = customerSelected;
    }
}
