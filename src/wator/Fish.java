package wator;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import core.Environment;
import core.Position;
import core.PropertiesReader;

public class Fish extends MarineAnimal{
	
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
				
				if(currentBreedTime >= PropertiesReader.getInstance().getFishBreedTime()){
					Fish babyFish = new Fish(env, posX, posY, true);
					env.getAgentGrid()[posX][posY] = babyFish;
					env.getAgentsToAdd().add(babyFish);
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
