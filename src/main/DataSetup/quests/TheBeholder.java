package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.*;

public class TheBeholder extends Quest{
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    private boolean beholderDefeated = false;
    private boolean playerParalyzed = false;
    private boolean beholderStunned = false;

    private int climbs = 0; // Number of successful climbs
    private final int CLIMBS_TO_HATCH = 5;

    public TheBeholder(Player player){
        super("the beholder", "towers underground", player);
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
        System.out.println("You enter the wizard's tower basement. Blue lights reflect off the walls, and the hatch above draws your attention.");
        System.out.println("The floor is uneven with loose stones. You sense a malevolent presence... a beholder is lurking.");

        while (!beholderDefeated && climbs < CLIMBS_TO_HATCH) {

            if (playerParalyzed) {
                System.out.println("\nYou are paralyzed and fall to the start of the basement!");
                climbs = 0;
                playerParalyzed = false;
                continue;
            }

            System.out.println("\nWhat do you do? (up, move, wait, help)");
            String action = sc.nextLine().trim().toLowerCase();

            switch (action) {
                case "up":
                    attemptClimb();
                    break;
                case "move":
                    System.out.println("You cautiously move through the basement...");
                    beholderTurn();
                    break;
                case "wait":
                    System.out.println("You wait, observing the beholder...");
                    beholderTurn();
                    break;
                case "help":
                    System.out.println("Available commands: up (climb), move (move), wait (wait)");
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }

        if (beholderDefeated) {
            System.out.println("The beholder has been petrified! The path to the hatch is clear. You can finally climb through!");
        } else if (climbs >= CLIMBS_TO_HATCH) {
            System.out.println("You successfully climb the stones and reach the hatch, escaping the beholder... for now.");
        }
    }

    private void attemptClimb() {
        System.out.println("You attempt to climb the stones towards the hatch...");

        if (!beholderStunned) {
            if (beholderTurn()) return; // attack may interrupt climb
        } else {
            System.out.println("The beholder is stunned and does nothing this turn.");
            beholderStunned = false; // stun lasts one turn
        }

        climbs++;
        System.out.println("You climb a bit higher. Steps climbed: " + climbs + "/" + CLIMBS_TO_HATCH);
    }

    private boolean beholderTurn() {
        String[] rays = {"paralysis", "petrifying", "light"};
        String chosenRay = rays[rand.nextInt(rays.length)];

        System.out.println("The beholder fires a " + chosenRay + " ray!");

        System.out.println("Type 'reflect' to attempt to deflect the ray with your mirror!");
        String input = sc.nextLine().trim().toLowerCase();
        List<String> validResponses = Arrays.asList("reflect", "mirror", "deflect");

        boolean reflected = validResponses.contains(input);

        switch (chosenRay) {
            case "paralysis":
                if (reflected) {
                    System.out.println("You deflect the paralysis ray away. The beholder is momentarily confused and does nothing next turn.");
                    beholderStunned = true;
                } else {
                    System.out.println("The paralysis ray hits you! You fall to the start of the basement.");
                    playerParalyzed = true;
                }
                break;
            case "petrifying":
                if (reflected) {
                    System.out.println("You perfectly reflect the petrifying ray back at the beholder! It is turned to stone, defeated.");
                    beholderDefeated = true;
                } else {
                    System.out.println("The petrifying ray hits you! You are instantly turned to stone. Game over.");
                    System.exit(0); // player dies
                }
                break;
            case "light":
                if (reflected) {
                    System.out.println("You reflect the light ray harmlessly. Nothing happens.");
                } else {
                    System.out.println("The light ray scans the room, revealing your position. But nothing else happens.");
                }
                break;
        }

        return reflected; // return true if player reflected (could affect other mechanics)
    }
}
