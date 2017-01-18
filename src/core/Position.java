package core;

public class Position {
	
	
	public int posX;
	
	public int posY;

	public Position(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}

	public final int getPosX() {
		return posX;
	}

	public final void setPosX(int posX) {
		this.posX = posX;
	}

	public final int getPosY() {
		return posY;
	}

	public final void setPosY(int posY) {
		this.posY = posY;
	}

}
