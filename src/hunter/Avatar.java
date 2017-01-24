package hunter;

import core.Agent;
import core.Environment;
import core.Position;
import core.PropertiesReader;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Random;

/**
 * Created by Geoffrey on 20/01/2017.
 */
public class Avatar extends Agent implements KeyListener{

    private int dirX;
    private int dirY;
    int speed;
    int eatDefender;

    boolean alive;
    boolean winner;
    boolean winnerCreated;

    public Avatar(Environment env, Color color, Position position) {
        super(env, color, position);
        dirX = 0;
        dirY = 0;
        speed = PropertiesReader.getInstance().getAvatarSpeed();
        eatDefender = 0;
        alive = true;
        winner = false;
        winnerCreated = false;
    }

    @Override
    public void update() {

    }

    @Override
    public void decide(int currentTick) {
        if(currentTick%speed == 0){

            int newPositionX = getPosition().getPosX()+dirX;
            int newPositionY = getPosition().getPosY()+dirY;

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
            if(dest != null){
                if(dest instanceof Wall) {
                    dirX = 0;
                    dirY = 0;
                } else if(dest instanceof Defender && ((Defender) dest ).isAlive() && eatDefender >= 0){
                    env.removeAgent(dest);
                    eatDefender++;
                    moveToNewPosition(newPositionX, newPositionY);
                    if(eatDefender >= 4 && !winnerCreated){
                        createWinner();
                        winnerCreated = true;
                    }
                } else if(dest instanceof Winner){
                    winner = true;
                }
            } else {
                moveToNewPosition(newPositionX, newPositionY);
            }
        }
    }

    private void createWinner() {
        List<Position> freePositions = env.getAllFreePositions();
        int randomIndex = (new Random()).nextInt(freePositions.size());
        env.addAgent(new Winner(env, freePositions.get(randomIndex)));
    }

    public void keyTyped(KeyEvent e) {
        //
    }

    public void keyPressed(KeyEvent e) {
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

    public void keyReleased(KeyEvent e) {
        //
    }

    public void kill(){
        if(isAlive() && !isWinner()) alive = false;
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean isWinner(){
        return winner;
    }

}
