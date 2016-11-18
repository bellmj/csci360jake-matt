package System;
import System.*;
import System.Registration.RegistrationForm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;


/**
 * Created by matt on 11/18/16.
 */
public class FileRegisterationHandler implements DataHandler<RegistrationForm> {

    FileWriter fw;
    BufferedWriter bw;
    PrintWriter out;
    Random rand = new Random();
    private final String NAME_OF_FILE = ((Integer)Math.abs(rand.nextInt())).toString();
    private final String DELIMITER = "END_OF_USER_INFO";

    public FileRegisterationHandler() {
        try {
            fw = new FileWriter(NAME_OF_FILE, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);
    }

    @Override
    public void add(RegistrationForm registrationForm) {
        out.println(DELIMITER);
        out.println(registrationForm.getLegalID());
        out.println(registrationForm.getFirstName());
        out.println(registrationForm.getMiddleName());
        out.println(registrationForm.getLastName());
        out.println(registrationForm.getBirthDate().toString());
        out.println(registrationForm.getPhoneNumber());
        out.println(registrationForm.getStreetAddress());
        out.println(registrationForm.getCity());
        out.println(registrationForm.getCounty());
        out.println(registrationForm.getState());
        out.println(registrationForm.getZip());
        out.flush();

    }

    @Override
    public List<RegistrationForm> getAll() {
        return null;
    }

    @Override
    /**
     * gets a Registration from from the users social security number
     */
    public RegistrationForm get(String social) {
        try {
            List<String> strList = Files.readAllLines(Paths.get(NAME_OF_FILE));
            boolean sentienalVal = false;
            outerLoop:
            for(int i = 0;i<strList.size();i++){
                String s = strList.get(i);
                if(s.equals(social)){
                    return new RegistrationForm(strList(i+1),strList.get(i+2),strList(i+3),strList(i+3),strList(i+3),strList(i+3),)
                    break outerLoop;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
       RegistrationForm rf = new RegistrationForm("matt","J","Bell","8434690500",8,2,1996,"q2rq23rerwr","q23r32rew","2056 comingtee ln","Mount Pleasant","Charelston","SC","29464");
        FileRegisterationHandler rHandler = new FileRegisterationHandler();
        rHandler.add(rf);
    }
}
