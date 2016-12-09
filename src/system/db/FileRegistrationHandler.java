package system.db;

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
    PrintWriter out;
    Random rand = new Random();
    private final String NAME_OF_FILE = "csci360jake-matt/src/system/db/.registration";
    private final byte[] SALT = {122, -86, -33, -14, -41, -59, 78, -80, 82, -51, 102, -69, 80, 123, -8, 55, -64, 94, 54, 78, -85, -31, 125, -96, -3, 61, -90, -47, 114, 101, 45, -90, 127, 110, -39, -121, 86, 116, -125, 14, 65, -91, -94, 25, 13, -40, -109, 43, 0, 23, -77, -95, -121, -41, 72, 77, -8, 124, -89, 28, 89, -39, 111, 107};
    private final String DELIMITER = "END_OF_USER_INFO";

    /**
     * Constructs a new FileRegistrationHandler.
     */
    public FileRegistrationHandler() {
        try {
            fw = new FileWriter(NAME_OF_FILE, true);
        } catch (IOException e) {

            e.printStackTrace();
        }
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);
    }

    /**
     * Adds the information from a RegistrationForm to .registration.
     *
     * @param registrationForm  the RegistrationForm to be added
     */
    @Override
    public void add(RegistrationForm registrationForm) {
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
    }

    /**
     * Returns a List of all stored RegistrationForms.
     *
     * @return  a List of RegistrationForms
     */
    @Override
    public List<RegistrationForm> getAll() {
        List<RegistrationForm> returnList = new ArrayList<>();
        try {
           List<String> strList = Files.readAllLines(Paths.get(NAME_OF_FILE));
            List<String> idList = new ArrayList<>();
           for(int i = 0;i<strList.size();i++) {
               String s = strList.get(i);
               if(s.trim().equals(DELIMITER)) {
                   idList.add(strList.get(i + 1));
               }
           }
            for(String social:idList){
               returnList.add(get(social));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * gets a registration from from the users social security number
     */
    @Override
    public RegistrationForm get(String social) {

        List<String> strList = null;
        try {
            strList = Files.readAllLines(Paths.get(NAME_OF_FILE));
        outerLoop:
            for(int i = 0;i<strList.size();i++){
                String s = strList.get(i);
                if(s.trim().equals(social)){
                    String firstName = strList.get(i+1).trim();
                    String middleName = strList.get(i+2).trim();
                    String lastName = strList.get(i+3).trim();
                    String dateString = strList.get(i+4);
//                    system.out.println(dateString);
                    Calendar birthDay = new GregorianCalendar();
                    birthDay.setTimeInMillis(Long.parseLong(dateString));
                    String phoneNumber = strList.get(i+5).trim();
                    String streetAddress = strList.get(i+6).trim();
                    String city = strList.get(i+7);
                    String county = strList.get(i+8);
                    String state = strList.get(i+9);
                    String zip = strList.get(i+10);
                    return new RegistrationForm(firstName,middleName,lastName,phoneNumber,birthDay.get(Calendar.DAY_OF_MONTH),birthDay.get(Calendar.MONTH)+1,birthDay.get(Calendar.YEAR),strList.get(i),streetAddress,city,county,state,zip);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        FileRegistrationHandler rHandler = new FileRegistrationHandler();

        try {
            Random random = new Random();

            ArrayList<String> list = new ArrayList<>();
            List<String> names = Files.readAllLines(Paths.get
                    ("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/names.txt"));
            List<String> phoneNums = Files.readAllLines(Paths.get
                    ("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/phoneNums" +
                    ".txt"));
            List<String> birthdays = Files.readAllLines(Paths.get
                    ("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/birthdays.txt"));
            List<String> birthmonths = Files.readAllLines(Paths.get
                    ("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/birthmonths.txt"));
            List<String> birthyears = Files.readAllLines(Paths.get
                    ("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/birthyears.txt"));
            List<String> dlNums = Files.readAllLines(Paths.get("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/dlNums.txt"));
            List<String> streets = Files.readAllLines(Paths.get
                    ("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/streets.txt"));
            List<String> cities = Files.readAllLines(Paths.get("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/cities.txt"));
            List<String> counties = Files.readAllLines(Paths.get("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/counties" +
                    ".txt"));
            List<String> zips = Files.readAllLines(Paths.get("D:/Documents/School/Computer Science/360 Project/csci360jake-matt/src/system/db/zips.txt"));

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
