package main.DataSetup.quests;

import java.util.HashMap;

public abstract class Quest {
    protected String locationWillBeChanged;
    protected String name;
    public Quest(String name, String locationWillBeChanged){
        this.name = name;
        this.locationWillBeChanged = locationWillBeChanged;
    }

    public String getLocationWillBeChanged(){
        return locationWillBeChanged;
    }
    public abstract void startQuest();
    public abstract boolean isComplete();
    public abstract boolean unlocksSecretDirection();
    public abstract HashMap<String, String> getSecretDirection();
}
