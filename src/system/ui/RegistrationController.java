package system.ui;

import system.registration.RegistrationForm;
import system.registration.RegistrationHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A screen used to register a new voter. There are multiple fields for
 * entering the information required to register a voter. If all of the
 * fields are filled in correctly, the information will be complied into a
 * <tt>RegistrationForm</tt> and saved by the <tt>RegistrationHandler</tt>.
 *
 * @see system.registration.RegistrationForm
 * @see RegistrationController
 * @see RegistrationHandler
 */
public class RegistrationController implements Initializable {

    private RegistrationHandler registrationHandler;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField middleNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField addressLine1TextField;

    @FXML
    private TextField addressLine2TextField;

    @FXML
    private TextField ssnTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField legalIDTextField;

    @FXML
    private ChoiceBox<String> stateChoiceBox;

    @FXML
    private ChoiceBox<String> countyChoiceBox;

    @FXML
    private ChoiceBox<String> cityChoiceBox;

    @FXML
    private TextField zipTextField;

    @FXML
    private Button cancelButton;

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
        assert firstNameTextField != null : "fx:id=\"firstNameTextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert middleNameTextField != null : "fx:id=\"middleNameTextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert lastNameTextField != null : "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert addressLine1TextField != null : "fx:id=\"addressLine1TextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert addressLine2TextField != null : "fx:id=\"addressLine2TextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert ssnTextField != null : "fx:id=\"ssnTextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert phoneNumberTextField != null : "fx:id=\"phoneNumberTextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert birthDatePicker != null : "fx:id=\"birthDatePicker\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert legalIDTextField != null : "fx:id=\"legalIDTextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert stateChoiceBox != null : "fx:id=\"stateChoiceBox\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert countyChoiceBox != null : "fx:id=\"countyChoiceBox\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert cityChoiceBox != null : "fx:id=\"cityChoiceBox\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert zipTextField != null : "fx:id=\"zipTextField\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'RegistrationForm.fxml'.";
        assert doneButton != null : "fx:id=\"doneButton\" was not injected: check your FXML file 'RegistrationForm.fxml'.";

        this.getChoices();

        this.cancelButton.setOnMouseClicked(event -> ((Stage) this.cancelButton.getScene().getWindow()).close());

        this.doneButton.setOnMouseClicked(event -> {
            if (this.formIsComplete()) {
                this.registrationHandler.register(this.getRegistrationForm());
                Alert voterRegistered = new Alert(Alert.AlertType.INFORMATION, "Voter registered.", ButtonType.OK);
                voterRegistered.showAndWait();
                ((Stage) this.cancelButton.getScene().getWindow()).close();
            }
        });
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
     * Sets up the <tt>ChoiceBox</tt>es for selecting a voter's state,
     * county, and city.
     */
    private void getChoices() {
        this.stateChoiceBox.getItems().add("SC");
        this.stateChoiceBox.setValue("SC");
        this.stateChoiceBox.setDisable(true);

        this.countyChoiceBox.getItems().add("Charleston");
        this.countyChoiceBox.setValue("Charleston");
        this.countyChoiceBox.setDisable(true);

        this.cityChoiceBox.getItems().add("Charleston");
        this.cityChoiceBox.setValue("Charleston");
        this.cityChoiceBox.setDisable(true);
    }

    /**
     * Checks whether all of the required fields have valid information in them.
     *
     * @return  true if form is valid, false if not
     */
    private boolean formIsComplete() {
        ArrayList<String> errors = new ArrayList<>();
        String ssn;

        if (firstNameTextField.getText().equals("")) {
            errors.add("First Name: Required.");
        } if (!this.stringHasOnlyAlphanumerics(firstNameTextField.getText())) {
            errors.add("First Name: Can only contain spaces, letters A - Z, or numbers 0 - 9.");
        } if (!this.stringHasOnlyAlphanumerics(middleNameTextField.getText())) {
            errors.add("Middle Name: Can only contain spaces, letters A - Z, or numbers 0 - 9.");
        } if (lastNameTextField.getText().equals("")) {
            errors.add("Last Name: Required.");
        } if (!this.stringHasOnlyAlphanumerics(lastNameTextField.getText())) {
            errors.add("Last Name: Can only contain spaces, letters A - Z, or numbers 0 - 9.");
        }

        if (birthDatePicker.getValue() == null) {
            errors.add("Birth Date: Required.");
        }

        if (addressLine1TextField.getText().equals("")) {
            errors.add("Address Line 1: Required.");
        } if (stateChoiceBox.getValue() == null) {
            errors.add("State: Required.");
        } if (countyChoiceBox.getValue() == null) {
            errors.add("County: Required.");
        } if (cityChoiceBox.getValue() == null) {
            errors.add("City: Required.");
        }

        if (zipTextField.getText().equals("")) {
            errors.add("Postal Code: Required.");
        } else {
            if (!this.stringHasOnlyDigits(zipTextField.getText())) {
                errors.add("Postal Code: Can only contain numbers 0 - 9.");
            } if (!this.stringHasLength(zipTextField.getText(), 5)) {
                errors.add("Postal Code: Invalid length.");
            }
        }

        ssn = removeSymbolsFromString(ssnTextField.getText());
        if (ssn.equals("")) {
            errors.add("Social Security #: Required.");
        } else {
            if (!this.stringHasOnlyDigits(ssn)) {
                errors.add("Social Security #: Can only contain numbers or dashes.");
            } if (!this.stringHasLength(ssn, 9)) {
                errors.add("Social Security #: Invalid length.");
            }
        }

        if (legalIDTextField.getText().equals("")) {
            errors.add("Driver's License #: Required.");
        } if (!stringHasOnlyAlphanumerics(legalIDTextField.getText())) {
            errors.add("Driver's License #: Can only contain letters A - Z, or numbers 0 - 9.");
        }

        if (!errors.isEmpty()) {
            this.showErrors(errors);
            return false;
        }
        return true;
    }

    /**
     * Takes the information from the <tt>Scene</tt>'s various fields and
     * puts it into a <tt>RegistrationForm</tt> to be registered by the
     * <tt>RegistrationHandler</tt>.
     *
     * @return  the completed RegistrationForm
     */
    private RegistrationForm getRegistrationForm() {
        return new RegistrationForm(
                firstNameTextField.getText(),
                middleNameTextField.getText(),
                lastNameTextField.getText(),
                phoneNumberTextField.getText(),
                birthDatePicker.getValue().getDayOfMonth(),
                birthDatePicker.getValue().getMonthValue(),
                birthDatePicker.getValue().getYear(),
                legalIDTextField.getText(),
                (addressLine1TextField.getText() + "\n" + addressLine2TextField.getText()),
                cityChoiceBox.getValue(),
                countyChoiceBox.getValue(),
                stateChoiceBox.getValue(),
                zipTextField.getText()
                );
    }

    /**
     * Shows an <tt>Alert</tt> containing the errors present in the form, if
     * the user has tried to register. Called by formIsComplete() once errors
     * are identified.
     *
     * @param errors  a list of messages describing errors in the form
     */
    private void showErrors(List<String> errors) {
        String errorString = "The form contains the following errors:\n\n";
        for (String error : errors) {
            errorString += error + "\n";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, errorString, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Checks that a <tt>String</tt> contains only letters or digits.
     *
     * @param string    the String to evaluate
     * @return  true if string contains only alphanumerics, false if not
     */
    private boolean stringHasOnlyAlphanumerics(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that a <tt>String</tt> contains only digits.
     *
     * @param string    the String to evaluate
     * @return  true if string contains only digits, false if not
     */
    private boolean stringHasOnlyDigits(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes any characters from a <tt>String</tt> which are not letters or
     * digits.
     *
     * @param string    the String to filter
     * @return  the resulting String
     */
    private String removeSymbolsFromString(String string) {
        String rtnval = "";
        for (char c : string.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                rtnval += c;
            }
        }
        return rtnval;
    }

    /**
     * Checks whether a specified <tt>String</tt> has a specified length.
     *
     * @param string    the String to evaulate
     * @param length    the int length to check for
     * @return  true if string is of length length, false if not
     */
    private boolean stringHasLength(String string, int length) {
        return string.length() == length;
    }
}
