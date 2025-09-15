package main.DataSetup.entities;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private List<String> inventory;
    private int Score;


    public Player(){
        inventory = new LinkedList<>();
    }
}
