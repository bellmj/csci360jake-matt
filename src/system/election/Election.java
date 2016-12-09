package system.election;

import java.util.*;

/**
 * A class which stores information about an election, including the
 * Positions that are up for election, the Candidates running for each
 * position, and any public opinion surveys (Propositions) that voters can
 * weigh their opinion on.
 *
 * @see ElectionHandler
 * @see Position
 * @see Candidate
 * @see Proposition
 */
class Election {

    private HashMap<String, Position> positions;
    private HashMap<String, Proposition> propositions;

    /**
     * Constructs an empty Election.
     */
    Election() {
        this.positions = new HashMap<>();
        this.propositions = new HashMap<>();
    }

    /**
     * Adds a vote to the specified Candidate.
     *
     * @param position  the Position that the candidate is running for
     * @param candidate the Candidate to case a vote for
     * @throws IllegalArgumentException if the position is not contained in
     * this election
     */
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
           candidate.setVotes(1L);
           actualCandidates.put(candidate.getName(),candidate);
        }else {
            actualCandidates.get(candidate.getName()).addVote();
        }
        positions.put(position1.getTitle(),position1);
    }

    /**
     * Adds support or opposition for a specified Proposition.
     *
     * @param proposition   the Proposition in question
     * @param vote  Boolean describing the voter's opinion
     * @throws IllegalArgumentException if the proposition is not contained
     * in this election
     */
    void addPropositionVote(String proposition,Boolean vote) throws IllegalArgumentException{
        if(!propositions.containsKey(proposition)) {
            throw new IllegalArgumentException("proposition not contained in election");
        }
        propositions.get(proposition).addSupport(vote);
    }

    /**
     * Adds a Position to the Election.
     *
     * @param position
     */
    void addPosition(Position position) {
        this.positions.put(position.getTitle(), position);
    }

    /**
     * Adds a Candidate to the specified Position.
     *
     * @param candidate the new Candidate
     * @param positionTitle the title of the position
     * @return  true if the candidate was successfully added
     */
    boolean addCandidateToPosition(Candidate candidate, String positionTitle) {
        boolean rtnval = this.positions.containsKey(positionTitle);
        if (rtnval) {
            this.positions.get(positionTitle).addCandidate(candidate);
        }
        return rtnval;
    }

    /**
     * Returns the HashMap containing the Positions in this Election.
     *
     * @return  the HashMap containing the Positions in this Election
     */
    HashMap<String,Position> getPositions() {
        return positions;
    }

    /**
     * Adds a Proposition to the Election.
     *
     * @param proposition   the new Proposition
     */
    void addProposition(Proposition proposition) {
        this.propositions.put(proposition.getName(), proposition);
    }

    /**
     * Returns the HashMap containing the Propositions in this Election.
     *
     * @return   the HashMap containing the Propositions in this
     *                      Election
     */
    HashMap<String,Proposition> getPropositions() {
        return propositions;
    }

}
