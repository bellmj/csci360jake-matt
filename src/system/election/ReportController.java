package system.election;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private ListView<Label> listView;

    private ElectionHandler electionHandler;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert listView != null : "fx:id=\"imageView\" was not injected: " +
                "check your FXML file 'Report.fxml'.";

        listView.setFocusTraversable(false);
    }

    public void setElectionHandler(ElectionHandler electionHandler) {
        this.electionHandler = electionHandler;
        populateListView();
    }

    private void populateListView() {
        HashMap<String, Position> positions = electionHandler.getPositions();
        HashMap<String, Proposition> propositions = electionHandler
                .getPropositions();

        for (Position position : positions.values()) {
            listView.getItems().add(new Label("Position: " + position.getTitle()));

            for (Candidate candidate : position.getCandidates()) {
                listView.getItems().add(new Label("\tCandidate: " + candidate
                        .getName() + "\n\t\tParty: " + candidate.getParty() +
                        "\n\t\tVotes: " + candidate.getVotes()));
            }
        }

        for (Proposition proposition : propositions.values()) {
            listView.getItems().add(new Label("Proposition: " + proposition
                    .getName() + "\n\t% Support: " + proposition.getSupport()));
        }
    }

}