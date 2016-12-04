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
    public Proposition(String name, String description,long votesFor,long votesAgainst) {
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
    }

    /**
     * Returns the percentage of voters which supported the Proposition.
     *
     * @return  a long representing the support received by the Proposition
     */
    public BigDecimal getSupport() {
        if(votesAgainst==0l && votesFor > 0l){
            return new BigDecimal(100l);
        }else if(votesAgainst == 0l && votesFor== 0l){
            return new BigDecimal(50l);
        }
        System.out.println(votesFor + " " + votesAgainst);
        BigDecimal decimalVoteFor = new BigDecimal(votesFor);
        BigDecimal decimalVotesAgainst = new BigDecimal(votesAgainst);
        return new BigDecimal(100l).multiply(decimalVoteFor.divide(new BigDecimal(votesFor+votesAgainst),9, RoundingMode.HALF_UP));
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
            votesFor+=1l;
        else
            votesAgainst+=1l;
    }

    /**
     * Returns the Proposition's description.
     *
     * @return  the Proposition's description.
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    /**
     * @return a string with the name of this proposition a description all delimited by semicolons
     */
    public String toString() {
        return name + ";" + description +";";
    }
}
