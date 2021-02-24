import java.util.*;

public class Game {
	private int backgroundNo;
	private SpaceConstructor spaceConstructor;
	
	public Game() {
		this.backgroundNo = 0; //initialize the current background number
		this.spaceConstructor = new SpaceConstructor(); //To declare a space constructor instance
		
		this.run(); //run the program, game starts!
	}

	public void run() {
		String username = Utils.input("Please enter your name: ");
		Player player = new Player(username.trim()); //input username

		System.out.println("\n========== Game Start ==========\n");

		this.welcome(player); //print all welcome texts
		
		while(true) {
			//Input process
			String[] collection = new String[2];

			do {
				System.out.println();
				System.out.print(">> ");

				String sentence = Utils.input("").toLowerCase(); //to lowercase the user input
 
				//To check does the evil user input any words
				if (sentence.length() < 1) {
					//Evil user does not input any word
					System.out.println("You must enter available commands.\n");
					continue;
				}

				//To separate the sentence into verb and subject and save the result to collection var.
				collection = Decoder.processSentence(sentence);

				//To check the validation of the verb
				if(!Decoder.filterVerb(collection)) {
					//Evil user inputs sh*t and try to kill the program. However, I won't let them do that!
					System.out.println("I can't recoginize the verb.\n");
					continue;
				} else {
					break; //jump to next step
				}
			} while(true);
			
			player.increaseMoves(); //increase moves
			
			//To check is the player harmed
			if (player.getIsHarmed()) {
				player.decreaseHealth(); //decrease player health
				
				//To check is user died
				if (player.getHealth() == 0) {
					System.out.println("\n[You are died]");
					System.out.println("==================== GAME OVER ====================");
					break;
				} else {
					System.out.print("[Caution] ");
					player.printHealth();
				}
			}
			
			//Common player movement
			if (collection[0].equals("face")) { //changing the orientation of the player
				//to check is the player mobile
				if (player.isMobile()) {
					switch(collection[1]) {
						case "n":
						case "north":
							player.setDirection("n");
							System.out.println("[Facing north]");
							break;
						
						case "e":
						case "east":
							player.setDirection("e");
							System.out.println("[Facing east]");
							break;
							
						case "s":
						case "south":
							player.setDirection("s");
							System.out.println("[Facing south]");
							break;
							
						case "w":
						case "west":
							player.setDirection("w");
							System.out.println("[Facing west]");
							break;
							
						default:
							player.speak("No such direction.");
							break;
					}
				} else {
					player.speak("Uh... I can't move.");
				}
			} else if (collection[0].equals("diagnostic") && !player.getIsHarmed()) { //output the health status
				player.printHealth();
			} else if (collection[0].equals("wear")) { //wear something from player's bag
				//To check the commands contains a subject
				if (collection[1].length() > 0) {
					//has subject
					//To check does the player carry the item and is the item dressed
					if (player.containsCarried(collection[1]) && player.getCarried(collection[1]).containsProperty("isDressed") && !player.getCarried(collection[1]).getProperty("isDressed")) {
						//To check is the item wearable
						if (player.getCarried(collection[1]).getProperty("isWearble")) {
							player.getCarried(collection[1]).setProperty("isDressed", true); //set the property of dressing to true which represents dressiing on body
							System.out.println("[Dressing " + collection[1] + "]"); //output
						} else {
							player.speak("I can't wear this thing.");
						}
					} else {
						player.speak("I can't find any " + collection[1] + " in my bag.");
					}
				} else {
					player.speak("What should I wear? (Missing a subject)");
				}
			} else if (collection[0].equals("whatihave")) { //output the thing the player carried
				String words = "";
				int i = 0;
						
				for(Map.Entry<String, Item> entry : player.getCarried().entrySet()) {
				    String key = entry.getKey();
				    Item value = entry.getValue();
				    
				    //To check the item is not dressed
				    if (!value.getProperty("isDressed")) {
						words += ((i > 0) ? ", " : "") + key;
						i++;
					}
				}
				
				if (i == 0) words = "nothing"; //if nothing in bag
				
				player.speak("I have " + words + " in my bag.");
			} else if (collection[0].equals("whatiwear")) { //output the items that the player is dressing
				String words = "";
				int i = 0;
						
				for(Map.Entry<String, Item> entry : player.getCarried().entrySet()) {
				    String key = entry.getKey();
				    Item value = entry.getValue();
				    
				    //To check the item is not dressed
				    if (value.getProperty("isDressed")) {
						words += ((i > 0) ? ", " : "") + key + " on " + value.getBehavior("wearTo");
						i++;
					}
				}
				
				if (i == 0) words = "nothing"; //if nothing in bag
				
				player.speak("I am wearing " + words + ".");
			} else {
				backgroundNo = spaceConstructor.runSetting(backgroundNo, collection, player); //let the background class do the magic
			}
			
			//To determine the end
			if (backgroundNo == spaceConstructor.getSettingSize() - 1) {
				System.out.println("==================== YOU WON!! ====================");
			}
		}
	}

	/**
	 * To print out the welcome texts
	 * 
	 * @param player The player instance
	 */
	public void welcome(Player player) {
		player.speak("Where am I? Why is a saw in front of me? Why is my left feet handcuffed? What happened! There is a letter. I should read it......");
		player.addCarried(new Item("handcuff", null, new String[]{"isWearable", "isDressed", "isPortable", "isImpedient"}, new boolean[]{false, true, false, true}, new String[]{"wearTo"}, new String[]{"left feet"}));
	}
}