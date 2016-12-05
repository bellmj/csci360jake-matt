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
 * Created by Jake's PC on 12/5/2016.
 */
public class AdminScreenController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button countButton;

    @FXML
    private Button recountButton;

    @FXML
    private Button viewResultsButton;

    @FXML
    private Button previousButton;

    private ElectionHandler electionHandler;
    private BallotHandler ballotHandler;
    private RegistrationHandler registrationHandler;
    private boolean shouldShowAdminOptions;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert imageView != null : "fx:id=\"imageView\" was not injected: " +
                "check your FXML file 'AdminScreen.fxml'.";
        assert countButton != null : "fx:id=\"countButton\" was not injected:" +
                " check your FXML file 'AdminScreen.fxml'.";
        assert recountButton != null : "fx:id=\"recountButton\" was not " +
                "injected: check your FXML file 'AdminScreen.fxml'.";
        assert viewResultsButton != null : "fx:id=\"viewResultsButton\" was " +
                "not injected: check your FXML file 'AdminScreen.fxml'.";
        assert previousButton != null : "fx:id=\"previousButton\" was not " +
                "injected: check your FXML file 'AdminScreen.fxml'.";

        countButton.setOnAction(action -> {
            //TODO Perform count
            //TODO Show alert indicating completion
        });

        recountButton.setOnAction(action -> {
            //TODO Print ballots to the console.
            //TODO Show alert with information.
        });

        viewResultsButton.setOnAction(action -> {
            //TODO Display popup with stylized report.
        });

        previousButton.setOnAction(action -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                        ("OpenScreen.fxml"));
                Pane root = fxmlLoader.load();
                OpenScreenController openScreenController = fxmlLoader.getController();

                openScreenController.setElectionHandler(electionHandler);
                openScreenController.setBallotHandler(ballotHandler);
                openScreenController.setRegistrationHandler(registrationHandler);
                openScreenController.setShouldShowAdminOptions(shouldShowAdminOptions);

                Stage stage = (Stage) previousButton.getScene().getWindow();
                stage.setScene(new Scene(root, 400, 550));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void setElectionHandler(ElectionHandler electionHandler) {
        this.electionHandler = electionHandler;
    }

    void setBallotHandler(BallotHandler ballotHandler) {
        this.ballotHandler = ballotHandler;
    }

    void setRegistrationHandler(RegistrationHandler registrationHandler) {
        this.registrationHandler = registrationHandler;
    }

    void setShouldShowAdminOptions(boolean shouldShowAdminOptions) {
        this.shouldShowAdminOptions = shouldShowAdminOptions;
    }

}
