package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Dungeon implements Serializable {

	private static final long serialVersionUID = -7230098702843143534L;
	private String name;
	private Tile[][] map;
	private Map<ItemType, Integer> itemInventory = new HashMap<ItemType, Integer>();

	public Dungeon(String name) {
		this.name = name;
		map = new Tile[Constants.MAP_SIZE][Constants.MAP_SIZE];
		for (int row = 0 ; row < map.length ; row++) {
			for (int col = 0 ; col < map[row].length ; col++) {
				if (row >= ((row/2)-(Constants.CENTER_SIZE/2)) && row <= ((row/2)+(Constants.CENTER_SIZE/2))
						&& col >= ((col/2)-(Constants.CENTER_SIZE/2)) && col <= ((col/2)+(Constants.CENTER_SIZE/2))) {
					map[row][col] = new EmptyTile(false);
				} else {
					map[row][col] = new EmptyTile(true);
				}
			}
		}
	}

	public String getName() {
		return name;
	}
	
	public Tile[][] getMap() {
		return map;
	}

	public void build(BuildingType type, int row, int col) {
		map[row][col] = new Building(type);
	}

	public void collect(int row, int col) {
		if (map[row][col] instanceof Building) {
			Building tile = (Building) map[row][col];
			ItemType type = tile.getBuildingType().getItemType();
			int nbOfItems = tile.collect();
			itemInventory.put(type, itemInventory.containsKey(type) ? itemInventory.get(type) + nbOfItems : nbOfItems);
		}
	}

}
