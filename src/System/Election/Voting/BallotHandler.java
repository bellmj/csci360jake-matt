package System.Election.Voting;

import System.Election.Candidate;
import System.Election.Position;
import System.Election.Proposition;
import System.DB.DBHandler;
import System.DB.DataHandler;

import java.util.HashMap;

/**
 * A class which handles operations done on and with Ballots in the voting
 * process. One instance of this class should be created when a machine
 * running this software is set up as a polling machine. As a voter is voting,
 * this class will record their selections in a <tt>Ballot</tt>. When the voter
 * has finished, this class will pass the Ballot on to the <tt>System.DB.DBHandler</tt>
 * so it can be stored in the database.
 *
 * @see Ballot
 * @see DBHandler
 */
public class BallotHandler {
    private DataHandler<Ballot> dataHandler;
    private Ballot ballot;

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
     * @pre position <> null
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
     * @pre proposition <> null
     *
     * @param proposition   the Proposition up for survey
     * @param supports  boolean - true if supports, false if opposes, null if abstains
     */
    public void addProposition(Proposition proposition, Boolean supports) {
        this.ballot.changeProposition(proposition, supports);
    }

    public HashMap<Position, Candidate> getCandidateSelections() {
        return this.ballot.getSelections();
    }

    public HashMap<Proposition, Boolean> getPropositionSelections() {
        return this.ballot.getPropositions();
    }

    //TODO Decide whether the BallotHandler should send the ballot to DBHandler directly or not
    /**
     *  @pre self.ballot <> null
     *  @post self.ballot = null
     */
    public void saveBallot() {
        if (this.ballot != null) {
            dataHandler.add(this.ballot);
            this.ballot = null;
        }
    }

    public void clearSelections() {
        this.ballot = new Ballot(this.ballot.getVoterHashID());
    }

    /**
     * The voter cancels all selections, and does not cast their Ballot, so the Ballot is
     *
     * @param ballot
     */
    public void cancelBallot(Ballot ballot) {
        this.ballot = null;
    }

}