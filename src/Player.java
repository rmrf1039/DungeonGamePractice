import java.util.*;

public class Player {
	private String name;
	private int health;
	private boolean isHarmed;
	private HashMap<String, Item> carried;
	private HashMap<String, Boolean> body;
	private String direction; 
	
	private int moves;

	public Player(String name) {
		this.name = name;
		this.health = 10;
		this.direction = "n";
		this.carried = new HashMap<String, Item>();
		this.moves = 0;
		this.isHarmed = false;
		
		this.body = new HashMap<String, Boolean>();
		body.put("left arm", true);
		body.put("right arm", true);
		body.put("left feet", true);
		body.put("right feet", true);
		body.put("tongue", true);
		body.put("left ear", true);
		body.put("right ear", true);
	}

	public String getName() {
		return name;
	}

	public int getMoves() {
		return moves;
	}
	
	public void increaseMoves() {
		moves++;
	}

	public void increaseHealth() {
		health += 1;
	}

	public void decreaseHealth() {
		health -= 1;
	}
	
	public boolean getIsHarmed() {
		return isHarmed;
	}
	
	public void setIsHarmed(boolean value) {
		isHarmed = value;
	}

	public int getHealth() {
		return health;
	}
	
	public void setBody(String name, boolean bool) {
		if (body.containsKey(name)) body.put(name, bool);
	}
	
	public boolean getBody(String name) {
		if (body.containsKey(name)) return body.get(name);
		
		return false;
	}
	
	public boolean containsBody(String name) {
		return body.containsKey(name);
	}
	
	public void addCarried(Item item) {
		carried.put(item.getName(), item);
	}
	
	public void removeCarried(Item item) {
		carried.remove(item.getName());
	}
	
	public boolean containsCarried(String name) {
		return carried.containsKey(name);
	}
	
	public Item getCarried(String name) {
		if (carried.containsKey(name)) return carried.get(name);
		
		return null;
	}
	
	public HashMap<String, Item> getCarried() {
		return carried;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String data) {
		direction = data;
	}
	
	public void speak(String sentence) {
		System.out.println(this.name + ": " + sentence);
	}
	
	public void printHealth() {
		System.out.println("Player health: " + (int)((float)(health) / 10 * 100) + "%");
	}
	
	public boolean isMobile() {
		for(Map.Entry<String, Item> entry : carried.entrySet()) {
		    if (entry.getValue().containsProperty("isImpedient") && entry.getValue().getProperty("isImpedient")) return false;
		}
		
		return true;
	}
}