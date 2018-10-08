package model;

public class EmptyTile extends Tile {

	private static final long serialVersionUID = 205427117422667286L;
	private boolean isWall;

	public EmptyTile(boolean isWall) {
		this.isWall = isWall;
	}

	@Override
	public String getImagePath() {
		return isWall ? "wall.png" : "ground.png";
	}

}
