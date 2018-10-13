package model.tile;

import model.item.BuildingType;
import model.Dungeon;

import java.util.Timer;
import java.util.TimerTask;

public class Building extends Tile {

	private static final long serialVersionUID = 1681946032634725905L;
	private BuildingType type;
	private transient Dungeon dungeon;
	private boolean isWorking;
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

	public boolean isWorking() {
		return isWorking;
	}

	public void startWork() {
		isWorking = true;
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				produce();
			}
		}, (long) type.getProductionTime());
	}

	public void produce() {
		nbOfItems += (int) Math.floor(Math.random()*3+1);
		isWorking = false;
	}

	public int collect() {
		int temp = nbOfItems;
		nbOfItems = 0;
		return temp;
	}

	public void update() {
		imagePath = type.getImagePath();
	}

}
