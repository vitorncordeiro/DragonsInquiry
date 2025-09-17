package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import main.DataSetup.entities.items.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TheDemon extends Quest{
    Scanner sc = new Scanner(System.in);


    public TheDemon(Player player){
        super("the demon", "demons room", player);
    }
    @Override
    public void startQuest() {

        while(true) {
            String decision = sc.nextLine().toLowerCase();

            if (decision.contains("kill") || decision.contains("attack") || decision.contains("defeat") || decision.contains("provoke")) {
                System.out.println("Demon: Hahaha! Do you think a mere mortal can kill me?");
                sc.nextLine();
                System.out.println("With a swift gesture of its clawed hand, a sphere of searing flame bursts into existence,\ncrackling with infernal energy. The heat scorches your skin before it even strikes.");
                String playerTurn = sc.nextLine().toLowerCase();
                if(playerTurn.contains("avoid") || playerTurn.contains("hide")|| playerTurn.contains("dodge")){
                    System.out.println("Oddly enough, your action was successful");
                    System.out.println("Meanwhile, the rest of the room begins to catch fire, with clear signs of collapse.");
                    return;
                }
                System.out.println("In an instant, the fireball engulfs you, blinding light and unbearable agony consumes your body");
                System.out.println("--Game over--");
                System.exit(0);
            } else if (decision.equals("banishment")) {
                System.out.println("The demon recoils as the incantation leaves your lips. Light binds its form, and with a \n" +
                        "roar torn between rage and fear, it is torn from this plane in a blinding flash.\n" +
                        "As it vanishes, the iron bars sealing the western door crack and disintegrate into dust.\n" +
                        "Silence falls. The way west is clear.");
                System.out.println("Although he has completely disappeared, the ring on his finger remains, falling to the ground and appearing to serve as a good belt for you.");
                String beltDecision = sc.nextLine().toLowerCase();
                System.out.println("Regardless of your thoughts, your instincts take over, and you take the belt");
                Quest.completeQuest(TheDemon.class);
                break;
            } else {
                System.out.println("\n" +
                        "Demon: My understanding of human languages is worse than the smell of your flesh, try something I understand");
            }
        }
    }

    @Override
    public boolean unlocksSecretDirection() {
        return true;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return new HashMap<>(Map.of("N", ""));
    }
}
