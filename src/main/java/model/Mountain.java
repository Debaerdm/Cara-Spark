package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mountain implements Serializable {

	private static final long serialVersionUID = 8838728274891228308L;
	private String name;
	private Map<String, Dungeon> dungeonByIP = new HashMap<String, Dungeon>();
	private static Mountain instance;

	public static Mountain getInstance() {
		if (instance == null) {
			instance = new Mountain("The Mountain");
			System.out.println("No mountain found, travelling to the closest mountain to settle the dungeons.");
		}
		return instance;
	}

	public static void serialize() {
		if (instance != null) {
			try {
				File file = new File("server.data");
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream output = new ObjectOutputStream(fileOut);
				output.writeObject(instance);
				output.close();
				fileOut.close();
				System.out.println("Serialization done.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deserialize() {
		FileInputStream file;
		try {
			file = new FileInputStream("server.data");
	        ObjectInputStream in = new ObjectInputStream(file);
	        instance = (Mountain) in.readObject();
	        in.close();
	        file.close();
		} catch (IOException | ClassNotFoundException e) {
			/* Do nothing, getInstance() will return a new Mountain when called for the first time */
		} 
	}

	private Mountain(String name) {
		this.name = name;
		dungeonByIP.put("test1", new Dungeon("dungeon 1"));
		dungeonByIP.put("test2", new Dungeon("dungeon 2"));
		dungeonByIP.put("test3", new Dungeon("dungeon 3"));
		serialize();
	}

	public String getName() {
		return name;
	}

	public void addDungeon(String ip) {
		dungeonByIP.put(ip, new Dungeon("Donjon de "+ip));
	}
	
	public Map<String, Dungeon> getDungeonsMap() {
		return dungeonByIP;
	}

}
