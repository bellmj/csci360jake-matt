package system.election;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class which contians the information for a position up for election, as
 * well as all of the candidates for that position.
 *
 * @see Candidate
 * @see ElectionHandler
 */
public class Position {

    private String title;
    private Map<String,Candidate> candidates;

    /**
     * Constructs a new Position with the specified title.
     *
     * @param title the title of the position up for election
     */
    public Position(String title) {
        this.title = title;
        this.candidates = new HashMap<>();
    }

    /**
     * Constructs a Position with the specified title and Candidates.
     *
     * @param title the title of the position up for election
     * @param candidates    a HashMap containing the candidates up for this
     *                      position
     */
    public Position(String title, HashMap<String,Candidate> candidates) {
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
        List<Candidate> rtn = new ArrayList<>();
        for (Map.Entry<String,Candidate> c : this.candidates.entrySet()) {
            rtn.add(new Candidate(c.getValue().getVotes(), c.getValue().getName(), c.getValue().getParty()));
        }
        return rtn;
    }

    /**
     * Returns the actual HashMap of Candidates for this Position.
     *
     * @return  the position's HashMap of Candidates
     */
    HashMap<String, Candidate> getActualCandidates(){
        return new HashMap<>(candidates);
    }

    /**
     * Adds a candidate for this position.
     *
     * @param candidate a Candidate up for this Position
     */
    void addCandidate(Candidate candidate) {
        this.candidates.put(candidate.getName(),candidate);
    }

    /**
     * Returns a String representation of the Position and its Candidates.
     *
     * @return  a String of the position
     */
    @Override
    public String toString() {
        String candidateString = "";
        for(Map.Entry<String,Candidate> c:this.candidates.entrySet()){
            candidateString += ";" + c.getValue();
        }
        return  title + candidateString;
    }
}
