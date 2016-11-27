package System.UI;

import System.Registration.RegistrationHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jake's PC on 11/27/2016.
 */
public class CheckRegistrationController implements Initializable {

    private RegistrationHandler registrationHandler;

    @FXML
    private TextField legalIDTextField;

    @FXML
    private Label legalIDLabel;

    @FXML
    private Pane colorField;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private ImageView resultImage;

    @FXML
    private Label resultLabel;

    @FXML
    private Label resultDescriptionLabel;

    @FXML
    private Label checkingLabel;

    @FXML
    private Pane IDNumberLayer;

    @FXML
    private Pane checkRegistrationLayer;

    @FXML
    private Button enterButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button doneButton;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert legalIDTextField != null : "fx:id=\"legalIDTextField\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert legalIDLabel != null : "fx:id=\"legalIDLabel\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert colorField != null : "fx:id=\"colorField\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert resultImage != null : "fx:id=\"resultImage\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert resultLabel != null : "fx:id=\"resultLabel\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert resultDescriptionLabel != null : "fx:id=\"resultDescriptionLabel\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert checkingLabel != null : "fx:id=\"checkingLabel\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert IDNumberLayer != null : "fx:id=\"IDNumberLayer\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert checkRegistrationLayer != null : "fx:id=\"checkRegistrationLayer\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert enterButton != null : "fx:id=\"enterButton\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert doneButton != null : "fx:id=\"doneButton\" was not injected: check your FXML file 'CheckRegistration.fxml'.";

        cancelButton.setOnMouseClicked(event -> ((Stage) this.cancelButton.getScene().getWindow()).close());
        enterButton.setOnMouseClicked(event -> {
            setUpCheckingView();
            setUpResultView(voterIsRegistered(this.legalIDTextField.getText()));
        });
        resetButton.setOnMouseClicked(event -> reset());
        doneButton.setOnMouseClicked(event -> ((Stage) this.doneButton.getScene().getWindow()).close());
    }

    public void setRegistrationHandler(RegistrationHandler registrationHandler) {
        this.registrationHandler = registrationHandler;
    }

    private boolean voterIsRegistered(String legalID) {
        return this.registrationHandler.voterIsRegistered(legalID);
    }

    public void reset() {
        progressIndicator.setVisible(true);
        resultImage.setVisible(false);
        resultImage.setImage(null);
        colorField.setStyle("-fx-background-color: lightblue");

        resultLabel.setText("");
        resultLabel.setVisible(false);
        resultDescriptionLabel.setText("");
        resultDescriptionLabel.setVisible(false);
        checkingLabel.setVisible(true);

        doneButton.setVisible(false);
        resetButton.setVisible(false);

        checkRegistrationLayer.setVisible(false);

        legalIDLabel.setVisible(true);
        legalIDTextField.setText("");
        legalIDTextField.setVisible(true);

        cancelButton.setVisible(true);
        enterButton.setVisible(true);

        IDNumberLayer.setVisible(true);
    }

    private void setUpCheckingView() {
        IDNumberLayer.setVisible(false);
        checkRegistrationLayer.setVisible(true);
    }

    private void setUpResultView(boolean result) {
        progressIndicator.setVisible(false);
        checkingLabel.setVisible(false);

        if (result) {
            colorField.setStyle("-fx-background-color: green");
            resultImage.setImage(new Image("/System/Resources/checkmark.png"));
            resultLabel.setText("Registered.");
            resultDescriptionLabel.setText("The voter may proceed to a polling machine.");
        } else {
            colorField.setStyle("-fx-background-color: red");
            resultImage.setImage(new Image("/System/Resources/cross.png"));
            resultLabel.setText("Not registered.");
            resultDescriptionLabel.setText("The voter will need to register before proceeding.");
        }

        colorField.setVisible(true);
        resultImage.setVisible(true);
        resultLabel.setVisible(true);
        resultDescriptionLabel.setVisible(true);

        resetButton.setVisible(true);
        doneButton.setVisible(true);
    }
}
