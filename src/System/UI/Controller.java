package System.UI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private ImageView image;

    @FXML
    private Button electionSetupButton;

    @FXML
    private Button verifyRegistrationButton;

    @FXML
    private Button registerButton;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'simple.fxml'.";
        assert electionSetupButton != null : "fx:id=\"electionSetupButton\" was not injected: check your FXML file 'simple.fxml'.";
        assert verifyRegistrationButton != null : "fx:id=\"verifyRegistrationButton\" was not injected: check your FXML file 'simple.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'simple.fxml'.";
        registerButton.setOnMouseClicked(event -> {
            //todo start registration
        });
        verifyRegistrationButton.setOnMouseClicked(event -> {
            //todo start verify registration
        });
        electionSetupButton.setOnMouseClicked(event -> {
            //todo start election setup
        });
    }

}
