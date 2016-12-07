package system.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.election.Candidate;
import system.election.ElectionHandler;
import system.election.ReportController;
import system.election.voting.Ballot;
import system.election.voting.BallotHandler;
import system.registration.RegistrationHandler;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

        viewResultsButton.setDisable(true);

        countButton.setOnAction(action -> {
            Dialog<String> passwordDialog = new Dialog<>();
            passwordDialog.setTitle("Add Position");
            ButtonType doneButtonType = new ButtonType("Done", ButtonBar
                    .ButtonData.OK_DONE);
            passwordDialog.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

            VBox vBox = new VBox();
            GridPane gridPane = new GridPane();
            Label emptyLabel = new Label("Cannot be empty.");
            emptyLabel.setStyle("-fx-text-fill: red");
            emptyLabel.setVisible(false);

            Label passwordLabel = new Label("Re-Enter Password: ");
            PasswordField passwordField = new PasswordField();

            gridPane.add(passwordLabel, 0, 0);
            gridPane.add(passwordField, 1, 0);
            vBox.getChildren().addAll(gridPane, emptyLabel);

            passwordDialog.getDialogPane().lookupButton(doneButtonType).addEventFilter(
                    ActionEvent.ACTION, event -> {
                        if (passwordField.getText().equals("")) {
                            emptyLabel.setVisible(true);
                            event.consume();
                        }
                    });

            passwordDialog.setResultConverter(buttonType -> {
                if (buttonType == doneButtonType) {
                    return passwordField.getText();
                } else {
                    return null;
                }
            });

            passwordDialog.getDialogPane().setContent(vBox);

            passwordDialog.showAndWait().ifPresent(response -> {
                final char[] password = response.toCharArray();
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().setAll(
                            new FileChooser.ExtensionFilter("Election File (*.elec)", "*.elec")
                    );
                    fileChooser.setTitle("Open Election File");
                    File file = fileChooser.showOpenDialog(countButton
                            .getScene().getWindow());

                    if (file == null || !file.exists()) {
                        throw new FileNotFoundException();
                    }
                    if (!file.getPath().substring(file.getPath().lastIndexOf("."))
                            .equals(".elec")) {
                        Alert invalidFileAlert = new Alert(Alert.AlertType.ERROR,
                                "Must be \".elec\" file.",
                                ButtonType.OK);
                        invalidFileAlert.showAndWait();
                        throw new IOException();
                    }

                    FileInputStream in = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(in);

                    String electionString = (String) ois.readObject();

                    in.close();
                    ois.close();

                    electionHandler.createElectionFromString(electionString);

                    electionHandler.assignVotesForBallots(password, ballotHandler
                            .getAllBallots(password));

                    Alert finished = new Alert(Alert.AlertType.INFORMATION,
                            "Counting complete.", ButtonType.OK);
                    finished.initModality(Modality.WINDOW_MODAL);
                    finished.initOwner(countButton.getScene().getWindow());
                    viewResultsButton.setDisable(false);
                    finished.showAndWait();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });

        recountButton.setOnAction(action -> {
            Dialog<String> passwordDialog = new Dialog<>();
            passwordDialog.setTitle("Add Position");
            ButtonType doneButtonType = new ButtonType("Done", ButtonBar
                    .ButtonData.OK_DONE);
            passwordDialog.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

            VBox vBox = new VBox();
            GridPane gridPane = new GridPane();
            Label emptyLabel = new Label("Cannot be empty.");
            emptyLabel.setStyle("-fx-text-fill: red");
            emptyLabel.setVisible(false);

            Label passwordLabel = new Label("Re-Enter Password: ");
            PasswordField passwordField = new PasswordField();

            gridPane.add(passwordLabel, 0, 0);
            gridPane.add(passwordField, 1, 0);
            vBox.getChildren().addAll(gridPane, emptyLabel);

            passwordDialog.getDialogPane().lookupButton(doneButtonType).addEventFilter(
                    ActionEvent.ACTION, event -> {
                        if (passwordField.getText().equals("")) {
                            emptyLabel.setVisible(true);
                            event.consume();
                        }
                    });

            passwordDialog.setResultConverter(buttonType -> {
                if (buttonType == doneButtonType) {
                    return passwordField.getText();
                } else {
                    return null;
                }
            });

            passwordDialog.getDialogPane().setContent(vBox);

            passwordDialog.showAndWait().ifPresent(response -> {
                final char[] password = response.toCharArray();
                List<Ballot> ballots = ballotHandler.getAllBallots(password);
                String selected;
                for (Ballot ballot : ballots) {
                    for (Map.Entry<String, Candidate> selection : ballot
                            .getSelections().entrySet()) {
                        if (selection.getValue() != null) {
                            selected = selection.getValue().getName();
                        } else {
                            selected = "Abstain";
                        }
                        System.out.println(selection.getKey() + ": " + selected);
                    }
                    for (Map.Entry<String, Boolean> proposition : ballot
                            .getPropositions().entrySet()) {
                        if (proposition.getValue() != null) {
                            if (proposition.getValue()) {
                                selected = "FOR";
                            } else {
                                selected = "AGAINST";
                            }
                        } else {
                            selected = "Abstain";
                        }
                        System.out.println(proposition.getKey() + ": " +
                                selected);
                    }
                }
            });
        });

        viewResultsButton.setOnAction(action -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                        ("fxml/Report.fxml"));
                Pane root = fxmlLoader.load();
                ReportController reportController = fxmlLoader
                        .getController();

                reportController.setElectionHandler(electionHandler);

                Stage stage = new Stage();
                stage.setTitle("Election Report");
                stage.setScene(new Scene(root, 400, 550));
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(viewResultsButton.getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        previousButton.setOnAction(action -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                        ("fxml/OpenScreen.fxml"));
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
