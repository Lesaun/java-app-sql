package softwareiiassessment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * Alert pane
 * 
 * @author Lesaun
 */
public class PaneEventAlert extends GridPane {
    private final Text eventAlertText = new Text("There is an Appointment within 15 minutes");
    private final Button okBtn = new Button("Ok");

    PaneEventAlert() {
        okBtn.setPrefWidth(71);

        for (int colWidth : new int[]{15,50,50,50,50,50,50,50,50,50,15}) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            getColumnConstraints().add(column);
        }

        for (int rowHeight : new int[]{15,50,40,15}) {
            RowConstraints row = new RowConstraints(rowHeight);
            getRowConstraints().add(row);
        }

        add(eventAlertText, 1, 1, 2, 1);
        add(okBtn, 8, 2, 2, 1);
    }

    /**
     * Set ok button handler
     *
     * @param handler ok button handler to set
     */
    public final void setOkBtnEvent(EventHandler<ActionEvent> handler) {
        okBtn.setOnAction(handler);
    }
}
