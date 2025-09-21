package main.DataSetup.locations;

import main.DataSetup.entities.Player;
import main.DataSetup.quests.*;

import java.util.HashMap;
import java.util.Map;

public class LocationData {
    private Map<String, Location> locations;
    public LocationData(Player player){

        this.locations = new HashMap<>(Map.ofEntries(
                Map.entry("camping", new Location("camping",
                        """
                        A humble ring of tents stands amidst the wild, smoke curling softly from the hearth.
                        Travelers gather here, trading tales beneath the stars.
                        It is a fleeting refuge, ever at the mercy of the wandering winds.
                        
                        """,
                        new ClimbTheTree(player),
                        new HashMap<>(Map.of("S", "village")))


                ),

                Map.entry("village", new Location("village",
                        """
                        Amidst bare, silent hills rises a village of worn stone.
                        The houses, with cold walls and roofs darkened by time, seem more like tombs than homes.
                        A few chimneys still emit smoke, and the air carries a heavy silence, broken only by shuffling footsteps on the stone streets.
                        Here, life doesn't flourish: it resists.""",
                        new TheTheft(player),
                        new HashMap<>(Map.of( "N", "camping", "S", "alley one")))

                ),
                Map.entry("alley one", new Location("alley one",
                        """
                                The stone bridges link the village to the ghettos,
                                their worn arches steady despite the years. Moss and ivy trace their
                                edges, and the faint creak of old lanterns sways in the breeze. Crossing
                                feels like stepping into a quieter, older part of the world, where time lingers differently.""",
                        new EmptyQuest(player),
                        new HashMap<>(Map.of("N", "village", "S", "ghetto", "W", "road toll")))
                ),
                Map.entry("road toll", new Location("road toll", """
                        The Road Toll stands where the path narrows, a crooked gate guarded
                        by a hulking orc. His tusked grin widens as he demands coins far beyond 
                        reason, his voice thick with mockery. Travelers speak of few who ever 
                        pay the toll — most turn back, for the price is heavier than any purse could bear.
                        """,
                        new EmptyQuest(player),
                        new HashMap<>(Map.of("E", "alley one", "W", "mountain"
                        )))
                ),
                Map.entry("ghetto", new Location("ghetto",
                        """
                        The stench strikes first — rot, unwashed flesh, " +
                        and the sour tang of sickness festering in the air. Crumbling hovels lean against
                        each other like broken teeth, their walls stitched with mold and despair. Ragged figures
                        huddle in the shadows, eyes hollow with hunger, their coughs echoing through the narrow alleys
                        like a dirge. Bandits loiter at corners, blades glinting faintly in the murk, while the sick
                        sprawl along the cobblestones, reaching out with trembling hands that will never find mercy.""",
                        new FindTheThief(player),
                        new HashMap<>(Map.of("N", "alley one")))


                ),
                Map.entry("caves entry", new Location("caves entry",
                        """
                        You notice the secret entry that the merchant had told you, and wonder how you didn't realize before.
                        Entering the cave, you can't see anything because of the darkness.""",
                        new GemsChamber(player),
                        new HashMap<>(Map.of("W", "demons room")))
                ),
                Map.entry("demons room", new Location("demons room",
                        """
                                You step into a vast, dimly-lit chamber. The cave walls are covered in thick layers of dry moss, and twisted roots hang from the ceiling 
                                like webs. The air is heavy, permeated with the odor of sulfur and flammable gas emanating from cracks in the floor. Each step echoes 
                                cracking sounds against the rocks, as if the entire cave were waiting for a breath of fire to bring it down.
                                At the center of the room stands a towering demon, muscles rippling beneath dark, glistening skin, horns 
                                curving skyward, and eyes like molten gold. Its gaze fixes upon you, and a low, rumbling voice vibrates the walls:
                                'None shall pass to the west while I remain.'
                                The western wall is dominated by an ominous door, black iron with infernal runes etched into its surface. 
                                You sense power emanating from it, as if whatever lies beyond waits with bated breath.
                                """,
                        new TheDemon(player),
                        new HashMap<>(Map.of("W", "treasure chamber")))
                ),
                Map.entry("treasure chamber", new Location("treasure chamber",
                        "",
                        new PentagramPuzzle(player),
                        new HashMap<>(Map.of("W", "towers underground")))
                ),
                Map.entry("towers underground", new Location("towers underground",
                        "",
                        new TheBeholder(player),
                        new HashMap<>(Map.of("N", "mountain")))
                ),
                Map.entry("mountain", new Location("mountain",
                        "",
                        new DragonHuntLabyrinth(player),
                        null))
        ));

    }
    public Map<String, Location> getLocations(){
        return locations;
    }


}



