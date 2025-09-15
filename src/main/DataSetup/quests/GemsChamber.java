package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.quests.Quest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GemsChamber extends Quest {
    public static boolean isComplete = false;
    Scanner sc = new Scanner(System.in);

    public GemsChamber(Player player){
        super("gems chamber", "caves entry", player);
    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection(){
        return null;
    }

    @Override
    public void startQuest(){
        System.out.println("At the center of the cavern, a gem pedestal with an ancient inscription, worn out by time, take your attention.");
        System.out.println("Above the pedestal, you see five shiny gems: an iridium, an opal, a fuchsite, a apatite and a topaz");
        System.out.println("What will you do?");
        System.out.println("[1] Approach to pedestal and try to read the inscription");
        System.out.println("[2] Foolishly put the gems in your pocket");
        String decision = sc.nextLine();

        if(decision.equals("2")){
            System.out.println("The gems that you inconsequentially put in your pockets began to vibrate and flash frantically");
            System.out.println("In an act of desperation, you take the gems out of your pocket and throw them on the ground...");
            System.out.println("The moment the gems touch the ground, they explode in a chain, with enough energy to shake \n" +
                    "the entire cave, causing the walls and ceiling above the cave to collapse.");
            System.out.println("\n--- GAME OVER ---");
            System.exit(0);
        }
        getInscriptionText();

        HashMap<Integer, String> correctAnswer = new HashMap<>(Map.of(1, "fuchsite", 2 , "opal", 3,  "topaz", 4, "iridium", 5, "apatite"));
        HashMap<Integer, String> playerAnswer = new HashMap<>();
        boolean firstAttempt = true;
        while(!playerAnswer.equals(correctAnswer)){
            playerAnswer.clear();
            if(!firstAttempt){
                System.out.println("The stones glow brightly for a moment, but the glow quickly fades");
                System.out.println("Perhaps you should try another combination");
            }
            for(int i = 1; i <= 5; i++){
                String gem = sc.nextLine().toLowerCase();
                playerAnswer.put(i, gem);
            }
            firstAttempt = false;
        }
        System.out.println("""
                As the last stone clicks into place, the wall trembles. Slowly, a hidden door slides open to the west,
                 revealing a narrow corridor, with a staircase as old as the cave at the end.
                """);


    }
    private void getInscriptionText(){
        System.out.println("The inscription says:");
        System.out.println("\"Μόνο η φωτιά θα σας βοηθήσει. Τοποθετήστε τις πέτρες με τη σωστή σειρά και το φως θα σας καθοδηγήσει\"");
        System.out.println("The word φωτιά take your attention.");

    }
}
