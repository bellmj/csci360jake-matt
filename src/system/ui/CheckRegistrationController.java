package system.ui;

import system.registration.RegistrationHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * A screen that accepts a state ID number, and checks the registration
 * database for an existing entry. If the voter is registered, then they are
 * ready to continue to a polling machine. Otherwise, they must register
 * before doing so.
 *
 * @see system.registration.RegistrationForm
 * @see CheckRegistrationController
 * @see RegistrationHandler
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

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param fxmlFileLocation
     * The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or <tt>null</tt> if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert legalIDTextField != null : "fx:id=\"legalIDTextField\" was not " +
                "injected: check your FXML file 'CheckRegistration.fxml'.";
        assert legalIDLabel != null : "fx:id=\"legalIDLabel\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert colorField != null : "fx:id=\"colorField\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not " +
                "injected: check your FXML file 'CheckRegistration.fxml'.";
        assert resultImage != null : "fx:id=\"resultImage\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert resultLabel != null : "fx:id=\"resultLabel\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert resultDescriptionLabel != null : "fx:id=\"resultDescriptionLabel\" " +
                "was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert checkingLabel != null : "fx:id=\"checkingLabel\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert IDNumberLayer != null : "fx:id=\"IDNumberLayer\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert checkRegistrationLayer != null : "fx:id=\"checkRegistrationLayer\" " +
                "was not injected: check your FXML file 'CheckRegistration.fxml'.";
        assert enterButton != null : "fx:id=\"enterButton\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";
        assert doneButton != null : "fx:id=\"doneButton\" was not injected: " +
                "check your FXML file 'CheckRegistration.fxml'.";

        cancelButton.setOnMouseClicked(event ->
                ((Stage) this.cancelButton.getScene().getWindow()).close());
        enterButton.setOnMouseClicked(event -> {
            setUpCheckingView();
            byte[] hashedID = voterIsRegistered(this.legalIDTextField.getText());
            resultDescriptionLabel.setText("The hashed user id is : " + Arrays.toString(hashedID));
            setUpResultView(hashedID!=null);

        });
        resetButton.setOnMouseClicked(event -> reset());
        doneButton.setOnMouseClicked(event ->
                ((Stage) this.doneButton.getScene().getWindow()).close());
    }

    /**
     * Sets the <tt>RegistrationHandler</tt> for this controller. This should
     * be done before the controller is shown.
     *
     * @param registrationHandler   the RegistrationHandler to use
     */
    public void setRegistrationHandler(RegistrationHandler registrationHandler) {
        this.registrationHandler = registrationHandler;
    }

    /**
     * Checks whether the voter with teh specified state ID number is
     * registered to vote.
     *
     * @param legalID   the voter's state ID number
     * @return  true if registered, false if not
     */
    private byte[] voterIsRegistered(String legalID) {
        return this.registrationHandler.voterIsRegistered(legalID);
    }

    /**
     * Resets the view to accept a state ID number.
     */
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

    /**
     * Changes the view to one which displays a progress indicator while
     * searching the registration database for the voter's state ID number.
     */
    private void setUpCheckingView() {
        IDNumberLayer.setVisible(false);
        checkRegistrationLayer.setVisible(true);
    }

    /**
     * Changes the view to one which displays whether a voter is registered
     * or not.
     */
    private void setUpResultView(boolean result) {
        progressIndicator.setVisible(false);
        checkingLabel.setVisible(false);

        if (result) {
            colorField.setStyle("-fx-background-color: green");
            resultImage.setImage(new Image("/system/resources/checkmark.png"));
            resultLabel.setText("Registered.");
            resultDescriptionLabel.setStyle("-fx-font-size: 14px;");

        } else {
            colorField.setStyle("-fx-background-color: red");
            resultImage.setImage(new Image("/system/resources/cross.png"));
            resultLabel.setText("Not registered.");
            resultDescriptionLabel.setText("The voter will need to register " +
                    "before proceeding.");
        }

        colorField.setVisible(true);
        resultImage.setVisible(true);
        resultLabel.setVisible(true);
        resultDescriptionLabel.setVisible(true);

        resetButton.setVisible(true);
        doneButton.setVisible(true);
    }
}
