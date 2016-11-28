package system.election;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which contians the information for a position up for election, as
 * well as all of the candidates for that position.
 *
 * @see Candidate
 * @see ElectionHandler
 */
public class Position {

    private String title;
    private List<Candidate> candidates;

    /**
     * Creates a new Position with the specified title.
     *
     * @param title the name of the position up for election
     */
    public Position(String title) {
        this.title = title;
        this.candidates = new ArrayList<Candidate>();
    }

    public Position(String title, List<Candidate> candidates) {
        this.title = title;
        this.candidates = candidates;
    }

    /**
     * Returns the title of the available position.
     *
     * @return  the title of the position
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns a copy of the list of candidates up for this position
     *
     * @return  a list of Candidates
     */
    public List<Candidate> getCandidates() {
        List<Candidate> rtn = new ArrayList();
        for (Candidate c : this.candidates) {
            rtn.add(new Candidate(c.getVotes(), c.getName(), c.getParty()));
        }
        return rtn;
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
