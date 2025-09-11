package DataSetup.locations;

import DataSetup.quests.EchoChamber;
import DataSetup.quests.FindTheThief;
import DataSetup.quests.TheTheft;

import java.util.HashMap;
import java.util.Map;

public class LocationData {
    private Map<String, Location> locations;
    public LocationData(){

        this.locations = new HashMap<>(Map.ofEntries(
                Map.entry("camping", new Location("camping",
                        """
                        A humble ring of tents stands amidst the wild, smoke curling softly from the hearth.
                        Travelers gather here, trading tales beneath the stars.
                        It is a fleeting refuge, ever at the mercy of the wandering winds.""",
                        new HashMap<>(Map.of("S", "village")))
                ),

                Map.entry("village", new Location("village",
                        """
                        A cluster of thatched roofs nestles warmly amidst rolling hills.
                        "Chimneys puff smoke, and laughter carries on the crisp air.
                        "Here folk live simply, with songs as old as the fields they till.""",
                        new HashMap<>(Map.of( "N", "camping", "S", "alley one")),
                        new TheTheft())
                ),
                Map.entry("alley one", new Location("alley one",
                        """
                                a route with a signThe stone bridges link the village to the guettos,
                                 their worn arches steady despite the years. Moss and ivy trace their
                                  edges, and the faint creak of old lanterns sways in the breeze. Crossing
                                   feels like stepping into a quieter, older part of the world, where time lingers differently.""",
                        new HashMap<>(Map.of("N", "village", "S", "ghetto", "W", "road toll")))
                ),
                Map.entry("road toll", new Location("road toll", """
                        The Road Toll stands where the path narrows, a crooked gate guarded 
                        by a hulking orc. His tusked grin widens as he demands coins far beyond 
                        reason, his voice thick with mockery. Travelers speak of few who ever 
                        pay the toll — most turn back, for the price is heavier than any purse could bear.
                        """,
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
                        sprawl along the cobblestones, reaching out with trembling hands that will never find mercy.
                        The Ghetto is where the world deposits its refuse — the unwanted, the discarded, the forgotten.
                        Here, misery is not an accident; it is the foundation on which everything else rests.""",
                        new HashMap<>(Map.of("N", "alley one")),
                        new FindTheThief())
                ),
                Map.entry("caves entry", new Location("caves entry",
                        """
                        As you enter the cave, you light the torch that the merchant gave you.
                        As the flame flares, the entire cave is revealed to you.""",
                        new HashMap<>(Map.of("E", "first room")),
                        new EchoChamber())
                )
        ));

    }
    public Map<String, Location> getLocations(){
        return locations;
    }


}
