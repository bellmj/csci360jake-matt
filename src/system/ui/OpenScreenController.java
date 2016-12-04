package system.ui;

import system.db.FileBallotHandler;
import system.db.FileRegistrationHandler;
import system.election.ElectionHandler;
import system.election.voting.BallotHandler;
import system.registration.RegistrationHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    private ElectionHandler electionHandler;
    private RegistrationHandler registrationHandler;
    private BallotHandler ballotHandler;

    @FXML
    private ImageView image;

    @FXML
    private Button electionSetupButton;

    @FXML
    private Button verifyRegistrationButton;

    @FXML
    private Button registerButton;


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

        this.electionHandler = new ElectionHandler();
        this.registrationHandler = new RegistrationHandler(new FileRegistrationHandler());
        this.ballotHandler = new BallotHandler(new FileBallotHandler());

        registerButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegistrationForm.fxml"));
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckRegistration.fxml"));
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ElectionSetup.fxml"));
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
    }

}
