package system.registration;

import system.db.DataHandler;
import system.election.Security;

import java.util.Arrays;
import java.util.Random;

/**
 * A class which handles the registration of new voters and the information
 * of existing voters. A single <tt>system.registration.RegistrationHandler</tt>
 * is created at the initialization of the voting system.
 *
 * @see RegistrationForm
 */
public class RegistrationHandler {

    private DataHandler<RegistrationForm> dataHandler;
    Random rando = new Random();
    private final byte[] SALT = {122, -86, -33, -14, -41, -59, 78, -80, 82, -51,
            102, -69, 80, 123, -8, 55, -64, 94, 54, 78, -85, -31, 125, -96, -3,
            61, -90, -47, 114, 101, 45, -90, 127, 110, -39, -121, 86, 116, -125,
            14, 65, -91, -94, 25, 13, -40, -109, 43, 0, 23, -77, -95, -121, -41,
            72, 77, -8, 124, -89, 28, 89, -39, 111, 107};

    /**
     * Constructs a new RegistrationHandler with the specified DataHandler.
     *
     * @param dataHandler   a DataHandler which handles RegistrationForms
     */
    public RegistrationHandler(DataHandler<RegistrationForm> dataHandler) {
        this.dataHandler = dataHandler;
    }

    /**
     * Saves a RegistrationForm to the registration "database."
     *
     * @param form  the RegistrationForm to save
     * @throws IllegalArgumentException if
     */
    public void register(RegistrationForm form) throws IllegalArgumentException {
        this.dataHandler.add(form);
    }

    public byte[] voterIsRegistered(String legalID) {
        try {
            if (this.dataHandler.get(legalID) == null) {
                return null;
            } else {
               return Security.hashPassword(legalID.toCharArray(), SALT, 4, 64);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
