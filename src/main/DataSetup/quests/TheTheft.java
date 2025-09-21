package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.entities.items.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TheTheft extends Quest {
    private boolean unlocksSecretDirection = true;
    private Scanner sc = new Scanner(System.in);

    public TheTheft(Player player) {
        super("main.DataSetup.quests.TheTheft", "alley one", player);
    }

    @Override
    public boolean unlocksSecretDirection() {
        return unlocksSecretDirection;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return new HashMap<>(Map.of("E", "caves entry"));
    }

    @Override
    public void startQuest() {
        if (this.isCompleted()) {
            endQuest();
            return;
        }

        System.out.println("Walking around the village, a door from the alley opens and an old merchant appears, calling you.");
        System.out.println("(Press Enter to continue...)");
        sc.nextLine();

        System.out.println("You entered the merchant's shop.");
        System.out.println("Kanon: Hey outsider! I've already heard about you, and how you look sick. Don't worry, I will help you...");
        System.out.println("...As long as you help me with a troublemaker.");

        boolean flag = true;
        while (flag) {
            System.out.println("\nAre you in?");
            System.out.println("[1] Yes, I will help");
            System.out.println("[2] No, I don't want to help");

            int choice = getChoice(3);

            switch (choice) {
                case 1 -> flag = false;
                case 2 -> System.out.println("Kanon:\nIt seems you don't know the local rules yet... You can't avoid the light path here. Let me try again.");
            }
        }

        System.out.println("Kanon: Alright, so listen carefully:");
        System.out.println("Yesterday, a terrible event has befallen me. One of mine... perfume bottles... was stolen " +
                "and the thief ran to the ghetto, to the south. As you can see, I’m just an old man, and I can't go after him alone. " +
                "And there are you! I think you get my point. Take the bottle back, and I will help you.");
    }

    public void endQuest() {
        System.out.println("Kanon: Very well, outsider, I see you didn't come back empty-handed. Holding up my end,");
        System.out.println("to the west of here, there is a tower where an intelligent, brilliant, magnificent wizard lives.");
        System.out.println("Go there and talk to him, perhaps he will help you with your... illness.");
        System.out.println("Take this lamp, and go to the south, then you may notice the cave entry in the east.");
        System.out.println("Take care of yourself, outsider. In spite of not needing to pay tolls for the road, danger lurks in that cave.");
        System.out.println("Lastly, if you see a portable mirror, do everything to take it with you.");

        getPlayer().addItemToInventory(new Item("Lamp",
                "A crooked brass lamp, its surface mottled with green corrosion and dark stains that look unsettlingly like dried blood."));
    }

    private int getChoice(int maxOption) {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= maxOption) {
                    return choice;
                } else {
                    System.out.println("Enter a number between 1 and " + maxOption + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter the number corresponding to your choice.");
            }
        }
    }
}
