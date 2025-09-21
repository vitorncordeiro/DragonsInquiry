package main.DataSetup.quests;

import main.DataSetup.entities.Player;

import java.util.*;

public class FinalSkyBattle extends Quest {

    private final Scanner sc = new Scanner(System.in);
    private final Random rand = new Random();

    // Combat / state variables
    private int mageHealth = 100;        // depletes to 0 to "defeat"
    private int mageShield = 3;          // number of successful shield layers to break via Phase 2 or good counters
    private int playerHP = 50;           // player health (if 0 you get a hard penalty but not immediate game over)
    private int dragonCharge = 0;        // when >= CHARGE_NEEDED, breath window opens
    private final int CHARGE_NEEDED = 3;
    private boolean dragonFused = true;  // assume fusion happened (player + dragon)
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
    public boolean unlocksSecretDirection() {
        return false;
    }

    @Override
    public HashMap<String, String> getSecretDirection() {
        return null;
    }

    /* ---------- Intro ---------- */
    private void introNarrative() {
        System.out.println("Winds claw at your face as you and the dragon circle the mage's tower.");
        System.out.println("The wizard hovers at the tower's crown, runes whirling around him like a storm.");
        System.out.println("Lightning rends the clouds; below, the tower's stones glint like teeth.");
        System.out.println("There is no ground here to hide behind — only sky, wind, and skill.");
        pause(900);
    }

    /* ---------- Phase 1: Sky Chase / Elemental volley mechanic ---------- */
    private void phaseOneSkyChase() {
        System.out.println("\n--- PHASE 1: SKY CHASE ---");
        System.out.println("The wizard launches a volley of elemental spells. You must react with the correct counter:");
        System.out.println("  fire  -> type 'absorb'  (dragon's flame)");
        System.out.println("  ice   -> type 'reflect' (scales refract)");
        System.out.println("  void  -> type 'dodge'   (evade into the wind)");
        System.out.println("You can also choose to 'cloud' to fly through storm clouds (risky but may avoid attacks).");
        pause(800);

        int rounds = 5; // number of volleys in phase 1
        for (int r = 1; r <= rounds && !fightOver; r++) {
            System.out.println("\nVolley " + r + " of " + rounds + ". Prepare!");
            pause(600);

            // Wizard randomly uses 1-2 spells this volley
            int spells = 1 + rand.nextInt(2);
            for (int s = 0; s < spells && !fightOver; s++) {
                String spell = randomSpell();
                System.out.println("\nThe wizard fires a " + spell.toUpperCase() + " spell!");
                System.out.print("Your action (absorb / reflect / dodge / cloud): ");
                String input = sc.nextLine().trim().toLowerCase();

                // cloud choice: 50% to avoid entire spell but 30% chance of lightning hit
                if (input.equals("cloud")) {
                    if (rand.nextInt(100) < 50) {
                        System.out.println("You streak through a cloud and avoid the spell!");
                        // small chance to charge dragon
                        if (rand.nextInt(100) < 30) {
                            dragonCharge++;
                            System.out.println("Dragon's essence hums — charge +" + (dragonCharge>0?1:0) + ".");
                        }
                        continue;
                    } else {
                        System.out.println("Lightning shreds the cloud as you pass! You take collision damage.");
                        applyDamage(8);
                        continue;
                    }
                }

                boolean correct = isCorrectResponseForSpell(spell, input);
                if (correct) {
                    System.out.println("Your response works — the spell is countered!");
                    // reward: small damage to mage or shield break
                    if (mageShield > 0) {
                        mageShield--;
                        System.out.println("A shard of the wizard's shield fractures! (Shield layers left: " + mageShield + ")");
                    } else {
                        mageHealth -= 6;
                        System.out.println("The wizard grunts as the spell rebounds — mage health -" + 6 + " (" + mageHealth + ").");
                    }
                    // charge the dragon slightly
                    dragonCharge++;
                    System.out.println("Dragon charge +" + 1 + " (current: " + dragonCharge + ").");
                } else {
                    System.out.println("Your reaction fails — the spell hits you!");
                    int dmg = spell.equals("fire") ? 10 : spell.equals("ice") ? 8 : 12;
                    applyDamage(dmg);
                    // mage may regain a little focus when we get hit
                    if (rand.nextInt(100) < 30) {
                        mageHealth += 3;
                        System.out.println("The wizard laughs and draws some strength back. (mage health +" + 3 + ")");
                    }
                }

                // immediate check for player HP falling too low
                if (playerHP <= 0) {
                    System.out.println("\nYour body reels and you nearly fall from the sky. The dragon snatches you in its claws and pulls you clear.");
                    System.out.println("You survive, but the momentum is gone — the wizard regains composure.");
                    // penalty: mage heals and dragon charge resets
                    mageHealth += 10;
                    dragonCharge = Math.max(0, dragonCharge - 2);
                    System.out.println("Mage recovers slightly. Dragon charge -" + 2 + " (current: " + dragonCharge + ").");
                    playerHP = 12; // partial recovery to keep the scene dramatic
                }

                if (mageHealth <= 0) {
                    System.out.println("\nThe wizard staggers — his energy falters!");
                    fightOver = true;
                    return;
                }

                // if dragon full charge, grant a breath window mid-phase
                if (dragonCharge >= CHARGE_NEEDED) {
                    System.out.println("\nYour dragon roars — its breath is ready. A bright window opens to 'unleash'.");
                    boolean success = timedBreathWindow();
                    if (success) {
                        System.out.println("Your breath scorches through the wizard's defenses, searing him!");
                        mageHealth -= 18;
                        dragonCharge = 0;
                        System.out.println("Mage health -" + 18 + " (" + mageHealth + ").");
                        if (mageHealth <= 0) { fightOver = true; return; }
                    } else {
                        System.out.println("The breath misfires and a backlash bruises you both.");
                        applyDamage(12);
                        dragonCharge = 0;
                    }
                }

                pause(400);
            }
        }

        System.out.println("\nThe volley storm subsides. This round leaves the wizard shaken, but not defeated.");
        pause(700);
    }

    /* ---------- Phase 2: Rune Duel (memory pattern) ---------- */
    private void phaseTwoRuneDuel() {
        System.out.println("\n--- PHASE 2: RUNE DUEL ---");
        System.out.println("The wizard summons glowing runes that orbit him — these feed his ward.");
        System.out.println("You must replicate the rune sequence to shatter a layer of the ward.");
        pause(700);

        int sequencesToSolve = Math.max(1, mageShield); // break remaining shields
        for (int i = 0; i < sequencesToSolve && !fightOver; i++) {
            List<String> seq = generateRuneSequence(3 + i); // increasing difficulty
            System.out.println("\nThe runes flash in sequence:");
            showSequenceBriefly(seq);

            System.out.print("Repeat the sequence (space-separated rune letters, e.g. A B C): ");
            String answer = sc.nextLine().trim().toUpperCase();
            List<String> parts = Arrays.asList(answer.split("\\s+"));

            if (parts.equals(seq)) {
                System.out.println("You mirror the runes perfectly — a section of the ward collapses!");
                mageShield = Math.max(0, mageShield - 1);
                System.out.println("Shield layers left: " + mageShield);
                mageHealth -= 12;
                System.out.println("The wizard recoils as the wards crack (mage health -" + 12 + " -> " + mageHealth + ").");
            } else {
                System.out.println("You fail to match the runes. A backlash slams into you!");
                applyDamage(10);
                // small chance wizard counters
                if (rand.nextInt(100) < 40) {
                    mageHealth += 6;
                    System.out.println("The wizard uses your mistake to siphon power. (mage health +" + 6 + ")");
                }
            }

            if (playerHP <= 0) {
                System.out.println("The strain is too much; the dragon snatches you down and circles to recover.");
                playerHP = 10; // survive but penalized
            }

            if (mageHealth <= 0) {
                System.out.println("\nThe wizard's energy collapses under the strain! He staggers.");
                fightOver = true;
                return;
            }
        }
    }

    /* ---------- Phase 3: Final Confrontation (elemental + breath combo & final choice) ---------- */
    private void phaseThreeFinalConfrontation() {
        System.out.println("\n--- PHASE 3: FINAL CONFRONTATION ---");
        System.out.println("The wizard, now exposed, prepares a cataclysmic storm — this is the final exchange.");
        pause(700);

        // A few high-stakes exchanges
        int exchanges = 4;
        for (int e = 1; e <= exchanges && !fightOver; e++) {
            System.out.println("\nExchange " + e + " of " + exchanges + ": The wizard gathers power!");
            String spell = randomSpellWeighted(); // more dangerous
            System.out.println("He unleashes a " + spell.toUpperCase() + " onslaught!");
            System.out.print("Your action (absorb / reflect / dodge): ");
            String input = sc.nextLine().trim().toLowerCase();

            boolean correct = isCorrectResponseForSpell(spell, input);
            if (correct) {
                System.out.println("Your maneuver succeeds and cuts deep into his remaining strength!");
                mageHealth -= 12;
                dragonCharge++;
                System.out.println("Mage health -" + 12 + " (" + mageHealth + "). Dragon charge +" + 1 + " (current: " + dragonCharge + ").");
            } else {
                System.out.println("You fail to handle the onslaught correctly — it hits you hard.");
                applyDamage(14);
                // wizard uses opening to buff himself
                if (rand.nextInt(100) < 35) {
                    mageHealth += 8;
                    System.out.println("The wizard channels your falter into a surge. (mage health +" + 8 + ")");
                }
            }

            // breath opportunity
            if (dragonCharge >= CHARGE_NEEDED) {
                System.out.println("\nThe dragon's maw gapes — another chance to unleash a finishing breath.");
                boolean success = timedBreathWindow();
                if (success) {
                    System.out.println("The breath cuts across the wizard like a comet!");
                    mageHealth -= 28;
                    System.out.println("Mage health -" + 28 + " (" + mageHealth + ").");
                    dragonCharge = 0;
                } else {
                    System.out.println("The timing is off; the breath flares uselessly and you are battered.");
                    applyDamage(16);
                    dragonCharge = 0;
                }
            }

            if (playerHP <= 0) {
                System.out.println("You are nearly broken by the barrage; the dragon pulls you away and buys time.");
                playerHP = 8;
            }

            if (mageHealth <= 0) {
                System.out.println("\nThe wizard collapses, his staff skittering away into the cloud.");
                fightOver = true;
                break;
            }
        }

        if (!fightOver) {
            // final choice moment: if mage still alive, present final options
            System.out.println("\nThe wizard hangs in the air, wounded but not yet ended.");
            System.out.println("You must choose how this ends: (mercy / strike / sacrifice)");
            System.out.print("> ");
            String choice = sc.nextLine().trim().toLowerCase();

            switch (choice) {
                case "mercy":
                    System.out.println("\nYou lower your weapon and call out to him. The wizard coughs, then laughs softly.");
                    System.out.println("\"Mercy,\" he says, \"is a rare madness among your kind.\"");
                    System.out.println("He breaks a spell and vanishes in a swirl of inked wind — not defeated, merely escaped.");
                    System.out.println("--- Ending: The Wizard Flees (ambiguous) ---");
                    break;
                case "strike":
                    System.out.println("\nYou drive the dragon's fury into him. The final strike rends his form.");
                    System.out.println("With a keening scream, the wizard dissolves into a cascade of starlight.");
                    System.out.println("The seals on the Pandora Vessel hold — a saving grace for the world.");
                    System.out.println("--- Ending: The Wizard Falls (good) ---");
                    break;
                case "sacrifice":
                    System.out.println("\nThe dragon bellows: 'I will finish this — you survive.' With a final roar, it lunges and consumes the wizard.");
                    System.out.println("A terrible shock wave follows. The dragon's form blurs; it is consumed by the act.");
                    System.out.println("You survive, but the dragon is gone. The cost is heavy, but the world is safe.");
                    System.out.println("--- Ending: Dragon Sacrifice (bittersweet) ---");
                    break;
                default:
                    System.out.println("Hesitation causes the wizard to seize the moment; he curses you and disappears into shadow.");
                    System.out.println("--- Ending: The Wizard Escapes ---");
                    break;
            }
        }
    }

    /* ---------- Utilities & small subsystems ---------- */

    // Random elemental spell
    private String randomSpell() {
        String[] spells = {"fire", "ice", "void"};
        return spells[rand.nextInt(spells.length)];
    }

    // Slightly more dangerous weighting for final phase
    private String randomSpellWeighted() {
        int r = rand.nextInt(100);
        if (r < 45) return "fire";
        if (r < 80) return "void";
        return "ice";
    }

    private boolean isCorrectResponseForSpell(String spell, String response) {
        switch (spell) {
            case "fire": return response.equals("absorb");
            case "ice":  return response.equals("reflect");
            case "void": return response.equals("dodge");
            default: return false;
        }
    }

    // Apply damage to playerHP and print status
    private void applyDamage(int dmg) {
        playerHP -= dmg;
        System.out.println("You take " + dmg + " damage. (HP: " + Math.max(0, playerHP) + ")");
    }

    // Timed breath window: user must type "unleash" within TIMEOUT_MS
    private boolean timedBreathWindow() {
        System.out.println("Type 'unleash' NOW to release the dragon's breath!");
        String input = sc.nextLine().trim();
        return input.equalsIgnoreCase("unleash");
    }


    private String readLineWithTimeout(long timeoutMs) {
        System.out.print("> ");
        return sc.nextLine();
    }


    /* ---------- Rune sequence helpers ---------- */
    private List<String> generateRuneSequence(int length) {
        String[] runes = {"A","B","C","D"};
        List<String> seq = new ArrayList<>();
        for (int i = 0; i < length; i++) seq.add(runes[rand.nextInt(runes.length)]);
        return seq;
    }

    private void showSequenceBriefly(List<String> seq) {
        // Show the sequence; in a real terminal we'd hide it after a moment.
        System.out.println(String.join(" ", seq));
        pause(1100 + seq.size() * 200);
        // Note: cannot truly clear console portably, so we proceed expecting player memory.
        System.out.println("(The runes fade. Now repeat them exactly.)");
    }

    /* ---------- End / epilogue ---------- */
    private void epilogue() {
        if (fightOver && mageHealth <= 0) {
            System.out.println("\nThe mage falls from the sky, his final scream torn apart by the wind.");
            System.out.println("Below, the tower crumbles. For now, the seals hold and the world breathes easier.");
            System.out.println("You and the dragon descend, battered but victorious.");
        } else {
            System.out.println("\nThe clouds swallow what remains of the fight. Fate remains uncertain.");
        }
        System.out.println("\n--- Quest Complete ---");
    }

    // small helper to pause narrative (milliseconds)
    private void pause(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}

