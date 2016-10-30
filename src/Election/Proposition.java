package Election;

public class Proposition {

    private String name;
    private String description;
    private long votesFor;
    private long votesAgainst;

    void newSupport() {
        this.votesFor++;
    }

    void newOpposition() {
        this.votesAgainst++;
    }

    String getName() {
        return this.name;
    }

    String getDescription() {
        return this.description;
    }

    long getSupport() {

    }

}
