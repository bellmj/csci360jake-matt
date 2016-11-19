package System.UI;

import System.Election.Candidate;
import System.Election.ElectionHandler;
import System.Election.Position;
import System.Election.Proposition;
import System.Election.Voting.BallotHandler;
import System.Registration.RegistrationForm;
import System.Registration.RegistrationHandler;
import System.DB.FileBallotHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestMain {

    public static Scanner scanner;
    public static RegistrationHandler registrationHandler;
    public static BallotHandler ballotHandler;
    public static ElectionHandler electionHandler;
    public static FileBallotHandler dbHandler;

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

        dbHandler = new FileBallotHandler();
        registrationHandler = new RegistrationHandler();
        ballotHandler = new BallotHandler(dbHandler);
        electionHandler = new ElectionHandler();

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
        System.out.print("\n\nWARNING: Once the program is set up as a voting \n" +
                "machine, you will be unable to return to the menu unless \n" +
                "you restart the software. Are you sure you want to continue? (Y/N): ");
        return scanner.nextLine().toLowerCase().equals("y");
    }

    private static void setUpVotingMachine() {
        boolean done = false;
        int method;
        String posName;
        String propName;
        String propDesc;
        String candidateName;
        String candidateParty;

        electionHandler.createNewElection();

        while (!done) {
            System.out.print("1. Add Position\n" +
                    "2. Add Candidate to Position\n" +
                    "3. Add Proposition\n" +
                    "4. Confirm Election\n" +
                    "Please select an option from above:");

            method = scanner.nextInt();
            scanner.nextLine();

            System.out.println("");

            switch (method) {
                case 1:
                    System.out.print("Enter title of position: ");
                    posName = scanner.nextLine();
                    electionHandler.addPosition(posName);
                    System.out.println("\n\nAdded position " + posName + " to election.\n");
                    break;
                case 2:
                    for (Position p : electionHandler.getPositions()) {
                        System.out.println(p.getTitle());
                    }
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
                    break;
                case 3:
                    System.out.print("Enter name of proposition: ");
                    propName = scanner.nextLine();
                    System.out.print("\nEnter description for the proposition: ");
                    propDesc = scanner.nextLine();
                    electionHandler.addProposition(propName, propDesc);
                    System.out.println("\n\nAdded proposition " + propName + " to election.\n");
                    break;
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
                    break;
                default:
                    break;
            }
        }
    }

    private static boolean vote() {
        boolean done = false;
        boolean verified;
        int method;
        String idNumber;
        int number;
        String candidateName;
        String candidateParty;

        List<Position> positions = electionHandler.getPositions();
        List<Proposition> propositions = electionHandler.getPropositions();

        System.out.print("1. Vote\n" +
                "2.Shutdown\n" +
                "Please select an option from above: ");
        method = scanner.nextInt();
        scanner.nextLine();

        if (method == 2) {
            done = true;
        } else if (method == 1) {
            verified = false;
            System.out.print("\nEnter hashed Voter ID number: ");
            idNumber = scanner.nextLine();
            ballotHandler.createBallot(idNumber);
            while (!verified) {
                System.out.println("For the following positions, you will be shown\n" +
                        "candidates, listed by number in no particular order.\n" +
                        "To make your selection, enter the number of the\n" +
                        "candidate you wish to vote for.\n" +
                        "If you wish to vote for a candidate not listed, enter the\n" +
                        "number 0.\n" +
                        "To abstain, enter the number 00.\n");

                // Vote for Positions
                for (Position p : positions) {
                    number = 1;
                    System.out.println("Position: " + p.getTitle());
                    for (Candidate c : p.getCandidates()) {
                        System.out.println("\t" + number + ". " + c.getName());
                        System.out.println("\t\t- " + c.getParty());
                        number++;
                    }
                    System.out.println("\t0. Write in candidate");
                    System.out.println("\t-1. Abstain");
                    System.out.print("Selection: ");

                    method = scanner.nextInt();
                    scanner.nextLine();

                    if (method > 0) {
                        ballotHandler.selectCandidate(p, p.getCandidates().get(method - 1));
                    } else if (method == 0) {
                        System.out.print("\nEnter candidate name: ");
                        candidateName = scanner.nextLine();
                        System.out.print("\nEnter candidate party: ");
                        candidateParty = scanner.nextLine();
                        ballotHandler.selectCandidate(p, electionHandler.addWriteInCandidateToPosition(candidateName, candidateParty, p));
                    } else {
                        ballotHandler.selectCandidate(p, null);
                    }
                }

                System.out.println("For the following public opinion surveys, you\n" +
                        "will be shown a brief description of what each\n" +
                        "proposition entails. You can cast your support\n" +
                        "for each proposition by entering either 1 or 2.\n" +
                        "To abstain, please enter the number 0.\n");

                // Vote for Propositions
                for (Proposition p : propositions) {
                    System.out.println("\nProposition: " + p.getName());
                    System.out.println("\t" + p.getDescription());
                    System.out.println("1: For");
                    System.out.println("2: Against");
                    System.out.println("0: Abstain");
                    System.out.print("Selection: ");

                    method = scanner.nextInt();
                    scanner.nextInt();

                    switch (method) {
                        case 0:
                            ballotHandler.addProposition(p, null);
                            break;
                        case 1:
                            ballotHandler.addProposition(p, true);
                            break;
                        case 2:
                            ballotHandler.addProposition(p, false);
                    }
                }

                HashMap<Position, Candidate> ballotCandidates = ballotHandler.getCandidateSelections();
                HashMap<Proposition, Boolean> ballotPropositions = ballotHandler.getPropositionSelections();

                System.out.println("Selections:");

                for (Map.Entry<Position, Candidate> s : ballotCandidates.entrySet()) {
                    System.out.println(s.getKey().getTitle() + ": " +
                            (s.getValue() == null ? "Abstain" : s.getValue().getName()));
                }

                for (Map.Entry<Proposition, Boolean> s : ballotPropositions.entrySet()) {
                    System.out.println("\n" + s.getKey().getName() + ": " +
                            (s.getValue() == null ? "Abstain" : s.getValue().toString()));
                }

                System.out.println("Are your selections above correct? (Y/N): ");

                switch (scanner.nextLine().trim().toLowerCase()) {
                    case "y":
                        ballotHandler.clearSelections();
                        break;
                    case "n":
                        ballotHandler.saveBallot();
                        verified = true;
                        break;
                }
            }

            System.out.println("Your ballot has been cast. Thank you for performing your civic duty!\n\n");
        }
        return done;
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
                phoneNumber, birthDate, birthMonth, birthYear, legalID, streetAddress,
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
