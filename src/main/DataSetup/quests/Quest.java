package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class Quest {
    protected String locationWillBeChanged;
    protected String name;
    private static Map<Class<? extends Quest>, Boolean> completedMap = new HashMap<>();
    private Player player;

    public Quest(String name, String locationWillBeChanged, Player player){
        this.name = name;
        this.player = player;
        this.locationWillBeChanged = locationWillBeChanged;
    }

    public String getLocationWillBeChanged(){
        return locationWillBeChanged;
    }

    public static Map<Class<? extends Quest>, Boolean> getCompletedMap(){
        return completedMap;
    }

    public String getName(){
        return name;
    }
    public Player getPlayer(){
        return player;
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
