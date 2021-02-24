import java.util.*;

public class SpaceConstructor {
	private Background[] settings;
	
	public SpaceConstructor() {
		//Set settings, build the constructor
		settings = new Background[5];
		HashMap<String, Item[]> sideSet = new HashMap<String, Item[]>();
		
		//Create background 0
		sideSet.put("n", new Item[]{ 
				new Item("door", null, new String[]{"isLocked", "isPassable"}, new boolean[]{true, true}, new String[]{"goTo"}, new String[]{"1"}),
				new Item("saw", null, new String[]{"isPortable", "isConsumable"}, new boolean[]{true, true}, new String[]{"residue"}, new String[]{"3"}),
				new Item("letter", null, new String[]{"isReadable", "isPortable"}, new boolean[]{true, true}, new String[]{"showTitle", "showText"}, new String[]{"Welcome Letter", "I want play a game... You are locked in a small room. Now, you must to find "
						+ "the key to unlcok the door. However, one of your feet is handcuffed, and you must to use your saw to cut it. Remember, once you cut your feet out, your health will start to decrease per move. You got 10 health! If you have foot or "
						+ "emergency pack, use it. It saves your life. Goodbye!"}),
				});
		sideSet.put("s", new Item[]{ 
				new Item("box", null, new String[]{"isDiscovered"}, new boolean[]{false}, new String[]{}, new String[]{}),
				new Item("key", "box", new String[]{"isPortable"}, new boolean[]{true}, new String[]{"unlock"}, new String[]{"door"}),
				new Item("meat", "box", new String[]{"isPortable"}, new boolean[]{true}, new String[]{}, new String[]{}),
				new Item("styptic", "box", new String[]{"isPortable", "isUsable"}, new boolean[]{true, true}, new String[]{"increaseHealth"}, new String[]{"4"}),
				});
		sideSet.put("e", new Item[]{});
		sideSet.put("w", new Item[]{});
		settings[0] = new Background(0, "Unknown room", "It's cold. North has a door but locked. Aganist the door, there is a box and a letter.", sideSet);
		
		//Create background 1
		sideSet.put("n", new Item[]{ 
				new Item("ventiduct", null, new String[]{"isPassable", "isLocked"}, new boolean[]{true, true}, new String[]{"goTo"}, new String[]{"4"})
				});
		sideSet.put("e", new Item[]{
				new Item("door", null, new String[]{"isPassable", "isLocked"}, new boolean[]{true, true}, new String[]{"goTo"}, new String[]{"2"}),
		});
		sideSet.put("s", new Item[]{ 
				new Item("door", null, new String[]{"isPassable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				new Item("cabinet", null, new String[]{"isDiscovered"}, new boolean[]{false}, new String[]{}, new String[]{}),
				new Item("emergency_pack", "cabinet", new String[]{"isPortable", "isUsable"}, new boolean[]{true, true}, new String[]{"increaseHealth", "cure"}, new String[]{"10", "true"}),
				//new Item("galoshes", "cabinet", new String[]{"isPortable", "isWearble", "isDressed"}, new boolean[]{true, true, false}, new String[]{"wearTo"}, new String[]{"foot"}),
				new Item("gun", "cabinet", new String[]{"isPortable", "isSpent"}, new boolean[]{true, false}, new String[]{"shot"}, new String[]{"10"}),
				});
		sideSet.put("w", new Item[]{
				new Item("door", null, new String[]{"isPassable", "isLocked"}, new boolean[]{true, true}, new String[]{"goTo"}, new String[]{"3"}),
		});
		settings[1] = new Background(1, "Silent room", "Here is so silent.... [Boom, door closed] I can't open the door that I just passed! Besides the door, there has a cabinet. In the east and the west, they both have a door. In the north, it has a ventiduct.", sideSet);
		
		//Create background 2
		sideSet.put("n", new Item[]{ 
				new Item("candles", null, new String[]{"isPortable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				});
		sideSet.put("e", new Item[]{ 
				//new Item("jesus_statue", null, new String[]{"isPortable", "isBreakable", "isDiscovered"}, new boolean[]{false, true, false}, new String[]{}, new String[]{}),
				new Item("key", null, new String[]{"isPortable"}, new boolean[]{true}, new String[]{"unlock"}, new String[]{"box"}),
				});
		sideSet.put("s", new Item[]{ 
				new Item("candles", null, new String[]{"isPortable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				});
		sideSet.put("w", new Item[]{ 
				new Item("door", null, new String[]{"isPassable", "isLocked"}, new boolean[]{true, false}, new String[]{"goTo"}, new String[]{"1"})
				});
		settings[2] = new Background(2, "Prayer room", "I see a key on the floor.", sideSet);
		
		//Create background 3
		sideSet.put("n", new Item[]{ 
				new Item("candles", null, new String[]{"isPortable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				});
		sideSet.put("e", new Item[]{ 
				new Item("door", null, new String[]{"isPassable", "isLocked"}, new boolean[]{true, false}, new String[]{"goTo"}, new String[]{"1"}),
				});
		sideSet.put("s", new Item[]{ 
				new Item("candles", null, new String[]{"isPortable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				});
		sideSet.put("w", new Item[]{ 
				new Item("box", null, new String[]{"isPortable", "isDiscovered", "isLocked"}, new boolean[]{false, false, true}, new String[]{}, new String[]{}),
				new Item("unlocker", "box", new String[]{"isPortable"}, new boolean[]{true}, new String[]{"unlock"}, new String[]{"ventiduct"}),
				//new Item("plate", null, new String[]{"isPortable"}, new boolean[]{false}, new String[]{"unlock", "shouldContain"}, new String[]{"buddha_statue", "meat"}),
				});
		settings[3] = new Background(3, "Buddhism temple", "I see a box but locked.", sideSet);
		
		//Create background 4
		sideSet.put("n", new Item[]{ 
				new Item("forest", null, new String[]{"isPassable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				});
		sideSet.put("e", new Item[]{ 
				new Item("forest", null, new String[]{"isPassable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				});
		sideSet.put("s", new Item[]{ 
				new Item("ventiduct", null, new String[]{"isPassable", "isLocked"}, new boolean[]{true, false}, new String[]{}, new String[]{}),
				});
		sideSet.put("w", new Item[]{ 
				new Item("forest", null, new String[]{"isPassable"}, new boolean[]{false}, new String[]{}, new String[]{}),
				});
		settings[4] = new Background(4, "Outside", "It is outside.", sideSet);
	}

	public int runSetting(int num, String[] command, Player player) {
		return settings[num].run(command, player); //selecting specific background
	}
	
	public int getSettingSize() {
		return settings.length;
	}
}