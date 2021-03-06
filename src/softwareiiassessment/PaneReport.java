package softwareiiassessment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * Report selection pane
 * 
 * @author Lesaun
 */
public class PaneReport extends GridPane {

    private final Text headerText = new Text("Reports");
    private final Button numApptByMonBtn = new Button("Number of Appointment types by Month");
    private final Button userScheduleBtn = new Button("Consultant schedulde");
    private final Button apptsByCustomer = new Button("Number of Appointments by Customer");
    private final Button doneBtn = new Button("Done");
    private final Text outputFolderText = new Text("Output folder is Documents");

    PaneReport() {
        numApptByMonBtn.setPrefWidth(250);
        userScheduleBtn.setPrefWidth(200);
        apptsByCustomer.setPrefWidth(250);

        for (int colWidth : new int[]{15,50,50,50,50,50,15}) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            getColumnConstraints().add(column);
        }

        for (int rowHeight : new int[]{15,30,50,50,50,50,50,15}) {
            RowConstraints row = new RowConstraints(rowHeight);
            getRowConstraints().add(row);
        }

        add(headerText, 1, 1, 2, 1);
        add(numApptByMonBtn, 1, 2, 8, 1);
        add(userScheduleBtn, 1, 3, 8, 1);
        add(apptsByCustomer, 1, 4, 8, 1);
        add(outputFolderText, 1, 5, 8, 1);        
        add(doneBtn, 1, 6, 2, 1);
    }

    /**
     * Set handler for number of appointment by month / type button
     *
     * @param handler handler to set
     */
    public final void setNumApptByTypeMonBtnEvent(
        EventHandler<ActionEvent> handler) {
        numApptByMonBtn.setOnAction(handler);
    }

    /**
     * Set user schedule button handler
     *
     * @param handler handler to set
     */
    public final void setUserScheduleBtnEvent(
        EventHandler<ActionEvent> handler) {
        userScheduleBtn.setOnAction(handler);
    }
    
    /**
     * Set appointment by customers handler
     *
     * @param handler handler to set
     */
    public final void setApptsByCustomerBtnEvent(
        EventHandler<ActionEvent> handler) {
        apptsByCustomer.setOnAction(handler);
    }
    
    /**
     * Set done button handler
     *
     * @param handler handler to set
     */
    public final void setDoneBtnEvent(EventHandler<ActionEvent> handler) {
        doneBtn.setOnAction(handler);
    }
}
