package model;

public class EmptyTile extends Tile {

	private static final long serialVersionUID = 205427117422667286L;

	EmptyTile(boolean isWall) {
		this.isWall = isWall;
		this.imagePath = isWall ? "wall.png" : "ground.png";
	}

	@Override
	public String getImagePath() {
		return this.imagePath;
	}

	@Override
	public boolean isWall() {
		return this.isWall;
	}

}
