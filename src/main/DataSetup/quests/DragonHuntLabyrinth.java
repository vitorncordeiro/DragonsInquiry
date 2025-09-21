package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.*;

public class DragonHuntLabyrinth extends Quest {
    private final Scanner sc = new Scanner(System.in);
    private final Random rand = new Random();

    // Maze parameters
    private final int SIZE = 7; // change to make the labyrinth larger/smaller
    private Cell[][] maze;
    private int px, py;           // player position (x = col, y = row)
    private int exitX, exitY;     // exit coordinates
    private boolean[][] visited;  // visited cells (for encounter triggers)

    // Orientation: 0 = NORTH, 1 = EAST, 2 = SOUTH, 3 = WEST
    private int orientation = 1; // start facing EAST into the labyrinth

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

        // player starts at entrance (px,py)
        gameLoop();

        if (labyrinthCleared) {
            System.out.println("\nAt last you step out of the maze into a long, unnerving corridor of stone.");
            System.out.println("For a moment the silence feels like a held breath..."); // leads into next scene
            corridorNarrative();
            dragonReveals();
            dialogueLoop();
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

    /* ---------- Arrival text (griffon detail) ---------- */
    private void arrivalNarrative() {
        System.out.println("The wizard lends you his mighty griffon. Its wings beat the freezing air into a blur.");
        System.out.println("You cling to its back as it cuts through gale and snow, riding over jagged ridges and cloud-slick cliffs.");
        System.out.println("After a long, bone-cold flight, the griffon sets you down on the peak of the northern mountain.");
    }

    /* ---------- Maze generation (recursive backtracker) ---------- */
    private void generateMaze(int n) {
        maze = new Cell[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                maze[r][c] = new Cell();
            }
        }

        // carve passages
        Stack<int[]> stack = new Stack<>();
        int sx = 0, sy = 0;
        maze[sy][sx].visited = true;
        stack.push(new int[]{sx, sy});

        int[] dx = {0, 1, 0, -1}; // N,E,S,W relative to grid coords
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
                // open passage both ways
                maze[cy][cx].open[dir] = true;
                maze[ny][nx].open[(dir + 2) % 4] = true;
                maze[ny][nx].visited = true;
                stack.push(new int[]{nx, ny});
            } else {
                stack.pop();
            }
        }

        // reset visited flags for encounter handling
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                maze[r][c].visited = false;
            }
        }
    }

    /* ---------- Choose entrance and exit ---------- */
    private void placeEntranceAndExit() {
        // Entrance at top-left (0,0) and exit at bottom-right (SIZE-1,SIZE-1)
        px = 0;
        py = 0;
        exitX = SIZE - 1;
        exitY = SIZE - 1;
    }

    /* ---------- Main exploration loop ---------- */
    private void gameLoop() {
        while (!labyrinthCleared) {

            // describe the current cell
            describeCell();

            System.out.print("\nWhat do you do? (forward, left, right, back, look, wait, help)\n> ");
            String action = sc.nextLine().trim().toLowerCase();

            if (action.equals("help")) {
                System.out.println("Commands: forward (move ahead), left (move left), right (move right), back (move back),");
                System.out.println("          look (inspect stones), wait (stay), help (show this).");
                continue;
            } else if (action.equals("look")) {
                lookAround();
                continue;
            } else if (action.equals("wait")) {
                System.out.println("You stand still. The wind whips between the white stones. Nothing changes... for now.");
                // small chance of random encounter even if you wait
                maybeTriggerEncounter();
                continue;
            }

            // interpret relative movement
            Integer relDir = parseRelativeDirection(action);
            if (relDir == null) {
                System.out.println("You hesitate and the labrynth's cold echoes. Try a valid command.");
                continue;
            }

            // check if there's a passage in that relative direction from the current cell
            int dir = relDir;
            if (!maze[py][px].open[dir]) {
                System.out.println("The stones close in; there is no way that way.");
                continue;
            }

            // apply movement
            move(dir);

            // on entering a new cell: maybe an encounter
            if (!visited[py][px]) {
                visited[py][px] = true;
                maybeTriggerEncounter();
            }

            // check exit
            if (px == exitX && py == exitY) {
                labyrinthCleared = true;
                break;
            }
        }
    }

    /* ---------- Map relative commands to absolute directions ---------- */
    // returns absolute direction index 0..3 or null if invalid
    private Integer parseRelativeDirection(String action) {
        switch (action) {
            case "forward":
                return orientation; // go ahead
            case "back":
                return (orientation + 2) % 4;
            case "left":
                return (orientation + 3) % 4;
            case "right":
                return (orientation + 1) % 4;
            default:
                return null;
        }
    }

    /* ---------- Move player in absolute direction ---------- */
    private void move(int dir) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        px += dx[dir];
        py += dy[dir];

        System.out.println("You move " + directionName(dir) + " through a narrow corridor of white stone.");
        // orientation remains the same (you keep facing the same way)
    }

    private String directionName(int dir) {
        switch (dir) {
            case 0: return "north";
            case 1: return "east";
            case 2: return "south";
            default: return "west";
        }
    }

    /* ---------- Describe available passages relative to player's orientation ---------- */
    private void describeCell() {
        System.out.println("\nYou find yourself between towering white stones. The passage tightens and widens in strange ways.");
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
        System.out.println("Sometimes you can hear distant groans of shifting stone, or the hollow whisper of something moving in the corridors.");
    }

    /* ---------- Encounters: random threats in unvisited cells ---------- */
    private void maybeTriggerEncounter() {
        int chance = rand.nextInt(100);
        if (chance < 40) { // 40% chance of encounter in a fresh cell
            triggerEncounter();
        } else {
            // small atmospheric text
            if (chance < 60) System.out.println("A cold wind brushes your face, but nothing more.");
        }
    }

    private void triggerEncounter() {
        String[] threats = {
                "an Ice Wraith, its form drifting like a pale cloak",
                "a sudden rock-shear — a narrow tunnel begins to collapse",
                "a pack of frozen wyvern-kin scratching at the stone",
                "the ghost of a lost traveler, eyes hollow and pleading"
        };
        String choice = threats[rand.nextInt(threats.length)];

        System.out.println("\nThreat! You encounter " + choice + " blocking your path.");
        System.out.println("You can attempt to (fight) or (flee). What do you do?");
        String action = sc.nextLine().trim().toLowerCase();

        if (action.equals("fight")) {
            // simple chance to win
            int roll = rand.nextInt(100);
            if (roll < 60) {
                System.out.println("You fight bravely and overcome the threat. You press onward.");
            } else {
                System.out.println("The encounter overwhelms you. You are thrown back toward the labyrinth entrance.");
                sendPlayerToEntrance();
            }
        } else if (action.equals("flee")) {
            // chance to flee successfully
            int roll = rand.nextInt(100);
            if (roll < 70) {
                System.out.println("You manage to slip away through a side corridor and put distance between you and the threat.");
                // move player one step back if possible
                retreatOneStep();
            } else {
                System.out.println("You fail to escape cleanly and are flung back to the labyrinth's entrance.");
                sendPlayerToEntrance();
            }
        } else {
            System.out.println("Hesitation costs you — the threat takes advantage. You are hurled back to the entrance.");
            sendPlayerToEntrance();
        }
    }

    private void sendPlayerToEntrance() {
        px = 0;
        py = 0;
        orientation = 1; // reset orientation to EAST
        System.out.println("You collapse at the entrance, breathless. The griffon's silver trail glints far above.");
    }

    private void retreatOneStep() {
        // attempt to step backward (opposite of orientation) if possible; else send to entrance
        int backDir = (orientation + 2) % 4;
        if (maze[py][px].open[backDir]) {
            move(backDir);
        } else {
            sendPlayerToEntrance();
        }
    }

    /* ---------- Inner Cell class ---------- */
    private static class Cell {
        // openings to N=0, E=1, S=2, W=3
        boolean[] open = new boolean[4];
        boolean visited = false;
    }


    private void corridorNarrative() {
        System.out.println("\nYou step carefully into the long corridor.");
        System.out.println("The white stones stretch out endlessly, the silence pressing in around you.");
        System.out.println("Your footsteps echo strangely — hollow, like walking on something not quite solid.");
        System.out.println("Snow dust trickles from the ceiling… and then stops.");
    }

    private void dragonReveals() {
        System.out.println("\nThe ground beneath you trembles.");
        System.out.println("Two vast eyes open beneath your feet, glowing with molten gold.");
        System.out.println("The 'corridor' shifts and rises. You realize in horror: you have been walking across the body of a slumbering DRAGON.");
        System.out.println("Its head lifts slowly, and its voice thunders inside your mind:");
        System.out.println("\n\"Mortal... You tread upon my back without knowing. Few reach this place alive.\"");
    }

    private void dialogueLoop() {
        boolean heardTruth = false;
        while (true) {
            System.out.print("\nWhat do you do? (speak, attack, listen, wait)\n> ");
            String input = sc.nextLine().trim().toLowerCase();

            switch (input) {
                case "speak":
                    System.out.println("\nYou stammer out your plight — the curse upon you, the wizard who sent you here.");
                    System.out.println("The dragon narrows its eyes.");
                    System.out.println("\"That sorcerer is no savior. He seeks my death to break the seals I guard.\"");
                    break;

                case "listen":
                    if (!heardTruth) {
                        System.out.println("\nThe dragon’s voice deepens, resonant with sorrow and power.");
                        System.out.println("\"I am the jailor of the Pandora Vessel. Within it, countless horrors are bound.");
                        System.out.println("Your 'wizard' is kin to those horrors — one of their exiled lords. He craves release.\"");
                        System.out.println("\"If I fall, the seal shatters. All malice and ruin will pour into this world.\"");
                        heardTruth = true;
                        System.out.println("\nThe dragon fixes you with a burning gaze: \"You must now choose.\"");
                    } else {
                        System.out.println("The dragon waits, its vast chest rising and falling like a mountain in rhythm.");
                    }
                    break;

                case "attack":
                    System.out.println("\nYour hand trembles toward the pouch the wizard gave you — the inhalable dust.");
                    System.out.println("The dragon tilts its head knowingly. \"So... you bear the poison of betrayal.\"");
                    confirmDarkEnding();
                    return;

                case "wait":
                    System.out.println("You hesitate. The dragon’s golden eyes remain locked on you, patient, eternal.");
                    break;

                default:
                    System.out.println("The dragon snorts. \"Choose your path, mortal. Do not waste my time.\"");
            }

            if (heardTruth) {
                System.out.println("\nChoices available: (use powder, accept fusion, wait)");
                System.out.print("> ");
                String choice = sc.nextLine().trim().toLowerCase();
                if (choice.equals("use powder")) {
                    confirmDarkEnding();
                    return;
                } else if (choice.equals("accept fusion")) {
                    fusionEnding();
                    return;
                } else if (choice.equals("wait")) {
                    System.out.println("The snow swirls as silence stretches. The dragon’s gaze never wavers.");
                } else {
                    System.out.println("The dragon growls: \"Speak clearly, mortal.\"");
                }
            }
        }
    }

    private void confirmDarkEnding() {
        System.out.println("\nYou release the inhalable dust as the wizard instructed.");
        System.out.println("The dragon breathes in — its massive body shudders, scales turning ashen.");
        System.out.println("With a final roar, it collapses. The ground cracks. A thunderous sound follows — the SEAL is broken.");
        System.out.println("From the rift gush shadows, voices of agony, beasts of despair. The sky blackens.");
        System.out.println("The wizard’s laughter echoes from afar as all the world begins to burn.");
        System.out.println("\n--- DARK ENDING: The Pandora Vessel is undone. The Age of Ruin begins. ---");
        System.out.println("thanks for playing, but you are a monster!");
    }

    private void fusionEnding() {
        System.out.println("\nThe dragon lowers its colossal head. \"Then accept the bond.\"");
        System.out.println("It chants words older than the mountain. A blazing light engulfs you.");
        System.out.println("Your body merges with its essence — scales shimmer on your arms, your heart pounds with ancient fire.");
        System.out.println("\n\"Together,\" the dragon’s voice roars within you, \"we shall confront the false wizard.\"");
        System.out.println("Your quest is no longer survival, but vengeance — and the fate of the world.");
        Quest quest = new FinalSkyBattle(getPlayer());
        quest.startQuest();
    }
}
