package system.election;

import java.util.*;

public class Election {

    private HashMap<String, Position> positions;
    private HashMap<String, Proposition> propositions;

    Election() {
        this.positions = new HashMap<>();
        this.propositions = new HashMap<>();
    }
    void addVote(String position,Candidate candidate) throws IllegalArgumentException{
        System.out.println(position + " " +candidate.getName());
        if(!position.contains(position))
            throw new IllegalArgumentException("position not contained in election");
        Position position1 = positions.get(position);
        if(position1==null){
            position1 = new Position(position);
        }
        Map<String,Candidate> actualCandidates = position1.getActualCandidates();
        if(!actualCandidates.containsKey(candidate.getName())){
            position1.addCandidate(candidate);
           candidate.setVotes(1l);
           actualCandidates.put(candidate.getName(),candidate);
        }else {
            actualCandidates.get(candidate.getName()).addVote();
        }
        positions.put(position1.getTitle(),position1);
    }
    void addPropositionVote(String proposition,Boolean vote) throws IllegalArgumentException{
        if(!propositions.containsKey(proposition)) {
            throw new IllegalArgumentException("proposition not contained in election");
        }
        propositions.get(proposition).addVote(vote);
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

    boolean addCandidateToPosition(Candidate candidate, String positionTitle) {
        boolean rtnval = this.positions.containsKey(positionTitle);
        if (rtnval) {
            this.positions.get(positionTitle).addCandidate(candidate);
        }
        return rtnval;
    }

    HashMap<String,Position> getPositions() {
        return positions;
    }

    void addProposition(Proposition proposition) {
        this.propositions.put(proposition.getName(), proposition);
    }

    HashMap<String,Proposition> getPropositions() {
        return propositions;
    }

}
