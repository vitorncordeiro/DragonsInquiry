package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.entities.items.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClimbTheTree extends Quest{
    Scanner sc = new Scanner(System.in);
    public ClimbTheTree(Player player){
        super("main.DataSetup.quests.ClimTheTree", "camping", player);
    }
    public HashMap<String, String> getSecretDirection(){
        return null;
    }
    @Override
    public boolean unlocksSecretDirection(){
        return false;
    }
    public void startQuest(){
        if(this.isCompleted()){
            return;
        }
        System.out.print("Around the camp, there are many trees, but one in particular catches\nyour attention due to a minute detail: a nest at the top of it.");
        System.out.println("While you observe the nest, you hear the wing-beat of something in direction of the nest: It's a majestic griffin, with what appears to be a piece of paper in its beak");
        System.out.println("The griffin starts to sleep.");
        String choice = sc.nextLine().toLowerCase();
        if(choice.contains("c") || choice.contains("u") || choice.contains("three")){
            System.out.println("Such a courage act must be recognized");
            System.out.println("You're about 10 meters above the ground. Inside the nest, you gaze upon the griffon vulture and realize how large she is. Beneath her wings, she protects his eggs and the paper you saw earlier.");

            if(sc.nextLine().toLowerCase().contains("take")){
                this.getPlayer().addItemToInventory(new Item("Banishment scroll", "This ancient tome trembles faintly in your hands, as if alive. Its brittle parchment is etched with ink that seems to shift when you look away, symbols twisting into shapes that defy mortal comprehension.\n" +
                        "The air around it grows heavy, charged with a power that hums just beyond understanding.\n" +
                        "Its message is clear, though cryptic: a command for the perverse to return whence it came, a fate written in threads of shadow and fire.\n" +
                        "Dare you speak the words that could rend reality? - Banishment\""));
                System.out.println(this.getPlayer().getItem("Banishment scroll").getDescription());
            }else{
                System.out.println("You leave the tome alone");
            }
            System.out.println("Now, you need find a way to get down");
            String finalAns = sc.nextLine().toLowerCase();
            while(!finalAns.contains("d")){
                System.out.println("There must be another way");
            }
            Quest.completeQuest(ClimbTheTree.class);
        }
    }
}
