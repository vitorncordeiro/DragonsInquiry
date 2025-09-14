package main.DataSetup.quests;

import java.util.HashMap;
import java.util.Map;

public class QuestData {
    private Map<String, Quest> quests;

    public QuestData(){
        this.quests = new HashMap<>(Map.of(
                "the theft", new TheTheft()
        )
        );
    }
    public Map<String, Quest> getQuests(){
        return quests;
    }

}
