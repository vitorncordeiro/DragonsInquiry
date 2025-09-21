package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TheDemon extends Quest {
    private Scanner sc = new Scanner(System.in);

    public TheDemon(Player player) {
        super("the demon", "demons room", player);
    }

    @Override
    public void startQuest() {
        System.out.println("A menacing demon looms before you, its eyes burning with hatred.");

        boolean questComplete = false;

        while (!questComplete) {
            System.out.println("\nWhat will you do?");
            System.out.println("[1] Attack the demon");
            System.out.println("[2] Say the word");

            int choice = getChoice();

            switch (choice) {
                case 1:
                    System.out.println("Demon: Hahaha! Do you think a mere mortal can kill me?");
                    sc.nextLine();
                    System.out.println("With a swift gesture of its clawed hand, a sphere of searing flame bursts into existence,");
                    System.out.println("crackling with infernal energy. The heat scorches your skin before it even strikes.");
                    System.out.println("In an instant, the fireball engulfs you, blinding light and unbearable agony consumes your body");
                    System.out.println("--Game over--");
                    System.exit(0);
                    break;
                case 2:
                    if(sc.nextLine().toLowerCase().contains("banishment")) {
                        System.out.println("You chant the banishment incantation. The demon recoils as the spell takes hold.");
                        System.out.println("Light binds its form, and with a roar torn between rage and fear, it is torn from this plane in a blinding flash.");
                        System.out.println("As it vanishes, the iron bars sealing the western door crack and disintegrate into dust.");
                        System.out.println("Silence falls. The way west is clear.");
                        Quest.completeQuest(TheDemon.class);
                        questComplete = true;
                        break;
                    }else{
                        System.out.println("You wrote it wrong");
                        System.out.println("--Game over--");
                        System.exit(0);
                    }
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private int getChoice() {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                int choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Enter the number corresponding to your choice.");
            }
        }
    }

    @Override
    public boolean unlocksSecretDirection() {
        return true;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return new HashMap<>(Map.of("N", ""));
    }
}
