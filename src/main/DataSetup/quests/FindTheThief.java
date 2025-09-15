package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import util.FileManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FindTheThief extends Quest{
    public static boolean isComplete = false;
    Scanner sc = new Scanner(System.in);

    public FindTheThief(Player player){
        super("find the thief", "Blank", player);
    }


    public HashMap<String, String> getSecretDirection(){
        return null;
    }
    @Override
    public boolean unlocksSecretDirection(){
        return false;
    }

    @Override
    public void startQuest(){

        while(!FindTheThief.isComplete) {
            System.out.println("Despite the aura of rot, you notice three unusual figures:\n[1] A peculiar metamorphosed creature\n" +
                    "[2] A man inside a barrel\n" +
                    "[3] A man who is talking to himself");
            System.out.println("\nYou may have questions. Approach one of them:");
            String ans = sc.nextLine().toLowerCase();
            if(ans.contains("barrel")){
                talkManInBarrel();
            }else if(ans.contains("creature") || ans.contains("metamorphosed")){
                talkMetamorphosedMan();
            } else if(ans.contains("himself") || ans.contains("talking") || ans.contains("schizophrenic")) {
                talkRestlessMan();
            }
        }

    }
    public void talkMetamorphosedMan(){
        System.out.println("""
                
                                                      #                                \s
                  --                                .-                                 \s
                      *                            +                                   \s
                          ++                      =                                    \s
                              .=+.                =           @                        \s
                                    =@.          #           @                         \s
                                        :@       -          @                          \s
                                           +     =        :-                           \s
                                             @%%@@      +.@                            \s
                                            @@@@@@@@# #@                               \s
                                           % %#@@@@@@@#@                               \s
                                             - @@@   @@@                               \s
                                      *:-**:     -=#@@@@   :                           \s
                                   :+.. * -@@@@@@@@@@@@@*.-- -                         \s
                                 -+-      :* @@@@@@@@@@@ @@    @===:                   \s
                                           . -#@@%@@@@@@@+@        *@+#+-              \s
                                           * #=@#@@@@@@@@@.                            \s
                                       .*@.# =*#@@%%@@@@@==@                           \s
                                      %#-@@@= @ #:**@@@@@@@-                           \s
                                     *@     @ @. #*@@@@+@@@@@                          \s
                                   -:@    : @ %#   @@%@@@@@@+@ :#                      \s
                                  *%    @ *+@-:+#  :+@@+@#@@+@@  +:                    \s
                                %=*         +@. @.   +%*+@@@ @    =-                   \s
                           -@@          #@   @. @=    +@ %@%+@.   #+                   \s
                                         %    @:: :#   %@@@@@@#    =+                  \s
                                              %@: +=@  @@=@@#@     - +                 \s
                                         @@    *@+.  + :+@@@%@      + :                \s
                                         @@     -@=. - . *@@@-       +.                \s
                                         @+       * =-.               #.+              \s
                                         @.         :  .%=:  @=#       +%              \s
                                        #*         -= --:                +*            \s
                                       @%         *                        @*          \s
                                      @                                      @@        \s
                                   #+                                           #      \s
                
                
                """);
        System.out.println("The man infront of you is deliberately indescribable. However, his belly is brown, dome-shaped and segmented. Also, he seems to have " +
                "many legs,\nand with his distorted voice, he ask you:\nGregor:\nWhat time is it? I think im going to be late!");
        sc.nextLine();
        System.out.println("Gregor:\nOh no! Im going to be late for work! My boss will fire me, and my family will " +
                "be desappointed in me, excuse me.");
        System.out.println("He turns his shell towards you");

    }

    public void talkManInBarrel(){
        System.out.println("Inside a rotting barrel, you see a gaunt man. His eyes gleam with strange lucidity,");
        System.out.println("and despite the stench around him, he sits as if he were a king on a throne.");
        System.out.println("Barrel Man:\nI have chosen freedom over chains, and my barrel over palaces.");
        System.out.println("Tell me, stranger... do you seek something material, or do you seek truth?");
        System.out.println("[1] I'm looking for a stolen bottle.");
        System.out.println("[2] Truth, if such a thing exists here.");
        String ans = sc.nextLine().toLowerCase();
        if(ans.contains("material") || ans.contains("bottle")){
            System.out.println("Barrel Man:\nThen you are already enslaved. Material goods is the leash that binds fools.\nLeave me, I have nothing for you.");
        } else if(ans.contains("truth")){
            System.out.println("Barrel Man:\nTruth? Then abandon your hunger, your fear, your pride.\nLive as I do — with nothing, yet lacking nothing.");
            System.out.println("He chuckles, a hollow sound echoing in the barrel, as if the wood itself mocks you.");
        } else {
            System.out.println("Barrel Man:\nYour silence speaks louder than words. Go, then. The world outside\nwill teach you what I cannot.");
        }
    }

    public void talkRestlessMan(){
        boolean confessed = false;
        boolean potionReturned = false;
        boolean spokeToJekyll = false;

        System.out.println("""
            
            A man trembles in the shadows, his coat tattered yet marked with 
            the remnants of once-refined tailoring. His eyes dart wildly, 
            as though two beasts wrestle behind them.
            
            Restless Man:
            "I... forgive me. These fits... they are not mine. 
            Another walks within me... and I cannot keep him caged..."
            
            """);

        // --- Primeira parte: descobrir o roubo ---
        while(!confessed){
            System.out.println("What will you do?");
            String input = sc.nextLine().toLowerCase();

            if(input.contains("condition") || input.contains("health") || input.contains("ask")){
                if(!spokeToJekyll){
                    System.out.println("You: You seem tormented. Speak — what gnaws at you?");
                    System.out.println("Dr. Jekyll: \"I sought to master the darkness of man... "
                            + "instead, I gave it flesh. He... Hyde... he wears my face when I falter.\"");
                    spokeToJekyll = true;
                } else {
                    System.out.println("Dr. Jekyll: \"Each sip of that cursed draught frees him. "
                            + "And now... now I am slave to my own creation.\"");
                }

            } else if(input.contains("stolen") || input.contains("potion") || input.contains("bottle")){
                if(!spokeToJekyll){
                    System.out.println("You: The merchant claims a potion was stolen. Were you there?");
                    System.out.println("The man’s lips curl into a jagged grin, voice twisting low.");
                    System.out.println("Mr. Hyde: \"Potion? Hah! Sweet nectar! I stole it, yes. "
                            + "His trembling hands could never guard such power.\"");
                } else {
                    System.out.println("Dr. Jekyll clutches his chest, voice breaking.");
                    System.out.println("Dr. Jekyll: \"It was him... Hyde... he took it. "
                            + "But my hands were his. My guilt is complete.\"");
                }
                confessed = true;

            } else if(input.contains("accuse") || input.contains("thief") || input.contains("liar")){
                System.out.println("You: Enough lies! You stole the potion!");
                System.out.println("Mr. Hyde erupts, a cruel laugh splitting the silence.");
                System.out.println("Mr. Hyde: \"Clever little worm... yes, I took it! And I would drink "
                        + "a thousand more, if only to silence gnats like you!\"");
                confessed = true;

            } else {
                System.out.println("The man shivers, whispering curses to the shadows.");
            }
        }

        // --- Segunda parte: recuperar a poção ---
        while(!potionReturned){
            System.out.println("\nThe truth is laid bare. Now you must reclaim the stolen potion.");
            String input = sc.nextLine().toLowerCase();

            if(input.contains("remorse") || input.contains("jekyll") || input.contains("appeal")){
                System.out.println("You: Jekyll, the potion is poison to your soul. Give it back — "
                        + "end this torment before it ends you.");
                System.out.println("Dr. Jekyll: \"You speak true... My crime festers within me. "
                        + "Take it, before he drinks again...\"");
                potionReturned = true;
                FindTheThief.isComplete = true;

            } else if(input.contains("reason") || input.contains("mercy") || input.contains("peace")){
                System.out.println("You: Return the potion, and I will not speak of this. You may yet find peace.");
                System.out.println("Dr. Jekyll bows his head, shame dripping from his words.");
                System.out.println("Dr. Jekyll: \"Mercy... after all I’ve done? You are kinder than I deserve. "
                        + "Take it, then.\"");
                potionReturned = true;
                FindTheThief.isComplete = true;

            } else if(input.contains("threat") || input.contains("fight") || input.contains("kill")){
                System.out.println("You: Hyde! If you resist me, I will end you where you stand.");
                System.out.println("Mr. Hyde sneers, voice dripping venom.");
                System.out.println("Mr. Hyde: \"End me? Hah! You end nothing. Still... this toy bores me.\"");
                System.out.println("He tosses the potion to the ground, grinning as if mocking your triumph.");
                potionReturned = true;
                FindTheThief.isComplete = true;

            } else if(input.contains("mock") || input.contains("weak") || input.contains("coward")){
                System.out.println("You: Is this all Hyde amounts to? A thief hiding behind Jekyll’s weakness?");
                System.out.println("Mr. Hyde’s grin freezes, his eyes narrowing to slits.");
                System.out.println("Mr. Hyde: \"Mock me? Foolish insect... I will carve your laughter from your throat!\"");
                System.out.println("\n\\u001B[31m--- GAME OVER ---\\u001B[0m");
                System.exit(0);

            } else {
                System.out.println("The man claws at his face, muttering incoherently.");
            }
        }

        System.out.println("\nYou recovered the stolen potion. The quest is complete.");
        Quest.completeQuest(FindTheThief.class);
    }



}
