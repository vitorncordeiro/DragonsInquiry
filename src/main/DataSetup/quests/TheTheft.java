package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.entities.items.Item;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TheTheft extends Quest {
    private boolean unlocksSecretDirection = true;
    Scanner sc = new Scanner(System.in);

    public TheTheft(Player player){
        super("main.DataSetup.quests.TheTheft", "alley one", player);
    }
    @Override
    public boolean unlocksSecretDirection(){
        return unlocksSecretDirection;
    }
    public HashMap<String, String> getSecretDirection(){
        return new HashMap<>(Map.of("E", "caves entry"));
    }
    @Override
    public void startQuest(){
        if(this.isCompleted()){
            endQuest();
            return;
        }
        System.out.println("Walking around the village, a door from the alley opens and an old merchant appears, calling you.");
        String entryAns = sc.nextLine().toLowerCase();
        if(entryAns.contains("no") || entryAns.contains("don") || entryAns.contains("do not")){
            System.out.println("Haha! You have no choice");
        }

        System.out.println("You entered the merchant's shop");
        System.out.println("Kanon:");
        System.out.println("Hey outsider! I've already listen about you, and how you looks like sick. Don't worry, I will help you..." +
                "\nAs long as you help me with a troublemaker.");
        boolean flag = true;
        while(flag){
            System.out.println("Are you in?");
            String ans = sc.nextLine().toLowerCase();
            if(ans.contains("y") || ans.contains("sure") || ans.contains("alright")){
                flag = false;
            }else if(ans.equalsIgnoreCase("negative") || ans.startsWith("n")){
                System.out.println("Kanon:\n" + "It seems you dont know the local rules yet..." + "\nYou can't avoid the light path here. Let me try again");
            }else if(ans.equals("banishment")){
                System.out.println("""
                        W-what...? You… how dare you utter that name before me?!
                        Foolish mortal… I was so close. Hidden among you, peddling worthless trinkets while harvesting souls with every deal.
                        And now… banishment?!
                        No… NO!! That spell should not even exist here…!*

                        You’ve undone everything before it even began…
                        — cursed be you, impatient player…"""
                        );
                System.out.println("--End--");
                System.exit(0);

            } else{
                System.out.println("Kanon:\n" + "Sorry outsider, i cant understand your accent.");
            }
        }
        System.out.println("Kanon:");
        System.out.println("Alright, so listen carefully: Yesterday, a terrible event has befallen me. One of mine... perfume bottles... was stolen " +
                "and the thief ran to the ghetto, to the south. As you can see,\n Im just an old man, and i cant go after him alone." +
                " And there are you! I think you get my point. Take the bottle back, and I will help you\n"

        );
    }
    public void endQuest(){
        System.out.println("Kanon:\nVery well, outsider, I see you didnt come back empty-handed. Holding up my end,\n" +
                "to the west of here, there is a tower, where an intelligent, brilliant, magnificent wizard lives.\nGo there " +
                "and talk to him, perhaps he will help you with your... illness.\n Take this lamp, and go to the south, then, you may notice the cave entry in east." +
                "\nTake care of yourself, outsider. in spite of not needed to pay bills for the road toll, the danger is in that cave.");
        getPlayer().addItemToInventory(new Item("Lamp", "A crooked brass lamp, its surface mottled with green corrosion and dark stains that look unsettlingly like dried blood."));

    }
}
