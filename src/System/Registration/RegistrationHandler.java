package System.Registration;

import System.DB.DataHandler;

/**
 * A class which handles the registration of new voters and the information
 * of existing voters. A single <tt>System.Registration.RegistrationHandler</tt> is created at the
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

//    public boolean canVote(String legalID) {
//
//    }

}
