package Registration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * A class which handles the registration of new voters and the information
 * of existing voters. A single <tt>Registration.RegistrationHandler</tt> is created at the
 * initialization of the voting system.
 *
 * @see RegistrationForm
 */
public class RegistrationHandler {

    private Random random;
    private File registrationDatabase;
    private PrintWriter writer;

    public RegistrationHandler() {
        this.random = new Random();
        try {
            this.registrationDatabase = new File("RegistrationDatabase.txt");
            this.writer = new PrintWriter(registrationDatabase, "UTF-8");
        } catch (IOException e) {
            System.out.println("IO Error");
            e.printStackTrace();
        }
    }

    public void register(RegistrationForm form) throws IllegalArgumentException {
        String entry = "";

        try {
            // Enter name information
            if (form.getFirstName() == null ||
                    form.getLastName() == null) {
                throw new IllegalArgumentException();
            }
            else {
                entry += form.getLastName() + "\t" + form.getFirstName() +
                        "\t" + form.getMiddleName() + "\t";
            }

            // Enter birthdate/phone number
            if (form.getBirthDate() == null ||
                    form.getPhoneNumber() == null) {
                throw new IllegalArgumentException();
            }
            else {
                entry += form.getBirthDate() + "\t" + form.getPhoneNumber() +
                        "\t";
            }

            // Enter ID numbers
            if (form.getSsn() == null ||
                    form.getLegalID() == null) {
                throw new IllegalArgumentException();
            }
            else {
                entry += form.getSsn() + "\t" + form.getLegalID() + "\t";
            }

            // Enter address
            if (form.getStreetAddress() == null ||
                    form.getCity() == null ||
                    form.getCounty() == null ||
                    form.getState() == null ||
                    form.getZip() == null) {
                throw new IllegalArgumentException();
            }
            else {
                entry += form.getStreetAddress() + "\t" + form.getCity() +
                        "\t" + form.getCounty() + "\t" + form.getState() +
                        "\t" + form.getZip();
            }

            entry += "\n";
            entry = random.nextInt(1000) + "\t" + entry;
            writer.append(entry);
            System.out.println("Success");

        } catch (IllegalArgumentException e) {
            // TODO Tell user that the form is incomplete.
            System.out.println("Incomplete registration form.");
        }
    }

    public static void main(String[] args) {
        RegistrationHandler registrationHandler = new RegistrationHandler();

        RegistrationForm form1 = new RegistrationForm("Jake", "Barton", "Marotta",
                "6158610301", 10, 05, 1996, "111223333", "SC123456789",
                "87 Columbus St A", "Charleston", "Charleston", "SC", "29403");

        registrationHandler.register(form1);
    }

}
