package system.election.voting;

import system.election.Candidate;
import system.election.Position;
import system.election.Proposition;

import java.util.HashMap;

/**
 * A class which stores a voter's selections. A <tt>Ballot</tt> keeps track of
 * the voter's <tt>Candidate</tt> selection for each <tt>Position</tt>, as well
 * as whether or not they support each proposition. An entry with a value of
 * null is interpreted to mean that the voter has abstained for that selection.
 *
 * @see BallotHandler
 */
public class Ballot {

    private HashMap<String, Candidate> selections;
    private HashMap<String, Boolean> propositions;
    private String voterHashID;

    /**
     * Constructs a ballot for the specified hashed voter ID.
     *
     * @param voterHashID   the hashed voter ID
     */
    Ballot(String voterHashID) {
        this.selections = new HashMap<>();
        this.propositions = new HashMap<>();
        this.voterHashID = voterHashID;
    }

    /**
     * Re-constructs a ballot based on its attributes.
     *
     * @param selections    a HashMap detailing the ballot's candidate
     *                      selections
     * @param propositions  a HashMap detailing the ballot's proposition
     *                      selections
     * @param voterHashID   the ballot's hashed voter ID
     */
    public Ballot(HashMap<String, Candidate> selections, HashMap<String,
            Boolean> propositions, String voterHashID) {
        this.selections = selections;
        this.propositions = propositions;
        this.voterHashID = voterHashID;
    }

    /**
     * Returns the hashed voter registration number associated with the Ballot.
     *
     * @return  String - the voter's hashed registration number
     */
    public String getVoterHashID() {
        return this.voterHashID;
    }

    /**
     * Returns a HashMap containing the voter's candidate selections for each
     * available position.
     *
     * @return  HashMap - the voter's candidate selections
     */
    public HashMap<String, Candidate> getSelections() {
        return this.selections;
    }

    /**
     * Returns a HashMap containing the selections for public opinion surveys.
     *
     * @return  HashMap - the voter's proposition responses
     */
    public HashMap<String, Boolean> getPropositions() {
        return this.propositions;
    }

    /**
     * Changes the voter's selection for a Position to the specified Candidate.
     *
     * @param position  Position - the selection to change
     * @param candidate Candidate - the value to change the selection to
     */
    void changeCandidate(Position position, Candidate candidate) {
        this.selections.put(position.getTitle(), candidate);
    }

    /**
     * Changes the voter's support for the specified Proposition.
     *
     * @param proposition   Proposition - the proposition to change support for
     * @param supports  boolean - true if supports, false if opposes, null if abstains
     */
    void changeProposition(Proposition proposition, Boolean supports) {
        this.propositions.put(proposition.getName(), supports);
    }
}
