package System.Election;

/**
 * A class which contains information on a Candidate, including their name,
 * party affiliation, and how many votes they have received. Their number of
 * votes will remain zero until Ballots are counted.
 *
 * @see Position
 * @see ElectionHandler
 */
public class Candidate {

    private String name;
    private String party;
    private long votes;

    /**
     * Constructs a new Candidate with the specified name and party affiliation.
     *
     * @param name  the name of the candidate
     * @param party the name of the party that the candidate is affiliated with
     */
    Candidate(String name, String party) {
        this.name = name;
        this.party = party;
        this.votes = 0;
    }

    /**
     * Returns the name of the Candidate.
     *
     * @return  the name of the Candidate
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the party affiliation of the Candidate.
     *
     * @return  the party of the Candidate
     */
    public String getParty() {
        return this.party;
    }

    /**
     * Returns the number of votes that the candidate has received.
     *
     * @return  long number of votes the candidate has received
     */
    long getVotes() {
        return votes;
    }

    /**
     * Increments the number of votes the Candidate has received.
     */
    void addVote() {
        this.votes++;
    }

}
