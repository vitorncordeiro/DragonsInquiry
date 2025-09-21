package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import java.util.*;

public class DragonHuntLabyrinth extends Quest {
    private final Scanner sc = new Scanner(System.in);
    private final Random rand = new Random();

    private final int SIZE = 7;
    private Cell[][] maze;
    private int px, py;
    private int exitX, exitY;
    private boolean[][] visited;
    private int orientation = 1;
    private boolean labyrinthCleared = false;

    public DragonHuntLabyrinth(Player player){
        super("dragon hunt labyrinth", "mountain", player);
    }

    @Override
    public void startQuest() {
        arrivalNarrative();
        generateMaze(SIZE);
        placeEntranceAndExit();
        visited = new boolean[SIZE][SIZE];

        System.out.println("\nYou dismount the griffon at the pale rim of the peak. Snow bites at your face.");
        System.out.println("Before you, the way in is not ruins or doors but a labyrinth of massive white stones — nature shaped into corridors.");
        System.out.println("The wind howls; a faint silver trail from the griffon's claws marks your arrival.");
        System.out.println("You step forward into the maze.");

        gameLoop();

        if (labyrinthCleared) {
            System.out.println("\nAt last you step out of the maze into a long, unnerving corridor of stone.");
            System.out.println("For a moment the silence feels like a held breath...");
            corridorNarrative();
            dragonReveals();
            dialogueLoop();
        }
    }

    @Override
    public boolean unlocksSecretDirection() { return false; }

    @Override
    public HashMap<String, String> getSecretDirection() { return null; }

    private void arrivalNarrative() {
        System.out.println("The wizard lends you his mighty griffon. Its wings beat the freezing air into a blur.");
        System.out.println("You cling to its back as it cuts through gale and snow, riding over jagged ridges and cloud-slick cliffs.");
        System.out.println("After a long, bone-cold flight, the griffon sets you down on the peak of the northern mountain.");
    }

    private void generateMaze(int n) {
        maze = new Cell[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                maze[r][c] = new Cell();
            }
        }

        Stack<int[]> stack = new Stack<>();
        int sx = 0, sy = 0;
        maze[sy][sx].visited = true;
        stack.push(new int[]{sx, sy});

        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        while (!stack.isEmpty()) {
            int[] cur = stack.peek();
            int cx = cur[0], cy = cur[1];

            List<Integer> neighbors = new ArrayList<>();
            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !maze[ny][nx].visited) {
                    neighbors.add(d);
                }
            }

            if (!neighbors.isEmpty()) {
                int dir = neighbors.get(rand.nextInt(neighbors.size()));
                int nx = cx + dx[dir];
                int ny = cy + dy[dir];
                maze[cy][cx].open[dir] = true;
                maze[ny][nx].open[(dir + 2) % 4] = true;
                maze[ny][nx].visited = true;
                stack.push(new int[]{nx, ny});
            } else {
                stack.pop();
            }
        }

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                maze[r][c].visited = false;
            }
        }
    }

    private void placeEntranceAndExit() {
        px = 0;
        py = 0;
        exitX = SIZE - 1;
        exitY = SIZE - 1;
    }

    private void gameLoop() {
        while (!labyrinthCleared) {
            describeCell();

            System.out.println("\nWhat do you do?");
            System.out.println("[1] Forward");
            System.out.println("[2] Left");
            System.out.println("[3] Right");
            System.out.println("[4] Back");
            System.out.println("[5] Look around");
            System.out.println("[6] Wait");
            System.out.println("[7] Help");

            int choice = readChoice(7);

            switch (choice) {
                case 1: handleMove("forward"); break;
                case 2: handleMove("left"); break;
                case 3: handleMove("right"); break;
                case 4: handleMove("back"); break;
                case 5: lookAround(); break;
                case 6:
                    System.out.println("You stand still. The wind whips between the white stones...");
                    maybeTriggerEncounter();
                    break;
                case 7:
                    System.out.println("Commands: [1] Forward, [2] Left, [3] Right, [4] Back, [5] Look, [6] Wait.");
                    break;
            }

            if (px == exitX && py == exitY) labyrinthCleared = true;
        }
    }

    private void handleMove(String dirCommand) {
        Integer relDir = parseRelativeDirection(dirCommand);
        if (relDir == null || !maze[py][px].open[relDir]) {
            System.out.println("The stones close in; there is no way that way.");
            return;
        }
        move(relDir);
        if (!visited[py][px]) {
            visited[py][px] = true;
            maybeTriggerEncounter();
        }
    }

    private Integer parseRelativeDirection(String action) {
        switch (action) {
            case "forward": return orientation;
            case "back": return (orientation + 2) % 4;
            case "left": return (orientation + 3) % 4;
            case "right": return (orientation + 1) % 4;
            default: return null;
        }
    }

    private void move(int dir) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        px += dx[dir];
        py += dy[dir];

        System.out.println("You move " + directionName(dir) + " through a narrow corridor of white stone.");
    }

    private String directionName(int dir) {
        switch (dir) {
            case 0: return "north";
            case 1: return "east";
            case 2: return "south";
            default: return "west";
        }
    }

    private void describeCell() {
        System.out.println("\nYou find yourself between towering white stones. The passage tightens and widens strangely.");
        List<String> options = new ArrayList<>();
        if (maze[py][px].open[orientation]) options.add("forward");
        if (maze[py][px].open[(orientation + 1) % 4]) options.add("right");
        if (maze[py][px].open[(orientation + 3) % 4]) options.add("left");
        if (maze[py][px].open[(orientation + 2) % 4]) options.add("back");

        if (options.isEmpty()) {
            System.out.println("It seems you are trapped by the stones on all sides.");
        } else {
            System.out.println("You can go: " + String.join(", ", options) + ".");
        }
    }

    private void lookAround() {
        System.out.println("Massive white stones tower on either side, edges worn smooth by wind and time.");
        System.out.println("Your footprints trail behind you in the thin dust of packed snow and ice.");
        System.out.println("Sometimes you hear distant groans of shifting stone, or whispers in the corridors.");
    }

    private void maybeTriggerEncounter() {
        int chance = rand.nextInt(100);
        if (chance < 40) {
            triggerEncounter();
        } else {
            if (chance < 60) System.out.println("A cold wind brushes your face, but nothing more.");
        }
    }

    private void triggerEncounter() {
        String[] threats = {
                "an Ice Wraith, its form drifting like a pale cloak",
                "a sudden rock-shear collapsing a tunnel",
                "a pack of frozen wyvern-kin scratching at the stone",
                "the ghost of a lost traveler, hollow-eyed"
        };
        String choice = threats[rand.nextInt(threats.length)];

        System.out.println("\nThreat! You encounter " + choice + " blocking your path.");
        System.out.println("[1] Fight");
        System.out.println("[2] Flee");

        int action = readChoice(2);

        if (action == 1) {
            int roll = rand.nextInt(100);
            if (roll < 60) {
                System.out.println("You fight bravely and overcome the threat. You press onward.");
            } else {
                System.out.println("The encounter overwhelms you. You are thrown back to the entrance.");
                sendPlayerToEntrance();
            }
        } else {
            int roll = rand.nextInt(100);
            if (roll < 70) {
                System.out.println("You slip away through a side corridor and escape.");
                retreatOneStep();
            } else {
                System.out.println("You fail to escape and are flung back to the entrance.");
                sendPlayerToEntrance();
            }
        }
    }

    private void sendPlayerToEntrance() {
        px = 0; py = 0; orientation = 1;
        System.out.println("You collapse at the entrance, breathless. The griffon's silver trail glints above.");
    }

    private void retreatOneStep() {
        int backDir = (orientation + 2) % 4;
        if (maze[py][px].open[backDir]) {
            move(backDir);
        } else {
            sendPlayerToEntrance();
        }
    }

    private static class Cell {
        boolean[] open = new boolean[4];
        boolean visited = false;
    }

    private void corridorNarrative() {
        System.out.println("\nYou step carefully into the long corridor.");
        System.out.println("The white stones stretch endlessly, silence pressing in.");
        System.out.println("Your footsteps echo hollowly, like walking on something not solid.");
        System.out.println("Snow dust trickles from the ceiling… and then stops.");
    }

    private void dragonReveals() {
        System.out.println("\nThe ground trembles. Two vast eyes open beneath your feet, glowing with molten gold.");
        System.out.println("The 'corridor' shifts and rises. You realize with horror: you have been walking across the body of a slumbering DRAGON.");
        System.out.println("Its head lifts, and its voice thunders inside your mind:");
        System.out.println("\"Mortal... You tread upon my back without knowing. Few reach this place alive.\"");
    }

    private void dialogueLoop() {
        boolean heardTruth = false;

        while (true) {
            System.out.println("\nWhat do you do?");
            System.out.println("[1] Speak");
            System.out.println("[2] Attack");
            System.out.println("[3] Listen");
            System.out.println("[4] Wait");

            int choice = readChoice(4);

            if (choice == 1) {
                System.out.println("You stammer out your plight. The dragon narrows its eyes.");
                System.out.println("\"That sorcerer is no savior. He seeks my death to break the seals I guard.\"");
            } else if (choice == 2) {
                confirmDarkEnding();
                return;
            } else if (choice == 3) {
                if (!heardTruth) {
                    System.out.println("\"I am the jailor of the Pandora Vessel. Countless horrors are bound within.");
                    System.out.println("Your 'wizard' is kin to those horrors. If I fall, the seal shatters.\"");
                    heardTruth = true;
                    System.out.println("The dragon fixes you with a burning gaze: \"You must now choose.\"");
                } else {
                    System.out.println("The dragon waits, vast and silent.");
                }
            } else {
                System.out.println("You wait. The dragon's golden eyes remain locked on you.");
            }

            if (heardTruth) {
                System.out.println("\nChoices:");
                System.out.println("[1] Use the powder");
                System.out.println("[2] Accept fusion");
                System.out.println("[3] Wait");

                int finalChoice = readChoice(3);
                if (finalChoice == 1) { confirmDarkEnding(); return; }
                if (finalChoice == 2) { fusionEnding(); return; }
            }
        }
    }

    private void confirmDarkEnding() {
        System.out.println("\nYou release the dust as the wizard instructed.");
        System.out.println("The dragon breathes in — its body shudders, scales turning ashen.");
        System.out.println("With a final roar, it collapses. The SEAL is broken.");
        System.out.println("Shadows and horrors pour into the world. The wizard’s laughter echoes afar.");
        System.out.println("\n--- DARK ENDING: The Pandora Vessel is undone. The Age of Ruin begins. ---");
    }

    private void fusionEnding() {
        System.out.println("\nThe dragon lowers its head. \"Then accept the bond.\"");
        System.out.println("A blazing light engulfs you. Your body merges with its essence.");
        System.out.println("\"Together,\" the dragon roars, \"we shall confront the false wizard.\"");
        Quest quest = new FinalSkyBattle(getPlayer());
        quest.startQuest();
    }

    private int readChoice(int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= 1 && choice <= max) return choice;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid choice. Enter a number between 1 and " + max + ".");
        }
    }
}
