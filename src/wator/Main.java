package wator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Agent;
import core.AgentPanel;
import core.Environment;
import core.Position;
import core.PropertiesReader;
import core.SMA;
import output.Log;
import particules.MMASWindow;

public class Main {

	public static void main(String[] args) {

		Environment env = new Environment();
		
		List<Agent> agents = new ArrayList<Agent>();

		List<Position> positions = new ArrayList<Position>();
		
		for(int x = 0; x < PropertiesReader.getInstance().getGridSizeX(); x++){
			for(int y = 0; y < PropertiesReader.getInstance().getGridSizeY(); y++){
				positions.add(new Position(x, y));
			}
		}
		
		for(int f = 0; f < PropertiesReader.getInstance().getFishNumber(); f++){
			Random r = new Random();
			Position position = positions.remove(r.nextInt(positions.size()));
			Fish fish = new Fish(env, position.getPosX(), position.getPosY());
			agents.add(fish);
		}
		
		for(int s = 0; s < PropertiesReader.getInstance().getSharkNumber(); s++){
			Random r = new Random();
			Position tmp = positions.remove(r.nextInt(positions.size()));
			Shark shark = new Shark(env, tmp.getPosX(), tmp.getPosY());
			agents.add(shark);
		}
		
		positions.clear();
		
		env.putAgents(agents);
		
		SMA sma = new SMA(env, agents);
		
		AgentPanel partPanel = new AgentPanel(Color.white);
		MMASWindow win = new MMASWindow("MinimalMultiAgentsSystem - Wator", partPanel);
		sma.addObserver(partPanel);
		win.setVisible(true);
		
		sma.run();
	}

}
