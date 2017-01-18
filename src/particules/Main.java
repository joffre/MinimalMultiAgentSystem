package particules;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Agent;
import core.AgentPanel;
import core.Environment;
import core.PropertiesReader;
import core.SMA;
import utils.AgentGenerator;

public class Main {
	
	public static void main(String[] args){
		
		Environment env = new Environment();
		
		List<Agent> agents = new ArrayList<Agent>();

		List<Point> positions = new ArrayList<Point>();
		
		for(int x = 0; x < PropertiesReader.getInstance().getGridSizeX(); x++){
			for(int y = 0; y < PropertiesReader.getInstance().getGridSizeY(); y++){
				positions.add(new Point(x, y));
			}
		}
		
		for(int i = 0; i < PropertiesReader.getInstance().getPartNumber(); i++){
			Random r = new Random();
			Point tmp = positions.remove(r.nextInt(positions.size()));
			Particule agent = AgentGenerator.createRandomAgent((int)tmp.getX(), (int)tmp.getY());
			agent.setEnvironment(env);
			agents.add(agent);
		}
		
		positions.clear();
		
		env.putAgents(agents);
		
		SMA sma = new SMA(env, agents);
		
		AgentPanel partPanel = new AgentPanel(Color.white);
		MMASWindow win = new MMASWindow("MinimalMultiAgentsSystem - Particules", partPanel);
		sma.addObserver(partPanel);
		win.setVisible(true);
		
		sma.run();
	}
	
	//+1 modulo la taille - déplacement à droite
	//-1 + la taille modulo la taille
	
}
