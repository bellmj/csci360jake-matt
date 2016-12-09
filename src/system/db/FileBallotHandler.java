package system.db;

import system.election.*;
import system.election.voting.Ballot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matt on 11/15/16.
 */
public class FileBallotHandler implements DataHandler<Ballot> {
    private final String SELECTION_DELIMITEER = "END_OF_SELECTIONS";
    private final String PROPOSITION_DELIMITER = "END_OF_PROPOSITIONS";
    private final String START_OF_ENTRY = "START_OF_ENTRY";
    private final static byte[] privateKey = {50, 96, 67, -32, 39, -40, 91, -64, 54, 55, 121, 90, -116, -6, -44, -74};
    FileWriter fw;
    File dataFile;
    BufferedWriter bw;
    PrintWriter out;
    private final String NAME_OF_FILE = ".datastore";

    public FileBallotHandler() {
        try {
            dataFile = new File(NAME_OF_FILE);
            if(!dataFile.exists()){
                dataFile.createNewFile();
            }
            fw = new FileWriter(NAME_OF_FILE,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);


    }
    private void openFile(){

        try {
            Security.decrypt(privateKey,dataFile,dataFile);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);
    }
    private void closeFile(){

        out.flush();
        out.close();
        try {
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Security.encrypt(privateKey,dataFile,dataFile);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Ballot ballot) {
        openFile();
        out.println( START_OF_ENTRY);
        out.println(ballot.getVoterHashID());
        ballot.getSelections().forEach((k,v) -> out.println("" + k + "~" + v));
        out.println(SELECTION_DELIMITEER);
        ballot.getPropositions().forEach((k,v) -> out.println("" + k + "~" + v));
        out.println(PROPOSITION_DELIMITER);
        out.flush();
        closeFile();
    }

    @Override
    public List<Ballot> getAll() {
      openFile();
        List<Ballot> returnList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(NAME_OF_FILE));
            for(int i = 0; i<lines.size();i++){
                lines.set(i, lines.get(i).trim());
            }
            for(int i =0;i<lines.size();i++){
                HashMap<String,Candidate> selectionHashMap = new HashMap<>();
                HashMap<String,Boolean> propositonHashMap = new HashMap<>();
                String voterID = "";
                if(lines.get(i).equals( START_OF_ENTRY)){
                    voterID = lines.get(i+1);


                    i+=2;
                    while(!lines.get(i).equals(SELECTION_DELIMITEER)){
                        String[] voterSelection = lines.get(i).split("~");
                        Candidate selection = new Candidate(0l,voterSelection[1],"N/A");
                        selectionHashMap.put(voterSelection[0],selection);
                        i++;
                    }
                    i++;
                    while(!lines.get(i).equals(PROPOSITION_DELIMITER)){
                        String[] propositionSelection = lines.get(i).split("~");
                        Boolean bool = Boolean.parseBoolean(propositionSelection[1]);
                        propositonHashMap.put(propositionSelection[0],bool);
                        i++;

                    }
                }
                Ballot ballot = new Ballot(selectionHashMap,propositonHashMap,voterID);
                returnList.add(ballot);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFile();
    return returnList;
    }

    public void eraseBallots() {
        try {
            FileWriter eraser = new FileWriter(NAME_OF_FILE, false);
            eraser.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ballot get(String id) {
        return null;
    }
    public static void main(String[] args){
        ElectionHandler electionHandler = new ElectionHandler();
        electionHandler.addCandidateToPosition("Gary Johnson","Libertarian","King of the World");
        electionHandler.addCandidateToPosition("Jill Stien","Libertarian","King of the World");
        electionHandler.addCandidateToPosition("Donald Drumpf","Libertarian","King of the World");
        electionHandler.addProposition("Legalize good Herb","we be leagalixging good herb");
        HashMap<String, Candidate> selections;
        String voterHashID = "adw43fladjs3";
        ArrayList<Candidate> candidateArrayList = new ArrayList<>();
        candidateArrayList.add(new Candidate(0l,"Gary Johnson","Libertarian"));
        candidateArrayList.add(new Candidate(0l,"Jill stien","Libertarian"));
        candidateArrayList.add(new Candidate(0l,"Donald drumpf","Libertarian"));
        selections = new HashMap<>();
        selections.put("King of the World",candidateArrayList.get(2));
        HashMap<String, Boolean> propositions;
        propositions = new HashMap<>();
        propositions.put("Legalize good Herb",true);
        Ballot ballot = new Ballot(selections,propositions,voterHashID);
        FileBallotHandler handler = new FileBallotHandler();
        handler.add(ballot);
        handler.add(ballot);
        handler.add(ballot);
        char[] pass = {'1','2','3','4'};
        electionHandler.assignVotesForBallots(pass,handler.getAll());
        electionHandler.printElection();
    }

}
