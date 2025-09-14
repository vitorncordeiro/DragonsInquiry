package main.DataSetup.quests;

import java.util.HashMap;

public class TheFisherman extends Quest {
    public TheFisherman(){
        super("the fisherman", "caves pond");
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
