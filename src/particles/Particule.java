package particles;

import java.awt.Color;

import core.Agent;
import core.Environment;
import core.Position;

public class Particule extends Agent{
	
	int pasX;
	int pasY;
	
	public Particule(Environment env, Color color, Position position, int pasX, int pasY){
		super(env, color, position);
		this.pasX = pasX;
		this.pasY = pasY;
	}
	
	public void update(){
		boolean frontCollision = false;
		int gridSizeX = env.getGridSizeX();
		int gridSizeY = env.getGridSizeY();
		
		int newPositionX = getPosition().getPosX()+pasX;
		int newPositionY = getPosition().getPosY()+pasY;
		
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
				newPositionX = getPosition().getPosX();
				frontCollision = true;
			}
			if(newPositionY >= gridSizeY || newPositionY <0){
				pasY *= -1;
				newPositionY = getPosition().getPosY();
				frontCollision = true;
			}
		}
		
		if(env.getAgentGrid()[newPositionX][newPositionY] != null && !frontCollision){
			exchangeAgentsDirection(this, (Particule) env.getAgentGrid()[newPositionX][newPositionY]);
		} else {
			env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = null;
			getPosition().setPosX(newPositionX);
			getPosition().setPosY(newPositionY);
			env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = this;
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

	public void decide(int currentTick){
		
	}

	@Override
	public String getCSVString() {
		return "PARTICLE;"+getPosition().getPosX()+";"+getPosition().getPosY()+";";
	}
}
