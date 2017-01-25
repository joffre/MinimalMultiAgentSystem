package hunter;

import core.AgentPanel;
import core.MColor;
import core.PropertiesReader;
import particles.MMASWindow;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Geoffrey on 20/01/2017.
 */
public class AvatarMain {
    public static void main(String[] args){

        AvatarSMA sma = new AvatarSMA();

        AgentPanel avatarPanel = new AgentPanel(MColor.TAN);
        MMASWindow win = new MMASWindow("MinimalMultiAgentsSystem - Hunters", avatarPanel);
        sma.addObserver(avatarPanel);
        sma.addAvatarKeyListener(win);
        win.addKeyListener(new HunterGameKL(sma));
        win.setVisible(true);

        sma.run();
    }
}
