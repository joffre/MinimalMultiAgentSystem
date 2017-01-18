package core;

import java.awt.Color;

public interface Agent {
	
	public void update();

	public void decide();

	public Color getColor();
	
	public Position getPosition();
	
}
