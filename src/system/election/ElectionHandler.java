package system.election;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.election.voting.Ballot;

import java.util.*;

/**
 * A class which handles the creation of an <tt>Election</tt>, which stores
 * information about the <tt>Position</tt>s and public opinion surveys that a
 * voter can cast their opinion on.
 */
public class ElectionHandler {

    private Election election;

    public ElectionHandler() {
        this.election = new Election();
    }

    /**
     * Creates a new <tt>Election</tt> from a <tt>String</tt> containing all
     * of the Election's information. This is called from
     * <tt>ElectionSetupController</tt> in order to convert information
     * entered by the user on election setup into an Election.
     *
     * @param electionString
     */
    public void createElectionFromString(String electionString) {
        election = new Election();
        String[] listViews = electionString.split("::");

        if (!listViews[0].equals("-1")) {

            String[] posCanLines = listViews[0].split("\n");
            String position;

            for (int i = 0; i < posCanLines.length; i++) {

                position = posCanLines[i];
                addPosition(position);

                for (int j = i + 1; j <= posCanLines.length; j++) {

                    if (j >= posCanLines.length ||
                            !posCanLines[j].substring(0, 1).equals("\t")) {
                        i = j - 1;
                        break;
                    }

                    addCandidateToPosition(posCanLines[j].substring(1),
                            posCanLines[j + 1].substring(9), position);
                    j++;
                }
            }
        }

        if (!listViews[1].equals("-1")) {
            String[] propLines = listViews[1].split("\n");

            for (int i = 0; i < propLines.length; i++) {
                addProposition(propLines[i], propLines[i + 1].substring(1));
                i++;
            }
        }

//        printElection();

    }

    /**
     * Adds a <tt>Position</tt> to the <tt>Election</tt> with the title
     * provided by the specified <tt>String</tt>.
     *
     * @param name  the name of the position
     */
    public void addPosition(String name) {
        this.election.addPosition(new Position(name));
    }

    /**
     * Adds a <tt>Candidate</tt> to the <tt>Election</tt> with the provided
     * name and party. The candidate put in the running for the
     * <tt>Position</tt> specified by the provided title.
     *
     * @param candidateName the name of the candidate
     * @param candidateParty    the party of the candidate
     * @param positionTitle the title of the position that the candidate is
     *                      running for
     * @return  true if the candidate was successfully added to the election
     */
    public boolean addCandidateToPosition(String candidateName,
                                          String candidateParty,
                                          String positionTitle) {
        return this.election.addCandidateToPosition(
                new Candidate(candidateName, candidateParty), positionTitle);
    }
    final char[] adminPass = {'1','2','3','4'};
    public void assignVotesForBallots(char[] password,List<Ballot> ballots){
//        System.out.println(ballots.get(0).getSelections());

            for(Ballot bal : ballots){
                for(Map.Entry<String,Candidate> entry:bal.getSelections()
                        .entrySet
                        ()) {
                    election.addVote(entry.getKey(), entry.getValue());
                }
                for(Map.Entry<String,Boolean> entry:bal.getPropositions().entrySet())
                    election.addPropositionVote(entry.getKey(), entry.getValue());
            }
    }

    /**
     * Adds a public opinion survey (<tt>Proposition</tt>) with the specified
     * title and description to the <tt>Election</tt>.
     *
     * @param title the title of the proposition
     * @param description   a description of the proposition
     */
    public void addProposition(String title, String description) {
        this.election.addProposition(new Proposition(title, description));
    }

    /**
     * Returns an <tt>ObservableList</tt> of the <tt>Position</tt>s currently
     * in an Election.
     *
     * @return  the list of existing positions
     */
    public ObservableList<Position> getPositionsUnmodifiable() {
        ObservableList<Position> rtnval = FXCollections
                .observableArrayList();
        for (Position position : getPositions().values()) {
            rtnval.add(new Position(position.getTitle(), position.getActualCandidates()));
        }
        return rtnval;
    }

    HashMap<String, Position> getPositions() {
        return this.election.getPositions();
    }

    /**
     * Returns a <tt>ObservableList</tt> of the <tt>Proposition</tt>s currently
     * in an Election.
     *
     * @return  the list of existing propositions
     */
    public ObservableList<Proposition> getPropositionsUnmodifiable() {
        ObservableList<Proposition> rtnval = FXCollections
                .observableArrayList();
        for (Proposition proposition : getPropositions().values()) {
            rtnval.add(new Proposition(proposition.getName(), proposition
                    .getDescription()));
        }
        return rtnval;
    }

    HashMap<String,Proposition> getPropositions() {
        return this.election.getPropositions();
    }

    /**
     * Prints a text representation of the <tt>Election</tt> to the console.
     */
    public void printElection() {
        for (Map.Entry<String,Position> entry : getPositions().entrySet()) {
            Position position = entry.getValue();
            System.out.println("Position: " + position.getTitle());
            for (Candidate candidate : position.getCandidates()) {
                System.out.println("\tCandidate: " + candidate.getName() + " " + candidate.getVotes());
                System.out.println("\t\tParty: " + candidate.getParty());
            }
        }

        for (Map.Entry<String,Proposition> entry : getPropositions().entrySet()) {
            Proposition proposition = entry.getValue();
            System.out.println("Proposition: " + proposition.getName() + " " + proposition.getSupport());
            System.out.println("\tDescription: " + proposition.getDescription());
        }
    }
}
