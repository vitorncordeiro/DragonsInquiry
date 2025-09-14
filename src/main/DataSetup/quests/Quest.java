package main.DataSetup.quests;

import java.util.HashMap;
import java.util.Map;

public abstract class Quest {
    protected String locationWillBeChanged;
    protected String name;
    private static final Map<Class<? extends Quest>, Boolean> completedMap = new HashMap<>();

    public Quest(String name, String locationWillBeChanged){
        this.name = name;
        this.locationWillBeChanged = locationWillBeChanged;
    }

    public String getLocationWillBeChanged(){
        return locationWillBeChanged;
    }

    public String getName(){
        return name;
    }

    public void completeQuest(){
        completedMap.put(this.getClass(), true);
    }

    public static boolean isCompleted(Class<? extends Quest> questClass) {
        return completedMap.getOrDefault(questClass, false);
    }

    public boolean isCompleted() {
        return completedMap.getOrDefault(this.getClass(), false);
    }

    public static void completeQuest(Class<? extends Quest> targetQuest){
        completedMap.put(targetQuest, true);
    }

    public abstract void startQuest();
    public abstract boolean unlocksSecretDirection();
    public abstract HashMap<String, String> getSecretDirection();
}
