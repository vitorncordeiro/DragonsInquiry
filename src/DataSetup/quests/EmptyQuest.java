package DataSetup.quests;

import java.util.HashMap;
import java.util.Map;

public class EmptyQuest extends Quest{
    public EmptyQuest(){
        super("Blank");



    }
    @Override
    public boolean isComplete(){
        return true;
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
