package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Agent {

	private Color color;
	private Position position;
	protected Environment env;


	public Agent(Environment env, Color color, Position position){
		this.env = env;
		this.color = color;
		this.position = position;
	}

	public abstract void update();

	public abstract void decide(int currentTick);

	public Color getColor(){
		return this.color;
	}

	public void setColor(Color color){
		this.color = color;
	}
	
	public Position getPosition(){
		return this.position;
	}

	protected List<Position> findFreePositions(){
		int gridSizeX = env.getGridSizeX();
		int gridSizeY = env.getGridSizeY();

		List<Position> positions = new ArrayList<Position>();
		for(int x = -1; x <= 1; x++){
			for(int y = -1; y <= 1; y++){
				if(!(x == 0 && y == 0)){
					int newPositionX = getPosition().getPosX()+x;
					int newPositionY = getPosition().getPosY()+y;
					if(env.isToric()){
						if(newPositionX < 0){
							newPositionX = gridSizeX -1;
						} else if(newPositionX >= gridSizeX){
							newPositionX = 0;
						}
						if(newPositionY < 0){
							newPositionY = gridSizeY -1;
						} else if(newPositionY >= gridSizeY){
							newPositionY = 0;
						}
					} else {
						if(newPositionX >= gridSizeX || newPositionX <0){
							newPositionX = getPosition().getPosX();
						}
						if(newPositionY >= gridSizeY || newPositionY <0){
							newPositionY = getPosition().getPosY();
						}
					}
					if(env.getAgentGrid()[newPositionX][newPositionY] == null){
						positions.add(new Position(newPositionX, newPositionY));
					}
				}
			}
		}
		return positions;
	}
}
