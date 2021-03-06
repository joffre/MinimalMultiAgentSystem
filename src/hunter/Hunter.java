package hunter;

import core.*;

import java.awt.*;

/**
 * Created by Geoffrey on 20/01/2017.
 */
public class Hunter extends Agent {

    Dijkstra dijkstra;

    public Hunter(Environment env, Position position, Dijkstra dijkstra) {
        super(env, MColor.BROWN, position);
        this.dijkstra = dijkstra;
    }

    @Override
    public void update() {

    }

    @Override
    public void decide(int currentTick) {
        if(currentTick%HunterGameKL.hunterSpeed == 0){
            Position newPos = dijkstra.getNextShortestPosition(getPosition());
            int newPositionX = newPos.getPosX();
            int newPositionY = newPos.getPosY();

            Agent dest = env.getAgentGrid()[newPositionX][newPositionY];
            if(dest != null){
                if(dest instanceof Avatar && ((Avatar) dest ).isAlive() && !((Avatar) dest ).isWinner()){
                    ((Avatar) dest).kill();
                    env.removeAgent(dest);
                    moveToNewPosition(newPositionX, newPositionY);
                }
            } else {
                moveToNewPosition(newPositionX, newPositionY);
            }
        }
    }

    @Override
    public String getCSVString() {
        return "AVATAR;"+getPosition().getPosX()+";"+getPosition().getPosY()+";";
    }
}
