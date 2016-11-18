package System;
import System.*;
import System.Registration.RegistrationForm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    private final String NAME_OF_FILE = ((Integer)rand.nextInt()).toString();

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
        out.println(registrationForm.getFirstName() + " " + registrationForm.getMiddleName() + " " + registrationForm.getLastName());
        out.println(registrationForm.getBirthDate().toString());
        out.println(registrationForm.getPhoneNumber());
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
    public RegistrationForm get(String id) {
        return null;
    }

    public static void main(String[] args) {
       RegistrationForm rf = new RegistrationForm("matt","J","Bell","8434690500",8,2,1996,"q2rq23rerwr","q23r32rew","2056 comingtee ln","Mount Pleasant","Charelston","SC","29464");
        FileRegisterationHandler rHandler = new FileRegisterationHandler();
        rHandler.add(rf);
    }
}
