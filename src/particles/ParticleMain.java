package particles;

import java.awt.Color;

import core.AgentPanel;
import core.SMA;

public class ParticleMain {
	
	public static void main(String[] args){
		
		SMA sma = new ParticleSMA();
		
		AgentPanel partPanel = new AgentPanel(Color.white);
		MMASWindow win = new MMASWindow("MinimalMultiAgentsSystem - Particules", partPanel);
		sma.addObserver(partPanel);
		win.setVisible(true);
		
		sma.run();
	}
}
