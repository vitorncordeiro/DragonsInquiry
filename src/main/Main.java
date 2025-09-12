package main;

import main.DataSetup.locations.Location;
import main.DataSetup.locations.LocationData;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static LocationData locations = new LocationData();
    public static void main(String[] args) {

        try {
            URL musicURL = Main.class.getResource("/musica.wav");
            if (musicURL != null) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(musicURL));
                clip.loop(Clip.LOOP_CONTINUOUSLY); // loop infinito
                clip.start();
            } else {
                System.err.println("Arquivo de música não encontrado!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Lets start the adventure");
        Location currentLocation = locations.getLocations().get("camping");
        boolean flag = true;
        while(flag){
            System.out.println("Current Location: " + currentLocation);
            System.out.println(currentLocation.getLocationDescription());
            currentLocation.getQuest().startQuest();
            if(currentLocation.getQuest().isComplete() && currentLocation.getQuest().unlocksSecretDirection() ){
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