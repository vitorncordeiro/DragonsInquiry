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
    private boolean canReflect = true; // Track if player can reflect this turn

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
                canReflect = true;
                continue;
            }

            System.out.println("\nWhat will you do?");
            System.out.println("[1] Climb the stones");
            System.out.println("[2] Move cautiously");
            if (canReflect) {
                System.out.println("[3] Reflect the beholder's ray");
            }

            int choice = getChoice();

            switch (choice) {
                case 1:
                    attemptClimb();
                    canReflect = true; // climbing does not restrict reflection next turn
                    break;
                case 2:
                    System.out.println("You cautiously move through the basement...");
                    beholderTurn();
                    canReflect = true; // moving does not restrict reflection next turn
                    break;
                case 3:
                    if (canReflect) {
                        boolean reflected = beholderTurn(true);
                        if (reflected) {
                            canReflect = false; // cannot reflect next turn
                        }
                    } else {
                        System.out.println("You cannot reflect again so soon. Choose another action.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        if (beholderDefeated) {
            System.out.println("The beholder has been petrified! The path to the hatch is clear. You can finally climb through!");
            Quest quest = new TheMageTower(getPlayer());
            quest.startQuest();
        } else if (climbs >= CLIMBS_TO_HATCH) {
            System.out.println("You successfully climb the stones and reach the hatch, escaping the beholder... for now.");
            Quest quest = new TheMageTower(getPlayer());
            quest.startQuest();
        }
    }

    private int getChoice() {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                int choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Enter a number corresponding to your choice.");
            }
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

    // Overloaded: forcedReflect=false for normal turn, true when player chooses reflect
    private boolean beholderTurn() {
        return beholderTurn(false);
    }

    private boolean beholderTurn(boolean forcedReflect) {
        String[] rays = {"paralysis", "petrifying", "light"};
        String chosenRay = rays[rand.nextInt(rays.length)];

        System.out.println("The beholder fires a " + chosenRay + " ray!");

        boolean reflected = false;

        if (forcedReflect) {
            System.out.println("You attempt to reflect the ray with your mirror!");
            reflected = true;
        } else {
            System.out.println("Type 'reflect' if you want to try deflecting the ray!");
            String input = sc.nextLine().trim().toLowerCase();
            List<String> validResponses = Arrays.asList("reflect", "mirror", "deflect");
            reflected = validResponses.contains(input);
        }

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

        return reflected;
    }
}
