package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.HashMap;
import java.util.Map;

public class QuestData {
    private Map<String, Quest> quests;

    public QuestData(Player player){
        this.quests = new HashMap<>(Map.of(
                "the theft", new TheTheft(player)
        )
        );
    }
    public Map<String, Quest> getQuests(){
        return quests;
    }

}
