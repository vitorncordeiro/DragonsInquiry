package util;

import main.DataSetup.quests.Quest;

import java.io.*;
import java.util.*;

public class FileManager {

    public static Map<String, List<String>> load(){
        File file = new File("src/save/save.txt");

        Map<String, List<String>> res = new HashMap<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            res.put("location", Collections.singletonList(bufferedReader.readLine()));
            String line;
            List<String> quests = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null){
                quests.add(line);
            }
            res.put("quests", quests);
        }
        catch(FileNotFoundException e){
            System.out.println("Not found");
        }
        catch(IOException e){
            System.out.println("something wet wrong");
        }


        return res;
    }
    public static void save(String location){
        File file = new File("src/save/save.txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(location);
            writer.newLine();
            for (var questName : Quest.getCompletedMap().keySet()) {
                writer.write(questName.getName());
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
