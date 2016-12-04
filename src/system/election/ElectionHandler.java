package system.election;

import java.util.List;

/**
 * A class which handles the creation of an <tt>Election</tt>, which stores
 * information about the <tt>Position</tt>s and public opinion surveys that a
 * voter can cast their opinion on.
 */
public class ElectionHandler {

    private Election election;

    /**
     * Creates an empty <tt>Election</tt>, assigning it to the class
     * variable election. This method should be called before other methods
     * which affect an existing Election.
     */
    public void createNewElection() {
        this.election = new Election();
    }

    /**
     * Creates a new <tt>Election</tt> from a <tt>String</tt> containing all
     * of the Election's information. This is called from
     * <tt>ElectionSetupController</tt> in order to convert information
     * entered by the user on election setup into an Election.
     *
     * @param electionString
     */
    public void createElectionFromString(String electionString) {
        createNewElection();

        String[] listViews = electionString.split("::");

        if (!listViews[0].equals("-1")) {

            String[] posCanLines = listViews[0].split("\n");
            String position;

            for (int i = 0; i < posCanLines.length; i++) {

                position = posCanLines[i];
                addPosition(position);

                for (int j = i + 1; j <= posCanLines.length; j++) {

                    if (j >= posCanLines.length ||
                            !posCanLines[j].substring(0, 1).equals("\t")) {
                        i = j - 1;
                        break;
                    }

                    addCandidateToPosition(posCanLines[j].substring(1),
                            posCanLines[j + 1].substring(9), position);
                    j++;
                }
            }
        }

        if (!listViews[1].equals("-1")) {
            String[] propLines = listViews[1].split("\n");

            for (int i = 0; i < propLines.length; i++) {
                addProposition(propLines[i], propLines[i + 1].substring(1));
                i++;
            }
        }

        printElection();

    }

    /**
     * Adds a <tt>Position</tt> to the <tt>Election</tt> with the title
     * provided by the specified <tt>String</tt>.
     *
     * @param name  the name of the position
     */
    public void addPosition(String name) {
        this.election.addPosition(new Position(name));
    }

    /**
     * Adds a <tt>Candidate</tt> to the <tt>Election</tt> with the provided
     * name and party. The candidate put in the running for the
     * <tt>Position</tt> specified by the provided title.
     *
     * @param candidateName the name of the candidate
     * @param candidateParty    the party of the candidate
     * @param positionTitle the title of the position that the candidate is
     *                      running for
     * @return  true if the candidate was successfully added to the election
     */
    public boolean addCandidateToPosition(String candidateName,
                                          String candidateParty,
                                          String positionTitle) {
        return this.election.addCandidateToPosition(
                new Candidate(candidateName, candidateParty), positionTitle);
    }

    /**
     * Adds a public opinion survey (<tt>Proposition</tt>) with the specified
     * title and description to the <tt>Election</tt>.
     *
     * @param title the title of the proposition
     * @param description   a description of the proposition
     */
    public void addProposition(String title, String description) {
        this.election.addProposition(new Proposition(title, description));
    }

    /**
     * Returns a <tt>List</tt> of the <tt>Position</tt>s currently in an
     * Election.
     *
     * @return  the list of existing positions
     */
    public List<Position> getPositions() {
        return this.election.getPositions();
    }

    /**
     * Returns a <tt>List</tt> of the <tt>Proposition</tt>s currently in an
     * Election.
     *
     * @return  the list of existing propositions
     */
    public List<Proposition> getPropositions() {
        return this.election.getPropositions();
    }

    /**
     * Prints a text representation of the <tt>Election</tt> to the console.
     */
    public void printElection() {
        for (Position position : getPositions()) {
            System.out.println("Position: " + position.getTitle());
            for (Candidate candidate : position.getCandidates()) {
                System.out.println("\tCandidate: " + candidate.getName());
                System.out.println("\t\tParty: " + candidate.getParty());
            }
        }

        for (Proposition proposition : getPropositions()) {
            System.out.println("Proposition: " + proposition.getName());
            System.out.println("\tDescription: " + proposition.getDescription());
        }
    }
}