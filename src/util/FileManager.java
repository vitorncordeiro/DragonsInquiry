package util;

import main.DataSetup.quests.Quest;

import java.io.*;
import java.util.*;

public class FileManager {

    private static final String SAVE_PATH = "save/save.txt";

    public static Map<String, List<String>> load() {
        File file = new File(SAVE_PATH);


        if (!file.exists()) {
            System.out.println("Save file not found. Criando arquivo vazio...");
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new HashMap<>();
        }

        Map<String, List<String>> res = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String firstLine = bufferedReader.readLine();
            res.put("location", firstLine != null ? Collections.singletonList(firstLine) : Collections.emptyList());

            String line;
            List<String> quests = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                quests.add(line);
            }
            res.put("quests", quests);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void save(String location) {
        File file = new File(SAVE_PATH);

        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(location);
                writer.newLine();
                for (var questName : Quest.getCompletedMap().keySet()) {
                    writer.write(questName.getName());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
