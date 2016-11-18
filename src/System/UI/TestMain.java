package System.UI;

import System.Election.Candidate;
import System.Election.ElectionHandler;
import System.Election.Position;
import System.Election.Proposition;
import System.Election.Voting.BallotHandler;
import System.Registration.RegistrationForm;
import System.Registration.RegistrationHandler;
import System.DBHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestMain {

    public static Scanner scanner;
    public static RegistrationHandler registrationHandler;
    public static BallotHandler ballotHandler;
    public static ElectionHandler electionHandler;
    public static DBHandler dbHandler;

    public static void main(String[] args) {
        System.out.println(".oPYo. .pPYo. .oPYo. o     o .oPYo. ooooo o o    o .oPYo. \n" +
                "    `8 8      8  .o8 8     8 8    8   8   8 8b   8 8    8 \n" +
                "  .oP' 8oPYo. 8 .P'8 8     8 8    8   8   8 8`b  8 8      \n" +
                "   `b. 8'  `8 8.d' 8 `b   d' 8    8   8   8 8 `b 8 8   oo \n" +
                "    :8 8.  .P 8o'  8  `b d'  8    8   8   8 8  `b8 8    8 \n" +
                "`YooP' `YooP' `YooP'   `8'   `YooP'   8   8 8   `8 `YooP8 \n" +
                ":.....::.....::.....::::..::::.....:::..::....:::..:..... \n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::::::::: \n" +
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        registrationHandler = new RegistrationHandler();
        ballotHandler = new BallotHandler();
        electionHandler = new ElectionHandler();
        dbHandler = new DBHandler();

        System.out.print("\nWelcome!\n");
        boolean done = false;
        boolean votingMachine = false;
        int method;

        scanner = new Scanner(System.in);

        while (!done) {
            System.out.print("1. Set Up Voting Machine\n" +
                    "2. Check Voter Registration\n" +
                    "3. Register New Voter\n" +
                    "4. Exit Program\n" +
                    "Please select an option from above: ");

            method = scanner.nextInt();
            scanner.nextLine();

            switch (method) {
                case 1:
                    if (willSetUpVotingMachine()) {
                        setUpVotingMachine();
                        done = true;
                        votingMachine = true;
                    }
                    break;
                case 2:
                    checkRegistration();
                    break;
                case 3:
                    registerNewVoter();
                    break;
                case 4:
                    done = true;
                    break;
                default:
                    break;
            }
        }

        while (votingMachine) {
            votingMachine = vote();
        }

        scanner.close();
    }

    private static boolean willSetUpVotingMachine() {
        System.out.print("\n\nWARNING: Once the program is set up as a voting " +
                "machine, you will be unable to return to the menu unless " +
                "you restart the software. Are you sure you want to continue? (Y/N): ");
        return scanner.nextLine().toLowerCase().equals("y");
    }

    private static boolean setUpVotingMachine() {
        boolean done = false;
        int method;
        String posName;
        String propName;
        String propDesc;
        String candidateName;
        String candidateParty;

        while (!done) {
            System.out.print("1. Add Position\n" +
                    "2. Add Candidate to Position\n" +
                    "3. Add Proposition\n" +
                    "4. Confirm Election\n" +
                    "Please select an option from above:");

            method = scanner.nextInt();
            scanner.nextLine();

            switch (method) {
                case 1:
                    System.out.print("Enter title of position: ");
                    posName = scanner.nextLine();
                    electionHandler.addPosition(posName);
                    System.out.println("\n\nAdded position " + posName + " to election.\n");
                    break;
                case 2:
                    System.out.print("Which position is this candidate running for?: ");
                    posName = scanner.nextLine();
                    System.out.print("\nEnter name of candidate: ");
                    candidateName = scanner.nextLine();
                    System.out.print("\nEnter party of candidate: ");
                    candidateParty = scanner.nextLine();
                    if (!electionHandler.addCandidateToPosition(candidateName, candidateParty, posName)) {
                        System.out.println("\n\nCould not add candidate to position; position does not exist.\n");
                    } else {
                        System.out.println("\n\nAdded candidate " + candidateName + " to position " + posName + ".\n");
                    }
                case 3:
                    System.out.print("Enter name of proposition: \n");
                    propName = scanner.nextLine();
                    System.out.print("Enter description for the proposition: \n");
                    propDesc = scanner.nextLine();
                    electionHandler.addProposition(propName, propDesc);
                    System.out.println("\n\nAdded proposition " + propName + " to election.\n");
                case 4:
                    List<Position> positions = electionHandler.getPositions();
                    List<Proposition> propositions = electionHandler.getPropositions();

                    for (Position p : positions) {
                        System.out.println("\nPosition: " + p.getTitle());
                        for (Candidate c : p.getCandidates()) {
                            System.out.println("\tName: " + c.getName());
                            System.out.println("\t\tParty: " + c.getParty());
                        }
                    }

                    for (Proposition p : propositions) {
                        System.out.println("\nProposition: " + p.getName());
                        System.out.println("\t" + p.getDescription());
                    }

                    done = true;
            }
        }
        return done;
    }

    private static boolean vote() {
        
    }

    private static void checkRegistration() {

        System.out.print("\nEnter State ID number: ");
        String idNumber = scanner.nextLine();

        boolean canVote;
        /*
        TODO
        Go to DBHandler and search database for registration entry. If present,
        voter can proceed, if absent, voter can't proceed.
         */
        canVote = true;

        if (canVote) {
            System.out.println("The voter is registered and can proceed to vote.\n");
        } else {
            System.out.println("The voter is not registered to vote. They must " +
                    "register before proceeding. \n");
        }
    }

    private static void registerNewVoter() {
        String firstName;
        String middleName;
        String lastName;

        String phoneNumber;

        int birthMonth;
        int birthDate;
        int birthYear;

        String ssn;
        String legalID;

        String streetAddress;
        String city;
        String county;
        String state;
        String zip;

        System.out.print("Please enter the following information:\nFirst Name: ");
        firstName = scanner.nextLine();

        System.out.print("\nMiddle Name: ");
        middleName = scanner.nextLine();

        System.out.print("\nLast Name: ");
        lastName = scanner.nextLine();

        System.out.print("\nPhone Number w/ Area Code (1234567890): ");
        phoneNumber = scanner.nextLine();

        System.out.print("\nBirth Month (Enter number 1 through 12): ");
        birthMonth = Integer.parseInt(scanner.nextLine());

        System.out.print("\nBirth Day (Enter number 1 through 31): ");
        birthDate = Integer.parseInt(scanner.nextLine());

        System.out.print("\nBirth Year (e.g. 2016): ");
        birthYear = Integer.parseInt(scanner.nextLine());

        System.out.print("\nSocial Security Number (123456789) : ");
        ssn = scanner.nextLine();

        System.out.print("\nState ID Number: ");
        legalID = scanner.nextLine();

        System.out.print("\nStreet Address Line 1: ");
        streetAddress = scanner.nextLine();

        System.out.print("\nStreet Address Line 2: ");
        streetAddress += "\n" + scanner.nextLine();

        System.out.print("\nCity: ");
        city = scanner.nextLine();

        System.out.print("\nCounty: ");
        county = scanner.nextLine();

        System.out.print("\nState: ");
        state = scanner.nextLine();

        System.out.print("\nZip code (12345): ");
        zip = scanner.nextLine();

        RegistrationForm form = new RegistrationForm(firstName, middleName, lastName,
                phoneNumber, birthDate, birthMonth, birthYear, ssn, legalID, streetAddress,
                city, county, state, zip);

        registrationHandler.register(form);

        System.out.println("\n\nName: " + firstName + " " + middleName + " " + lastName +
                "\nPhone Number: " + phoneNumber +
                "\nBirthdate: " + birthMonth + "/" + birthDate + "/" + birthYear +
                "\nSSN: " + ssn +
                "\nState ID: " + legalID +
                "\nStreetAddress:\n" + streetAddress +
                "\nCity: " + city +
                "\nCounty: " + county +
                "\nState: " + state +
                "\nZip Code: " + zip);
        System.out.println("\nRegistered.\n\n");
    }
}
