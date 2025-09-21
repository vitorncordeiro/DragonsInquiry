package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.quests.Quest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GemsChamber extends Quest {
    public static boolean isComplete = false;
    private final Scanner sc = new Scanner(System.in);

    public GemsChamber(Player player) {
        super("Gems Chamber", "caves entry", player);
    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return null;
    }

    @Override
    public void startQuest() {
        if (this.isCompleted()) {
            return;
        }

        System.out.println("You step into a dark cavern. The air is damp, and you can barely see.");
        boolean lampUsed = false;

        while (!lampUsed) {
            System.out.println("\nChoose an action:");
            System.out.println("[1] Turn on a lamp");
            System.out.println("[2] Walk blindly into the darkness");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    lampUsed = true;
                    System.out.println("The lamp flickers to life, illuminating the cavern around you.");
                    break;
                case "2":
                    System.out.println("You stumble blindly, slip on the wet ground, and realize you need some light.");
                    break;
                default:
                    System.out.println("That’s not a valid choice.");
            }
        }

        System.out.println("\nAt the center of the cavern, you see a pedestal with an ancient inscription.");
        System.out.println("Above it rest five shining gems: iridium, opal, fuchsite, apatite, and topaz.");

        boolean continueExploring = true;

        while (continueExploring) {
            System.out.println("\nChoose an action:");
            System.out.println("[1] Put the gems in your pockets");
            System.out.println("[2] Examine the inscription");
            System.out.println("[3] Try solving the puzzle");
            System.out.println("[4] Leave the chamber");

            String decision = sc.nextLine().trim();

            switch (decision) {
                case "1":
                    System.out.println("The gems you stuffed into your pockets vibrate violently and begin to glow!");
                    System.out.println("They explode in a devastating chain reaction, collapsing the cave...");
                    System.out.println("\n--- GAME OVER ---");
                    System.exit(0);
                    break;

                case "2":
                    getInscriptionText();
                    break;

                case "3":
                    solvePuzzle();
                    continueExploring = false;
                    break;

                case "4":
                    System.out.println("You decide to leave the chamber, the mystery of the gems unsolved.");
                    return;

                default:
                    System.out.println("That’s not a valid choice.");
            }
        }
    }

    private void solvePuzzle() {
        HashMap<Integer, String> correctAnswer = new HashMap<>(Map.of(
                1, "fuchsite",
                2, "opal",
                3, "topaz",
                4, "iridium",
                5, "apatite"
        ));

        HashMap<Integer, String> playerAnswer = new HashMap<>();
        boolean firstAttempt = true;

        while (!playerAnswer.equals(correctAnswer)) {
            playerAnswer.clear();

            if (!firstAttempt) {
                System.out.println("The gems glow for a brief moment, then fade. The combination is wrong.");
                System.out.println("You should try another order...");
            }

            System.out.println("\nEnter the names of the gems in the order you place them (5 total):");
            for (int i = 1; i <= 5; i++) {
                System.out.print(i + ") ");
                String gem = sc.nextLine().trim().toLowerCase();
                playerAnswer.put(i, gem);
            }

            firstAttempt = false;
        }

        System.out.println("""
                As the last stone clicks into place, the pedestal vibrates and the cavern shakes.
                Slowly, a hidden door slides open to the west, revealing a narrow corridor with an ancient staircase.
                """);

        Quest.completeQuest(GemsChamber.class);
    }

    private void getInscriptionText() {
        System.out.println("\nThe inscription says:");
        System.out.println("\"Mono i fotia tha sas voithisei. Topothetiste tis petres me ti sosti seira kai to fos tha sas kathodigisei.\"");
        System.out.println("The word 'fotia' (fire) catches your attention.");
    }
}
