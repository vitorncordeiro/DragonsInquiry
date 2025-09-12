package main.DataSetup.quests;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TheTheft extends Quest {
    public static boolean isComplete = false;
    private boolean unlocksSecretDirection = true;
    Scanner sc = new Scanner(System.in);

    public TheTheft(){
        super("alley one");
    }
    @Override
    public boolean unlocksSecretDirection(){
        return unlocksSecretDirection;
    }
    public HashMap<String, String> getSecretDirection(){
        return new HashMap<>(Map.of("E", "caves entry"));
    }
    @Override
    public boolean isComplete(){
        return isComplete;
    }
    @Override
    public void startQuest(){
        if(TheTheft.isComplete){
            endQuest();
            return;
        }
        System.out.println("Kanon:");
        System.out.println("Hey outsider! I've already listen about you, and your mission here. Don't worry, I will help you..." +
                "\nAs long as you help me with a troublemaker.");
        boolean flag = true;
        while(flag){
            System.out.println("Are you in?" + "\n[Y]es \n[N]o");
            String ans = sc.nextLine();
            if(ans.equalsIgnoreCase("Y")){
                flag = false;
            }else if(ans.equalsIgnoreCase("N")){
                System.out.println("Kanon:\n" + "It seems you dont know the local rules yet..." + "\nYou can't avoid the light path here. Let me try again");
            }else{
                System.out.println("Kanon:\n" + "Sorry outsider, i cant understand your accent.");
            }
        }
        System.out.println("Alright, so listen carefully: Yesterday, a terrible event has befallen me. One of mine... perfume bottles... was stolen " +
                "and the thief ran to the ghetto. As you can see,\n Im just an old man, and i cant go after him alone." +
                "And there are you! I think you get my point. Take the ring back, and I will help you"

        );
    }
    public void endQuest(){
        System.out.println("Kanon:\nVery well, outsider, I see you didnt come back empty-handed. Holding up my end,\n" +
                "to the west of here, there is a tower, where an intelligent, brilliant, magnificent wizard lives.\nGo there " +
                "and talk to him, perhaps he will help you with your... illness.\n Take this torch, and go to the south, then, you may notice the cave entry in east." +
                "\nTake care of yourself, outsider. in spite of not needed to pay bills for the road toll, the danger is in that cave.");
        System.out.println("[You received a Torch]");

    }
}
