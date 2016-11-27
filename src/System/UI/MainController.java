package System.UI;

import System.DB.FileBallotHandler;
import System.DB.FileRegistrationHandler;
import System.Election.ElectionHandler;
import System.Election.Voting.BallotHandler;
import System.Registration.RegistrationHandler;
import com.sun.javafx.stage.StageHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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
                stage.initOwner(StageHelper.getStages().get(0));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        verifyRegistrationButton.setOnMouseClicked(event -> {
            //todo start verify registration
        });
        electionSetupButton.setOnMouseClicked(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Once the program is set up as a voting machine, you " +
                            "will be unable to return to the menu unless " +
                            "you restart the software. Are you sure you " +
                            "want to continue?", ButtonType.CANCEL, ButtonType.YES);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // Begin voting setup.
                }
            });
        });
    }

}
