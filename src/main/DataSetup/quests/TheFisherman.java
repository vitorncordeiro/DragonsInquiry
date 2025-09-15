package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;

public class TheFisherman extends Quest {
    public TheFisherman(Player player){
        super("the fisherman", "caves pond", player);
    }

    @Override
    public void startQuest() {
        System.out.println("");
    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return null;
    }


}
