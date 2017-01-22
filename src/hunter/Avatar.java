package hunter;

import core.Agent;
import core.Environment;
import core.Position;
import core.PropertiesReader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Geoffrey on 20/01/2017.
 */
public class Avatar extends Agent implements KeyListener{

    int dirX;
    int dirY;
    int speed;

    public Avatar(Environment env, Color color, Position position) {
        super(env, color, position);
        dirX = 0;
        dirY = 0;
        speed = PropertiesReader.getInstance().getAvatarSpeed();
    }

    @Override
    public void update() {

    }

    @Override
    public void decide(int currentTick) {
        if(currentTick%speed == 0){

            int newPositionX = getPosition().getPosX()+dirX;
            int newPositionY = getPosition().getPosX()+dirY;

            int gridSizeX = env.getGridSizeX();
            int gridSizeY = env.getGridSizeY();

            /**
             * New position (toric or not)
             */
            if(env.isToric()){
                if(newPositionX < 0){
                    newPositionX = gridSizeX -1;
                } else if(newPositionX >= gridSizeX){
                    newPositionX = 0;
                }
                if(newPositionY < 0){
                    newPositionY = gridSizeY -1;
                } else if(newPositionY >= gridSizeY){
                    newPositionY = 0;
                }
            } else {
                if(newPositionX >= gridSizeX || newPositionX <0){
                    newPositionX = getPosition().getPosX();
                    dirX = 0;
                }
                if(newPositionY >= gridSizeY || newPositionY <0){
                    newPositionY = getPosition().getPosY();
                    dirY = 0;
                }
            }
            Agent dest = env.getAgentGrid()[newPositionX][newPositionY];
            if(dest != null && dest instanceof Wall){
                dirX = 0;
                dirY = 0;
            } else {
                env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = null;
                getPosition().setPosX(newPositionX);
                getPosition().setPosY(newPositionY);
                env.getAgentGrid()[getPosition().getPosX()][getPosition().getPosY()] = this;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT :
                dirX = 0;
                dirY = -1;
                break;
            case KeyEvent.VK_RIGHT :
                dirX = 0;
                dirY = 1;
                break;
            case KeyEvent.VK_DOWN :
                dirX = 1;
                dirY = 0;
                break;
            case KeyEvent.VK_UP :
                dirX = -1;
                dirY = 0;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }
}
