import java.util.*;

public class Item {
	private String name;
	private HashMap<String, Boolean> properties;
	private HashMap<String, String> behaviors;
	private String position;

	public Item(String name, String position, String[] propertiesName, boolean[] propertiesValue, String[] behaviorsName, String[] behaviorsValue) {
		this.name = name;
		this.position = position;
		
		this.properties = new HashMap<String, Boolean>();
		for (int i = 0; i < propertiesName.length; i ++) {
			properties.put(propertiesName[i], propertiesValue[i]);
		}
		
		this.behaviors = new HashMap<String, String>();
		for (int i = 0; i < behaviorsName.length; i ++) {
			behaviors.put(behaviorsName[i], behaviorsValue[i]);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String location) {
		position = location;
	}
	
	public boolean getProperty(String name) {
		if (properties.containsKey(name)) return properties.get(name);
		
		return false;
	}

	public void setProperty(String name, boolean bool) {
		if (properties.containsKey(name)) properties.put(name, bool);
	}
	
	public boolean containsProperty(String name) {
		return properties.containsKey(name);
	}
	
	public void setBehavior(String name, String data) {
		if (behaviors.containsKey(name)) { behaviors.put(name, data); }
	}
	
	public String getBehavior(String name) {
		if (behaviors.containsKey(name)) return behaviors.get(name);
		
		return "";
	}
	
	public HashMap<String, String> getBehavior() {
		return behaviors;
	}
	
	public boolean containsBehavior(String name) {
		return behaviors.containsKey(name);
	}
}