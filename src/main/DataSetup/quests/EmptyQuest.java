package main.DataSetup.quests;

import main.DataSetup.quests.Quest;

import java.util.HashMap;
import java.util.Map;

public class EmptyQuest extends Quest {
    public EmptyQuest(){
        super("blank", "Blank");



    }

    public HashMap<String, String> getSecretDirection(){
        return new HashMap<>(Map.of("", ""));
    }
    @Override
    public boolean unlocksSecretDirection(){
        return false;
    }
    public void startQuest(){
        System.out.print("");
    }

}
