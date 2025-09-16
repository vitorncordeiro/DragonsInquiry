package main.DataSetup.entities;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private List<String> inventory;
    public Player(){
        inventory = new LinkedList<>();
    }
    public void showInventory(){
        inventory.forEach(System.out::println);
    }
    public void addItemToInventory(String item){
        inventory.add(item);
        System.out.println("[" +  item + " added to inventory]");
    }
}
