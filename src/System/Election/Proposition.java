package System.Election;

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
    long getSupport() {
        return votesFor / votesAgainst;
    }

    /**
     * Returns the name of the Proposition.
     *
     * @return  the name of the Proposition
     */
    String getName() {
        return this.name;
    }

    /**
     * Returns the Proposition's description.
     *
     * @return  the Proposition's description.
     */
    String getDescription() {
        return this.description;
    }
}
