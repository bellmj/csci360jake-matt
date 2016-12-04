package system.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.election.ElectionHandler;
import system.election.Position;
import system.election.voting.BallotHandler;
import system.resources.VotingCycle;

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

    @FXML
    private Label positionTitleLabel;

    @FXML
    private Label propositionTitleLabel;

    @FXML
    private ListView positionListView;

    @FXML
    private Label propositionDescriptionLabel;

    @FXML
    private Button positionPreviousButton;

    @FXML
    private Button propositionPreviousButton;

    @FXML
    private Button positionNextButton;

    @FXML
    private Button propositionNextButton;

    @FXML
    private Button positionCancelButton;

    @FXML
    private Button propositionCancelButton;

    private BallotHandler ballotHandler;
    private FXMLLoader fxmlLoader;
    private VotingCycle votingCycle;

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

        // PositionVotingScreen.fxml
        assert positionTitleLabel != null : "fx:id=\"positionTitleLabel\" was" +
                " not injected: check your FXML file 'VotingHome.fxml'.";
        assert positionListView != null : "fx:id=\"positionListView\" was" +
                " not injected: check your FXML file 'VotingHome.fxml'.";
        assert positionPreviousButton != null : "fx:id=\"positionPreviousButton\" " +
                "was not injected: check your FXML file 'VotingHome.fxml'.";
        assert positionNextButton != null : "fx:id=\"positionNextButton\" " +
                "was not injected: check your FXML file 'VotingHome.fxml'.";
        assert positionCancelButton != null : "fx:id=\"positionCancelButton\"" +
                " was not injected: check your FXML file 'VotingHome.fxml'.";

        // PropositionVotingScreen.fxml
        assert propositionTitleLabel != null : "fx:id=\"propositionTitleLabel\" " +
                "was" +
                " not injected: check your FXML file 'VotingHome.fxml'.";
        assert propositionDescriptionLabel != null :
                "fx:id=\"propositionDescriptionLabel\" was" +
                " not injected: check your FXML file 'VotingHome.fxml'.";
        assert propositionPreviousButton != null :
                "fx:id=\"propositionPreviousButton\" " +
                "was not injected: check your FXML file 'VotingHome.fxml'.";
        assert propositionNextButton != null : "fx:id=\"propositionNextButton\" " +
                "was not injected: check your FXML file 'VotingHome.fxml'.";
        assert propositionCancelButton != null : "fx:id=\"propositionCancelButton\"" +
                " was not injected: check your FXML file 'VotingHome.fxml'.";

        this.votingCycle = getVotingCycle();

        voteButton.setOnAction(action -> {
            Dialog<String> hashStringDialog = new Dialog<String>();
            hashStringDialog.setTitle("Enter Voter ID");
            ButtonType doneButtonType = new ButtonType("Done", ButtonBar
                    .ButtonData.OK_DONE);
            hashStringDialog.getDialogPane().getButtonTypes().addAll
                    (doneButtonType, ButtonType.CANCEL);

            Label forPollworkerLabel = new Label("FOR POLLWORKER");
            forPollworkerLabel.setStyle("-fx-font-size: 22;");
            forPollworkerLabel.setStyle("-fx-padding: 20px");
            Label IDLabel = new Label("Voter's hashed ID#: ");
            TextField IDTextField = new TextField();

            VBox vBox = new VBox();
            GridPane gridPane = new GridPane();
            gridPane.add(IDLabel, 0, 0);
            gridPane.add(IDTextField, 1, 0);
            vBox.getChildren().addAll(forPollworkerLabel, gridPane);
            hashStringDialog.getDialogPane().setContent(vBox);

            hashStringDialog.setResultConverter(buttonTypePressed -> {
                    if (buttonTypePressed == doneButtonType) {
                        return IDTextField.getText();
                    } else {
                        return null;
                    }
            });

            hashStringDialog.initModality(Modality.WINDOW_MODAL);
            hashStringDialog.initOwner(this.voteButton.getScene().getWindow());
            hashStringDialog.showAndWait().ifPresent(response -> {
//                ballotHandler.createBallot(response);A

            });

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

    private VotingCycle getVotingCycle() {
        return new VotingCycle();
    }

}
