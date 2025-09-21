package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.entities.items.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClimbTheTree extends Quest {
    Scanner sc = new Scanner(System.in);

    public ClimbTheTree(Player player) {
        super("main.DataSetup.quests.ClimbTheTree", "camping", player);
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return null;
    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    public void startQuest() {
        if (this.isCompleted()) {
            return;
        }

        System.out.println("Around the camp, there are many trees, but one in particular catches");
        System.out.println("your attention due to a minute detail: a nest at the top of it.");
        System.out.println("While you observe the nest, you hear the wing-beat of something in direction of the nest: ");
        System.out.println("It's a majestic griffin, with what appears to be a piece of paper in its beak.");
        System.out.println("The griffin starts to sleep.");


        System.out.println("\nWhat do you want to do?");
        System.out.println("[1] Climb the tree");
        System.out.println("[2] Leave it alone");
        int choice = readChoice(2);

        if (choice == 1) {
            System.out.println("Such a courage act must be recognized!");
            System.out.println("You're about 10 meters above the ground. Inside the nest, you gaze upon the griffon vulture and realize how large she is.");
            System.out.println("Beneath her wings, she protects her eggs and the paper you saw earlier.");


            System.out.println("\nWhat do you do?");
            System.out.println("[1] Try to take the paper");
            System.out.println("[2] Leave it alone");
            int secondChoice = readChoice(2);

            if (secondChoice == 1) {
                this.getPlayer().addItemToInventory(new Item("Banishment scroll",
                        "This ancient tome trembles faintly in your hands, as if alive. Its brittle parchment is etched with ink that seems to shift when you look away, symbols twisting into shapes that defy mortal comprehension.\n" +
                                "The air around it grows heavy, charged with a power that hums just beyond understanding.\n" +
                                "Its message is clear, though cryptic: a command for the perverse to return whence it came, a fate written in threads of shadow and fire.\n" +
                                "Dare you speak the words that could rend reality? - Banishment\""));

                System.out.println(this.getPlayer().getItem("Banishment scroll").getDescription());
            } else {
                System.out.println("You leave the tome alone.");
            }


            System.out.println("\nNow, you need to find a way to get down.");
            System.out.println("[1] Carefully climb down");
            System.out.println("[2] Jump recklessly (not recommended!)");

            int finalChoice = readChoice(2);
            if (finalChoice == 1) {
                System.out.println("You carefully climb down and return safely to the camp.");
                Quest.completeQuest(ClimbTheTree.class);
            } else {
                System.out.println("You jump recklessly... fortunately, you survive, but you're badly hurt.");

                Quest.completeQuest(ClimbTheTree.class);
            }
        } else {
            System.out.println("You decide it's too dangerous and leave the griffin alone.");

        }
    }


    private int readChoice(int max) {
        int choice = -1;
        while (choice < 1 || choice > max) {
            System.out.print("> ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Try again.");
            }
        }
        return choice;
    }
}
