package system.ui;

import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import system.election.Candidate;
import system.election.ElectionHandler;
import system.election.Position;
import system.election.Proposition;
import system.election.voting.BallotHandler;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controls the voting process, cycling through various <tt>Scene</tt>s. Once
 * this controller is closed, the entire program closes.
 *
 * @see system.election.voting.Ballot
 * @see BallotHandler
 * @see ElectionHandler
 */
public class VotingController implements Initializable {

    @FXML private ImageView imageView;
    @FXML private Button voteButton;
    @FXML private Button exitButton;

    @FXML private Label positionTitleLabel;
    @FXML private ListView<String> positionListView;
    @FXML private Button positionPreviousButton;
    @FXML private Button positionCancelButton;
    @FXML private Button positionNextButton;

    @FXML private Label propositionTitleLabel;
    @FXML private Label propositionDescriptionLabel;
    @FXML private RadioButton forRadioButton;
    @FXML private RadioButton againstRadioButton;
    @FXML private RadioButton abstainRadioButton;
    @FXML private Button propositionPreviousButton;
    @FXML private Button propositionCancelButton;
    @FXML private Button propositionNextButton;

    @FXML private ListView<Label> summaryListView;
    @FXML private Button summaryPreviousButton;
    @FXML private Button summaryCancelButton;
    @FXML private Button finishButton;

    private ToggleGroup radioButtonGroup;
    private ElectionHandler electionHandler;
    private BallotHandler ballotHandler;
    private Position[] positions;
    private Proposition[] propositions;
    private HashMap<String, Integer> currentCandidateOptions;
    private int index;

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
        assert imageView != null : "fx:id=\"imageView\" was not injected: " +
                "check your FXML file 'VotingHome.fxml'.";
        assert voteButton != null : "fx:id=\"voteButton\" was not injected: " +
                "check your FXML file 'VotingHome.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: " +
                "check your FXML file 'VotingHome.fxml'.";

        // PositionVotingScreen.fxml
        assert positionTitleLabel != null : "fx:id=\"positionTitleLabel\" was" +
                " not injected: check your FXML file 'PositionVotingScreen.fxml'.";
        assert positionListView != null : "fx:id=\"positionListView\" was" +
                " not injected: check your FXML file 'PositionVotingScreen.fxml'.";
        assert positionPreviousButton != null : "fx:id=\"positionPreviousButton\" " +
                "was not injected: check your FXML file 'PositionVotingScreen.fxml'.";
        assert positionNextButton != null : "fx:id=\"positionNextButton\" " +
                "was not injected: check your FXML file 'PositionVotingScreen.fxml'.";
        assert positionCancelButton != null : "fx:id=\"positionCancelButton\"" +
                " was not injected: check your FXML file 'PositionVotingScreen.fxml'.";

        // PropositionVotingScreen.fxml
        assert propositionTitleLabel != null : "fx:id=\"propositionTitleLabel\" " +
                "was" +
                " not injected: check your FXML file 'PropositionVotingScreen.fxml'.";
        assert propositionDescriptionLabel != null :
                "fx:id=\"propositionDescriptionLabel\" was" +
                " not injected: check your FXML file 'PropositionVotingScreen.fxml'.";
        assert propositionPreviousButton != null :
                "fx:id=\"propositionPreviousButton\" " +
                "was not injected: check your FXML file 'PropositionVotingScreen.fxml'.";
        assert propositionNextButton != null : "fx:id=\"propositionNextButton\" " +
                "was not injected: check your FXML file 'PropositionVotingScreen.fxml'.";
        assert propositionCancelButton != null : "fx:id=\"propositionCancelButton\"" +
                " was not injected: check your FXML file 'PropositionVotingScreen.fxml'.";
        assert forRadioButton != null : "fx:id=\"forRadioButton\" was not " +
                "injected: check your FXML file 'PropositionVotingScreen.fxml'.";
        assert againstRadioButton != null : "fx:id=\"againstRadioButton\" was" +
                " not injected: check your FXML file 'PropositionVotingScreen" +
                ".fxml'.";
        assert abstainRadioButton != null : "fx:id=\"abstainRadioButton\" " +
                "was not injected: check your FXML file " +
                "'PropositionVotingScreen.fxml'.";

        // VotingSummary.fxml
        assert summaryListView != null : "fx:id=\"summaryListView\" was not " +
                "injected: check your FXML file 'VotingSummary.fxml'.";
        assert summaryPreviousButton != null : "fx:id=\"summaryPreviousButton\" " +
                "was not injected: check your FXML file 'VotingSummary.fxml'.";
        assert finishButton != null : "fx:id=\"finishButton\" was not " +
                "injected: check your FXML file 'VotingSummary.fxml'.";
        assert summaryCancelButton != null : "fx:id=\"summaryCancelButton\" " +
                "was not injected: check your FXML file 'VotingSummary.fxml'.";

        this.index = 0;
        this.currentCandidateOptions = new HashMap<>();
    }

    /**
     * Sets the <tt>BallotHandler</tt> for this controller. This should
     * be done before the controller is shown.
     *
     * @param ballotHandler   the BallotHandler to use
     */
    void setBallotHandler(BallotHandler ballotHandler) {
        this.ballotHandler = ballotHandler;
    }

    /**
     * Sets the <tt>ElectionHandler</tt> for this controller. This should
     * be done before the controller is shown.
     *
     * @param electionHandler   the ElectionHandler to use
     */
    void setElectionHandler(ElectionHandler electionHandler) {
        this.electionHandler = electionHandler;
    }

    void setPositions(Position[] positions) {
        this.positions = positions;
    }

    void setPropositions(Proposition[] propositions) {
        this.propositions = propositions;
    }
    void setIndex(int index) {
        this.index = index;
    }

    private void loadHomeScreen(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("fxml/VotingHome.fxml"));
            Pane root = fxmlLoader.load();
            VotingController votingController = fxmlLoader.getController();
            votingController.setIndex(0);
            votingController.setBallotHandler(ballotHandler);
            votingController.setElectionHandler(electionHandler);
            votingController.setPositions(positions);
            votingController.setPropositions(propositions);

            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPositionScreen(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("fxml/PositionVotingScreen.fxml"));
            Pane root = fxmlLoader.load();
            VotingController votingController = fxmlLoader.getController();

            votingController.setIndex(index);
            votingController.setBallotHandler(ballotHandler);
            votingController.setElectionHandler(electionHandler);
            votingController.setPositions(positions);
            votingController.setPropositions(propositions);
            votingController.readInCurrentPosition();

            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInCurrentPosition() {
        positionListView.setEditable(false);
        positionListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        positionListView.getSelectionModel().getSelectedItems().addListener(
                new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> c) {
                        if (positionListView.getSelectionModel()
                                .getSelectedItem().equals("Write-in...")) {
                            Dialog<Pair<String, String>> writeInDialog = new
                                    Dialog<>();
                            writeInDialog.setTitle("Add Candidate");
                            ButtonType doneButtonType = new ButtonType("Done",
                                    ButtonBar.ButtonData.OK_DONE);
                            writeInDialog.getDialogPane().getButtonTypes()
                                    .addAll(doneButtonType, ButtonType.CANCEL);

                            VBox vBox = new VBox();
                            GridPane gridPane = new GridPane();
                            Label emptyLabel = new Label("Cannot be empty.");
                            emptyLabel.setStyle("-fx-text-fill: red");
                            emptyLabel.setId("emptyLabel");
                            emptyLabel.setVisible(false);

                            Label nameLabel = new Label("Name: ");
                            TextField nameTextField = new TextField();
                            Label partyLabel = new Label("Party: ");
                            TextField partyTextField = new TextField();

                            gridPane.add(nameLabel, 0, 0);
                            gridPane.add(nameTextField, 1, 0);
                            gridPane.add(partyLabel, 0, 1);
                            gridPane.add(partyTextField, 1, 1);
                            vBox.getChildren().addAll(gridPane, emptyLabel);

                            writeInDialog.getDialogPane().lookupButton
                                    (doneButtonType).addEventFilter(
                                    ActionEvent.ACTION, event -> {
                                        if (nameTextField.getText().equals("") ||
                                                partyTextField.getText().equals("")) {
                                            emptyLabel.setVisible(true);
                                            event.consume();
                                        }
                                    });

                            writeInDialog.setResultConverter(buttonType -> {
                                if (buttonType == doneButtonType) {
                                    return new Pair<>(nameTextField.getText(),
                                            partyTextField.getText());
                                } else {
                                    return null;
                                }
                            });

                            writeInDialog.getDialogPane().setContent(vBox);
                            writeInDialog.initModality(Modality.WINDOW_MODAL);
                            writeInDialog.initOwner(positionListView.getScene
                                    ().getWindow());

                            writeInDialog.showAndWait().ifPresent(response -> {
                                if (response != null) {
                                    positionListView.getItems().add
                                            (positionListView.getItems().size
                                                    () - 2, response.getKey()
                                                    + "\n\t" + response.getValue());
                                    positionListView.getSelectionModel()
                                            .select(positionListView.getItems
                                                    ().size() - 3);
                                } else {
                                    positionListView.getSelectionModel()
                                            .clearSelection();
                                }
                            });


                        }
                    }
                });

        positionTitleLabel.setText(positions[index].getTitle());
        if (index == 0) {
            positionPreviousButton.setDisable(true);
        }

        currentCandidateOptions.clear();
        positionListView.getItems().clear();

        for (Candidate candidate : positions[index].getCandidates()) {
            positionListView.getItems().add(candidate.getName() + "\n\t"
                    + candidate.getParty());
            currentCandidateOptions.put(candidate.getName(),
                    positionListView.getItems().size() - 1);
        }
        positionListView.getItems().add("Write-in...");
        positionListView.getItems().add("Abstain");

        // If the ballot contains a value for the position already
        if (ballotHandler.getCandidateSelections().containsKey
                (positions[index].getTitle())) {
            Candidate selection = ballotHandler.getCandidateSelections()
                    .get(positions[index].getTitle());

            // If abstained or left empty
            if (selection == null) {
                positionListView.getSelectionModel().select(positionListView
                        .getItems().size() - 1);
                // If written in
            } else {
                if (!currentCandidateOptions.containsKey(selection.getName())) {
                    positionListView.getItems().add(positionListView.getItems()
                            .size() - 2, selection.getName() + "\n\t" +
                            selection.getParty());
                    currentCandidateOptions.put(selection.getName(),
                            positionListView.getItems().size() - 3);
                }
                positionListView.getSelectionModel().select
                        (currentCandidateOptions.get(selection.getName()));
            }
        }
    }

    private void loadPropositionScreen(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("fxml/PropositionVotingScreen.fxml"));
            Pane root = fxmlLoader.load();
            VotingController votingController = fxmlLoader.getController();

            votingController.setIndex(index);
            votingController.setBallotHandler(ballotHandler);
            votingController.setElectionHandler(electionHandler);
            votingController.setPositions(positions);
            votingController.setPropositions(propositions);
            votingController.groupRadioButtons();
            votingController.readInCurrentProposition();

            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInCurrentProposition() {
        propositionTitleLabel.setText(propositions[index].getName());
        propositionDescriptionLabel.setText(propositions[index].getDescription());

        if (ballotHandler.getPropositionSelections().containsKey(propositions[index]
                .getName())) {
            Boolean supportValue = ballotHandler.getPropositionSelections()
                    .get(propositions[index].getName());
            System.out.println(propositions[index]);
            System.out.println(supportValue);
            if (supportValue != null) {
                if (supportValue) {
                    forRadioButton.setSelected(true);
                } else {
                    againstRadioButton.setSelected(true);
                }
            } else {
                abstainRadioButton.setSelected(true);
            }
        }
    }

    private void groupRadioButtons() {
        radioButtonGroup = new ToggleGroup();
        forRadioButton.setToggleGroup(radioButtonGroup);
        forRadioButton.setUserData("FOR");
        forRadioButton.setSelected(false);
        againstRadioButton.setToggleGroup(radioButtonGroup);
        againstRadioButton.setUserData("AGAINST");
        againstRadioButton.setSelected(false);
        abstainRadioButton.setToggleGroup(radioButtonGroup);
        abstainRadioButton.setUserData("Abstain");
        abstainRadioButton.setSelected(false);
    }

    private void loadSummaryScreen(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("fxml/VotingSummary.fxml"));
            Pane root = fxmlLoader.load();
            VotingController votingController = fxmlLoader.getController();

            votingController.setBallotHandler(ballotHandler);
            votingController.setElectionHandler(electionHandler);
            votingController.setPositions(positions);
            votingController.setPropositions(propositions);
            votingController.readInBallotSummary();

            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInBallotSummary() {
        summaryListView.setFocusTraversable(false);

        for (Map.Entry<String, Candidate> entry : ballotHandler
                .getCandidateSelections().entrySet()) {
            if (entry.getValue() == null) {
                summaryListView.getItems().add(new Label(entry.getKey() + ": Abstain"));
            } else {
                summaryListView.getItems().add(new Label(entry.getKey() + ": " + entry
                        .getValue().getName()));
            }
        }

        String supportValue;
        for (Map.Entry<String, Boolean> entry : ballotHandler
                .getPropositionSelections().entrySet()) {
            supportValue = supportBooleanToString(entry.getValue());
            summaryListView.getItems().add(new Label(entry.getKey() + ": " +
                    supportValue));
        }
    }

    @FXML
    private void voteButtonAction() throws IOException {
        Dialog<String> hashStringDialog = new Dialog<>();
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
            ballotHandler.createBallot(response);
            loadPositionScreen((Stage) voteButton.getScene().getWindow());
        });
    }

    @FXML
    private void exitButtonAction() throws IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you" +
                " sure you want to exit the program?",
                ButtonType.CANCEL, ButtonType.YES);
        confirmation.initModality(Modality.WINDOW_MODAL);
        confirmation.initOwner(exitButton.getScene().getWindow());
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Platform.exit();
            }
        });
    }

    @FXML
    private void nextButtonAction(ActionEvent event) throws IOException {

        String selected;
        if (event.getSource() == positionNextButton) {

            selected = positionListView.getSelectionModel().getSelectedItem();
            if (selected == null || selected.equals("Abstain")) {
                ballotHandler.selectCandidate(positions[index], null);
            } else {
                ballotHandler.selectCandidate(positions[index], new Candidate
                        (0L, selected.substring(0, selected.lastIndexOf('\n')),
                        selected.substring(selected.lastIndexOf('\t') + 1)));
            }

            if (index < positions.length - 1) {
                index++;
                loadPositionScreen((Stage) positionNextButton.getScene()
                        .getWindow());

            } else {
                index = 0;
                loadPropositionScreen((Stage) positionNextButton.getScene()
                        .getWindow());
            }
        } else {
            if (radioButtonGroup.getSelectedToggle() != null) {
                selected = radioButtonGroup.getSelectedToggle().getUserData()
                        .toString();
            } else {
                selected = null;
            }


            Boolean supportValue = supportStringToBoolean(selected);
            ballotHandler.addProposition(propositions[index], supportValue);

            if (index < propositions.length - 1) {
                index++;
                loadPropositionScreen((Stage) propositionNextButton.getScene
                        ().getWindow());
            } else {
                loadSummaryScreen((Stage) propositionNextButton.getScene().getWindow());
            }
        }
    }

    @FXML
    private void previousButtonAction(ActionEvent event) throws IOException {

        String selected;
        if (event.getSource() == positionPreviousButton) {

            selected = positionListView.getSelectionModel().getSelectedItem();
            if (selected == null || selected.equals("Abstain")) {
                ballotHandler.selectCandidate(positions[index], null);
            } else {
                ballotHandler.selectCandidate(positions[index], new Candidate
                        (0L, selected.substring(0, selected.lastIndexOf('\n')),
                        selected.substring(selected.lastIndexOf('\t') + 1)));
            }

            if (index > 0) {
                index--;
                loadPositionScreen((Stage) positionPreviousButton.getScene()
                        .getWindow());
            }
        } else if (event.getSource() == propositionPreviousButton){

            if (radioButtonGroup.getSelectedToggle() != null) {
                selected = radioButtonGroup.getSelectedToggle().getUserData()
                        .toString();
            } else {
                selected = null;
            }

            Boolean supportValue = supportStringToBoolean(selected);
            ballotHandler.addProposition(propositions[index], supportValue);

            if (index > 0) {
                index--;
                loadPropositionScreen((Stage) propositionPreviousButton
                        .getScene().getWindow());
            } else {
                index = positions.length - 1;
                loadPositionScreen((Stage) propositionPreviousButton.getScene()
                        .getWindow());
            }
        } else {
            index = propositions.length - 1;
            loadPropositionScreen((Stage) summaryPreviousButton.getScene()
                    .getWindow());
        }
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you" +
                " sure you want to cancel your ballot?", ButtonType.CANCEL,
                ButtonType.YES);
        Object source = event.getSource();
        confirmation.initModality(Modality.WINDOW_MODAL);
        if (source == positionCancelButton) {
            confirmation.initOwner(positionCancelButton.getScene().getWindow());
        } else if (source == propositionCancelButton){
            confirmation.initOwner(propositionCancelButton.getScene().getWindow());
        } else {
            confirmation.initOwner(summaryCancelButton.getScene().getWindow());
        }
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                index = 0;
                currentCandidateOptions.clear();
                ballotHandler.cancelBallot();
                if (source == positionCancelButton) {
                    loadHomeScreen((Stage) positionCancelButton.getScene()
                            .getWindow());
                } else if (source == propositionCancelButton){
                    loadHomeScreen((Stage) propositionCancelButton.getScene()
                            .getWindow());
                } else {
                    loadHomeScreen((Stage) summaryCancelButton.getScene()
                            .getWindow());
                }
            }
        });
    }

    @FXML
    private void finishButtonAction() throws IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Cast " +
                "your ballot?", ButtonType.NO, ButtonType.YES);
        confirmation.initOwner(finishButton.getScene().getWindow());
        confirmation.initModality(Modality.WINDOW_MODAL);
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                ballotHandler.saveBallot();

                Alert thanks = new Alert(Alert.AlertType.INFORMATION,
                        "Thank you for performing your civic duty!",
                        ButtonType.OK);
                thanks.initOwner(finishButton.getScene().getWindow());
                thanks.initModality(Modality.WINDOW_MODAL);
                thanks.showAndWait();
                loadHomeScreen((Stage) finishButton.getScene().getWindow());
            }
        });
    }

    private Boolean supportStringToBoolean(@Nullable String s) {
        Boolean supportValue;
        switch (s) {
            case "FOR":
                supportValue = true;
                break;
            case "AGAINST":
                supportValue = false;
                break;
            default:
                supportValue = null;
                break;
        }
        return supportValue;
    }

    private String supportBooleanToString(Boolean b) {
        String supportValue;
        if (b != null) {
            if (b) {
                supportValue = "FOR";
            } else  {
                supportValue = "AGAINST";
            }
        } else {
            supportValue = "Abstain";
        }
        return supportValue;
    }
}
