package wator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Agent;
import core.Environment;
import core.Position;
import core.PropertiesReader;
import output.Log;

public class Shark extends MarineAnimal{
	int currentStarveTime;
	public static final int SHARK_STARVE_TIME = PropertiesReader.getInstance().getSharkStarveTime();
	public static final int SHARK_BREED_TIME = PropertiesReader.getInstance().getSharkBreedTime();
	
	public Shark(Environment environment, Position position) {
		this(environment, position, false);
	}
	
	public Shark(Environment environment, Position position, boolean baby) {
		super(environment, (baby)?Color.PINK:Color.RED, position, baby);
		this.currentStarveTime = SHARK_STARVE_TIME;
	}
	
	@Override
	public void update() {
		//générer les positions possibles
		if(isAlive()){
			if(baby){
				baby = false;	
				setColor(Color.RED);
			}
			if(currentStarveTime > 0){
				Position newPosition = null;
				List<Fish> fishesToEat = findFishesToEat();
				Random random = new Random();
				if(!fishesToEat.isEmpty()){
					Fish fish = fishesToEat.get(random.nextInt(fishesToEat.size()));
					newPosition = fish.getPosition();
					fish.kill();
					env.getAgentsToRemove().add(fish);
					env.getAgentGrid()[newPosition.getPosX()][newPosition.getPosY()] = null;
					currentStarveTime = SHARK_STARVE_TIME;
					Log.info("Agent;"+"Fish;"+"DEAD;"+fish.getPosition().getPosX()+";"+fish.getPosition().getPosY()+";");
				} else {
					List<Position> positions = findFreePositions();
					if(!positions.isEmpty()){
						newPosition = positions.get(random.nextInt(positions.size()));
					}
				}
				if(newPosition != null){
					env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = null;
					if(currentBreedTime >= SHARK_BREED_TIME){
						Shark babyShark = new Shark(env, new Position(getPosition().getPosX(), getPosition().getPosY()), true);
						env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = babyShark;
						env.getAgentsToAdd().add(babyShark);
						currentBreedTime = -1;
						Log.info("Agent;"+"Shark;"+"BORN;"+getPosition().getPosX()+";"+getPosition().getPosY()+";");
					}
					getPosition().setPosX(newPosition.getPosX());
					getPosition().setPosY(newPosition.getPosY());
					env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = this;
				}
				currentStarveTime--;
				currentBreedTime++;
			} else {
				env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = null;
				kill();
				env.getAgentsToRemove().add(this);
				Log.info("Agent;"+"Shark;"+"DEAD;"+getPosition().getPosX()+";"+getPosition().getPosY()+";");
			}
		}
	}
	
	private List<Fish> findFishesToEat(){
		int gridSizeX = env.getGridSizeX();
		int gridSizeY = env.getGridSizeY();
		
		List<Fish> fishes = new ArrayList<Fish>();
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
					Agent fToEat = env.getAgentGrid()[newPositionX][newPositionY];
					if(fToEat != null && fToEat instanceof Fish){
						fishes.add((Fish) fToEat);
					}
				}
			}
		}
		return fishes;
	}

	@Override
	public void decide(int currentTick) {
	}

}
