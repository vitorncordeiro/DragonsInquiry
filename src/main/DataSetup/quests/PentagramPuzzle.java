package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.*;

public class PentagramPuzzle extends Quest{

    public PentagramPuzzle(Player player){
        super("take the mirror", "treasure chamber", player);
    }

    private static final Map<String, String> expected = new LinkedHashMap<>();
    static {
        expected.put("SW", "N");
        expected.put("N",  "SE");
        expected.put("SE", "W");
        expected.put("W",  "E");
        expected.put("E",  "SW");
    }

    private static final int MAX_ERRORS = 3;

    public void startQuest() {
        Scanner sc = new Scanner(System.in);
        int errors = 0;
        boolean solved = false;

        System.out.println("""
                You've entered a dimly lit chamber, except for a shaft of light coming from the southwest corner of the
                chamber. The chamber has small mirrors hanging on the wall at each of the cardinal and intermediate points.
                """);
        System.out.println("In the middle of the chamber, a pedestal with a serpent's head catches your eye, with an engraving.");
        System.out.println("""
                "The serpent coils in the root of the world, hidden in the southwest.
                It climbs toward the crown above,
                then slithers down into the abyss of the southeast.
                It winds its body across the western stone,
                strikes toward the dawn in the east,
                and returns to the dark coil from whence it came."
                """);
        String current = "SW"; // beam starts at SW

        while (true) {
            String required = expected.get(current);


            String input = sc.nextLine().trim();
            String dir = normalizeDirection(input);

            if (dir == null) {
                continue;
            }

            if (dir.equals(required)) {
                System.out.println("The light hits the mirror at " + current + " and points to " + required + ".");
                current = required;

                // if the beam has returned to SW, puzzle is solved
                if (current.equals("SW")) {
                    solved = true;
                    break;
                }
            } else {
                errors++;
                if (errors >= MAX_ERRORS) {
                    System.out.println("\nThe ground shakes violently. Stones fall from above...");
                    System.out.println("The cave collapses and you are buried under the rubble.");
                    System.out.println("--GAME OVER--");
                    System.exit(0);
                    return;
                }else{
                    System.out.println("You feel the chamber shake slightly, and suddenly, the serpent's eyes light up.");
                }
            }
        }

        if (solved) {
            System.out.println("\nThe mirrors align perfectly. The beam traces a glowing star.");
            System.out.println("A hidden mechanism clicks. The pedestal releases the hand mirror!");
            System.out.println("You take the hand mirror");
            System.out.println("simultaneously, a passage, which appears to be the last, opens to the north.");
        }
    }

    private String normalizeDirection(String raw) {
        if (raw == null) return null;
        String s = raw.trim().toUpperCase(Locale.ROOT);

        switch (s) {
            case "N": case "NORTH": return "N";
            case "NE": case "NORTHEAST": return "NE";
            case "E": case "EAST": return "E";
            case "SE": case "SOUTHEAST": return "SE";
            case "S": case "SOUTH": return "S";
            case "SW": case "SOUTHWEST": return "SW";
            case "W": case "WEST": return "W";
            case "NW": case "NORTHWEST": return "NW";
            default: return null;
        }
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



