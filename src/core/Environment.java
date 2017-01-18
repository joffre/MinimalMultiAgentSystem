package core;

import java.util.ArrayList;
import java.util.List;

public class Environment {
	
	private Agent[][] agentGrid;

	private List<Agent> agentsToRemove;
	private List<Agent> agentsToAdd;

	public Environment() {
		super();
		agentGrid = new Agent[PropertiesReader.getInstance().getGridSizeX()][PropertiesReader.getInstance().getGridSizeY()];
		setAgentsToRemove(new ArrayList<Agent>());
		setAgentsToAdd(new ArrayList<Agent>());
	}
	
	public Agent[][] getAgentGrid(){
		return this.agentGrid;
	}
	
	public void putAgents(List<Agent> agents){
		for(Agent agent : agents){
			agentGrid[agent.getPosition().posX][agent.getPosition().posY] = agent;
		}
	}

	public List<Agent> getAgentsToRemove() {
		return agentsToRemove;
	}

	public void setAgentsToRemove(List<Agent> agentsToRemove) {
		this.agentsToRemove = agentsToRemove;
	}

	public List<Agent> getAgentsToAdd() {
		return agentsToAdd;
	}

	public void setAgentsToAdd(List<Agent> agentsToAdd) {
		this.agentsToAdd = agentsToAdd;
	}

	public int getAgentNumber() {
		int i = 0;
		
		for(int x = 0; x < agentGrid.length; x++){
			for(int y = 0; y < agentGrid[0].length; y++){
				if(agentGrid[x][y] != null){
					i++;
				}
			}
		}
		
		return i;
	}
	
}
