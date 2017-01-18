package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class AgentPanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1883316852901580151L;
	
	private SMA sma = null;
	
	public AgentPanel(Color backgroundColor) {
		super();
		this.setPreferredSize(new Dimension(PropertiesReader.getInstance().getCanvasSizeX(), PropertiesReader.getInstance().getCanvasSizeY()));
		this.setBackground(backgroundColor);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		int gridSizeX = PropertiesReader.getInstance().getGridSizeX();
		int gridSizeY = PropertiesReader.getInstance().getGridSizeY();
		int boxSize = PropertiesReader.getInstance().getBoxSize();
		if(sma != null)
		for(int a = 0; a < sma.getAgentList().size(); a++){
			Agent agent = sma.getAgentList().get(a);
			
			int x = agent.getPosition().getPosX();
			int y = agent.getPosition().getPosY();
			
			g.setColor(agent.getColor());	
			g.fillOval(x*boxSize,y*boxSize,boxSize,boxSize);
		}
		
		/*
		
		for(int l = 0; l < gridSizeX; l++){
			for(int c = 0; c < gridSizeY; c++){
				Agent agent = env.getAgentGrid()[l][c];
				g.setColor(agent.getColor());
				g.fillOval(l*boxSize,c*boxSize,boxSize,boxSize);
			}
		}*/
		
		if(PropertiesReader.getInstance().gridIsVisible()){
			g.setColor(Color.DARK_GRAY);
			for(int x = 1; x < gridSizeX; x++){
				g.drawLine(x*boxSize, 0, x*boxSize, boxSize*gridSizeX);
			}
			
			for(int y = 1; y < gridSizeY; y++){
				g.drawLine(0, y*boxSize, boxSize*gridSizeY, y*boxSize);
			}
		}
	}

	@Override
	public void update(Observable obs, Object arg1) {
		sma = (SMA) obs;
		repaint();
	}
}
