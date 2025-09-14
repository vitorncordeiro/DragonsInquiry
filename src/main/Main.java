package main;

import main.DataSetup.locations.Location;
import main.DataSetup.locations.LocationData;
import main.DataSetup.quests.QuestData;
import util.FileManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static LocationData locations = new LocationData();
    public static FileManager fileManager = new FileManager();
    public static QuestData quests = new QuestData();


    public static void main(String[] args) {


        System.out.println("Lets start the adventure");

        Map<String, List<String>> savedData = fileManager.load();

        Location currentLocation = locations.getLocations().get("camping"); // default

        List<String> savedLocation = savedData.get("location");
        if(savedLocation != null && !savedLocation.isEmpty() && savedLocation.get(0) != null){
            String locName = savedLocation.get(0);
            if(locations.getLocations().containsKey(locName)){
                currentLocation = locations.getLocations().get(locName);
            }
        }

        List<String> savedQuests = savedData.get("quests");
        if(savedQuests != null && !savedQuests.isEmpty()){
            for(var q : quests.getQuests().values()){
                if(savedQuests.contains(q.getName())){
                    q.completeQuest();
                }
            }
        }



        boolean flag = true;
        while(flag){
            FileManager.save(currentLocation.getLocationName());
            System.out.println("Current Location: " + currentLocation);
            System.out.println(currentLocation.getLocationDescription());
            currentLocation.getQuest().startQuest();
            if(currentLocation.getQuest().isCompleted() && currentLocation.getQuest().unlocksSecretDirection() ){
                addSecretDirection(locations.getLocations().get(currentLocation.getQuest().getLocationWillBeChanged()), currentLocation.getQuest().getSecretDirection());
            }

            System.out.println("Choose an direction:\n" + locations.getLocations().get(currentLocation.getLocationName()).showNextPlaces());
            String direction = scanner.nextLine().toUpperCase();
            if(locations.getLocations().get(currentLocation.getLocationName()).getNextPlaces().containsKey(direction) || direction.equals("Q")){
                switch(direction){
                    case "W" -> currentLocation = locations.getLocations().get(currentLocation.getNextPlaces().get("W"));
                    case "N" -> currentLocation = locations.getLocations().get(currentLocation.getNextPlaces().get("N"));
                    case "S" -> currentLocation = locations.getLocations().get(currentLocation.getNextPlaces().get("S"));
                    case "E" -> currentLocation = locations.getLocations().get(currentLocation.getNextPlaces().get("E"));
                    case "Q" -> flag = false;
                }
            }else{
                System.out.println("From here, the wind doesnt blow in that direction");
            }
        }


    }
    public static void addSecretDirection(Location location, HashMap<String, String> map){
        location.getNextPlaces().putAll(map);
    }

}