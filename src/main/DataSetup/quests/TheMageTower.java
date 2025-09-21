package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;
import java.util.Scanner;

public class TheMageTower extends Quest{

    public TheMageTower(Player player){
        super("the mage tower", "towers underground", player);
    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return null;
    }
    private Scanner sc = new Scanner(System.in);
    private boolean reachedTop = false;
    private boolean talkedToWizard = false;
    private boolean stoleItem = false;
    private boolean questAccepted = false;

    @Override
    public void startQuest() {
        System.out.println("You climb through the hatch and find yourself at the entrance of the wizard’s tower.");
        System.out.println("In front of you, a long staircase spirals upward into the shadows...");

        while (!questAccepted) {
            if (!reachedTop) {
                String action = sc.nextLine().trim().toLowerCase();

                switch (action) {
                    case "stairs":
                    case "up":
                        climbStairs();
                        break;
                    case "look":
                        System.out.println("The entrance hall is quiet. Dust covers the floor, and the only way forward is up the staircase.");
                        break;
                    case "wait":
                        System.out.println("You wait, but nothing changes...");
                        break;
                    default:
                        System.out.println("Invalid command. Try again.");
                }
            } else {
                wizardRoom();
            }
        }
    }

    private void climbStairs() {
        System.out.println("You begin climbing the tall, spiraling staircase. The air grows heavy with the scent of old tomes and incense...");
        System.out.println("After a long climb, you reach a grand chamber — the wizard’s study.");
        reachedTop = true;
    }

    private void wizardRoom() {
        System.out.println("\nYou are inside the wizard’s chamber.");
        if (!talkedToWizard) {
            System.out.println("The room is filled with shelves of ancient books, glowing artifacts, and mysterious potions.");
            System.out.println("In the corner of the room, you see a giant sleeping griffon");
            System.out.println("A tall wizard stands before a large desk, watching you with a piercing gaze.");
        }
        String action = sc.nextLine().trim().toLowerCase();

        switch (action) {
            case "talk":
                talkToWizard();
                break;
            case "steal":
                stealItem();
                break;
            case "look":
                System.out.println("You see magical scrolls, shimmering crystals, and a staff resting against the desk.");
                System.out.println("In the corner of the room, you see a giant sleeping griffon");
                System.out.println("The wizard clearly treasures these artifacts.");
                break;
            default:
                System.out.println("The wizard narrows his eyes, clearly unimpressed by your indecision.");
        }
    }

    private void talkToWizard() {
        if (!talkedToWizard) {
            System.out.println("You approach the wizard. His deep voice fills the chamber:");
            System.out.println("It looks like you got pass all the tower protections, well done.");
            System.out.println("\"I know why you are here. The curse that binds you is strong...\"");
            System.out.println("\"But I can help you." +
                    "For that, I need you to look for an ingredient that I don't have with me.\"");
            System.out.println("The wizard leans closer, his eyes glowing faintly.");
            System.out.println("\"Bring me the liver of the dragon that dwells atop the northern mountain.\"");
            System.out.println("Hitch a ride with the griffon.");
            talkedToWizard = true;
        } else {
            System.out.println("Wizard: \"The dragon’s liver. That is the price of your cure. Do not waste time.\"");
        }

        System.out.println("\nYou have no choice but to accept this quest.");
        questAccepted = true;
    }

    private void stealItem() {
        if (!stoleItem) {
            System.out.println("You reach for a glowing crystal on the shelf...");
            System.out.println("The wizard slams his staff on the ground. Thunder echoes in the chamber.");
            System.out.println("\"FOOLISH MORTAL! Touch my belongings again, and you shall regret it!\"");
            stoleItem = true;
        } else {
            System.out.println("The wizard glares at you, his eyes burning with anger. Best not try that again.");
        }
    }
}
