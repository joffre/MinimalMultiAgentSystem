package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {	
	
	private final String GRID_SIZE_X = "GRID_SIZE_X";
	private final String GRID_SIZE_Y = "GRID_SIZE_Y";
	private final String CANVAS_SIZE_X = "CANVAS_SIZE_X";
	private final String CANVAS_SIZE_Y = "CANVAS_SIZE_Y";
	private final String BOX_SIZE = "BOX_SIZE";
	private final String DELAY = "DELAY";
	private final String SCHEDULING = "SHEDULING";
	private final String GRID = "GRID";
	private final String TRACE = "TRACE";
	private final String SEED = "SEED";
	private final String REFRESH = "REFRESH";
	private final String PART_NUMBER = "PART_NUMBER";
	private final String TORIC = "TORIC";
	
	private final String TICK_NUMBER = "TICK_NUMBER";
	
	private final String SHARK_NUMBER = "SHARK_NUMBER";
	private final String SHARK_BREED_TIME = "SHARK_BREED_TIME";
	private final String SHARK_STARVE_TIME = "SHARK_STARVE_TIME";
	
	private final String FISH_NUMBER = "FISH_NUMBER";
	private final String FISH_BREED_TIME = "FISH_BREED_TIME";

	private final String WALLS_PERCENT="WALLS_PERCENT";
	private final String HUNTER_NUMBER="HUNTER_NUMBER";
	private final String HUNTER_SPEED="HUNTER_SPEED";
	private final String AVATAR_SPEED="AVATAR_SPEED";
	private final String DEFENDER_LIFE="DEFENDER_LIFE";
	
	private Properties p;

	private static PropertiesReader instance = null;
	
	private PropertiesReader(File configFile){
		p = new Properties();
		if(!configFile.exists()){
			try {
				configFile.createNewFile();
				p.setProperty(GRID_SIZE_X, "200");
				p.setProperty(GRID_SIZE_Y, "200");
				p.setProperty(CANVAS_SIZE_X, "1080");
				p.setProperty(CANVAS_SIZE_Y, "900");
				p.setProperty(BOX_SIZE, "10");
				p.setProperty(DELAY, "100");
				p.setProperty(SCHEDULING, "S"); //EQUITABLE / SEQUENTIEL / ALEATOIRE
				p.setProperty(GRID, "true");
				p.setProperty(TRACE, "true");
				p.setProperty(SEED, "0");
				p.setProperty(REFRESH, "1");
				p.setProperty(TICK_NUMBER, "0");
				p.setProperty(TORIC, "false");
				
				p.setProperty(PART_NUMBER, "1000");
				
				p.setProperty(SHARK_NUMBER, "1000");
				p.setProperty(SHARK_BREED_TIME, "10");
				p.setProperty(SHARK_STARVE_TIME, "3");
				
				p.setProperty(FISH_NUMBER, "1000");
				p.setProperty(FISH_BREED_TIME, "2");

				p.setProperty(WALLS_PERCENT, "40");
				p.setProperty(HUNTER_NUMBER, "5");
				p.setProperty(HUNTER_SPEED, "1");
				p.setProperty(AVATAR_SPEED, "1");
				p.setProperty(DEFENDER_LIFE, "10");
				
				p.store((new FileWriter(configFile)), "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				p.load((new FileReader(configFile)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static PropertiesReader getInstance(){
		return (instance == null)? new PropertiesReader(new File("config.properties")) : instance ;
	}
	
	public int getGridSizeX(){
		return Integer.parseInt((String) p.getOrDefault(GRID_SIZE_X, 60));
	}
	
	public int getGridSizeY(){
		return Integer.parseInt((String) p.getOrDefault(GRID_SIZE_Y, 60));
	}
	
	public int getCanvasSizeX(){
		return Integer.parseInt((String) p.getOrDefault(CANVAS_SIZE_X, 1080));
	}
	
	public int getCanvasSizeY(){
		return Integer.parseInt((String) p.getOrDefault(CANVAS_SIZE_Y, 900));
	}
	
	public int getBoxSize(){
		return Integer.parseInt((String) p.getOrDefault(BOX_SIZE, 10));
	}

	public int getDelay(){
		return Integer.parseInt((String) p.getOrDefault(DELAY, 100));
	}
	
	public char getScheduling(){
		return ((String) p.getOrDefault(SCHEDULING, "S")).charAt(0);
	}
	
	public boolean gridIsVisible(){
		return Boolean.parseBoolean((String) p.getOrDefault(GRID, true));
	}
	
	public boolean traceIsVisible(){
		return Boolean.parseBoolean((String) p.getOrDefault(TRACE, true));
	}
	
	public int getSeed(){
		return Integer.parseInt((String) p.getOrDefault(SEED, 0));
	}
	
	public int getRefreshFrequency(){
		return Integer.parseInt((String) p.getOrDefault(REFRESH, 1));
	}
	
	public int getPartNumber(){
		return Integer.parseInt((String) p.getOrDefault(PART_NUMBER, 1000));
	}
	
	public boolean isToric(){
		return Boolean.parseBoolean((String) p.getOrDefault(TORIC, false));
	}
	
	public int getTickNumber(){
		return Integer.parseInt((String) p.getOrDefault(TICK_NUMBER, 0));
	}
	
	public int getSharkNumber(){
		return Integer.parseInt((String) p.getOrDefault(SHARK_NUMBER, 0));
	}
	
	public int getSharkBreedTime(){
		return Integer.parseInt((String) p.getOrDefault(SHARK_BREED_TIME, 10));
	}
	
	public int getSharkStarveTime(){
		return Integer.parseInt((String) p.getOrDefault(SHARK_STARVE_TIME, 3));
	}
	
	public int getFishNumber(){
		return Integer.parseInt((String) p.getOrDefault(FISH_NUMBER, 0));
	}
	
	public int getFishBreedTime(){
		return Integer.parseInt((String) p.getOrDefault(FISH_BREED_TIME, 2));
	}

	public int getWallsPercent(){
		return Integer.parseInt((String) p.getOrDefault(WALLS_PERCENT,40));
	}

	public int getHunterNumber(){
		return Integer.parseInt((String) p.getOrDefault(HUNTER_NUMBER, 5));
	}

	public int getHunterSpeed(){
		return Integer.parseInt((String) p.getOrDefault(HUNTER_SPEED, 1));
	}

	public int getAvatarSpeed(){
		return Integer.parseInt((String) p.getOrDefault(AVATAR_SPEED, 1));
	}

	public int getDefenderLife(){
		return Integer.parseInt((String) p.getOrDefault(DEFENDER_LIFE, 6));
	}
}
