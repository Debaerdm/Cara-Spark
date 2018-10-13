package model.tile;

import java.io.Serializable;

public abstract class Tile implements Serializable {

	private static final long serialVersionUID = 7089070436583956248L;
	protected boolean isWall;
	protected String imagePath;
	protected int row;
	protected int col;

	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public String getImagePath() {
		return imagePath;
	}

	public boolean isWall() {
		return isWall;
	}

	public abstract void update();

}
