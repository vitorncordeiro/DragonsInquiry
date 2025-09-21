package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.quests.Quest;

import java.util.HashMap;
import java.util.Map;

public class EmptyQuest extends Quest {
    public EmptyQuest(Player player){
        super("blank", "Blank", player);



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
