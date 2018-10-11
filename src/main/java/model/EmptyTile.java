package model;

public class EmptyTile extends Tile {

	private static final long serialVersionUID = 205427117422667286L;
	private transient Dungeon dungeon;

	public EmptyTile(Dungeon dungeon, boolean isWall, int row, int col) {
		super(row, col);
		this.dungeon = dungeon;
		this.isWall = isWall;
	}

	public void update() {
		if (!isWall) {
			imagePath = "ground.png";
		} else {
			boolean north = !(dungeon.getTile(row-1, col) == null || dungeon.getTile(row-1, col).isWall());
			boolean east = !(dungeon.getTile(row, col+1) == null || dungeon.getTile(row, col+1).isWall());
			boolean west = !(dungeon.getTile(row, col-1) == null || dungeon.getTile(row, col-1).isWall());
			boolean south = !(dungeon.getTile(row+1, col) == null || dungeon.getTile(row+1, col).isWall());
			boolean northeast = !north && !east && !(dungeon.getTile(row-1, col+1) == null || dungeon.getTile(row-1, col+1).isWall());
			boolean southeast = !south && !east && !(dungeon.getTile(row+1, col+1) == null || dungeon.getTile(row+1, col+1).isWall());
			boolean southwest = !south && !west && !(dungeon.getTile(row+1, col-1) == null || dungeon.getTile(row+1, col-1).isWall());
			boolean northwest = !north && !west && !(dungeon.getTile(row-1, col-1) == null || dungeon.getTile(row-1, col-1).isWall());
			imagePath = "wall_"
			+ (northwest ? "1" : "")
			+ (north ? "2" : "")
			+ (northeast ? "3" : "")
			+ (east ? "4" : "")
			+ (southeast ? "5" : "")
			+ (south ? "6" : "")
			+ (southwest ? "7" : "")
			+ (west ? "8" : "")
			+".png";
		}
	}

}
