package particules;

import java.awt.Color;

import core.Agent;
import core.Environment;
import core.Position;
import core.PropertiesReader;

public class Particule implements Agent{
	Color color;
	int posX, posY;
	
	int pasX, pasY;
	
	Environment env;
	
	public Particule(Color color, int posX, int posY, int pasX, int pasY){
		this.color = color;
		this.posX = posX;
		this.posY = posY;
		this.pasX = pasX;
		this.pasY = pasY;
	}
	
	public void setEnvironment(Environment env){
		this.env = env;
	}
	
	public void update(){
		//+1 modulo la taille - deplacement à droite
		//-1 + la taille modulo la taille
		boolean frontCollision = false;
		int gridSizeX = env.getGridSizeX();
		int gridSizeY = env.getGridSizeY();
		
		int newPositionX = posX+pasX;
		int newPositionY = posY+pasY;
		
		/**
		 * New position (toric or not)
		 */
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
				pasX *= -1;
				newPositionX = posX;
				frontCollision = true;
			}
			if(newPositionY >= gridSizeY || newPositionY <0){
				pasY *= -1;
				newPositionY = posY;
				frontCollision = true;
			}
		}
		
		if(env.getAgentGrid()[newPositionX][newPositionY] != null && !frontCollision){
			exchangeAgentsDirection(this, (Particule) env.getAgentGrid()[newPositionX][newPositionY]);
		} else {
			env.getAgentGrid()[posX][posY] = null;
			posX = newPositionX;
			posY = newPositionY;
			env.getAgentGrid()[posX][posY] = this;
		}
	}
	
	private void exchangeAgentsDirection(Particule agent, Particule agent2) {
		int tmpPasX = agent.pasX;
		int tmpPasY = agent.pasY;
		
		agent.pasX = agent2.pasX;
		agent.pasY = agent2.pasY;
		
		agent2.pasX = tmpPasX;
		agent2.pasY = tmpPasY;
	}

	public void decide(){
		
	}

	public Color getColor() {
		return color;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}

	@Override
	public Position getPosition() {
		return new Position(posX, posY);
	}
}
