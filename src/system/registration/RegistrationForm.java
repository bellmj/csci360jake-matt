package system.registration;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A class which contains all of the information necessary for registering to
 * vote in South Carolina.
 *
 * @see RegistrationHandler
 */
public class RegistrationForm {

    private String firstName;
    private String middleName;
    private String lastName;

    private GregorianCalendar birthDate;
    private String phoneNumber;

    private String legalID;

    private String streetAddress;
    private String city;
    private String county;
    private String state;
    private String zip;

    /**
     * Constructs a completed <tt>system.registration.RegistrationForm</tt> from the provided
     * information. <p>
     *
     * The voter's middle name can be null.
     *
     * @param firstName the voter's first name
     * @param middleName the voter's middle name
     * @param lastName  the voter's last name
     * @param legalID   the voter's legal ID number
     * @param streetAddress the voter's street address
     * @param city  the voter's city of residence
     * @param county    the voter's county of residence
     * @param state the voter's state of residence
     * @param zip   the voter's postal code
     */
    public RegistrationForm(String firstName, String middleName,
                            String lastName, String phoneNumber, int birthDay,
                            int birthMonth, int birthYear,
                            String legalID, String streetAddress, String city,
                            String county, String state, String zip) {
        this.firstName = firstName;
        if (middleName == null) {
            this.middleName = "";
        }
        else {
            this.middleName = middleName;
        }
        this.lastName = lastName;

        this.phoneNumber = phoneNumber;
        this.birthDate = new GregorianCalendar(birthYear, birthMonth - 1, birthDay);

        this.legalID = legalID;

        this.streetAddress = streetAddress;
        this.city = city;
        this.county = county;
        this.state = state;
        this.zip = zip;
    }

    /**
     * Gets the first name stored in the form.
     *
     * @return  the form's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the middle name stored in the form.
     *
     * @return  the form's middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Gets the last name stored in the form.
     *
     * @return  the form's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the birthdate stored in the form.
     *
     * @return  the form's birthdate
     */
    public GregorianCalendar getBirthDate() {
        return birthDate;
    }

    /**
     * Gets the phone number stored in the form.
     *
     * @return  the form's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the legal ID stored in the form.
     *
     * @return  the form's legal ID
     */
    public String getLegalID() {
        return legalID;
    }

    /**
     * Gets the street address stored in the form.
     *
     * @return  the form's street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Gets the city stored in the form.
     *
     * @return  the form's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the county stored in the form.
     *
     * @return  the form's county
     */
    public String getCounty() {
        return county;
    }

    /**
     * Gets the state stored in the form.
     *
     * @return  the form's state
     */
    public String getState() {
        return state;
    }

    /**
     * Gets the postal code stored in the form.
     *
     * @return  the form's zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Returns a String of each field in the form delimited by '.
     *
     * @return String representation of a the form
     */
    @Override
    public String toString() {
        return "RegistrationForm{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + (birthDate.get(Calendar.MONTH)+1) + "/"+ birthDate.get(Calendar.DAY_OF_MONTH)+ "/" + birthDate.get(Calendar.YEAR) +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", legalID='" + legalID + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
