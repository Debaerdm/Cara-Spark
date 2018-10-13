package model.item;

public enum ItemType {

	ROCK(5),
	GOLD(25),
	GEMS(50);

	private int gain;

	ItemType(int gain) {
		this.gain = gain;
	}

	public int getGain() {
		return gain;
	}

}
