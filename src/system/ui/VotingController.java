package system.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import system.election.ElectionHandler;
import system.election.voting.BallotHandler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the voting process, cycling through various <tt>Scene</tt>s. Once
 * this controller is closed, the entire program closes.
 *
 * @see system.election.voting.Ballot
 * @see BallotHandler
 * @see ElectionHandler
 */
public class VotingController implements Initializable {

    @FXML
    private Button voteButton;

    @FXML
    private Button exitButton;

    private BallotHandler ballotHandler;

    private ElectionHandler electionHandler;

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

        // VotingHome.fxml
        assert voteButton != null : "fx:id=\"voteButton\" was not injected: " +
                "check your FXML file 'VotingHome.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: " +
                "check your FXML file 'VotingHome.fxml'.";


        voteButton.setOnAction(action -> {
            Dialog<String> hashStringDailog = new Dialog<String>();
            ButtonType doneButtonType = new ButtonType("Done", ButtonBar
                    .ButtonData.OK_DONE);
            hashStringDailog.getDialogPane().getButtonTypes().addAll
                    (doneButtonType, ButtonType.CANCEL);

            Label forPollworkerLabel = new Label("FOR POLLWORKER");
            forPollworkerLabel.setStyle("-fx-font-size: 22;");
            forPollworkerLabel.setStyle("-fx-padding: 20px");
            Label IDLabel = new Label("Voter's hashed ID#: ");
            TextField IDTextField = new TextField();

        });
        exitButton.setOnAction(action -> Platform.exit());

    }

    /**
     * Sets the <tt>BallotHandler</tt> for this controller. This should
     * be done before the controller is shown.
     *
     * @param ballotHandler   the BallotHandler to use
     */
    public void setBallotHandler(BallotHandler ballotHandler) {
        this.ballotHandler = ballotHandler;
    }

    /**
     * Sets the <tt>ElectionHandler</tt> for this controller. This should
     * be done before the controller is shown.
     *
     * @param electionHandler   the ElectionHandler to use
     */
    public void setElectionHandler(ElectionHandler electionHandler) {
        this.electionHandler = electionHandler;
    }
}
