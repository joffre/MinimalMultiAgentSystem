package core;

import java.util.*;

public abstract class SMA extends Observable{

	private List<Agent> agentList;
	private Environment env;
	public static final int TICK_NUMBER = PropertiesReader.getInstance().getTickNumber();
	public static final char SCHEDULING_TYPE = PropertiesReader.getInstance().getScheduling();
	public static final int DELAY = PropertiesReader.getInstance().getDelay();
	protected int currentTick;

	public SMA(){
		this.env = new Environment();
		agentList = new ArrayList<Agent>();
		currentTick = -1;

		initAgents();
	}
	
	public List<Agent> getAgentList(){
		return this.agentList;
	}

	public abstract void initAgents();

	public abstract void startTickAction();

	public void run(){
		int currentTick = 0;
		while(TICK_NUMBER == 0 || currentTick < TICK_NUMBER){
			
			switch (SCHEDULING_TYPE) {
			case 'E': //'E' - Equitable
				Collections.shuffle(agentList);
				for(Agent agent : agentList){
					agent.decide(currentTick);
					agent.update();
				}
				break;
			case 'A': //'A' - Random
				int size = agentList.size();
				Random r = new Random();
				for(int i = 0; i < r.nextInt(size); i ++){
					Agent agent = agentList.get(r.nextInt(size));
					agent.decide(currentTick);
					agent.update();
				}
				break;
			default: //'S' - Sequential
				for(Agent agent : agentList){
					agent.decide(currentTick);
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

	public abstract void endTickAction();

	public Environment getEnv(){
		return this.env;
	}

	public int getCurrentTick(){
		return this.currentTick;
	}
}
