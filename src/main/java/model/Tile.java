package model;

import java.io.Serializable;

public abstract class Tile implements Serializable {

	private static final long serialVersionUID = 7089070436583956248L;
	protected boolean isWall;
	protected String imagePath;

	public String getImagePath() {
		return imagePath;
	}

	public boolean isWall() {
		return isWall;
	}

	public abstract void update();

}
