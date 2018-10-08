package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mountain implements Serializable {

	private static final long serialVersionUID = 8838728274891228308L;
	private String name;
	private Map<String, Dungeon> dungeonByIP = new HashMap<String, Dungeon>();

	public Mountain(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addDungeon(String ip) {
		dungeonByIP.put(ip, new Dungeon("Donjon de "+ip));
	}

}
