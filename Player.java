/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woffortune;

import java.util.ArrayList;

/**
 * Class that defines a player for a game with monetary winnings and a limited
 * number of choices
 *Ï
 * @author clatulip Fernanda Sanchez
 */
public class Player {

    private int winnings = 0; // amount of money won
    private String name; // player's name
    private int numGuesses = 0; // number of times they've tried to solve puzzle
    public ArrayList<String> prizesWon = new ArrayList<String>();

    /**
     * Constructor
     *
     * @param name String that is the player's name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Getter
     *
     * @return int amount of money won so far
     */
    public int getWinnings() {
        return winnings;
    }

    /**
     * Adds amount to the player's winnings
     *
     * @param amount int money to add
     */
    public void incrementScore(int amount) {
        if (amount < 0) {
            return;
        }
        this.winnings += amount;
    }

    /**
     * Getter
     *
     * @return String player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     *
     * @return int the number of guesses used up
     */
    public int getNumGuesses() {
        return numGuesses;
    }

    /**
     * Add 1 to the number of guesses used up
     */
    public void incrementNumGuesses() {
        this.numGuesses++;
    }

    /**
     * Resets for a new game (only number of guesses) This does not reset the
     * winnings.
     */
    public void reset() {
        this.numGuesses = 0;
    }

    public void bankrupt() {
        this.winnings = 0;
        prizesWon.clear();
        
    }

    /**
     * Method to add/hold all the prizes the player has won  
     * @param prize 
     */
    public void allPrizes(String prize) {
        prizesWon.add(prize);
    }

}
