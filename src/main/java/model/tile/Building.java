package model.tile;

import model.item.BuildingType;
import model.Dungeon;

public class Building extends Tile {

	private static final long serialVersionUID = 1681946032634725905L;
	private BuildingType type;
	private transient Dungeon dungeon;
	private int nbOfItems = 0;

	public Building(Dungeon dungeon, BuildingType type, int row, int col) {
		super(row, col);
		this.dungeon = dungeon;
		this.type = type;
		isWall = false;
		imagePath = type.getImagePath();
	}

	public BuildingType getBuildingType() {
		return type;
	}

	public void produce() {
		dungeon.collect(type.getItemType(), (int) Math.floor(Math.random()*3)+1);
	}

	public void update() {
		imagePath = type.getImagePath();
	}

}
