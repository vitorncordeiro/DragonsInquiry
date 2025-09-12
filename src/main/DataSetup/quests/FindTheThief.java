package main.DataSetup.quests;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FindTheThief extends Quest{
    public static boolean isComplete = false;
    Scanner sc = new Scanner(System.in);

    public FindTheThief(){
        super("Blank");
    }
    @Override
    public boolean isComplete(){
        return true;
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
                    "[3] A man who seems restless");
            System.out.println("\nYou may have questions. Approach one of them:");
            switch (sc.nextLine()) {
                case "1" -> talkMetamorphosedMan();
                case "2" -> talkManInBarrel();
                case "3" -> talkRestlessMan();
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
        System.out.println("[1] Be late for what?");
        sc.nextLine();
        System.out.println("Gregor:\nBeing late for work, of course. Common, tell me what time is it, if i'm late,\nmy boss will fire me, and my family will " +
                "be desappointed in me.");
        System.out.println("[1] Leave him to his madness, he is clearly hallucinating");
        sc.nextLine();
    }

    public void talkManInBarrel(){
        System.out.println("Inside a rotting barrel, you see a gaunt man. His eyes gleam with strange lucidity,");
        System.out.println("and despite the stench around him, he sits as if he were a king on a throne.");
        System.out.println("Barrel Man:\nI have chosen freedom over chains, and my barrel over palaces.");
        System.out.println("Tell me, stranger... do you seek something material, or do you seek truth?");
        System.out.println("[1] I'm looking for a stolen bottle.");
        System.out.println("[2] Truth, if such a thing exists here.");
        String choice = sc.nextLine();
        if(choice.equals("1")){
            System.out.println("Barrel Man:\nThen you are already enslaved. Material goods is the leash that binds fools.\nLeave me, I have nothing for you.");
        } else if(choice.equals("2")){
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
        boolean angeredHyde = false;

        System.out.println("""
                
                A man trembles in the shadows, his coat tattered yet marked with 
                the remnants of once-refined tailoring. His eyes dart wildly, 
                as though two beasts wrestle behind them.
                
                Restless Man:
                "I... forgive me. These fits... they are not mine. 
                Another walks within me... and I cannot keep him caged..."
                
                """);

        // --- First loop: uncover the theft ---
        while(!confessed){
            System.out.println("What will you do?");
            System.out.println("[1] Ask gently about his condition.");
            System.out.println("[2] Question him about the stolen potion.");
            System.out.println("[3] Accuse him directly of theft.");

            String choice = sc.nextLine();

            switch(choice){
                case "1" -> {
                    if(!spokeToJekyll){
                        System.out.println("You: You seem tormented. Speak — what gnaws at you?");
                        System.out.println("Dr. Jekyll: \"I sought to master the darkness of man... "
                                + "instead, I gave it flesh. He... Hyde... he wears my face when I falter.\"");
                        spokeToJekyll = true;
                    } else {
                        System.out.println("Dr. Jekyll: \"Each sip of that cursed draught frees him. "
                                + "And now... now I am slave to my own creation.\"");
                    }
                }
                case "2" -> {
                    if(!spokeToJekyll){
                        System.out.println("You: The merchant claims a potion was stolen. Were you there?");
                        System.out.println("The man’s lips curl into a jagged grin, voice twisting low.");
                        System.out.println("Mr. Hyde: \"Potion? Hah! Sweet nectar! I stole it, yes. "
                                + "His trembling hands could never guard such power.\"");
                        confessed = true;
                    } else {
                        System.out.println("Dr. Jekyll clutches his chest, voice breaking.");
                        System.out.println("Dr. Jekyll: \"It was him... Hyde... he took it. "
                                + "But my hands were his. My guilt is complete.\"");
                        confessed = true;
                    }
                }
                case "3" -> {
                    System.out.println("You: Enough lies! You stole the potion!");
                    System.out.println("Mr. Hyde erupts, a cruel laugh splitting the silence.");
                    System.out.println("Mr. Hyde: \"Clever little worm... yes, I took it! And I would drink "
                            + "a thousand more, if only to silence gnats like you!\"");
                    confessed = true;
                    angeredHyde = true;
                }
                default -> System.out.println("The man shivers, whispering curses to the shadows.");
            }
        }

        // --- Second loop: recover the potion ---
        while(!potionReturned){
            System.out.println("\nThe truth is laid bare. Now you must reclaim the stolen potion.");
            System.out.println("[1] Appeal to Dr. Jekyll’s remorse.");
            System.out.println("[2] Reason with him, offering mercy if he returns it.");
            System.out.println("[3] Threaten Mr. Hyde.");
            System.out.println("[4] Mock Hyde’s weakness.");

            String choice2 = sc.nextLine();

            switch(choice2){
                case "1" -> {
                    System.out.println("You: Jekyll, the potion is poison to your soul. Give it back — "
                            + "end this torment before it ends you.");
                    System.out.println("Dr. Jekyll: \"You speak true... My crime festers within me. "
                            + "Take it, before he drinks again...\"");
                    System.out.println("With trembling hands, he produces a vial — the stolen potion — "
                            + "and presses it into your palm.");
                    potionReturned = true;
                    FindTheThief.isComplete = true;
                }
                case "2" -> {
                    System.out.println("You: Return the potion, and I will not speak of this. You may yet find peace.");
                    System.out.println("Dr. Jekyll bows his head, shame dripping from his words.");
                    System.out.println("Dr. Jekyll: \"Mercy... after all I’ve done? You are kinder than I deserve. "
                            + "Take it, then.\"");
                    System.out.println("He yields the vial, tears streaking down his face.");
                    potionReturned = true;
                    FindTheThief.isComplete = true;
                }
                case "3" -> {
                    System.out.println("You: Hyde! If you resist me, I will end you where you stand.");
                    System.out.println("Mr. Hyde sneers, voice dripping venom.");
                    System.out.println("Mr. Hyde: \"End me? Hah! You end nothing. Still... this toy bores me.\"");
                    System.out.println("He tosses the potion to the ground, grinning as if mocking your triumph.");
                    potionReturned = true;
                    FindTheThief.isComplete = true;
                }
                case "4" -> {
                    System.out.println("You: Is this all Hyde amounts to? A thief hiding behind Jekyll’s weakness?");
                    System.out.println("Mr. Hyde’s grin freezes, his eyes narrowing to slits.");
                    System.out.println("Mr. Hyde: \"Mock me? Foolish insect... I will carve your laughter from your throat!\"");
                    System.out.println("Before you can react, he lunges. Teeth, claws, madness — you feel your life torn away.");
                    System.out.println("\n--- GAME OVER ---");
                    System.exit(0);
                }
                default -> System.out.println("The man claws at his face, muttering incoherently.");
            }
        }

        System.out.println("\nYou recovered the stolen potion. The quest is complete.");
        TheTheft.isComplete = true;
    }


}
