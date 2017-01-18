package core;

import java.util.ArrayList;
import java.util.List;

public class Environment {
	
	private Agent[][] agentGrid;
	private boolean isToric;

	private List<Agent> agentsToRemove;
	private List<Agent> agentsToAdd;

	public Environment() {
		super();
		agentGrid = new Agent[PropertiesReader.getInstance().getGridSizeX()][PropertiesReader.getInstance().getGridSizeY()];
		isToric = PropertiesReader.getInstance().isToric();
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

	public int getGridSizeX(){
		return agentGrid.length;
	}

	public int getGridSizeY(){
		return agentGrid[0].length;
	}

	public boolean isToric(){
		return isToric;
	}
}
