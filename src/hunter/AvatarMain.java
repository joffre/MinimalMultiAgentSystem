package hunter;

import core.AgentPanel;
import particles.MMASWindow;

import java.awt.*;

/**
 * Created by Geoffrey on 20/01/2017.
 */
public class AvatarMain {

    public static void main(String[] args){
        AvatarSMA sma = new AvatarSMA();

        AgentPanel avatarPanel = new AgentPanel(Color.LIGHT_GRAY);
        MMASWindow win = new MMASWindow("MinimalMultiAgentsSystem - Hunters", avatarPanel);
        sma.addObserver(avatarPanel);
        sma.addAvatarKeyListener(win);
        win.addKeyListener(new HunterGameKL(sma));
        win.setVisible(true);

		/*WatorLineChart lineChart = new WatorLineChart();

		sma.addObserver(lineChart);

		Thread t = new Thread(){
			@Override
			public void run() {
				lineChart.run();
			}
		};

		t.start();*/
        sma.run();


    }
}
