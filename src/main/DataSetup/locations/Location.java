package main.DataSetup.locations;

import main.DataSetup.quests.EmptyQuest;
import main.DataSetup.quests.Quest;

import java.util.Map;


public class Location {
    protected  Map<String, String> nextPlaces;
    protected String locationDescription;
    protected String locationName;
    protected String asciiArt;
    protected Quest quest;

    public Location(String locationName, String locationDescription, Map<String, String> nextPlaces){
        this(locationName, locationDescription, nextPlaces, new EmptyQuest(), "");
    }
    public Location(String locationName, String locationDescription, Map<String, String> nextPlaces, Quest quest){
        this(locationName, locationDescription, nextPlaces, quest, "");
    }
    public Location(String locationName, String locationDescription, Map<String, String> nextPlaces, String asciiArt){
        this(locationName, locationDescription, nextPlaces, new EmptyQuest(), asciiArt);
    }
    public Location(String locationName, String locationDescription, Map<String, String> nextPlaces, Quest quest, String asciiArt){
        this.locationDescription = locationDescription;
        this.nextPlaces = nextPlaces;
        this.asciiArt = asciiArt;
        this.locationName = locationName;
        this.quest = quest;


    }
    public String showNextPlaces(){
        String res = "";
        for(var p : nextPlaces.entrySet()){
            res += p.getKey() + ": " + p.getValue() + "\n";
        }
        return res;
    }
    public String getAsciiArt(){
        return asciiArt;
    }
    public Quest getQuest(){
        return quest;
    }
    public String getLocationName(){
        return locationName;
    }
    public Map<String, String> getNextPlaces(){
        return nextPlaces;
    }
    public String getLocationDescription(){
        return locationDescription;
    }
    @Override
    public String toString(){
        return locationName;
    }

}
