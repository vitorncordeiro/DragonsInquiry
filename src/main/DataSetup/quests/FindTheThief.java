package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import java.util.*;

public class FindTheThief extends Quest {
    public static boolean isComplete = false;
    private final Scanner sc = new Scanner(System.in);

    public FindTheThief(Player player) {
        super("find the thief", "Blank", player);
    }

    @Override
    public HashMap<String, String> getSecretDirection() { return null; }

    @Override
    public boolean unlocksSecretDirection() { return false; }

    @Override
    public void startQuest() {
        while (!FindTheThief.isComplete) {
            System.out.println("\nDespite the aura of rot, you notice three unusual figures:");
            System.out.println("[1] A peculiar metamorphosed creature");
            System.out.println("[2] A man inside a barrel");
            System.out.println("[3] A man who is talking to himself");

            int choice = readChoice(3);

            switch (choice) {
                case 1 -> talkMetamorphosedMan();
                case 2 -> talkManInBarrel();
                case 3 -> talkRestlessMan();
            }
        }
    }

    private void talkMetamorphosedMan() {
        System.out.println("""
                
                A grotesque creature twitches before you. 
                His belly is brown, dome-shaped and segmented. 
                Many legs scrape the ground as he speaks with a distorted voice.
                
                Gregor: "What time is it? I think I'm going to be late!"
                """);

        System.out.println("[1] Answer the question");
        System.out.println("[2] Remain silent");

        int choice = readChoice(2);

        if (choice == 1) {
            System.out.println("Gregor: \"Oh no! I'm going to be late for work! "
                    + "My boss will fire me, my family will be disappointed... excuse me.\"");
        } else {
            System.out.println("Gregor groans, turning his shell away from you.");
        }
    }

    private void talkManInBarrel() {
        System.out.println("""
                Inside a rotting barrel sits a gaunt man. 
                His eyes gleam with strange lucidity, as if he were a king on a throne.
                
                Barrel Man: "I have chosen freedom over chains, and my barrel over palaces.
                Tell me, stranger... do you seek something material, or do you seek truth?"
                """);

        System.out.println("[1] Material things");
        System.out.println("[2] Truth");
        System.out.println("[3] Stay silent");

        int choice = readChoice(3);

        switch (choice) {
            case 1 -> System.out.println("""
                    Barrel Man: "Then you are already enslaved. 
                    Material goods are the leash that binds fools. Leave me."
                    """);
            case 2 -> System.out.println("""
                    Barrel Man: "Truth? Then abandon hunger, fear, and pride. 
                    Live as I do — with nothing, yet lacking nothing."
                    """);
            case 3 -> System.out.println("""
                    Barrel Man: "Your silence speaks louder than words. 
                    The world outside will teach you what I cannot."
                    """);
        }
    }

    private void talkRestlessMan() {
        boolean confessed = false;
        boolean potionReturned = false;

        System.out.println("""
                A trembling man hides in the shadows. 
                His coat is tattered yet once-refined. 
                His eyes dart wildly, as though two beasts wrestle within.
                
                Restless Man: "Forgive me... these fits are not mine. 
                Another walks within me... I cannot keep him caged..."
                """);

        // --- Part 1: The confession ---
        while (!confessed) {
            System.out.println("\nWhat will you do?");
            System.out.println("[1] Ask about his condition");
            System.out.println("[2] Ask about the stolen potion");
            System.out.println("[3] Accuse him of being the thief");

            int choice = readChoice(3);

            switch (choice) {
                case 1 -> System.out.println("""
                        Dr. Jekyll: "I sought to master the darkness of man... instead I gave it flesh.
                        He... Hyde... he wears my face when I falter."
                        """);
                case 2 -> {
                    System.out.println("""
                            Mr. Hyde: "Potion? Sweet nectar! I stole it, yes. 
                            His trembling hands could never guard such power."
                            """);
                    confessed = true;
                }
                case 3 -> {
                    System.out.println("""
                            Mr. Hyde: "Clever little worm... yes, I took it! 
                            And I would drink a thousand more, if only to silence gnats like you!"
                            """);
                    confessed = true;
                }
            }
        }

        // --- Part 2: Reclaiming the potion ---
        while (!potionReturned) {
            System.out.println("\nThe truth is laid bare. Now you must reclaim the stolen potion.");
            System.out.println("[1] Appeal to Dr. Jekyll’s remorse");
            System.out.println("[2] Offer mercy if he returns it");
            System.out.println("[3] Threaten him");
            System.out.println("[4] Mock him");

            int choice = readChoice(4);

            switch (choice) {
                case 1 -> {
                    System.out.println("""
                            You: "Jekyll, the potion is poison to your soul. End this torment before it ends you."
                            Dr. Jekyll: "You speak true... Take it, before he drinks again..."
                            """);
                    potionReturned = true;
                    FindTheThief.isComplete = true;
                }
                case 2 -> {
                    System.out.println("""
                            You: "Return the potion, and I will not speak of this. You may yet find peace."
                            Dr. Jekyll: "Mercy... after all I've done? You are kinder than I deserve. Take it, then."
                            """);
                    potionReturned = true;
                    FindTheThief.isComplete = true;
                }
                case 3 -> {
                    System.out.println("""
                            Mr. Hyde: "End me? Hah! You end nothing. Still... this toy bores me."
                            He tosses the potion to the ground, mocking your triumph.
                            """);
                    potionReturned = true;
                    FindTheThief.isComplete = true;
                }
                case 4 -> {
                    System.out.println("""
                            You: "Is this all Hyde amounts to? A coward hiding behind Jekyll’s weakness?"
                            Mr. Hyde: "Mock me? Foolish insect... I will carve your laughter from your throat!"
                            
                            --- GAME OVER ---
                            """);
                    System.exit(0);
                }
            }
        }

        System.out.println("\nYou recovered the stolen potion. The quest is complete.");
        Quest.completeQuest(FindTheThief.class);
    }

    /* --- Utility method --- */
    private int readChoice(int max) {
        int choice;
        while (true) {
            System.out.print("> ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= 1 && choice <= max) return choice;
            } catch (Exception ignored) {}
            System.out.println("Invalid option. Try again.");
        }
    }
}
