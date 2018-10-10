package model;

public enum BuildingType {

	ROCK_MINE("rock_mine.png", ItemType.ROCK, 5000),
	GOLD_MINE("gold_mine.png", ItemType.GOLD, 12000),
	GEMS_MINE("gems_mine.png", ItemType.GEMS, 16000);

	private String imagePath;
	private ItemType itemType;
	private int productionTime;

	BuildingType(String imagePath, ItemType itemType, int productionTime) {
		this.imagePath = imagePath;
		this.itemType = itemType;
		this.productionTime = productionTime;
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

}
