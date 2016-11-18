package System.Election;

import java.util.HashMap;
import java.util.List;

public class ElectionHandler {

    private Election election;

    public void createNewElection() {
        this.election = new Election();
    }

    public void addPosition(Position position) {
        this.election.addPosition(position);
    }

    public void addPosition(String name) {
        this.election.addPosition(new Position(name));
    }

    public boolean addCandidateToPosition(Candidate candidate, Position position) {
        return this.election.addCandidateToPosition(candidate, position);
    }

    public boolean addCandidateToPosition(Candidate candidate, String positionTitle) {
        return this.election.addCandidateToPosition(candidate, new Position(positionTitle));
    }

    public boolean addCandidateToPosition(String candidateName, String candidateParty, String positionTitle) {
        return this.election.addCandidateToPosition(new Candidate(candidateName, candidateParty), new Position(positionTitle));
    }

    public boolean addCandidateToPosition(String candidateName, String candidateParty, Position position) {
        return this.election.addCandidateToPosition(new Candidate(candidateName, candidateParty), position);
    }

    public Candidate addWriteInCandidateToPosition(String candidateName, String candidateParty, Position position) {
        Candidate candidate = new Candidate(candidateName, candidateParty);
        addCandidateToPosition(candidate, position);
        return candidate;
    }
    public Candidate addWriteInCandidateToPosition(String candidateName, String candidateParty, String positionTitle) {
        Candidate candidate = new Candidate(candidateName, candidateParty);
        addCandidateToPosition(candidate, positionTitle);
        return candidate;
    }

    public List<Position> getPositions() {
        return this.election.getPositions();
    }

    public void addProposition(Proposition proposition) {
        this.election.addProposition(proposition);
    }

    public void addProposition(String name, String description) {
        this.election.addProposition(new Proposition(name, description));
    }

    public List<Proposition> getPropositions() {
        return this.election.getPropositions();
    }

}
