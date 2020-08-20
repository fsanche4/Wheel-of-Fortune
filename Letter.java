/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package woffortune;

/**
 * Class that holds letters for a wheel of fortune game
 * @author clatulip Fernanda Sanchez
 */
public class Letter {
    private char letter; // the letter
    private boolean guessed = false; // whether or not it has been guessed
    private boolean capitalized = false; // whether it is a capital letter
    private boolean space = false; // whether it is a space

    /**
     * Constructor - builds the Letter object
     * If letter is special character, we mark it as guessed 
    * so that it shows.
     * @param char letter 
     */
    public Letter(char letter) {
        this.letter = letter;
        // find out if this is a space
        if (letter == ' ') {
            space = true;
            guessed = true;
        } else if (!(Character.isAlphabetic(letter))) {
            guessed = true; // this makes punctuation show, and not part of puzzle
        } else {
            // check if capitalized
            if (Character.isUpperCase(letter)) {
                capitalized = true;
                letter = Character.toLowerCase(letter);
            }
        }
    }
    
    /**
     * Getter
     * @return char letter
     */
    public char getLetter() {
        return letter;
    }
    
    /**
     * Getter
     * @return boolean capitalized
     */
    public boolean isCap() {
        return capitalized;
    }
    
    /**
     * Getter
     * @return boolean space 
     */
    public boolean isSpace() {
        return space;
    }
    
    /**
     * Setter - sets guessed to true 
    */
    public void setFound() {
        guessed = true;
    }
    
    /**
     * Getter
     * @return boolean guessed 
     */
    public boolean isFound() {
        return guessed;
    }
    
}
