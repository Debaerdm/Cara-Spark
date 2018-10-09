package model;

import java.io.Serializable;

public abstract class Tile implements Serializable {

	private static final long serialVersionUID = 7089070436583956248L;
	boolean isWall;
	String imagePath;

	public abstract String getImagePath();
	public abstract boolean isWall();

}
