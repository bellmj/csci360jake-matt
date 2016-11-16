package System;

import System.Election.Candidate;
import System.Election.Position;
import System.Election.Proposition;
import System.Election.Voting.Ballot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matt on 11/15/16.
 */
public class FileBallotHandler implements DataHandler<Ballot> {
    private final String SELECTION_DELIMITEER = "END_OF_SELECTIONS";
    private final String PROPOSITION_DELIMITER = "END_OF_PROPOSITIONS";
    FileWriter fw;
    BufferedWriter bw;
    PrintWriter out;

    private final String NAME_OF_FILE = "datastore";
    public FileBallotHandler() {
        try {
            fw = new FileWriter(NAME_OF_FILE, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);


    }

    @Override
    public void add(Ballot ballot) {
        out.println(ballot.getVoterHashID());
        ballot.getSelections().forEach((k,v) -> out.println("" + k + " " + v));
        out.println(SELECTION_DELIMITEER);
        ballot.getPropositions().forEach((k,v) -> out.println("" + k + " " + v));
        out.println(PROPOSITION_DELIMITER);
        out.flush();
    }

    @Override
    public List<String> getAll() {
        try {
            return Files.readAllLines(Paths.get(NAME_OF_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Ballot get(String id) {
        return null;
    }
    public static void main(String[] args){
        HashMap<Position, Candidate> selections;
        String voterHashID = "adw43fladjs3";
        ArrayList<Candidate> candidateArrayList = new ArrayList<>();
        candidateArrayList.add(new Candidate(0l,"Gary Johnson","Libertarian"));
        selections = new HashMap<>();
        selections.put(new Position("King of the World",candidateArrayList,candidateArrayList.get(0)),candidateArrayList.get(0));
        HashMap<Proposition, Boolean> propositions;
        propositions = new HashMap<>();
        propositions.put(new Proposition("Legalize good Herb","We be legalizing good herb",0,0),true);
        Ballot ballot = new Ballot(selections,propositions,voterHashID);
        FileBallotHandler hander = new FileBallotHandler();
        hander.add(ballot);
        System.out.println(hander.getAll());
    }

}
