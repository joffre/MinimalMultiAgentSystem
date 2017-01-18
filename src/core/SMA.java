package core;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class SMA extends Observable{

	private List<Agent> agentList;
	private Environment env;
	
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
		while(PropertiesReader.getInstance().getTickNumber() == 0 || currentTick < PropertiesReader.getInstance().getTickNumber()){
			
			switch (PropertiesReader.getInstance().getScheduling()) {
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
			
			/*if(!agentList.containsAll(env.getAgentsToRemove())){
				System.out.println("FFFFAAAAIIIILLLL");
				for(Agent a : env.getAgentsToRemove()){
					if(!agentList.contains(a)){
						System.out.println(a +" " + a.getPosition().getPosX()+ "," + a.getPosition().getPosY() + " " + a.getColor());
						for(Agent c : agentList){
							if(c.toString().contains("false")){
								System.out.println("--> " + a +" " + c.getPosition().getPosX()+ "," + c.getPosition().getPosY() + " " + a.getColor());
							}
						}
					}
				}
			}*/
			agentList.removeAll(env.getAgentsToRemove());
			env.getAgentsToAdd().removeAll(env.getAgentsToRemove());
			agentList.addAll(env.getAgentsToAdd());
						
			env.getAgentsToRemove().clear();
			env.getAgentsToAdd().clear();

			setChanged();
			notifyObservers();
			try {
				Thread.sleep(PropertiesReader.getInstance().getDelay());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(agentList.size() == 0) break;
		}
	}
}
