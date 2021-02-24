import java.util.*;

public class Background {
	public int no;
	public String name;
	public String description;
	public HashMap<String, HashMap<String, Item>> side;

	public Background(int no, String name, String description, HashMap<String, Item[]> side) {
		this.no = no;
		this.name = name;
		this.description = description;
		this.side = new HashMap<String, HashMap<String, Item>>();
		
		//make a for loop for four orientations
		for (int i = 0; i < 4; i++) {
			String d = "n";
			
			switch(i) {
				case 1:
					d = "e";
					break;
					
				case 2:
					d = "s";
					break;
					
				case 3:
					d = "w";
					break;
			}
			
			HashMap<String, Item> items = new HashMap<String, Item>();
			
			//inject values to HashMap var.
			for (int j = 0; j < side.get(d).length; j++) {
				items.put(side.get(d)[j].getName(), side.get(d)[j]);
			}
			
			//final touch of injection
			this.side.put(d, items);
		}
	}
	
	public int run(String[] command, Player player) {
		//Do output or something for a generation
		/*System.out.println("No: " + no);
		System.out.println("Background: " + name);
		System.out.println("=============== Player ===============");
		System.out.println("Health: " + (player.getHealth() / 10 * 100) + "%");
		System.out.println("Moves: " + player.getMoves());
		System.out.println("Direction: " + player.getDirection());
		System.out.println("=============== Message ===============");*/
		
		//shortcut variable declaration
		HashMap<String, Item> spSide = this.side.get(player.getDirection());
		
		//To identify the verb and choose the correct behavior
		switch(command[0]) {
			case "whereami":
				player.speak("I am currently at " + name + ". " + description);
				break;
				
			case "use":
				//To check the command has subject
				if (command[1].length() > 0) {
					//To check it only has one subject
					if (command[1].indexOf(" ") == -1) {
						//To check is the item existed in bag
						if (player.containsCarried(command[1])) {
							//To check is the item usable
							if (player.getCarried(command[1]).containsProperty("isUsable") && player.getCarried(command[1]).getProperty("isUsable")) {
								for(Map.Entry<String, String> entry : player.getCarried(command[1]).getBehavior().entrySet()) {
								    String key = entry.getKey();
								    String value = entry.getValue();
									
									switch (key) {
									    case "increaseHealth":
									    	int currentHealth = player.getHealth();
									    	
									    	for (int i = currentHealth; i < ((currentHealth + Integer.parseInt(value) < 10) ? currentHealth + Integer.parseInt(value) : 10); i++) {
									    		player.increaseHealth();
									    	}
									    	
									    	System.out.println("[Health increased to " + (int)((float)(player.getHealth()) / 10 * 100) + "%]");
									    	
									    	break;
									    	
									    case "cure":
									    	player.setIsHarmed(false);
									    	System.out.println("[Got cured]");
									    	
									    	break;
								    }
								}
								
								player.removeCarried(player.getCarried(command[1]));
							} else {
								player.speak("I don't think I can use this...");
							}
						}
					} else {
						player.speak("I can only use one thing at a time.");
					}
				} else {
					player.speak("What should I user? (Missing a subject)");
				}
				break;
		
			case "go":
				//to check is the player mobile
				if (player.isMobile()) {
					//search any passable item in the specific side
					for(Map.Entry<String, Item> entry : spSide.entrySet()) {
					    Item value = entry.getValue();
					    
					    //to filter and find the item that contains passable property
					    if (value.containsProperty("isPassable")) {
					    	//To check is the item passable
					    	if (value.getProperty("isPassable")) {
					    		//To check is the item locked
					    		if (value.containsProperty("isLocked") && !value.getProperty("isLocked")) {
					    			System.out.println("[Walking...]");
						    		
						    		return Integer.parseInt(value.getBehavior("goTo")); //stop the generation and pass the new background no to game class.
						    	} else {
						    		player.speak("The " + value.getName() + " is locked. Where is the key?");
						    	}
					    	} else {
					    		System.out.print("[Terrible sound appeared]");
								player.speak("I better not to go there.");
					    	}
						}
					}
				} else {
					player.speak("Uh... I can't move.");
				}
				break;
		
			case "take":
				//To check the command has subject
				if (command[1].length() > 0) {
					String[] subjectCollection = Decoder.filterSubject(Decoder.processSubject(command[1]));
					
					for (int i = 0; i < subjectCollection.length; i++) {
						//To check the side has the item and the item has been discovered. 
						if (spSide.containsKey(subjectCollection[i]) && (spSide.get(subjectCollection[i]).getPosition() == null || spSide.get(spSide.get(subjectCollection[i]).getPosition()).getProperty("isDiscovered"))) {
							//To check is the item portable
							if (spSide.get(subjectCollection[i]).containsProperty("isPortable") && spSide.get(subjectCollection[i]).getProperty("isPortable")) {
								player.addCarried(spSide.get(subjectCollection[i])); //transfer the item to player instance from background instance
								spSide.remove(subjectCollection[i]); //remove the item from background instance
								System.out.println("[Taking " + subjectCollection[i] + "]"); //output the success of transferring
							} else {
								player.speak("I can't carry the " + subjectCollection[i] + ".");
							}
						} else {
							player.speak("I don't see or find the " + subjectCollection[i] + " here.");
						}
					}
				} else {
					player.speak("What should I take? (Missing a subject)");
				}
				break;
				
			case "throw":
				//To check the existence of subject
				if (command[1].length() > 0) {
					//To check the existence of item in player's carried
					if (player.containsCarried(command[1])) {
						player.getCarried(command[1]).setPosition(null); //change the position of the item
						spSide.put(command[1], player.getCarried(command[1])); //add the item to the side
						player.removeCarried(player.getCarried(command[1])); //remove the item from player's carried
						System.out.println("[Throwing the " + command[1] + " out]");
					} else {
						player.speak("I don't have " + command[1] + ".");
					}
				} else {
					player.speak("Throw what? (Missing a subject)");
				}
				
				break;
				
			case "saw":
				//To check the existence of subject
				if (command[1].length() > 0) {
					String[] subjectCollection = Decoder.processSubject(command[1]);
					
					//To check the subject has match to limited size, so the program would know the user is typing correct commands
					if (subjectCollection.length >= 3 && Utils.arrContains(subjectCollection, "by")) {
						//Subject processing, separating two different kinds of objects
						String beCutItem = "";
						String toolItem = "";
						boolean find = false;
						
						for (int i = 0; i < subjectCollection.length; i++) {
							if (subjectCollection[i].equals("by")) {
								find = true;
							} else if (find) {
								toolItem += subjectCollection[i] + " ";
							} else {
								beCutItem += subjectCollection[i] + " ";
							}
						}
						
						beCutItem = beCutItem.trim();
						toolItem = toolItem.trim();
						
						if (player.containsBody(beCutItem)) {
							if (player.containsCarried(toolItem)) {
								player.setBody(beCutItem, false); //disable the body part
								player.setIsHarmed(true); //set the health state
								player.speak("Ahhhhhhhh!");
								
								//To remove all the item that wear the body part
								for(Map.Entry<String, Item> entry : player.getCarried().entrySet()) {
								    if (entry.getValue().containsBehavior("wearTo") && entry.getValue().getBehavior("wearTo").indexOf(beCutItem) != -1) player.removeCarried(entry.getValue());
								}
								
								if (player.getCarried(toolItem).getProperty("isConsumable")) {
									//To determine is the tool going to break
									if (Integer.parseInt(player.getCarried(toolItem).getBehavior("residue")) - 1 == 0) {
										player.removeCarried(player.getCarried(toolItem));
										System.out.println("[" + toolItem + " broke]");
									} else {
										player.getCarried(toolItem).setBehavior("residue", "" + (Integer.parseInt(player.getCarried(toolItem).getBehavior("residue")) - 1));
										System.out.println("[" + toolItem + "'s residue: " + player.getCarried(toolItem).getBehavior("residue") + "]");
									}
								}
							} else {
								player.speak("I don't have " + toolItem + ".");
							}
						} else if (beCutItem.equals("door")) {
							//To check the existence of door
							if (spSide.containsKey("door")) {
								//To check the property of isLocked
								if (spSide.get("door").getProperty("isLocked")) {
									//To check is the door passable
									if (spSide.get(beCutItem).getProperty("isPassable")) {
										spSide.get(beCutItem).setProperty("isLocked", false);
										System.out.println("[Door opened]");
									} else {
										player.speak("I can't saw this door. It is impossible.");
									}
									
									if (player.getCarried(toolItem).getProperty("isConsumable")) {
										//To determine is the tool going to break
										if (Integer.parseInt(player.getCarried(toolItem).getBehavior("residue")) - 1 == 0) {
											player.removeCarried(player.getCarried(toolItem));
											System.out.println("[" + toolItem + " broke]");
										} else {
											player.getCarried(toolItem).setBehavior("residue", "" + (Integer.parseInt(player.getCarried(toolItem).getBehavior("residue")) - 1));
											System.out.println("[" + toolItem + "'s residue: " + player.getCarried(toolItem).getBehavior("residue") + "]");
										}
									}
								} else {
									player.speak("It is already unlocked.");
								}
							} else {
								player.speak("Ther is no door.");
							}
						} else {
							player.speak("I don't have " + beCutItem + " on my body!");
						}
					} else {
						player.speak("I don't understand... (Correct syntax: saw [be cut] by [tool])");
					}
				} else {
					player.speak("What should I cut and by what? (Missing subjects)");
				}
				break;
				
			case "read":
				//To check the existence of subject
				if (command[1].length() > 0) {
					//To check the existence of item in player's carried
					if (player.containsCarried(command[1]) || spSide.containsKey(command[1])) {
						Item item = (player.containsCarried(command[1])) ? player.getCarried(command[1]) : spSide.get(command[1]); //get the item from either player's carried or background
						
						//To check the property of the item
						if (item.containsProperty("isReadable") && item.getProperty("isReadable")) {
							System.out.println("=============== " + item.getBehavior("showTitle") + " ===============\n");
							
							String text = item.getBehavior("showText").trim();
							int lineTake = 0;
							
							for (int i = 0; i < item.getBehavior("showText").length(); i++) {
								//To determine is the next word will over the max length of the passage area
								if ((lineTake + text.substring(0, ((text.indexOf(" ") != -1) ? text.indexOf(" ") : text.length())).length()) > 32 + item.getBehavior("showTitle").length()) {
									System.out.println();
									lineTake = 0;
								} else {
									System.out.print(text.charAt(0));
									text = text.substring(1);
									lineTake++;
								}
							}
							
							System.out.println("\n(End)");
							
							for (int i = 0; i < 32 + item.getBehavior("showTitle").length(); i++) {
								System.out.print("=");
							}
							
							System.out.println();
						} else {
							player.speak("Uhhh... It is hard to read.");
						}
					} else {
						player.speak("I don't see that.");
					}
				} else {
					player.speak("Read what? (Missing a subject)");
				}
				break;
				
			case "unlock":
				//To check the existence of subject
				if (command[1].length() > 0) {
					//To check the property of isLocked
					if (spSide.containsKey(command[1]) && spSide.get(command[1]).containsProperty("isLocked")) {
						//To check is the item locked
						if (spSide.get(command[1]).getProperty("isLocked")) {
							if (player.containsCarried("key") || player.containsCarried("unlocker")) {
								//To check is the key match to the object
								if ((player.containsCarried("key") && !player.getCarried("key").getBehavior("unlock").equals(command[1]))) {
									player.speak("The key doesn't match");
									break;
								} 
								
								spSide.get(command[1]).setProperty("isLocked", false); //change the property of isLocked to false
								System.out.println("[Opening the " + command[1] + "]"); //output the change to UI
								
								//To determine either the subject is a key or unlocker
								if (player.containsCarried("key")) {
									player.removeCarried(player.getCarried("key")); //remove a key from player's carried
									System.out.println("[The key is destroyed]");
								} else {
									player.removeCarried(player.getCarried("unlocker")); //remove a unlocker from player's carried
									System.out.println("[The unlocker is destroyed]");
								}
								
								System.out.println("[Successfully unlock]");
							} else {
								player.speak("I don't have anything to unlock this.");
							}
						} else {
							player.speak("It is already unlocked.");
						}
					} else {
						player.speak("It doesn't have any lock to unlock.");
					}
				} else {
					player.speak("What should I unlock? (Missing a subject)");
				}
				break;
				
			case "search":
				//To check does the command has a subject
				if (command[1].length() == 0) {
					//No subject
					//print all discovered item from the specific side
					String words = "";
					int i = 0;
							
					for(Map.Entry<String, Item> entry : spSide.entrySet()) {
					    String key = entry.getKey();
					    Item value = entry.getValue();
					    
					    if (value.getPosition() == null || value.getProperty("isDiscovered")) {
							words += ((i > 0) ? ", " : "") + key;
							i++;
						}
					}
					
					if (i == 0) words = "nothing"; //if nothing is under the subject
					
					System.out.println("[Found " + words + "]");
				} else {
					//To check the existence of the subject in the side and has the subject already been discovered before
					if (spSide.containsKey(command[1]) && (spSide.get(command[1]).getPosition() == null || spSide.get(spSide.get(command[1]).getPosition()).getProperty("isDiscovered"))) {
						//To check is the item locked
						if (spSide.get(command[1]).containsProperty("isLocked") && spSide.get(command[1]).getProperty("isLocked")) {
							player.speak("It is locked. Where is the key?");
							break;
						}
						
						//print all item under the subject
						String words = "";
						int i = 0;
						
						for(Map.Entry<String, Item> entry : spSide.entrySet()) {
						    String key = entry.getKey();
						    Item value = entry.getValue();
						    
						    if (value.getPosition() != null && value.getPosition().equals(command[1])) {
								words += ((i > 0) ? ", " : "") + key;
								i++;
							}
						}
						
						if (i == 0) words = "nothing"; //if nothing is under the subject
						
						spSide.get(command[1]).setProperty("isDiscovered", true); //change the items under the subject to discovered
						player.speak("I see " + words + ".");
					} else {
						//can't find the subject, but print all the item has been discovered
						String words = "";
						int i = 0;
						
						for(Map.Entry<String, Item> entry : spSide.entrySet()) {
						    String key = entry.getKey();
						    Item value = entry.getValue();
						    
						    if (value.getPosition() == null || spSide.get(value.getPosition()).getProperty("isDiscovered")) {
						    	words += ((i > 0) ? ", " : "") + key;
								i++;
							}
						}
						
						player.speak("I don't see that or not found yet. I only see " + words + " before.");
					}
				}
				break;
		}
		
		return no;
	}
}