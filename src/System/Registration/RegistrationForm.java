package System.Registration;

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

    public RegistrationForm() {
        this.firstName = null;
        this.middleName = "";
        this.lastName = null;

        this.birthDate = null;
        this.phoneNumber = null;

        this.ssn = null;
        this.legalID = null;

        this.streetAddress = null;
        this.city = null;
        this.county = null;
        this.state = null;
        this.zip = null;
    }

    /**
     * Constructs a completed <tt>System.Registration.RegistrationForm</tt> from the provided
     * information. <p>
     *
     * The voter's middle name can be null.
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
        if (middleName == null) {
            this.middleName = "";
        }
        else {
            this.middleName = middleName;
        }
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
     * Gets the first name stored in the form.
     *
     * @return  the form's first name
     */
    String getFirstName() {
        return firstName;
    }

    /**
     * Sets the voter's first name to the specified value.
     *
     * @param firstName the voter's first name
     */
    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name stored in the form.
     *
     * @return  the form's middle name
     */
    String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the voter's middle name to the specified value.
     *
     * @param middleName the voter's middle name
     */
    void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the last name stored in the form.
     *
     * @return  the form's last name
     */
    String getLastName() {
        return lastName;
    }

    /**
     * Sets the voter's last name to the specified value.
     *
     * @param lastName  the voter's last name
     */
    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the birthdate stored in the form.
     *
     * @return  the form's birthdate
     */
    Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the voter's birthdate to the specified Date object.
     *
     * @param birthDate the voter's birthdate
     */
    void setBirthDate(Date birthDate) {
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
    void setBirthDate(int birthYear, int birthMonth, int birthDay) {
        this.birthDate = new GregorianCalendar(birthYear, birthMonth - 1, birthDay).getTime();
    }

    /**
     * Gets the phone number stored in the form.
     *
     * @return  the form's phone number
     */
    String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the voter's phone number to the specified value.
     *
     * @param phoneNumber   the voter's phone number
     */
    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the ssn stored in the form.
     *
     * @return  the form's ssn
     */
    String getSsn() {
        return ssn;
    }

    /**
     * Sets the voter's social security number to the specified value.
     *
     * @param ssn   the voter's social security number
     */
    void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Gets the legal ID stored in the form.
     *
     * @return  the form's legal ID
     */
    String getLegalID() {
        return legalID;
    }

    /**
     * Sets the voter's legal ID number to the specified value.
     *
     * @param legalID   the voter's legal ID number
     */
    void setLegalID(String legalID) {
        this.legalID = legalID;
    }

    /**
     * Gets the street address stored in the form.
     *
     * @return  the form's street address
     */
    String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the voter's street address to the specified value.
     *
     * @param streetAddress the voter's street address
     */
    void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Gets the city stored in the form.
     *
     * @return  the form's city
     */
    String getCity() {
        return city;
    }

    /**
     * Sets the voter's city of residence to the specified value.
     *
     * @param city  the voter's city of residence
     */
    void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the county stored in the form.
     *
     * @return  the form's county
     */
    String getCounty() {
        return county;
    }

    /**
     * Sets the voter's county of residence to the specified value.
     *
     * @param county    the voter's county of residence
     */
    void setCounty(String county) {
        this.county = county;
    }

    /**
     * Gets the state stored in the form.
     *
     * @return  the form's state
     */
    String getState() {
        return state;
    }

    /**
     * Sets the voter's state of residence to the specified value.
     *
     * @param state the voter's state of residence
     */
    void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the postal code stored in the form.
     *
     * @return  the form's zip code
     */
    String getZip() {
        return zip;
    }

    /**
     * Sets the voter's postal code to the specified value.
     *
     * @param zip   the voter's postal code
     */
    void setZip(String zip) {
        this.zip = zip;
    }
}