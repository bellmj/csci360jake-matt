package system.election.voting;

import system.db.FileBallotHandler;
import system.election.Candidate;
import system.election.Position;
import system.election.Proposition;
import system.db.DataHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A class which handles operations done on and with Ballots in the voting
 * process. One instance of this class should be created when a machine
 * running this software is set up as a polling machine. As a voter is voting,
 * this class will record their selections in a <tt>Ballot</tt>. When the voter
 * has finished, this class will pass the Ballot on to the <tt>system.db.DBHandler</tt>
 * so it can be stored in the database.
 *
 * @see Ballot
 * @see DBHandler
 */
public class BallotHandler {
    private DataHandler<Ballot> dataHandler;
    private Ballot ballot;
    private final char[] adminPassword = {'1', '2', '3', '4'};

    /**
     * Constructs a BallotHandler with the specified DataHandler.
     *
     * @param dataHandler   a DataHandler which handles Ballots
     */
    public BallotHandler(DataHandler<Ballot> dataHandler) {
        this.dataHandler = dataHandler;
    }

    /**
     * Creates a Ballot for the specified voterHashID.
     *
     * @param voterHashID   String - the voter's hashed registration number
     */
    public void createBallot(String voterHashID) {
        this.ballot = new Ballot(voterHashID);
    }

    /**
     * Stores a voter's Candidate selection to their Ballot.
     *
     * @param position  the Position for which the Candidate was selected
     * @param candidate the Candidate that was selected
     */
    public void selectCandidate(Position position, Candidate candidate) {
        this.ballot.changeCandidate(position, candidate);
    }

    /**
     * Stores a voter's decision on a public opinion survey.
     *
     * @param proposition   the Proposition up for survey
     * @param supports  boolean - true if supports, false if opposes, null if abstains
     */
    public void addProposition(Proposition proposition, Boolean supports) {
        this.ballot.changeProposition(proposition, supports);
    }

    /**
     * Returns the candidate selections for the active Ballot.
     *
     * @return  a HashMap detailing the ballot's candidate selections
     */
    public HashMap<String, Candidate> getCandidateSelections() {
        return this.ballot.getSelections();
    }

    /**
     * Returns the proposition selections for the active Ballot.
     *
     * @return  a HashMap detailing the ballot's proposition selections
     */
    public HashMap<String, Boolean> getPropositionSelections() {
        return this.ballot.getPropositions();
    }

    /**
     * Saves the active Ballot, then clears it.
     */
    public void saveBallot() {
        if (this.ballot != null) {
            dataHandler.add(this.ballot);
            cancelBallot();
        }
    }

    /**
     * The voter cancels all selections, and does not cast their Ballot, so the Ballot is
     */
    public void cancelBallot() {
        this.ballot = null;
    }

    /**
     * Retrieves all stored Ballots.
     *
     * @param adminPassword an admin password required to retrieve Ballots
     * @return  a List of Ballots
     */
    public List<Ballot> getAllBallots(char[] adminPassword) {
        if (Arrays.equals(adminPassword, this.adminPassword)) {
            return this.dataHandler.getAll();
        } else {
            return null;
        }
    }

    /**
     * Erases all stored Ballots.
     */
    public void eraseBallots() {
        ((FileBallotHandler) dataHandler).eraseBallots();
    }

}