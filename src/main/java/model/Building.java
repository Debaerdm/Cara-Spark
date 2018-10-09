package model;

import java.util.Timer;
import java.util.TimerTask;

public class Building extends Tile {

	private static final long serialVersionUID = 1681946032634725905L;
	private BuildingType type;
	private boolean isWorking;
	private int nbOfItems = 0;

	public Building(BuildingType type) {
		this.type = type;
	}

	public BuildingType getBuildingType() {
		return type;
	}

	@Override
	public String getImagePath() {
		return type.getImagePath();
	}

	@Override
	public boolean isWall() {
		return true;
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

}
