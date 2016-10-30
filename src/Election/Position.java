package Election;

import java.util.ArrayList;

public class Position {

    private String name;
    private ArrayList<Candidate> candidates;
    private Candidate winner;

    void declareWinner(Candidate winner) {
        this.winner = winner;
    }

    void addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
    }
    
}
