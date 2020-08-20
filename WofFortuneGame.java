/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woffortune;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * WofFortuneGame class Contains all logistics to run the game
 *
 * @author clatulip Fernanda Sanchez
 */
public class WofFortuneGame {

    private boolean puzzleSolved = false;

    private Wheel wheel;
    private Player player1;
    private String phrase = "Once upon a time";
    //private Letter[] letter_array = new Letter[16];
    private ArrayList<Letter> letterPhrase = new ArrayList<Letter>();
    private ArrayList<String> newPhrases = new ArrayList<String>();
    private ArrayList<Player> allPlayers = new ArrayList<Player>();
    private ArrayList<String> possiblePrizes = new ArrayList<String>();

    /**
     * Constructor
     *
     * @param wheel Wheel
     * @throws InterruptedException
     */
    public WofFortuneGame(Wheel wheel) throws InterruptedException {
        // get the wheel
        this.wheel = wheel;

        // do all the initialization for the game
        setUpGame();

    }

    /**
     * Plays the game
     *
     * @throws InterruptedException
     */
    public void playGame() throws InterruptedException {
        // while the puzzle isn't solved, keep going
        while (!puzzleSolved) {
            for (int i = 0; i < allPlayers.size(); i++) {
                // let the current player play
                playTurn(allPlayers.get(i));
            }
        }
    }

    /**
     * Sets up all necessary information to run the game
     */
    private void setUpGame() {
        addPhrases();

        //create new scanner object and use scanner constructor
        Scanner scan = new Scanner(System.in);

        System.out.println("How many players are there?");
        try {

            int numPlayers = scan.nextInt();

            for (int i = 0; i < numPlayers; i++) {
                Scanner playerName = new Scanner(System.in);
                System.out.println("Enter player name");
                String player = playerName.nextLine();
                allPlayers.add(new Player(player));

            }
        } catch (InputMismatchException ime) {
            System.out.println("Error: Input mismatch thrown");
        }

        // print out the rules
        System.out.println("RULES!");
        System.out.println("Each player gets to spin the wheel, to get a number value");
        System.out.println("Each player then gets to guess a letter. If that letter is in the phrase, ");
        System.out.println(" the player will get the amount from the wheel for each occurence of the letter");
        System.out.println("If you have found a letter, you will also get a chance to guess at the phrase");
        System.out.println("Each player only has three guesses, once you have used up your three guesses, ");
        System.out.println("you can still guess letters, but no longer solve the puzzle.");
        System.out.println();

        //Ask player if they want to enter their own phrase
        System.out.println("Do you want to enter your own phrase? Y or N");

        //Use try catch block to make sure player inputs a letter rather than a number 
        try {
            //Create char object to use to scan the players response
            char answer = scan.next().charAt(0);

            //If statement to see when player wants to input their own phrase
            //both capital and lowercase answers are accepted
            if ((answer == 'y') || (answer == 'Y')) {
                System.out.println("Enter phrase");

                //Create new scanner object and use scanner constructor for player's phrase
                Scanner nP = new Scanner(System.in);

                //Replaces orig phrase with new phrase and prints the line
                this.phrase = nP.nextLine();

                //If player chooses n, randomly choose from one of the 10 given phrases in "addPhrases"
            } else {
                Random pickPhrase = new Random();
                this.phrase = newPhrases.get(pickPhrase.nextInt(newPhrases.size()));
            }
            // for each character in the phrase, create a letter and add to letters array
            for (int i = 0; i < phrase.length(); i++) {
                letterPhrase.add(new Letter(phrase.charAt(i)));
            }
            // setup done
        } catch (Exception general) {
            System.out.println("Error: Exception thrown");
        }

    }

    /**
     * One player's turn in the game Spin wheel, pick a letter, choose to solve
     * puzzle if letter found
     *
     * @param player
     * @throws InterruptedException
     */
    private void playTurn(Player player) throws InterruptedException {
        int money = 0;

        String temp = null;
        //Try catch block to stop thread if the thread is interrupted by player
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println(player.getName() + ", you have $" + player.getWinnings());
            System.out.println("Spin the wheel! <press enter>");
            sc.nextLine();
            System.out.println("<SPINNING>");
            Thread.sleep(200);
            Wheel.WedgeType type = wheel.spin();
            System.out.print("The wheel landed on: ");
            switch (type) {
                case MONEY:
                    money = wheel.getAmount();
                    System.out.println("$" + money);
                    break;

                case LOSE_TURN:
                    System.out.println("LOSE A TURN");
                    System.out.println("So sorry, you lose a turn.");
                    return; // doesn't get to guess letter

                case BANKRUPT:
                    System.out.println("BANKRUPT");
                    player.bankrupt();
                    player.prizesWon.clear();
                    return; // doesn't get to guess letter
                //Add case for new prizes
                case PRIZES:
                    //Call addPrizes method to randomly select 1 of the 10
                    addPrizes();
                    int pI = (int) (Math.random() * possiblePrizes.size());
                    System.out.println("Your Prize is: " + possiblePrizes.get(pI));
                    //Insert prize won into a temporary holding String for the player who won the prize
                    temp = possiblePrizes.get(pI);
                    break;

                default:

            }
            System.out.println("");
            System.out.println("Here is the puzzle:");
            showPuzzle();
            System.out.println();
            System.out.println(player.getName() + ", please guess a letter.");
            //String guess = sc.next();

            char letter = sc.next().charAt(0);
            if (!Character.isAlphabetic(letter)) {
                System.out.println("Sorry, but only alphabetic characters are allowed. You lose your turn.");
            } else {
                // search for letter to see if it is in
                int numFound = 0;
                for (Letter l : letterPhrase) {
                    if ((l.getLetter() == letter) || (l.getLetter() == Character.toUpperCase(letter))) {
                        l.setFound();
                        numFound += 1;
                    }
                }
                if (numFound == 0) {
                    System.out.println("Sorry, but there are no " + letter + "'s.");
                } else {
                    if (numFound == 1) {
                        System.out.println("Congrats! There is 1 letter " + letter + ":");
                    } else {
                        System.out.println("Congrats! There are " + numFound + " letter " + letter + "'s:");
                    }
                    System.out.println();
                    showPuzzle();
                    System.out.println();
                    
                    //Gather the amount of money and prizes won from each player and print what was won
                    if (type == Wheel.WedgeType.MONEY) {
                        player.incrementScore(numFound * money);
                        System.out.println("You earned $" + (numFound * money) + ", and you now have: $" + player.getWinnings());
                    } else if (type == Wheel.WedgeType.PRIZES) {
                        //Get the temporary String to insert the prize won and print out what is it 
                        player.allPrizes(temp);
                        System.out.println("You earned a " + temp);
                    }
                    //Try catch block to catch if player inputs anything other then 'Y/N'
                    try {
                        System.out.println("Would you like to try to solve the puzzle? (Y/N)");
                        letter = sc.next().charAt(0);
                        System.out.println();
                        if ((letter == 'Y') || (letter == 'y')) {
                            solvePuzzleAttempt(player);
                        }
                    } catch (Exception general) {
                        System.out.println("Error: Exception thrown");
                    }

                }

            }
        } catch (InterruptedException ie) {
            System.out.println("Error: Interrupted exception thrown");
        }
    }

    /**
     * Logic for when user tries to solve the puzzle
     *
     * @param player
     */
    private void solvePuzzleAttempt(Player player) {

        if (player.getNumGuesses() >= 3) {
            System.out.println("Sorry, but you have used up all your guesses.");
            return;
        }

        player.incrementNumGuesses();
        System.out.println("What is your solution?");
        try {
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n");
            String guess = sc.next();
            if (guess.compareToIgnoreCase(phrase) == 0) {
                System.out.println("Congratulations! You guessed it!");
                puzzleSolved = true;
                // Round is over. Write message with final stats
                // TODO
                //Go through all the players determine who won and get/give their name
                //Get the earnings for the players from the allPLayers arraylist
                for (int i = 0; i < allPlayers.size(); i++) {
                    System.out.println("The winner is:" + allPlayers.get(i).getName());
                    System.out.println("Your earnings are:" + allPlayers.get(i).getWinnings());
                    //Use new index to get the prizes for the player from previous for loop
                    //Create new player nP for to exhibit prizes won from the player in previous for loop
                    for (int j = 0; j < allPlayers.get(i).prizesWon.size(); j++) {
                        Player nP = allPlayers.get(i);
                        System.out.println(nP.getName() + "'s prizes are:");
                        System.out.println(nP.prizesWon.get(j));
                    }
                }
            } else {
                System.out.println("Sorry, but that is not correct.");
            }
        } catch (Exception general) {
            System.out.println("Error: Excpetion thrown");
        }

    }

    /**
     * Display the puzzle on the console
     */
    private void showPuzzle() {
        System.out.print("\t\t");
        for (Letter l : letterPhrase) {
            if (l.isSpace()) {
                System.out.print("   ");
            } else {
                if (l.isFound()) {
                    System.out.print(Character.toUpperCase(l.getLetter()) + " ");
                } else {
                    System.out.print(" _ ");
                }
            }
        }
        System.out.println();

    }

    /**
     * For a new game reset player's number of guesses to 0
     */
    public void reset() {
        player1.reset();
    }

    /**
     * Method to add own phrases when player doesn't wish to create a phrase
     */
    public void addPhrases() {
        newPhrases.add("I'm a pusher");
        newPhrases.add("Goldmember");
        newPhrases.add("I can't think of anymore");
        newPhrases.add("Firmly grasp it.");
        newPhrases.add("Ravioli Ravioli ");
        newPhrases.add("Lol");
        newPhrases.add("Shrek");
        newPhrases.add("Badabing Badaboom");
        newPhrases.add("As if!");
        newPhrases.add("Fugget about it");
    }

    /**
     * Method to hold all possible different prizes when the player lands on a
     * prize wedge
     */
    public void addPrizes() {
        possiblePrizes.add("Brand New CAR!!");
        possiblePrizes.add("Treadmill");
        possiblePrizes.add("Cruise around the world");
        possiblePrizes.add("Pet Hamster");
        possiblePrizes.add("a left reebok shoe");
        possiblePrizes.add("lifetime supply of nail cutters");
        possiblePrizes.add("a jar of air containing the plague");
        possiblePrizes.add("Your dream house");
        possiblePrizes.add("Toy dino");
        possiblePrizes.add("DVD set of all Avatar: The Last AirBender seasons");
        possiblePrizes.add("Free Chic-Fil-A for an hour");
        possiblePrizes.add("3 really big, soft blankets");
        possiblePrizes.add("The Iphone 4");
        possiblePrizes.add("Trip to the state you least want to visit");
        possiblePrizes.add("A bag of fake gold");
        possiblePrizes.add("3 inch TV");
    }

}
