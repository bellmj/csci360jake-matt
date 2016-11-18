package System.Election;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Election {

    private HashMap<String, Position> positions;
    private HashMap<String, Proposition> propositions;

    Election() {
        this.positions = new HashMap<>();
        this.propositions = new HashMap<>();
    }

    void addPosition(Position position) {
        this.positions.put(position.getTitle(), position);
    }

    boolean addCandidateToPosition(Candidate candidate, Position position) {
        boolean rtnval = this.positions.containsKey(position.getTitle());
        if (rtnval) {
            this.positions.get(position.getTitle()).addCandidate(candidate);
        }
        return rtnval;
    }

    List<Position> getPositions() {
        List<Position> rtn = new ArrayList<>();
        rtn.addAll(this.positions.values());
        return rtn;
    }

    void addProposition(Proposition proposition) {
        this.propositions.put(proposition.getName(), proposition);
    }

    List<Proposition> getPropositions() {
        List<Proposition> rtn = new ArrayList<>();
        rtn.addAll(this.propositions.values());
        return rtn;
    }

}
