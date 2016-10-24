package Registration;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A class which contains all of the information necessary for registering to
 * vote in South Carolina.
 *
 * @see RegistrationHandler
 */
class RegistrationForm {

    private String firstName;
    private String middleName;
    private String lastName;

    private Date birthDate;
    private String phoneNumber;

    private String ssn;
    private String legalID;

    private String streetAddress;
    private String city;
    private String county;
    private String state;
    private String zip;

    /**
     * Constructs a completed <tt>Registration.RegistrationForm</tt> from the provided
     * information.
     *
     * @param firstName the voter's first name
     * @param middleName the voter's middle name
     * @param lastName  the voter's last name
     * @param ssn   the voter's social security number
     * @param legalID   the voter's legal ID number
     * @param streetAddress the voter's street address
     * @param city  the voter's city of residence
     * @param county    the voter's county of residence
     * @param state the voter's state of residence
     * @param zip   the voter's postal code
     */
    public RegistrationForm(String firstName, String middleName,
                            String lastName, String phoneNumber, int birthDay,
                            int birthMonth, int birthYear, String ssn,
                            String legalID, String streetAddress, String city,
                            String county, String state, String zip) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;

        this.phoneNumber = phoneNumber;
        this.birthDate = new GregorianCalendar(birthYear, birthMonth - 1, birthDay).getTime();

        this.ssn = ssn;
        this.legalID = legalID;

        this.streetAddress = streetAddress;
        this.city = city;
        this.county = county;
        this.state = state;
        this.zip = zip;
    }

    /**
     * Sets the voter's first name to the specified value.
     *
     * @param firstName the voter's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the voter's middle name to the specified value.
     *
     * @param middleName the voter's middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Sets the voter's last name to the specified value.
     *
     * @param lastName  the voter's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the voter's birthdate to the specified Date object.
     *
     * @param birthDate the voter's birthdate
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Sets the voter's birthdate to a Date object made with the provided
     * information.
     *
     * @param birthYear the voter's birth year
     * @param birthMonth    the voter's birth month
     * @param birthDay  the voter's birth day
     */
    public void setBirthDate(int birthYear, int birthMonth, int birthDay) {
        this.birthDate = new GregorianCalendar(birthYear, birthMonth - 1, birthDay).getTime();
    }

    /**
     * Sets the voter's phone number to the specified value.
     *
     * @param phoneNumber   the voter's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the voter's social security number to the specified value.
     *
     * @param ssn   the voter's social security number
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Sets the voter's legal ID number to the specified value.
     *
     * @param legalID   the voter's legal ID number
     */
    public void setLegalID(String legalID) {
        this.legalID = legalID;
    }

    /**
     * Sets the voter's street address to the specified value.
     *
     * @param streetAddress the voter's street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Sets the voter's city of residence to the specified value.
     *
     * @param city  the voter's city of residence
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the voter's county of residence to the specified value.
     * @param county    the voter's county of residence
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * Sets the voter's state of residence to the specified value.
     *
     * @param state the voter's state of residence
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Sets the voter's postal code to the specified value.
     *
     * @param zip   the voter's postal code
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
