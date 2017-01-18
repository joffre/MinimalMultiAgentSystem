package wator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import core.Agent;
import core.Environment;
import core.Position;
import core.PropertiesReader;

public abstract class MarineAnimal implements Agent {

	int posX, posY;
	int currentBreedTime; //0
	boolean baby;
	Environment env;
	Color color;
	protected boolean alive;
	
	protected MarineAnimal(Environment environment,int posX, int posY, boolean baby) {
		super();
		this.baby = baby;
		this.posX = posX;
		this.posY = posY;
		this.currentBreedTime = 0;
		this.env = environment;
		this.alive = true;
	}
	
	protected List<Position> findFreePositions(){
		int gridSizeX = PropertiesReader.getInstance().getGridSizeX();
		int gridSizeY = PropertiesReader.getInstance().getGridSizeY();
		
		List<Position> positions = new ArrayList<Position>();
		for(int x = -1; x <= 1; x++){
			for(int y = -1; y <= 1; y++){
				if(!(x == 0 && y == 0)){
					int newPositionX = posX+x;
					int newPositionY = posY+y;
					if(PropertiesReader.getInstance().isToric()){
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
							newPositionX = posX;
						}
						if(newPositionY >= gridSizeY || newPositionY <0){
							newPositionY = posY;
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

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Position getPosition() {
		return new Position(posX, posY);
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public void kill(){
		this.alive = false;
	}
	
	public String toString(){
		return alive+"";
	}
}
