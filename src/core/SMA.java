package core;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class SMA extends Observable{

	private List<Agent> agentList;
	private Environment env;
	public static final int TICK_NUMBER = PropertiesReader.getInstance().getTickNumber();
	public static final char SCHEDULING_TYPE = PropertiesReader.getInstance().getScheduling();
	public static final int DELAY = PropertiesReader.getInstance().getDelay();

	public SMA(Environment env, List<Agent> agents){
		this.env = env;
		agentList = agents;
	}
	
	public void setAgentList(List<Agent> agents){
		this.agentList = agents;
	}
	
	public List<Agent> getAgentList(){
		return this.agentList;
	}
	
	public void run(){
		int currentTick = 0;
		while(TICK_NUMBER == 0 || currentTick < TICK_NUMBER){
			
			switch (SCHEDULING_TYPE) {
			case 'E': //'E' - Equitable
				Collections.shuffle(agentList);
				for(Agent agent : agentList){
					agent.decide();
					agent.update();
				}
				break;
			case 'A': //'A' - Random
				int size = agentList.size();
				Random r = new Random();
				for(int i = 0; i < r.nextInt(size); i ++){
					Agent agent = agentList.get(r.nextInt(size));
					agent.decide();
					agent.update();
				}
				break;
			default: //'S' - Sequential
				for(Agent agent : agentList){
					agent.decide();
					agent.update();
				}
			}
			currentTick ++;

			if(!env.getAgentsToRemove().isEmpty()) {
				agentList.removeAll(env.getAgentsToRemove());
				env.getAgentsToAdd().removeAll(env.getAgentsToRemove());
				env.getAgentsToRemove().clear();
			}
			if(!env.getAgentsToAdd().isEmpty()){
				agentList.addAll(env.getAgentsToAdd());
				env.getAgentsToAdd().clear();
			}

			setChanged();
			notifyObservers();
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(agentList.size() == 0) break;
		}
	}

	public Environment getEnv(){
		return this.env;
	}
}
