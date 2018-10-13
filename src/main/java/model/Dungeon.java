package model;

import model.config.Constants;
import model.item.BuildingType;
import model.item.ItemType;
import model.tile.Building;
import model.tile.EmptyTile;
import model.tile.Tile;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class Dungeon implements Serializable {

	private static final long serialVersionUID = -7230098702843143534L;
	private String name;
	private Tile[][] map;
	private Map<ItemType, Integer> itemInventory = new EnumMap<>(ItemType.class);

	public Dungeon(String name) {
		this.name = name;
		itemInventory.put(ItemType.ROCK, 60);
		map = new Tile[Constants.MAP_SIZE][Constants.MAP_SIZE];
		for (int row = 0 ; row < map.length ; row++) {
			for (int col = 0 ; col < map[row].length ; col++) {
				if (row >= ((Constants.MAP_SIZE/2)-(Constants.CENTER_SIZE/2)) && row <= ((Constants.MAP_SIZE/2)+(Constants.CENTER_SIZE/2))
						&& col >= ((Constants.MAP_SIZE/2)-(Constants.CENTER_SIZE/2)) && col <= ((Constants.MAP_SIZE/2)+(Constants.CENTER_SIZE/2))) {
					map[row][col] = new EmptyTile(this, false, row, col);
				} else {
					map[row][col] = new EmptyTile(this, true, row, col);
				}
			}
		}
		update();
	}

	public String getName() {
		return name;
	}

	public Tile[][] getMap() {
		return map;
	}

    public int getItemStock(ItemType type) {
        return (itemInventory.get(type) != null ? itemInventory.get(type) : 0);
    }

    public Tile getTile(int row, int col) {
		if (row >= 0 && row < map.length && col >= 0 && col < map[row].length) {
			return map[row][col];
		} else {
			return null;
		}
	}

    private void update() {
    	for (Tile[] row : map) {
			for (Tile tile : row) {
				tile.update();
			}
		}
    }

    public void setTile(Tile tile) {
        if (tile.getRow() >= 0 && tile.getRow() < map.length && tile.getCol() >= 0 && tile.getCol() < map[tile.getRow()].length) {
            map[tile.getRow()][tile.getCol()] = tile;
        }
    }

    public void dig(int row, int col) {
    	map[row][col] = new EmptyTile(this, false, row, col);
    	update();
    }

	public void build(BuildingType type, int row, int col) {
	    Building building = new Building(this, type, row, col);
		map[row][col] = building;
		itemInventory.put(ItemType.ROCK, getItemStock(ItemType.ROCK) - building.getBuildingType().getCost());
		update();
	}

	public void collect(int row, int col) {
		if (map[row][col] instanceof Building) {
			Building tile = (Building) map[row][col];
			ItemType type = tile.getBuildingType().getItemType();
			int nbOfItems = tile.collect();
			itemInventory.put(type, getItemStock(type) + nbOfItems);
		}
	}

}
