package main.DataSetup.entities;

import main.DataSetup.entities.items.Item;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Player {
    private List<Item> inventory;
    public Player(){
        inventory = new LinkedList<>();
    }
    public void showInventory(){
        inventory.forEach(System.out::println);
    }
    public List<Item> getInventory(){
        return inventory;
    }
    public boolean hasItem(String name){
        for(var i : inventory){
            if(i.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }
    public Item getItem(String name){
        if (hasItem(name)) {
            for (var i : inventory){
                if(i.getName().equalsIgnoreCase(name)) return i;
            }
        }
        return null;
    }
    public void addItemToInventory(Item item){
        inventory.add(item);
        System.out.println("[" +  item + " added to inventory]");

    }
}
