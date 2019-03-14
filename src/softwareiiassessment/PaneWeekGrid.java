package softwareiiassessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.SATURDAY;

/**
 * A Pane with TableView and controls
 * 
 * @author lesaun
 */
public class PaneWeekGrid extends GridPane {

    /**
     * Constructs the pane with given title and TableView
     * 
     * @param customers
     */
    PaneWeekGrid(int weekOfYear, TimeZone timeZone, SQLAPI api,
             EventHandler<ActionEvent> editApptHandler,
             EventHandler<ActionEvent> addApptHandler) {
        
        HashMap<Integer, ArrayList<ORMAppointment>> appointmentsByDayOfWeek;
        appointmentsByDayOfWeek = api.getAppontmentsByDayOfWeek(weekOfYear,
                                                                timeZone);
 
       // Add width constraint for columns
        this.getColumnConstraints().add(new ColumnConstraints(33));
        for (int i = 0; i < 7; i++) {
            ColumnConstraints column = new ColumnConstraints(162);
            this.getColumnConstraints().add(column);    
        }
        this.getColumnConstraints().add(new ColumnConstraints(33));
        
        // Add row
        this.getRowConstraints().add(new RowConstraints(162));

        // Add items to grid
        this.add(new PaneDayGrid(appointmentsByDayOfWeek.get(SUNDAY),
                 editApptHandler, addApptHandler), 0, 0);
        this.add(new PaneDayGrid(appointmentsByDayOfWeek.get(MONDAY),
                 editApptHandler, addApptHandler), 1, 0);
        this.add(new PaneDayGrid(appointmentsByDayOfWeek.get(TUESDAY),
                 editApptHandler, addApptHandler), 2, 0);
        this.add(new PaneDayGrid(appointmentsByDayOfWeek.get(WEDNESDAY),
                 editApptHandler, addApptHandler), 3, 0);
        this.add(new PaneDayGrid(appointmentsByDayOfWeek.get(THURSDAY),
                 editApptHandler, addApptHandler), 4, 0);
        this.add(new PaneDayGrid(appointmentsByDayOfWeek.get(FRIDAY),
                 editApptHandler, addApptHandler), 5, 0);
        this.add(new PaneDayGrid(appointmentsByDayOfWeek.get(SATURDAY),
                 editApptHandler, addApptHandler), 6, 0);
        
        this.setGridLinesVisible(true);
    }
}
