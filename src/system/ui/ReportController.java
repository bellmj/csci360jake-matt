package system.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import system.election.Candidate;
import system.election.ElectionHandler;
import system.election.Position;
import system.election.Proposition;

import java.net.URL;
import java.util.List;
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

    void setElectionHandler(ElectionHandler electionHandler) {
        this.electionHandler = electionHandler;
        populateListView();
    }

    private void populateListView() {
        List<Position> positions = electionHandler.getPositionsUnmodifiable();
        List<Proposition> propositions = electionHandler
                .getPropositionsUnmodifiable();

        for (Position position : positions) {
            listView.getItems().add(new Label("Position: " + position.getTitle()));

            for (Candidate candidate : position.getCandidates()) {
                listView.getItems().add(new Label("\tCandidate: " + candidate
                        .getName() + "\n\t\tParty: " + candidate.getParty() +
                        "\n\t\tVotes: " + candidate.getVotes()));
            }
        }

        for (Proposition proposition : propositions) {
            listView.getItems().add(new Label("Proposition: " + proposition
                    .getName() + "\n\t% Support: " + proposition.getSupport()));
        }
    }

}
