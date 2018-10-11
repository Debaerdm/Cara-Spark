package model;

public enum BuildingType {

	ROCK_MINE("rock_mine.png", ItemType.ROCK, 5000, 50),
	GOLD_MINE("gold_mine.png", ItemType.GOLD, 12000, 300),
	GEMS_MINE("gems_mine.png", ItemType.GEMS, 16000, 1500);

	private String imagePath;
	private ItemType itemType;
	private int productionTime;
	private int cost;

	BuildingType(String imagePath, ItemType itemType, int productionTime, int cost) {
		this.imagePath = imagePath;
		this.itemType = itemType;
		this.productionTime = productionTime;
		this.cost = cost;
	}

	public static BuildingType create(ItemType itemType) {
		switch (itemType) {
			case GEMS:
				return BuildingType.GEMS_MINE;
			case GOLD:
				return BuildingType.GOLD_MINE;
			case ROCK:
				return BuildingType.ROCK_MINE;
		}

		return null;
	}

	public String getImagePath() {
		return imagePath;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public int getProductionTime() {
		return productionTime;
	}

	public int getCost() {
		return cost;
	}
}
