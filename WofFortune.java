/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woffortune;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Execution Harness for Wheel of Fortune Game Creates wheel, creates game
 * instance, loops through rounds
 *
 * @author clatulip Fernanda Sanchez
 */
public class WofFortune {

    public static void main(String[] args) throws InterruptedException {
        int round = 1;

        //Try catch to see if there are any other possible errors
        try {
            boolean keepPlaying = true;
            Wheel wheel = new Wheel();
            Scanner sc = new Scanner(System.in);

            System.out.println("Welcome to the Wheel of Fortune!");
            WofFortuneGame game = new WofFortuneGame(wheel);

            while (keepPlaying) {
                System.out.println("***********************");
                System.out.println("      ROUND " + round);
                System.out.println("***********************");
                System.out.println();
                game.playGame();

                // game played and ended, see if they want to play another round
                System.out.println();
                System.out.println("Would you like to play again?");
                char ans = sc.next().charAt(0);
                if ((ans == 'y') || (ans == 'Y')) {
                    // play again
                    round += 1;
                    game.reset();
                } else {
                    // don't play again
                    keepPlaying = false;
                }
            }
        } catch (Exception general) {
            System.out.println("Error: Exception thrown");
        }

        System.out.println("Thank-you for playing the Wheel of Fortune!");

    }
}
