package System.Election;

import java.util.ArrayList;

/**
 * A class which contians the information for a position up for election, as
 * well as all of the candidates for that position. A Candidate can be declared
 * the winner of a Position once vote counting has completed.
 *
 * @see Candidate
 * @see ElectionHandler
 */
public class Position {

    private String title;
    private ArrayList<Candidate> candidates;
    private Candidate winner;

    /**
     * Creates a new Position with the specified title.
     *
     * @param title the name of the position up for election
     */
    Position(String title) {
        this.title = title;
        this.candidates = new ArrayList<Candidate>();
    }

    public Position(String title, ArrayList<Candidate> candidates, Candidate winner) {
        this.title = title;
        this.candidates = candidates;
        this.winner = winner;
    }

    /**
     * Returns false if no winner has been declared yet.
     *
     * @return  true if there is a winner, false if null
     */
    boolean hasWinner() {
        return winner != null;
    }

    /**
     * Declares a winner for this position.
     *
     * @param winner    the Candidate who won
     */
    void declareWinner(Candidate winner) {
        this.winner = winner;
    }

    /**
     * Adds a candidate for this position.
     *
     * @param candidate a Candidate up for this Position
     */
    void addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
    }

    @Override
    public String toString() {
        String candidateString = "";
        for(Candidate c:this.candidates){
            candidateString += ";" + c;
        }
        return  title + candidateString;
    }
}
