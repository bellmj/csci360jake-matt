
360Voting README

================================================================================

Table of Contents

1. Login
2. Main Screen
    2.1. Voting Machine Setup
        2.1.1. Entering Election Options
        2.1.2. The Voting Cycle
    2.2. Verify Voter Registration
    2.3. Register Voter
3. Administrative Tools
    3.1. Count Votes
    3.2. Hard Count Votes
    3.3. Display Results
4. Test Cases

================================================================================

1. Login

To start this program, run the Main class located in csci360jake-matt\src\system\ui.
Upon startup, you will be asked for a username and password. There are two sample
combinations:

    username: pollworker
    password: 1234

    (For Admin Privileges)
    username: admin
    password: 1234

================================================================================

2. Main Screen

The first screen displays the logo, and three large buttons, each of which initiate
a core function of the program. If you've signed in as an administrator, there is
also a smaller button with an arrow, which will pull up the administrative tools.
This system is intended to be used in two parts: the main program, which is open on
a pollworker's computer at the entrance to the precinct, and as a "polling machine",
which is set up to accept votes. The pollworker can use this program to register new
voters, or to check the registration status of existing voters.

    2.1. Voting Machine Setup

    A polling machine is locked into a cycle of voting, to support multiple voters
    using it one after the other.

        2.1.1. Entering Election Options

        In order to set up a polling machine, the pollworker must first specify the
        election. In this context, an election is defined as all of the positions up for
        election, all of the candidates up for those positions, and any public opinion
        surveys that may be applicable to the voting district. The window in which the
        election is defined contains two ways that the pollworker can define an election:

            1: By hand

                The pollworker can manually enter each position, candidate, and public
                opinion survey using the buttons at the bottom of the window (public
                opinion surveys are accessible by the "Public Opinion Surveys" tab at
                the top of the window, and have their own set of buttons).

            2: By loading a .elec file

                If the pollworker has been given a .elec file, then the user may select
                it by pressing the "Load .elec File" button, and the election will be
                loaded into the window. At this point, the pollworker can make any
                changes that are necessary to tailor the election to that voting
                district. However, if any changes are made, another .elec file will need
                to be saved by pressing the "Save .elec File" button. The same .elec file
                used at each polling machine must be used when counting the votes.

        Once the election is entered, the pollworker can press "Done", and the program
        will show the polling machine. The pollworker should take caution, as once the
        polling machine is shown, the main menu cannot be accessed, and any ballots that
        were stored beforehand will be cleared. This means that the polling machine
        should stay active until voting completes.

        2.1.2. The Voting Cycle

        The first thing that will be seen in the polling machine is a sort of home screen,
        which is the resting state of the polling machine between voters. Only the
        pollworker should interact with this screen. There are two buttons, "Vote" and
        "Exit". "Exit" will exit the polling machine, and "Vote" will start the voting
        process for one voter. After "Vote" is pressed, the pollworker will have to enter
        the voter's hashed voter ID (obtained by checking their registration status). It
        is not necessary for the hashed voter ID to be correct, but highly recommended, as
        it allows for multiple votes by the same person to be detected. For this reason,
        the pollworker should take care to always be the one to enter in the ID, and to
        enter it in accurately.

        Once voting has commenced, the voter will be shown a series of screens that detail
        their options for each position up for election and for each public opinion survey.
        For a position, a list of candidates will be shown, along with the options
        "Write-in" and "Abstain". If the voter selects "Write-in", they will be asked for
        their candidate's name and party, neither of which can be empty. The party is
        inconsequential, as only the name will be stored in their ballot. It only serves
        as a comparison for that voter when looking at the other candidates. If the voter
        continues without selecting an option, then their selection will default to
        "Abstain".

        For each public opinion survey, the voter will be shown the name of the proposition,
        a short description of it, and three buttons: "FOR", "AGAINST", and "Abstain". Like
        in the case of a position, if the voter continues without selecting an option, it
        will default to "Abstain".

        After all of the choices have been exhausted, the voter will be shown a list of
        their choices and asked to confirm. By pressing "Previous", the voter can go back
        through the options and make whatever changes they like. By pressing "Finish", the
        voter will cast their ballot, and the polling machine will reset back to the home
        screen.

    2.2. Verify Voter Registration

    In this window, the pollworker can enter a voter's state-issued ID number to determine
    whether they are registered, and subsequently able to vote. If the voter is not
    registered, then the pollworker will have to register the voter before they are allowed
    to proceed to a polling machine. If the voter is registered already, the pollworker is
    provided with a sequence of numbers that are the voter's hashed state ID number. This
    will need to be entered for the voter at the polling machine, and will be written to
    the ballot to verify that the ballot was cast by a unique voter.

    2.3. Register Voter

    A form is provided for the pollworker to enter the prospective voter's information. Once
    the information is valid and submitted, the voter is registered. The pollworker will
    need to check the registration again in order to obtain the voter's hashed state ID.

================================================================================

3. Administrative Tools

If an administrator has logged in, then they will have access to this screen via the small
arrow button on the main screen. Like the main screen, there are three large buttons each
pertaining to a function of the program, accompanied by a smalled button leading back to the
main screen.

    3.1. Count Votes

    The administrator can count the currently stored ballots by specifying the election for
    which the ballots were cast with the .elec file that the pollworker saved when setting
    up the polling machine.

    3.2. Hard Count Votes

    Each selection in each ballot is printed to the console for a long, tedious recount. In
    a physical implementation, the ballots would be printed by an actual printer onto paper.

    3.3. Display Results

    The administrator can pull up a window that displays the election results in a readable
    format.

================================================================================

4. Test Cases

An election has been provided in system/resources, called testElection.elec, to save time
when setting up the polling machine. Remember that if any changes are made, a new .elec file
will have to be saved in order to count the ballots. Ballots are stored in
system/db/.datastore, but they are encrypted.

One hundred voters with random names, addresses, and other information have been generated
and registered. Registered voters are stored in system/db/.registration, and are also
encrypted. The state ID numbers of all 100 voters are stored in system/resources/dlNums.txt,
but for convenience in entering them into the Check Registration window, some working and
non-working ID numbers are listed below:

    Are registered:
    62068475
    28654417
    73272490
    69911822
    98233559

    Are not registered:
    35918643
    81649573
    25876431
    36914725
    78945612

