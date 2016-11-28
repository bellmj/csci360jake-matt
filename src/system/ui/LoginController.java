package system.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jake's PC on 11/27/2016.
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterButton;

    @FXML
    private Label invalidLoginLabel;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert enterButton != null : "fx:id=\"enterButton\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert invalidLoginLabel != null : "fx:id=\"invalidLoginLabel\" was not injected: check your FXML file 'LoginScreen.fxml'.";

        invalidLoginLabel.setVisible(false);
        invalidLoginLabel.setStyle("-fx-text-fill: red");

        enterButton.setOnMouseClicked(event -> {
            if (loginInfoIsValid()) {
                ((Stage) enterButton.getScene().getWindow()).close();
            } else {
                invalidLoginLabel.setVisible(true);
            }
        });
    }

    private boolean loginInfoIsValid() {
        return (usernameTextField.getText().equals("admin")) &&
                passwordField.getText().equals("1234");
    }

}
