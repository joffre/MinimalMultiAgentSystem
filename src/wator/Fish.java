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
	
	public Fish(Environment environment, int posX, int posY) {
		this(environment, posX, posY, false);
	}
	
	public Fish(Environment environment,int posX, int posY, boolean baby) {
		super(environment, posX, posY, baby);
		color = (baby)?Color.CYAN:Color.BLUE;
	}
	
	@Override
	public void update() {
		//générer les positions possibles
		
		if(isAlive()){
			if(baby){
				baby = false;
				color = Color.BLUE;
			}
			List<Position> positions = findFreePositions();
			//si une nouvelle position a été trouvée, bouger dans l'environnement
			if(!positions.isEmpty()){
				
				Random random = new Random();
				
				env.getAgentGrid()[posX][posY] = null;
				
				if(currentBreedTime >= FISH_BREED_TIME){
					Fish babyFish = new Fish(env, posX, posY, true);
					env.getAgentGrid()[posX][posY] = babyFish;
					env.getAgentsToAdd().add(babyFish);
					Log.info("Agent;"+"Fish;"+"BORN;"+posX+";"+posY+";");
					currentBreedTime = -1;
				}
				
				Position position = positions.get(random.nextInt(positions.size()));
				posX = position.getPosX();
				posY = position.getPosY();
				env.getAgentGrid()[posX][posY] = this;
				currentBreedTime++;
			}
		}
	}

	@Override
	public void decide() {
	}

}
