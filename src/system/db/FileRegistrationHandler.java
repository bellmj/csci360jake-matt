package system.db;

import system.election.CryptoException;
import system.election.Security;
import system.registration.RegistrationForm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Handles the writing and retrieval of RegistrationForms to .registration.
 */
public class FileRegistrationHandler implements DataHandler<RegistrationForm> {

    FileWriter fw;
    BufferedWriter bw;
    File dataFile;
    PrintWriter out;

    private final String NAME_OF_FILE = "csci360jake-matt/src/system/db/.registration";
    private final byte[] SALT = {122, -86, -33, -14, -41, -59, 78, -80, 82, -51, 102, -69, 80, 123, -8, 55, -64, 94, 54, 78, -85, -31, 125, -96, -3, 61, -90, -47, 114, 101, 45, -90, 127, 110, -39, -121, 86, 116, -125, 14, 65, -91, -94, 25, 13, -40, -109, 43, 0, 23, -77, -95, -121, -41, 72, 77, -8, 124, -89, 28, 89, -39, 111, 107};
    private final static byte[] privateKey = {50, -96, 67, -33, 39, -40, 91, -64, 54, 55, 121, -90, -116, 6, -44, 74};
    private final String DELIMITER = "END_OF_USER_INFO";

    /**
     * Constructs a new FileRegistrationHandler.
     */
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

    /**
     * Adds the information from a RegistrationForm to .registration.
     *
     * @param registrationForm  the RegistrationForm to be added
     */
    @Override
    public void add(RegistrationForm registrationForm) {
        openFile();
        if (get(registrationForm.getLegalID()) != null) {
            System.out.println("Voter already registered.");
        } else {
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
        }
        closeFile();
    }

    /**
     * Returns a List of all stored RegistrationForms.
     *
     * @return  a List of RegistrationForms
     */
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

    /**
     * gets a registration from from the users social security number
     */
    @Override
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
        FileRegistrationHandler rHandler = new FileRegistrationHandler();

        try {
            Random random = new Random();

            ArrayList<String> list = new ArrayList<>();
            List<String> names = Files.readAllLines(Paths.get
                    ("csci360jake-matt/src/system/resources/names.txt"));
            List<String> phoneNums = Files.readAllLines(Paths.get
                    ("csci360jake-matt/src/system/resources/phoneNums" +
                            ".txt"));
            List<String> birthdays = Files.readAllLines(Paths.get
                    ("csci360jake-matt/src/system/resources/birthdays.txt"));
            List<String> birthmonths = Files.readAllLines(Paths.get
                    ("csci360jake-matt/src/system/resources/birthmonths.txt"));
            List<String> birthyears = Files.readAllLines(Paths.get
                    ("csci360jake-matt/src/system/resources/birthyears.txt"));
            List<String> dlNums = Files.readAllLines(Paths.get("csci360jake-matt/src/system/resources/dlNums.txt"));
            List<String> streets = Files.readAllLines(Paths.get
                    ("csci360jake-matt/src/system/resources/streets.txt"));
            List<String> cities = Files.readAllLines(Paths.get("csci360jake-matt/src/system/resources/cities.txt"));
            List<String> counties = Files.readAllLines(Paths.get("csci360jake-matt/src/system/resources/counties" +
                    ".txt"));
            List<String> zips = Files.readAllLines(Paths.get("csci360jake-matt/src/system/resources/zips.txt"));

            for (int i = 0; i < 100; i++) {
                String[] name = names.get(i).split(" ");
                list.add(i, name[0] + "\n\n" + name[1] + "\n" + phoneNums.get
                        (i) + "\n" +
                                birthdays.get(i) + "\n" + birthmonths.get(i) +
                                "\n" + birthyears.get(i) + "\n" + dlNums.get
                        (i) +
                        "\n" +
                                random.nextInt(999) + " " + streets.get(i) +
                        "\nCharleston\n" + counties.get(i) + "\nSC\n" + zips.get(i));
            }

            String[] info;
            for (String s : list) {
                info = s.split("\n");
                rHandler.add(new RegistrationForm(info[0], info[1], info[2],
                        info[3], Integer.parseInt(info[4]), Integer.parseInt
                        (info[5]),
                        Integer.parseInt(info[6]), info[7],
                        info[8],
                        info[9], info[10], info[11], info[12]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
