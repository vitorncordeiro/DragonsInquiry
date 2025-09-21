package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;
import java.util.Scanner;

public class PentagramPuzzle extends Quest {

    public PentagramPuzzle(Player player) {
        super("Pentagram Puzzle", "treasure chamber", player);
    }

    @Override
    public void startQuest() {
        Scanner sc = new Scanner(System.in);

        System.out.println("""
                You enter a dimly lit treasure chamber. 
                At the center, a pedestal carved in the shape of a serpent's head awaits you.
                Its eyes glow faintly, and an ancient inscription is engraved on its surface:
                
                "The serpent coils in the root of the world, hidden in the southwest.
                It climbs toward the crown above,
                then slithers down into the abyss of the southeast.
                It winds its body across the western stone,
                strikes toward the dawn in the east,
                and returns to the dark coil from whence it came."
                """);

        System.out.println("\nThe riddle seems to describe a pattern... perhaps a symbol.");
        System.out.println("What word will you speak to the pedestal?");

        boolean solved = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (!solved) {
            String answer = sc.nextLine().trim().toLowerCase();
            attempts++;

            if (answer.equals("star")) {
                solved = true;
                System.out.println("\nThe serpent’s eyes shine brightly!");
                System.out.println("A glowing star pattern appears across the chamber floor.");
                System.out.println("The pedestal clicks, releasing a hand mirror into your grasp.");
                System.out.println("At the same time, a hidden passage opens to the north...");
                Quest.completeQuest(PentagramPuzzle.class);
            } else {
                if (attempts >= MAX_ATTEMPTS) {
                    System.out.println("\nThe ground trembles violently...");
                    System.out.println("The serpent hisses as the chamber collapses around you!");
                    System.out.println("-- GAME OVER --");
                    System.exit(0);
                } else {
                    System.out.println("The serpent’s eyes flare red. That word is wrong...");
                    System.out.println("Try again.");
                }
            }
        }
    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return null;
    }
}
