package softwareiiassessment;

import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Login pane
 *
 * @author Lesaun
 */
public class PaneLogin extends GridPane {

    private final Text headerText = new Text("Login");

    private final Text usernamePrompt = new Text("Username");
    private final TextField usernameField = new TextField();
    private final Text passwordPrompt = new Text("Password");
    private final PasswordField passwordField = new PasswordField();
    private final Text failedLoginText = new Text("The username and " +
                                                  "password did not match.");

    private final Button loginBtn = new Button("Login");
    private final Button exitBtn = new Button("Exit");

    PaneLogin() {
        loginBtn.setPrefWidth(71);
        exitBtn.setPrefWidth(71);

        failedLoginText.setFill(Color.RED);

        for (int colWidth : new int[]{15,50,50,50,50,50,50,50,50,50,15}) {
            ColumnConstraints column = new ColumnConstraints(colWidth);
            getColumnConstraints().add(column);
        }

        for (int rowHeight : new int[]{15,30,50,50,20,40,15}) {
            RowConstraints row = new RowConstraints(rowHeight);
            getRowConstraints().add(row);
        }

        add(headerText, 1, 1, 2, 1);
        add(usernamePrompt, 2, 2, 1, 1);
        add(usernameField, 4, 2, 3, 1);
        add(passwordPrompt, 2, 3, 1, 1);
        add(passwordField, 4, 3, 3, 1);
        add(loginBtn, 6, 5, 2, 1);
        add(exitBtn, 8, 5, 2, 1);
    }

    /**
     * Set locale
     *
     * @param locale
     */
    public void setLocale(Locale locale) {
        if (locale.equals(Locale.FRANCE) || locale.equals(Locale.FRENCH)) {
            headerText.setText("Connexion");
            usernamePrompt.setText("Nom d'utilisateur");
            passwordPrompt.setText("Mot de passe");
            failedLoginText.setText("Le nom d'utilisateur et le mot de passe " +
                                    " ne correspondent pas");
            loginBtn.setText("Connexion");
            exitBtn.setText("Sortie");
        } else if (locale.equals(Locale.ITALIAN) ||
                   locale.equals(Locale.ITALY)) {
            headerText.setText("Acciedi");
            usernamePrompt.setText("Nome utente");
            passwordPrompt.setText("Password");
            failedLoginText.setText("Il nome utente e la password non " +
                                    "corrispondevano.");
            loginBtn.setText("Acciedi");
            exitBtn.setText("Uscita");
        }
    }

    /**
     * Set login button handler
     *
     * @param handler set login button handler
     */
    public final void setLoginBtnEvent(EventHandler<ActionEvent> handler) {
        loginBtn.setOnAction(handler);
    }

    /**
     * Shows error message
     */
    public void showErrorMessage() {
        add(failedLoginText, 2, 4, 4, 1);
    }

    /**
     * Set exit button handler
     *
     * @param handler exit button handler
     */
    public final void setExitBtnEvent(EventHandler<ActionEvent> handler) {
        exitBtn.setOnAction(handler);
    }

    /**
     * Returns the username
     *
     * @return
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Returns the password
     *
     * @return the password
     */
    public String getPassword() {
        return passwordField.getText();
    }
}
