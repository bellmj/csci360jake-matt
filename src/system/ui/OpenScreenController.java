package system.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.election.ElectionHandler;
import system.election.voting.BallotHandler;
import system.registration.RegistrationHandler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The central controller which leads to the three main functionalities of
 * the program.
 * <p>
 *     From this scene, the user can press one of three <tt>Button</tt>s to
 *     register a new voter, check the registration status of a prospective
 *     voter, or set up a polling machine to accept votes. All windows except
 *     for those following setup as a polling machine are children of this
 *     controller.
 * </p>
 *
 * @see RegistrationController
 * @see CheckRegistrationController
 * @see ElectionSetupController
 */
public class OpenScreenController implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Button electionSetupButton;

    @FXML
    private Button verifyRegistrationButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button toAdminOptionsButton;

    private ElectionHandler electionHandler;
    private RegistrationHandler registrationHandler;
    private BallotHandler ballotHandler;
    private boolean shouldShowAdminOptions;

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
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'OpenScreen.fxml'.";
        assert electionSetupButton != null : "fx:id=\"electionSetupButton\" was not injected: check your FXML file 'OpenScreen.fxml'.";
        assert verifyRegistrationButton != null : "fx:id=\"verifyRegistrationButton\" was not injected: check your FXML file 'OpenScreen.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'OpenScreen.fxml'.";
        assert toAdminOptionsButton != null : "fx:id=\"toAdminOptionsButton\" was not injected: check your FXML file 'OpenScreen.fxml'.";

        registerButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/RegistrationForm.fxml"));
                Parent root = fxmlLoader.load();
                RegistrationController registrationController = fxmlLoader.getController();
                registrationController.setRegistrationHandler(this.registrationHandler);

                Stage stage = new Stage();
                stage.setTitle("Register a New Voter");
                stage.setScene(new Scene(root, 1000, 365));
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(registerButton.getScene().getWindow());
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        verifyRegistrationButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/CheckRegistration.fxml"));
                Parent root = fxmlLoader.load();
                CheckRegistrationController checkRegistrationController = fxmlLoader.getController();
                checkRegistrationController.setRegistrationHandler(this.registrationHandler);
                checkRegistrationController.reset();

                Stage stage = new Stage();
                stage.setTitle("Check registration");
                stage.setScene(new Scene(root, 700, 275));
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(verifyRegistrationButton.getScene().getWindow());
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        electionSetupButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ElectionSetup.fxml"));
                Parent root = fxmlLoader.load();
                ElectionSetupController electionSetupController = fxmlLoader.getController();
                electionSetupController.setElectionHandler(this.electionHandler);
                electionSetupController.setBallotHandler(this.ballotHandler);

                Stage stage = new Stage();
                stage.setTitle("Election Setup");
                stage.setScene(new Scene(root, 900, 700));
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(electionSetupButton.getScene().getWindow());
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        toAdminOptionsButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                        ("fxml/AdminScreen.fxml"));
                Pane root = fxmlLoader.load();
                AdminScreenController adminScreenController = fxmlLoader
                        .getController();

                adminScreenController.setElectionHandler(electionHandler);
                adminScreenController.setBallotHandler(ballotHandler);
                adminScreenController.setRegistrationHandler(registrationHandler);
                adminScreenController.setShouldShowAdminOptions(shouldShowAdminOptions);

                Stage stage = (Stage) toAdminOptionsButton.getScene().getWindow();
                stage.setScene(new Scene(root, 400, 550));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void setBallotHandler(BallotHandler ballotHandler) {
        this.ballotHandler = ballotHandler;
    }

    void setElectionHandler(ElectionHandler electionHandler) {
        this.electionHandler = electionHandler;
    }

    void setRegistrationHandler(RegistrationHandler registrationHandler) {
        this.registrationHandler = registrationHandler;
    }

    void setShouldShowAdminOptions(boolean shouldShowAdminOptions) {
        this.shouldShowAdminOptions = shouldShowAdminOptions;
        toAdminOptionsButton.setVisible(shouldShowAdminOptions);
    }
}
