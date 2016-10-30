import Election.Voting.Ballot;

public class DBHandler {

    private MongoClient mongoClient;
    private DB db;
    private MongoCredential dbCredential;
    private String adminUserName;
    private char[] adminPassword;

    DBHandler(String adminUserName, char[] adminPassword) {

    }

    boolean addBallot(Ballot ballot, char[] adminPassword) {

    }

    Ballot[] getBallotArray() {

    }

}
