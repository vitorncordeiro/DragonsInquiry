package main.DataSetup.quests;

import main.DataSetup.entities.Player;
import java.util.*;

public class FinalSkyBattle extends Quest {

    private final Scanner sc = new Scanner(System.in);
    private final Random rand = new Random();

    private int mageHealth = 100;
    private int mageShield = 3;
    private int playerHP = 50;
    private int dragonCharge = 0;
    private final int CHARGE_NEEDED = 3;
    private boolean fightOver = false;

    public FinalSkyBattle(Player player){
        super("final sky battle", "mountain", player);
    }

    @Override
    public void startQuest() {
        introNarrative();
        phaseOneSkyChase();
        if (fightOver) return;
        phaseTwoRuneDuel();
        if (fightOver) return;
        phaseThreeFinalConfrontation();
        epilogue();
    }

    @Override
    public boolean unlocksSecretDirection() { return false; }

    @Override
    public HashMap<String, String> getSecretDirection() { return null; }

    /* ---------- Intro ---------- */
    private void introNarrative() {
        System.out.println("Winds claw at your face as you and the dragon circle the mage's tower.");
        System.out.println("The wizard hovers at the tower's crown, runes whirling around him like a storm.");
        System.out.println("There is no ground here to hide behind — only sky, wind, and skill.");
        pause(900);
    }

    /* ---------- Phase 1: Sky Chase ---------- */
    private void phaseOneSkyChase() {
        System.out.println("\n--- PHASE 1: SKY CHASE ---");
        System.out.println("Counter the wizard’s elemental volleys!");

        int rounds = 5;
        for (int r = 1; r <= rounds && !fightOver; r++) {
            System.out.println("\nVolley " + r + " of " + rounds + ".");
            pause(500);

            int spells = 1 + rand.nextInt(2);
            for (int s = 0; s < spells && !fightOver; s++) {
                String spell = randomSpell();
                System.out.println("The wizard casts a " + spell.toUpperCase() + " spell!");

                String[] options = {"Absorb", "Reflect", "Dodge", "Fly into a cloud"};
                int choice = readChoice(options);

                String response = options[choice - 1].toLowerCase();

                if (response.contains("cloud")) {
                    if (rand.nextInt(100) < 50) {
                        System.out.println("You streak through the cloud and avoid the spell!");
                        if (rand.nextInt(100) < 30) {
                            dragonCharge++;
                            System.out.println("Dragon’s energy hums. Charge +" + 1 + ".");
                        }
                    } else {
                        System.out.println("Lightning strikes! You take damage.");
                        applyDamage(8);
                    }
                } else if (isCorrectResponseForSpell(spell, response)) {
                    System.out.println("Your response counters the spell!");
                    if (mageShield > 0) {
                        mageShield--;
                        System.out.println("The wizard’s shield cracks! Remaining layers: " + mageShield);
                    } else {
                        mageHealth -= 6;
                        System.out.println("The wizard is struck! Health: " + mageHealth);
                    }
                    dragonCharge++;
                } else {
                    System.out.println("The spell hits you!");
                    int dmg = spell.equals("fire") ? 10 : spell.equals("ice") ? 8 : 12;
                    applyDamage(dmg);
                }

                if (playerHP <= 0) {
                    System.out.println("You nearly fall — but the dragon rescues you. Penalty applied!");
                    mageHealth += 10;
                    dragonCharge = 0;
                    playerHP = 12;
                }
                if (mageHealth <= 0) { fightOver = true; return; }

                if (dragonCharge >= CHARGE_NEEDED) {
                    System.out.println("\nYour dragon is ready to breathe fire!");
                    String[] unleashOptions = {"Unleash breath", "Hold back"};
                    int unleashChoice = readChoice(unleashOptions);
                    if (unleashChoice == 1) {
                        System.out.println("The breath scorches the wizard!");
                        mageHealth -= 18;
                        dragonCharge = 0;
                        if (mageHealth <= 0) { fightOver = true; return; }
                    } else {
                        System.out.println("You hold the dragon’s power in check...");
                    }
                }
            }
        }
    }

    /* ---------- Phase 2: Rune Duel ---------- */
    private void phaseTwoRuneDuel() {
        System.out.println("\n--- PHASE 2: RUNE DUEL ---");
        int sequencesToSolve = Math.max(1, mageShield);

        for (int i = 0; i < sequencesToSolve && !fightOver; i++) {
            List<String> seq = generateRuneSequence(3 + i);
            System.out.println("The runes flash: " + String.join(" ", seq));
            pause(1000);

            String[] answers = {"Repeat correctly", "Guess randomly", "Do nothing"};
            int choice = readChoice(answers);

            if (choice == 1) {
                System.out.println("You mirror the runes — shield layer shatters!");
                mageShield = Math.max(0, mageShield - 1);
                mageHealth -= 12;
            } else {
                System.out.println("You fail! The runes backlash.");
                applyDamage(10);
            }

            if (playerHP <= 0) { playerHP = 10; }
            if (mageHealth <= 0) { fightOver = true; return; }
        }
    }

    /* ---------- Phase 3: Final Confrontation ---------- */
    private void phaseThreeFinalConfrontation() {
        System.out.println("\n--- PHASE 3: FINAL CONFRONTATION ---");

        int exchanges = 3;
        for (int e = 1; e <= exchanges && !fightOver; e++) {
            String spell = randomSpellWeighted();
            System.out.println("The wizard casts a " + spell.toUpperCase() + "!");

            String[] options = {"Absorb", "Reflect", "Dodge"};
            int choice = readChoice(options);
            String response = options[choice - 1].toLowerCase();

            if (isCorrectResponseForSpell(spell, response)) {
                mageHealth -= 12;
                dragonCharge++;
            } else {
                applyDamage(14);
            }

            if (dragonCharge >= CHARGE_NEEDED) {
                System.out.println("Dragon’s breath is ready again!");
                String[] breathOptions = {"Unleash", "Save power"};
                if (readChoice(breathOptions) == 1) {
                    mageHealth -= 28;
                    dragonCharge = 0;
                }
            }

            if (mageHealth <= 0) { fightOver = true; return; }
        }

        System.out.println("Final decision:");
        String[] endings = {"Show mercy", "Strike down", "Sacrifice dragon"};
        int ending = readChoice(endings);

        switch (ending) {
            case 1: System.out.println("Ending: The wizard flees."); break;
            case 2: System.out.println("Ending: The wizard is slain."); break;
            case 3: System.out.println("Ending: The dragon sacrifices itself."); break;
        }
    }

    /* ---------- Utilities ---------- */
    private int readChoice(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ") " + options[i]);
        }
        int choice;
        while (true) {
            System.out.print("> ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= 1 && choice <= options.length) return choice;
            } catch (Exception ignored) {}
            System.out.println("Invalid option. Try again.");
        }
    }

    private void applyDamage(int dmg) {
        playerHP -= dmg;
        System.out.println("You take " + dmg + " damage. (HP: " + Math.max(0, playerHP) + ")");
    }

    private String randomSpell() {
        String[] spells = {"fire", "ice", "void"};
        return spells[rand.nextInt(spells.length)];
    }

    private String randomSpellWeighted() {
        int r = rand.nextInt(100);
        if (r < 45) return "fire";
        if (r < 80) return "void";
        return "ice";
    }

    private boolean isCorrectResponseForSpell(String spell, String response) {
        return (spell.equals("fire") && response.equals("absorb"))
                || (spell.equals("ice") && response.equals("reflect"))
                || (spell.equals("void") && response.equals("dodge"));
    }

    private List<String> generateRuneSequence(int length) {
        String[] runes = {"A","B","C","D"};
        List<String> seq = new ArrayList<>();
        for (int i = 0; i < length; i++) seq.add(runes[rand.nextInt(runes.length)]);
        return seq;
    }

    private void pause(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    private void epilogue() {
        System.out.println("\nThe storm fades. The battle is over.");
    }
}
