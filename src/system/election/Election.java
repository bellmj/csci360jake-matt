package system.election;

import java.util.ArrayList;
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
        for (Position p : this.positions.values()) {
            rtn.add(new Position(p.getTitle(), p.getCandidates()));
        }
        return rtn;
    }

    void addProposition(Proposition proposition) {
        this.propositions.put(proposition.getName(), proposition);
    }

    List<Proposition> getPropositions() {
        List<Proposition> rtn = new ArrayList<>();
        for (Proposition p : this.propositions.values()) {
            rtn.add(new Proposition(p.getName(), p.getDescription()));
        }
        return rtn;
    }

}
