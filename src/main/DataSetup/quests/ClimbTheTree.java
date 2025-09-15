package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;
import java.util.Map;

public class ClimbTheTree extends Quest{
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
        System.out.print("");
    }
}
