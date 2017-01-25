package wator;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import core.Environment;
import core.Position;
import core.PropertiesReader;
import output.Log;

public class Fish extends MarineAnimal{

	public static final int FISH_BREED_TIME = PropertiesReader.getInstance().getFishBreedTime();
	
	public Fish(Environment environment, Position position) {
		this(environment, position, false);
	}
	
	public Fish(Environment environment,Position position, boolean baby) {
		super(environment, (baby)?Color.CYAN:Color.BLUE ,position, baby);
	}
	
	@Override
	public void update() {
		//générer les positions possibles
		
		if(isAlive()){
			if(baby){
				baby = false;
				setColor(Color.BLUE);
			}
			List<Position> positions = findFreePositions();
			//si une nouvelle position a été trouvée, bouger dans l'environnement
			if(!positions.isEmpty()){
				
				Random random = new Random();
				
				env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = null;
				
				if(currentBreedTime >= FISH_BREED_TIME){
					Fish babyFish = new Fish(env, new Position(getPosition().getPosX(), getPosition().getPosY()), true);
					env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = babyFish;
					env.getAgentsToAdd().add(babyFish);
					Log.info(getCSVString());
					currentBreedTime = -1;
				}
				
				Position newPosition = positions.get(random.nextInt(positions.size()));
				getPosition().setPosX(newPosition.getPosX());
				getPosition().setPosY(newPosition.getPosY());
				env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = this;
				currentBreedTime++;
			}
		}
	}

	@Override
	public void decide(int currentTick) {
	}

	@Override
	public String getCSVString() {
		return "Agent;"+"Fish;"+"BORN;"+getPosition().getPosX()+";"+getPosition().getPosY()+";";
	}

}
