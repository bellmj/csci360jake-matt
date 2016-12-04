package system.ui;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import system.election.ElectionHandler;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ElectionSetupController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private ListView<String> positionListView;

    @FXML
    private ListView<String> propositionListView;

    @FXML
    private Button addPosButton;

    @FXML
    private Button addCanButton;

    @FXML
    private Button addPropButton;

    @FXML
    private Button editPosCanButton;

    @FXML
    private Button editPropButton;

    @FXML
    private Button deletePosCanButton;

    @FXML
    private Button deletePropButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button doneButton;

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
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert positionListView != null : "fx:id=\"positionListView\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert propositionListView != null : "fx:id=\"propositionListView\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert addPosButton != null : "fx:id=\"addPosButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert addCanButton != null : "fx:id=\"addCanButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert addPropButton != null : "fx:id=\"addCanButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert editPosCanButton != null : "fx:id=\"editPosCanButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert editPropButton != null : "fx:id=\"editPropButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert deletePosCanButton != null : "fx:id=\"deletePosCanButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert deletePropButton != null : "fx:id=\"deletePropButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert loadButton != null : "fx:id=\"loadButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";
        assert doneButton != null : "fx:id=\"doneButton\" was not injected: check your FXML file 'ElectionSetup.fxml'.";

        positionListView.setEditable(false);
        positionListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        positionListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                setAvailablePosCanButtons();
            }
        });

        propositionListView.setEditable(false);
        propositionListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        propositionListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                setAvailablePropButtons();
            }
        });

        addCanButton.setDisable(true);
        deletePosCanButton.setDisable(true);
        deletePropButton.setDisable(true);
        editPosCanButton.setDisable(true);
        editPropButton.setDisable(true);

        addPosButton.setOnAction(action -> addPosition());

        addCanButton.setOnAction(action -> addCandidate());

        addPropButton.setOnAction(action -> addProposition());

        editPosCanButton.setOnAction(action -> {
            if (positionListView.getSelectionModel().getSelectedItem().substring(0, 1).equals("\t")) {
                editCandidate();
            } else {
                editPosition();
            }
        });

        editPropButton.setOnAction(action -> editProposition());

        deletePosCanButton.setOnAction(action -> {
            if (positionListView.getSelectionModel().getSelectedItem().substring(0, 1).equals("\t")) {
                deleteCandidate();
            } else {
                deletePosition();
            }
        });

        deletePropButton.setOnAction(action -> deleteProposition());

        loadButton.setOnAction(action -> {
            if (!electionIsEmpty()) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Overwrite this election?", ButtonType.YES, ButtonType.CANCEL);
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        loadElectionFromFile();
                    }
                });
            } else {
                loadElectionFromFile();
            }
        });

        saveButton.setOnAction(action -> {
            if (!electionIsEmpty()) {
                saveElectionToFile();
            }
        });

        cancelButton.setOnAction(action -> ((Stage) this.cancelButton.getScene().getWindow()).close());

        doneButton.setOnAction(action -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Once the program is set up as a voting machine, you " +
                            "will be unable to return to the menu unless " +
                            "you restart the software. Are you sure you " +
                            "want to continue?", ButtonType.CANCEL, ButtonType.YES);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        saveElection();
                        // TODO Switch to fullscreen voting
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    public void setElectionHandler(ElectionHandler electionHandler) {
        this.electionHandler = electionHandler;
    }

    private void saveElection() {
        electionHandler.createElectionFromString(electionToString());
    }
    
    private void setAvailablePosCanButtons() {
        if (positionListView.getSelectionModel().getSelectedItem() != null) {
            editPosCanButton.setDisable(false);
            deletePosCanButton.setDisable(false);
            addCanButton.setDisable(false);
        } else {
            editPosCanButton.setDisable(true);
            deletePosCanButton.setDisable(true);
            addCanButton.setDisable(true);
        }
    }

    private void setAvailablePropButtons() {
        if (positionListView.getSelectionModel().getSelectedItem() != null)  {
            editPropButton.setDisable(false);
            deletePropButton.setDisable(false);
        } else {
            editPropButton.setDisable(true);
            deletePropButton.setDisable(true);
        }
    }

    private void addPosition() {
        Dialog<String> addDialog = new Dialog<>();
        addDialog.setTitle("Add Position");
        ButtonType addButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();
        Label emptyLabel = new Label("Cannot be empty.");
        emptyLabel.setStyle("-fx-text-fill: red");
        emptyLabel.setVisible(false);

        Label titleLabel = new Label("Title: ");
        TextField titleTextField = new TextField();

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleTextField, 1, 0);
        vBox.getChildren().addAll(gridPane, emptyLabel);

        addDialog.getDialogPane().lookupButton(addButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            if (titleTextField.getText().equals("")) {
                emptyLabel.setVisible(true);
                event.consume();
            }
        });

        addDialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                return titleTextField.getText();
            } else {
                return null;
            }
        });

        addDialog.getDialogPane().setContent(vBox);

        addDialog.showAndWait().ifPresent(response -> positionListView.getItems().add(response));
    }

    private void addCandidate() {
        int index = positionListView.getSelectionModel().getSelectedIndex();

        do {
            index++;
        } while (index < positionListView.getItems().size() && positionListView.getItems().get(index).charAt(0) == '\t');

        int newIndex = index;

        Dialog<Pair<String, String>> addDialog = new Dialog<>();
        addDialog.setTitle("Add Candidate");
        ButtonType addButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

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

        addDialog.getDialogPane().lookupButton(addButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            if (nameTextField.getText().equals("") || partyTextField.getText().equals("")) {
                emptyLabel.setVisible(true);
                event.consume();
            }
        });

        addDialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                return new Pair<>(nameTextField.getText(), partyTextField.getText());
            } else {
                return null;
            }
        });

        addDialog.getDialogPane().setContent(vBox);
        addDialog.showAndWait().ifPresent(response -> {
            positionListView.getItems().add(newIndex, "\t" + response.getKey());
            positionListView.getItems().add(newIndex + 1, "\t\tParty: " + response.getValue());
        });
    }

    private void addProposition() {
        Dialog<Pair<String, String>> addDialog = new Dialog<>();
        addDialog.setTitle("Add Proposition");
        ButtonType addButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();
        Label emptyLabel = new Label("Cannot be empty.");
        emptyLabel.setStyle("-fx-text-fill: red");
        emptyLabel.setId("emptyLabel");
        emptyLabel.setVisible(false);

        Label titleLabel = new Label("Title: ");
        TextField titleTextField = new TextField();
        Label descriptionLabel = new Label("Description: ");
        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setWrapText(true);

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleTextField, 1, 0);
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionTextArea, 1, 1);
        vBox.getChildren().addAll(gridPane, emptyLabel);

        addDialog.getDialogPane().lookupButton(addButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            if (titleTextField.getText().equals("") || descriptionTextArea.getText().equals("")) {
                emptyLabel.setVisible(true);
                event.consume();
            }
        });

        addDialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                return new Pair<>(titleTextField.getText(), descriptionTextArea.getText().replace("\n", ""));
            } else {
                return null;
            }
        });

        addDialog.getDialogPane().setContent(vBox);
        addDialog.showAndWait().ifPresent(response -> {
            propositionListView.getItems().add(response.getKey());
            propositionListView.getItems().add("\t" + response.getValue());
        });
    }

    private void deletePosition() {
        int index1 = positionListView.getSelectionModel().getSelectedIndex();
        int index2 = index1;

        do {
            index2++;
        } while (index2 < positionListView.getItems().size() && positionListView.getItems().get(index2).charAt(0) == '\t');

        do {
            index2--;
            positionListView.getItems().remove(index2);
        } while (index2 > index1);
    }

    private void deleteCandidate() {
        int index = positionListView.getSelectionModel().getSelectedIndex();
        if (positionListView.getSelectionModel().getSelectedItem().substring(0, 2).equals("\t\t")) {
            index--;
        }
        positionListView.getItems().remove(index);
        positionListView.getItems().remove(index);
    }

    private void deleteProposition() {
        int index = propositionListView.getSelectionModel().getSelectedIndex();
        if (propositionListView.getSelectionModel().getSelectedItem().substring(0, 1).equals("\t")) {
            index--;
        }
        propositionListView.getItems().remove(index);
        propositionListView.getItems().remove(index);
    }

    private void editPosition() {
        String currentValue = positionListView.getSelectionModel().getSelectedItem();
        int index = positionListView.getSelectionModel().getSelectedIndex();
        Dialog<String> editDialog = new Dialog<>();
        editDialog.setTitle("Edit Position");
        ButtonType editButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        editDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();
        Label emptyLabel = new Label("Cannot be empty.");
        emptyLabel.setStyle("-fx-text-fill: red");
        emptyLabel.setId("emptyLabel");
        emptyLabel.setVisible(false);

        Label titleLabel = new Label("Title: ");
        TextField titleTextField = new TextField(currentValue);

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleTextField, 1, 0);
        vBox.getChildren().addAll(gridPane, emptyLabel);

        editDialog.getDialogPane().lookupButton(editButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            if (titleTextField.getText().equals("")) {
                emptyLabel.setVisible(true);
                event.consume();
            }
        });

        editDialog.setResultConverter(buttonType -> {
            if (buttonType == editButtonType) {
                return titleTextField.getText();
            } else {
                return null;
            }
        });

        editDialog.getDialogPane().setContent(vBox);
        editDialog.showAndWait().ifPresent(response -> positionListView.getItems().set(index, response));
    }

    private void editCandidate() {
        String currentNameValue = positionListView.getSelectionModel().getSelectedItem();
        String currentPartyValue;
        int index;
        if (currentNameValue.substring(0, 2).equals("\t\t")) {
            currentPartyValue = currentNameValue;
            index = positionListView.getSelectionModel().getSelectedIndex() - 1;
            currentNameValue = positionListView.getItems().get(index);
        } else {
            index = positionListView.getSelectionModel().getSelectedIndex();
            currentPartyValue = positionListView.getItems().get(index + 1);
        }

        Dialog<Pair<String, String>> editDialog = new Dialog<>();
        editDialog.setTitle("Edit Candidate");
        ButtonType editButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        editDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();
        Label emptyLabel = new Label("Cannot be empty.");
        emptyLabel.setStyle("-fx-text-fill: red");
        emptyLabel.setId("emptyLabel");
        emptyLabel.setVisible(false);

        Label nameLabel = new Label("Name: ");
        TextField nameTextField = new TextField(currentNameValue.substring(1));
        Label partyLabel = new Label("Party: ");
        TextField partyTextField = new TextField(currentPartyValue.substring(9));

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(partyLabel, 0, 1);
        gridPane.add(partyTextField, 1, 1);
        vBox.getChildren().addAll(gridPane, emptyLabel);

        editDialog.getDialogPane().lookupButton(editButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            if (nameTextField.getText().equals("") || partyTextField.getText().equals("")) {
                emptyLabel.setVisible(true);
                event.consume();
            }
        });

        editDialog.setResultConverter(buttonType -> {
            if (buttonType == editButtonType) {
                return new Pair<>(nameTextField.getText(), partyTextField.getText());
            } else {
                return null;
            }
        });

        editDialog.getDialogPane().setContent(vBox);
        editDialog.showAndWait().ifPresent(response -> {
            positionListView.getItems().set(index, "\t" + response.getKey());
            positionListView.getItems().set(index + 1, "\t\tParty: " + response.getValue());
        });
    }

    private void editProposition() {
        String currentTitleValue = propositionListView.getSelectionModel().getSelectedItem();
        String currentDescriptionValue;
        int index;
        if (currentTitleValue.substring(0, 1).equals("\t")) {
            currentDescriptionValue = currentTitleValue;
            index = propositionListView.getSelectionModel().getSelectedIndex() - 1;
            currentTitleValue = propositionListView.getItems().get(index);
        } else {
            index = propositionListView.getSelectionModel().getSelectedIndex();
            currentDescriptionValue = propositionListView.getItems().get(index + 1);
        }
        Dialog<Pair<String, String>> editDialog = new Dialog<>();
        editDialog.setTitle("Edit Proposition");
        ButtonType editButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        editDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();
        Label emptyLabel = new Label("Cannot be empty.");
        emptyLabel.setStyle("-fx-text-fill: red");
        emptyLabel.setId("emptyLabel");
        emptyLabel.setVisible(false);

        Label titleLabel = new Label("Title: ");
        TextField titleTextField = new TextField(currentTitleValue);
        Label descriptionLabel = new Label("Description: ");
        TextArea descriptionTextArea = new TextArea(currentDescriptionValue.substring(1));
        descriptionTextArea.setWrapText(true);

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleTextField, 1, 0);
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionTextArea, 1, 1);
        vBox.getChildren().addAll(gridPane, emptyLabel);

        editDialog.getDialogPane().lookupButton(editButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            if (titleTextField.getText().equals("")) {
                emptyLabel.setVisible(true);
                event.consume();
            }
        });

        editDialog.setResultConverter(buttonType -> {
            if (buttonType == editButtonType) {
                return new Pair<>(titleTextField.getText(), descriptionTextArea.getText().replace("\n", ""));
            } else {
                return null;
            }
        });
        editDialog.getDialogPane().setContent(vBox);
        editDialog.showAndWait().ifPresent(response -> {
            propositionListView.getItems().set(index, response.getKey());
            propositionListView.getItems().set(index + 1, "\t" + response.getValue());
        });
    }

    private String electionToString() {

        String electionString = "";

        if (!positionListView.getItems().isEmpty()) {
            for (int i = 0; i < positionListView.getItems().size() - 1; i++) {
                electionString += positionListView.getItems().get(i) + "\n";
            }
            electionString += positionListView.getItems().get(positionListView.getItems().size() - 1);
        } else {
            electionString += "-1";
        }

        electionString +="::";

        if (!propositionListView.getItems().isEmpty()) {
            for (int j = 0; j < propositionListView.getItems().size() - 1; j++) {
                electionString += propositionListView.getItems().get(j) + "\n";
            }
            electionString += propositionListView.getItems().get(propositionListView.getItems().size() - 1);
        } else {
            electionString += "-1";
        }

        return electionString;
    }

    private boolean saveElectionToFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Election File (*.elec)", "*.elec"));
            fileChooser.setTitle("Save Election File");
            File file = fileChooser.showSaveDialog(saveButton.getScene().getWindow());

            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(out);

            oos.writeObject(electionToString());

            out.flush();
            out.close();
            oos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadElectionFromFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Election File (*.elec)", "*.elec"));
            fileChooser.setTitle("Open Election File");
            File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());

            if (!file.getPath().substring(file.getPath().lastIndexOf(".")).equals(".elec")) {
                Alert invalidFileAlert = new Alert(Alert.AlertType.ERROR, "Must be \".elec\" file.", ButtonType.OK);
                invalidFileAlert.showAndWait();
                throw new IOException();
            }

            FileInputStream in = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(in);

            String electionString = (String) ois.readObject();
            positionListView.getItems().clear();
            propositionListView.getItems().clear();
            putElectionStringInController(electionString);

            in.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void putElectionStringInController(String electionString) {
        String[] listViews = electionString.split("::");

        if (!listViews[0].equals("-1")) {

            String[] posCanLines = listViews[0].split("\n");

            for (int i = 0; i < posCanLines.length; i++) {

                positionListView.getItems().add(posCanLines[i]);

                for (int j = i + 1; j <= posCanLines.length; j++) {

                    if (j >= posCanLines.length || !posCanLines[j].substring(0, 1).equals("\t")) {
                        i = j - 1;
                        break;
                    }

                    positionListView.getItems().addAll(posCanLines[j], posCanLines[j + 1]);
                    j++;
                }
            }
        }

        if (!listViews[1].equals("-1")) {

            String[] propLines = listViews[1].split("\n");

            for (int i = 0; i < propLines.length; i++) {
                propositionListView.getItems().addAll(propLines[i], propLines[i + 1]);
                i++;
            }
        }
    }

    private boolean electionIsEmpty() {
        return positionListView.getItems().isEmpty() &&
                propositionListView.getItems().isEmpty();
    }
}
