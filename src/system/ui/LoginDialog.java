package system.ui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * A custom <tt>Dialog</tt> class, used to authenticate a user before starting
 * the accessing the main features of the program. The Dialog returns a
 * <tt>Boolean</tt>, indicating whether the user's credentials were valid. This
 * should always be true, as the Dialog can only be closed by logging in with
 * valid credentials, cancelling login, or closing the window.
 * <p>
 *     The LoginDialog contains a <tt>TextField</tt> for the user's username,
 *     and a <tt>PasswordField</tt> for their password. There are two
 *     <tt>Buttons</tt>, one two cancel the login, and another to attempt to
 *     authenticate the information entered. If the user is authenticated,
 *     the LoginDialog will yield to <tt>OpenScreenController</tt>.
 * </p>
 *
 * @see OpenScreenController
 */
public class LoginDialog extends Dialog<Boolean> {

    private TextField usernameTextField;
    private PasswordField passwordField;

    /**
     * Constructs an instance of LoginDialog.
     */
    public LoginDialog() {
        super();

        this.setTitle("Authentication");
        this.usernameTextField = new TextField();
        this.passwordField = new PasswordField();

        ButtonType loginButtonType = new ButtonType(
                "Login",
                ButtonBar.ButtonData.OK_DONE
        );
        this.getDialogPane().getButtonTypes()
                .addAll(ButtonType.CANCEL, loginButtonType);

        this.setResultConverter(buttonTypePressed ->
                buttonTypePressed == loginButtonType && isValidLogin());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(this.usernameTextField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(this.passwordField, 1, 1);

        Label invalidLoginLabel = new Label("Invalid Login");
        invalidLoginLabel.setId("Label");
        invalidLoginLabel.setStyle("-fx-text-fill: red");
        invalidLoginLabel.setVisible(false);

        VBox vBox = new VBox(gridPane, invalidLoginLabel);
        vBox.setAlignment(Pos.CENTER);

        this.getDialogPane().setContent(vBox);

        this.getDialogPane().lookupButton(loginButtonType)
                .addEventFilter(ActionEvent.ACTION, event -> {
            if (!isValidLogin()) {
                invalidLoginLabel.setVisible(true);
                event.consume();
            }
        });
    }

    /**
     * Checks that the information entered by the user is valid login
     * information.
     *
     * @return
     */
    private boolean isValidLogin() {
        return usernameTextField.getText().equals("admin") &&
                passwordField.getText().equals("1234");
    }

}