package system.db;

import system.election.CryptoException;
import system.election.Security;
import system.registration.RegistrationForm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/**
 * Created by matt on 11/18/16.
 */
public class FileRegistrationHandler implements DataHandler<RegistrationForm> {

    FileWriter fw;
    BufferedWriter bw;
    File dataFile;
    PrintWriter out;
    private final String NAME_OF_FILE = ".registration";
    private final byte[] SALT = {122, -86, -33, -14, -41, -59, 78, -80, 82, -51, 102, -69, 80, 123, -8, 55, -64, 94, 54, 78, -85, -31, 125, -96, -3, 61, -90, -47, 114, 101, 45, -90, 127, 110, -39, -121, 86, 116, -125, 14, 65, -91, -94, 25, 13, -40, -109, 43, 0, 23, -77, -95, -121, -41, 72, 77, -8, 124, -89, 28, 89, -39, 111, 107};
    private final static byte[] privateKey = {50, -96, 67, -33, 39, -40, 91, -64, 54, 55, 121, -90, -116, 6, -44, 74};
    private final String DELIMITER = "END_OF_USER_INFO";

    public FileRegistrationHandler() {
        try {
            dataFile = new File(NAME_OF_FILE);
            fw = new FileWriter(NAME_OF_FILE, true);
        } catch (IOException e) {

            e.printStackTrace();
        }
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                closeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openFile() {

        try {
            Security.decrypt(privateKey, dataFile, dataFile);
            fw = new FileWriter(NAME_OF_FILE, true);
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);
    }

    private void closeFile() {

        out.flush();
        out.close();
        try {
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Security.encrypt(privateKey, dataFile, dataFile);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(RegistrationForm registrationForm) {
        openFile();
        out.println(DELIMITER);
        out.println(registrationForm.getLegalID());
        out.println(registrationForm.getFirstName());
        out.println(registrationForm.getMiddleName());
        out.println(registrationForm.getLastName());
        out.println(registrationForm.getBirthDate().getTimeInMillis());
        out.println(registrationForm.getPhoneNumber());
        out.println(registrationForm.getStreetAddress());
        out.println(registrationForm.getCity());
        out.println(registrationForm.getCounty());
        out.println(registrationForm.getState());
        out.println(registrationForm.getZip());
        out.flush();
        closeFile();
    }

    @Override
    public List<RegistrationForm> getAll() {
        openFile();
        List<RegistrationForm> returnList = new ArrayList<>();
        try {
            List<String> strList = Files.readAllLines(Paths.get(NAME_OF_FILE));
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < strList.size(); i++) {
                String s = strList.get(i);
                if (s.trim().equals(DELIMITER)) {
                    idList.add(strList.get(i + 1));
                }
            }
            for (String social : idList) {
                returnList.add(getRegistrationForm(social, strList));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFile();
        return returnList;
    }

    @Override
    /**
     * gets a registration from from the users social security number
     */
    public RegistrationForm get(String social) {
        openFile();
        try {
            List<String> strList = Files.readAllLines(Paths.get(NAME_OF_FILE));
            RegistrationForm returnForm = getRegistrationForm(social,strList);
            closeFile();
            return returnForm;
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFile();
        return null;

    }


    private RegistrationForm getRegistrationForm(String social, List<String> strList) {
        outerLoop:
        for (int i = 0; i < strList.size(); i++) {
            String s = strList.get(i);
            if (s.trim().equals(social)) {
                String firstName = strList.get(i + 1).trim();
                String middleName = strList.get(i + 2).trim();
                String lastName = strList.get(i + 3).trim();
                String dateString = strList.get(i + 4);
//                    system.out.println(dateString);
                Calendar birthDay = new GregorianCalendar();
                birthDay.setTimeInMillis(Long.parseLong(dateString));
                String phoneNumber = strList.get(i + 5).trim();
                String streetAddress = strList.get(i + 6).trim();
                String city = strList.get(i + 7);
                String county = strList.get(i + 8);
                String state = strList.get(i + 9);
                String zip = strList.get(i + 10);
                return new RegistrationForm(firstName, middleName, lastName, phoneNumber, birthDay.get(Calendar.DAY_OF_MONTH), birthDay.get(Calendar.MONTH) + 1, birthDay.get(Calendar.YEAR), strList.get(i), streetAddress, city, county, state, zip);
            }

        }
        return null;
    }

    public static void main(String[] args) throws CryptoException {
        RegistrationForm rf = new RegistrationForm("matt", "J", "Bell", "8434690500", 8, 2, 1996, "1234", "2056 comingtee ln", "Mount Pleasant", "Charelston", "SC", "29464");
        RegistrationForm rf1 = new RegistrationForm("matt", "J", "Bell", "8434690500", 8, 2, 1996, "5678", "2056 comingtee ln", "Mount Pleasant", "Charelston", "SC", "29464");
        RegistrationForm rf2 = new RegistrationForm("matt", "J", "Bell", "8434690500", 8, 2, 1996, "4321", "2056 comingtee ln", "Mount Pleasant", "Charelston", "SC", "29464");
        RegistrationForm rf3 = new RegistrationForm("matt", "J", "Bell", "8434690500", 8, 2, 1996, "8765", "2056 comingtee ln", "Mount Pleasant", "Charelston", "SC", "29464");
        FileRegistrationHandler rHandler = new FileRegistrationHandler();
//        rHandler.add(rf);
//        rHandler.add(rf2);
//        System.out.println(rHandler.get("1234"));
//       System.out.println(rHandler.getAll().size());
        rHandler.closeFile();

//

    }

}
