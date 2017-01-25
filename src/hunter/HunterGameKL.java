package hunter;

import core.PropertiesReader;
import core.SMA;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by escobedo on 24/01/17.
 */
public class HunterGameKL implements KeyListener {

    public static int hunterSpeed = PropertiesReader.getInstance().getHunterSpeed();
    public static int avatarSpeed = PropertiesReader.getInstance().getAvatarSpeed();
    private AvatarSMA aSMA;

    public HunterGameKL(AvatarSMA sma) {
        super();
        this.aSMA = sma;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                if(hunterSpeed - 1 > 0) hunterSpeed -= 1;
                break;
            case KeyEvent.VK_Z:
                hunterSpeed += 1;
                break;
            case KeyEvent.VK_O:
                if(avatarSpeed - 1 > 0) avatarSpeed -= 1;
                break;
            case KeyEvent.VK_P:
                hunterSpeed += 1;
                break;
            case KeyEvent.VK_SPACE:
                aSMA.paused = !aSMA.isPaused();
                System.out.println("Game status : " + (aSMA.isPaused() ?"Paused" : "Running"));
                break;
            case KeyEvent.VK_W:
                if(SMA.DELAY + 10 > 0) SMA.DELAY -= 10;
                break;
            case KeyEvent.VK_X:
                SMA.DELAY += 10;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
