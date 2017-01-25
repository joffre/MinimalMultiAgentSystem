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
	public static final boolean GRID_IS_VISIBLE = PropertiesReader.getInstance().gridIsVisible();
	public static final int BOX_SIZE = PropertiesReader.getInstance().getBoxSize();

	private SMA sma = null;
	
	public AgentPanel(Color backgroundColor) {
		super();
		this.setPreferredSize(new Dimension(PropertiesReader.getInstance().getCanvasSizeX(), PropertiesReader.getInstance().getCanvasSizeY()));
		this.setBackground(backgroundColor);
	}

	public void paint(Graphics g) {
		super.paint(g);

		if(sma != null) {
			int gridSizeX = sma.getEnv().getGridSizeX();
			int gridSizeY = sma.getEnv().getGridSizeY();
			for (int a = 0; a < sma.getAgentList().size(); a++) {
				Agent agent = sma.getAgentList().get(a);

				int y = agent.getPosition().getPosX();
				int x = agent.getPosition().getPosY();

				g.setColor(agent.getColor());
				g.fillOval(x * BOX_SIZE, y * BOX_SIZE, BOX_SIZE-1, BOX_SIZE);
			}
			if(GRID_IS_VISIBLE){
				g.setColor(Color.DARK_GRAY);
				for(int x = 1; x <= gridSizeY; x++){
					g.drawLine(x*BOX_SIZE, 0, x*BOX_SIZE, BOX_SIZE*gridSizeX);
				}

				for(int y = 1; y <= gridSizeX; y++){
					g.drawLine(0, y*BOX_SIZE, BOX_SIZE*gridSizeY, y*BOX_SIZE);
				}
			}
		}
	}

	public void update(Observable obs, Object arg1) {
		sma = (SMA) obs;
		repaint();
	}
}
