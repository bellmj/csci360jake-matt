package system.election;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A class which holds information for a public opinion survey, for which a
 * voter can express their support or opposition. For the purposes of this
 * class, both support and opposition fit into a category called "support",
 * which can be retrieved from this class as a percentage of support for the
 * proposition.
 */
public class Proposition {

    private String name;
    private String description;
    private long votesFor;
    private long votesAgainst;

    /**
     * Constructs a new Proposition with the specified name and description.
     *
     * @param name  the name of the Proposition
     * @param description   a description of what the Proposition entails
     */
    Proposition(String name, String description) {
        this.name = name;
        this.description = description;
        this.votesFor = 0;
        this.votesAgainst = 0;
    }

    /**
     * Constructs a Proposition with the specified name, description,
     * support, and opposition.
     *
     * @param name  the name of the proposition
     * @param description   a description of the proposition
     * @param votesFor  long number of votes supporting the proposition
     * @param votesAgainst  long number of votes opposing the proposition
     */
    public Proposition(String name, String description, long votesFor, long
            votesAgainst) {
        this.name = name;
        this.description = description;
        this.votesFor = votesFor;
        this.votesAgainst = votesAgainst;
    }

    /**
     * Adds a vote for or against the Proposition. If support is true, votesFor
     * is incremented. If false, votesAgainst is incremented. A null value is
     * treated as if the voter abstained.
     *
     * @param support   a boolean expressing whether or not the voter supports
     *                  this proposition
     */
    void addSupport(Boolean support) {
        if (support != null) {
            if (support) {
                this.votesFor++;
            } else {
                this.votesAgainst++;
            }
        }
        System.out.println("FOR: " + this.votesFor);
        System.out.println("AGAINST: " + this.votesAgainst);
    }

    /**
     * Returns the percentage of voters which supported the Proposition.
     *
     * @return  a long representing the support received by the Proposition
     */
    public BigDecimal getSupport() {
        if(votesAgainst==0L && votesFor > 0L){
            return new BigDecimal(100L);
        }else if(votesAgainst == 0L && votesFor== 0L){
            return new BigDecimal(50L);
        }
        BigDecimal decimalVoteFor = new BigDecimal(votesFor);
        BigDecimal decimalVotesAgainst = new BigDecimal(votesAgainst);
        return new BigDecimal(100L).multiply(decimalVoteFor.divide(new
                BigDecimal(votesFor+votesAgainst),9, RoundingMode.HALF_UP));
}

    /**
     * Returns the name of the Proposition.
     *
     * @return  the name of the Proposition
     */
    public String getName() {
        return this.name;
    }
    void addVote(Boolean bool){
        if(bool)
            votesFor+=1L;
        else
            votesAgainst+=1L;
    }

    /**
     * Returns the Proposition's description.
     *
     * @return  the Proposition's description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string with the name and description of this proposition
     * delimited by semicolons.
     *
     * @return a string representation of this proposition
     */
    @Override
    public String toString() {
        return name + ";" + description +";";
    }
}
