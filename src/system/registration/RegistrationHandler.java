package system.registration;

import system.db.DataHandler;

/**
 * A class which handles the registration of new voters and the information
 * of existing voters. A single <tt>system.registration.RegistrationHandler</tt> is created at the
 * initialization of the voting system.
 *
 * @see RegistrationForm
 */
public class RegistrationHandler {

    private DataHandler<RegistrationForm> dataHandler;

    public RegistrationHandler(DataHandler<RegistrationForm> dataHandler) {
        this.dataHandler = dataHandler;
    }

//    private boolean informationIsValid(RegistrationForm form) {
//
//    }

    public void register(RegistrationForm form) throws IllegalArgumentException {
        this.dataHandler.add(form);
    }

    public boolean voterIsRegistered(String legalID) {
        try {
            return this.dataHandler.get(legalID) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
