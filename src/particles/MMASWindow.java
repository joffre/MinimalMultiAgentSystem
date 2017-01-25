package particles;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import core.AgentPanel;

public class MMASWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1469044612333244500L;

	public MMASWindow(String name, AgentPanel panel) throws HeadlessException {
		super(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(new JScrollPane(panel));
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	
}
