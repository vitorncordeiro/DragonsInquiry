package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;

public class PentagramPuzzle extends Quest{

    public PentagramPuzzle(Player player){
        super("take the mirror", "treasure chamber", player);
    }

    @Override
    public void startQuest() {

    }

    @Override
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return null;
    }
}
