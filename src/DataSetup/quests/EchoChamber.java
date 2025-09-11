package DataSetup.quests;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EchoChamber extends Quest{
    public static boolean isComplete = false;
    Scanner sc = new Scanner(System.in);

    public EchoChamber(){
        super("caves entry");
    }

    @Override
    public boolean isComplete(){
        return isComplete;
    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection(){
        return new HashMap<>(Map.of("", ""));
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
        while(!playerAnswer.equals(correctAnswer)){
            playerAnswer.clear();
            System.out.println("Put the gems in the correct order (write the correctly name of each gem)");
            for(int i = 1; i <= 5; i++){
                String gem = sc.nextLine().toLowerCase();
                playerAnswer.put(i, gem);
            }
        }


    }
    private void getInscriptionText(){
        System.out.println("The inscription says:");
        System.out.println("\"Μόνο η φωτιά θα σας βοηθήσει. Τοποθετήστε τις πέτρες με τη σωστή σειρά και το φως θα σας καθοδηγήσει\"");
        System.out.println("The word φωτιά take your attention.");

    }
}
