package hunter;

import core.Agent;
import core.Position;
import core.PropertiesReader;
import core.SMA;
import output.Log;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Geoffrey on 21/01/2017.
 */
public class AvatarSMA extends SMA {

    Random rand;
    int DEFENDER_SPAWN_FREQUENCY;
    private final int DEFENDER_LIFE = PropertiesReader.getInstance().getDefenderLife();
    Avatar player;
    Dijkstra dijkstra;
    public boolean paused;

    public AvatarSMA(){
        super();
        DEFENDER_SPAWN_FREQUENCY = 50;

    }

    @Override
    public void initAgents() {
        rand = new Random(PropertiesReader.getInstance().getSeed());
        PropertiesReader params = PropertiesReader.getInstance();
        dijkstra = new Dijkstra(params.getGridSizeX(), params.getGridSizeY());

        List<Position> positions = new ArrayList<Position>();

        for(int x = 0; x < params.getGridSizeX(); x++){
            for(int y = 0; y < params.getGridSizeY(); y++){
                positions.add(new Position(x, y));
            }
        }

        List<Position> positionsToExclude = new ArrayList<>();

        //INIT WALLS
        /**
         * Alea Walls
         */
        int wallsNumber = getEnv().getGridSizeX()*getEnv().getGridSizeY() * params.getWallsPercent() / 100;

        for(int w = 0; w < wallsNumber; w++){
            Position wallPosition = positions.remove(rand.nextInt(positions.size()));
            getAgentList().add(new Wall(getEnv(), Color.GRAY, wallPosition));
            positionsToExclude.add(new Position(wallPosition.getPosX(), wallPosition.getPosY()));
        }

        dijkstra.setExcludedPositions(positionsToExclude);

        //init Avatar
        Position avatarPosition = positions.remove((rand.nextInt(positions.size())));
        player = new Avatar(getEnv(), Color.GREEN, avatarPosition, dijkstra);
        getAgentList().add(player);

        //init Hunters
        int huntersNumber = params.getHunterNumber();
        for(int h = 0; h < huntersNumber; h++){
            Position hunterPosition = positions.remove(rand.nextInt(positions.size()));
            getAgentList().add(new Hunter(getEnv(), hunterPosition, dijkstra));
        }

        positions.clear();

        getEnv().putAgents(getAgentList());
    }

    public void addAvatarKeyListener(JFrame panel){
        for(Agent a : getAgentList()){
            if(a instanceof Avatar){
                panel.addKeyListener((Avatar) a);
            }
        }
    }

    @Override
    public void startTickAction() {

    }

    @Override
    protected boolean isPaused() {
        return paused;
    }

    @Override
    public void endTickAction() {
        if(player.isAlive()){
            if(!player.isWinner()){
                /**
                 * Spawn defender
                 */
                if(getCurrentTick() > 0 && getCurrentTick() % DEFENDER_SPAWN_FREQUENCY == 0){
                    List<Position> freePositions = getEnv().getAllFreePositions();

                    if(!freePositions.isEmpty()) {
                        Defender defender = new Defender(getEnv(), Color.CYAN, freePositions.get(rand.nextInt(freePositions.size())), DEFENDER_LIFE);

                        getEnv().addAgent(defender);
                    }
                }
            } else {
                currentTick = -1;
                JOptionPane.showMessageDialog (null, "You won ! Congratulations ;)", "Owned !", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            currentTick = -1;
            JOptionPane.showMessageDialog (null, "Game Over, You died...!", "Failed !", JOptionPane.ERROR_MESSAGE);
        }
        if(Log.isEnabled())
            for(Agent a : getAgentList()){
                if(!(a instanceof Wall)){
                    Log.info(a.getCSVString());
                }
            }
    }
}
