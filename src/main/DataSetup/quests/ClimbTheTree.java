package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClimbTheTree extends Quest{
    Scanner sc = new Scanner(System.in);
    public ClimbTheTree(Player player){
        super("climb the three", "camping", player);
    }
    public HashMap<String, String> getSecretDirection(){
        return null;
    }
    @Override
    public boolean unlocksSecretDirection(){
        return false;
    }
    public void startQuest(){
        System.out.print("Around the camp, there are many trees, but one in particular catches\nyour attention due to a minute detail: a nest at the top of it.");
        String choice = sc.nextLine().toLowerCase();
        if(choice.contains("c") || choice.contains("u")){
            System.out.println("You're about 10 meters above the ground. Inside the nest, you see three eggs, and a fourth egg, much larger than the others, catches your eye.");

            if(sc.nextLine().toLowerCase().contains("take")){
                this.getPlayer().addItemToInventory("Mysterious Egg");
            }else{
                System.out.println("You leave the eggs alone");
            }
            System.out.println("Now, you need find a way to get down");
            String finalAns = sc.nextLine().toLowerCase();
            while(!finalAns.contains("d")){
                System.out.println("There must be another way");
            }
        }
    }
}
