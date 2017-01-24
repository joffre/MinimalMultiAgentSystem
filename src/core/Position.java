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

	@Override
	public boolean equals(Object obj){
		if(obj instanceof Position){
			Position toE = (Position) obj;
			return toE.getPosX() == getPosX() && toE.getPosY() == getPosY();
		}
		return false;
	}

	@Override
	public int hashCode(){
		int tmp = ( posY +  ((posX+1)/2));
		return posX +  ( tmp * tmp);
	}
}
