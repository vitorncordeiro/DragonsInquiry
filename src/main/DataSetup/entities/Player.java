package main.DataSetup.entities;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private List<String> inventory;
    private int Score;


    public Player(){
        inventory = new LinkedList<>();
    }
    public void showInventory(){
        inventory.forEach(System.out::println);
    }
    public String addItemToInventory(String item){
        inventory.add(item);
        return "[" +  item + " added to inventory]";
    }
}
