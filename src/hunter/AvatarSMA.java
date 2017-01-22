package hunter;

import core.Agent;
import core.Position;
import core.PropertiesReader;
import core.SMA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Geoffrey on 21/01/2017.
 */
public class AvatarSMA extends SMA {

    @Override
    public void initAgents() {

        PropertiesReader params = PropertiesReader.getInstance();
        Random random = new Random(params.getSeed());

        List<Position> positions = new ArrayList<Position>();

        for(int x = 0; x < PropertiesReader.getInstance().getGridSizeX(); x++){
            for(int y = 0; y < PropertiesReader.getInstance().getGridSizeY(); y++){
                positions.add(new Position(x, y));
            }
        }

        //INIT WALLS
        /**
         * Alea Walls
         */
        int wallsNumber = getEnv().getGridSizeX()*getEnv().getGridSizeY() * params.getWallsPercent() / 100;

        for(int w = 0; w < wallsNumber; w++){
            Position wallPosition = positions.remove(random.nextInt(positions.size()));
            getAgentList().add(new Wall(getEnv(), Color.GRAY, wallPosition));
        }

        //init Avatar
        Position avatarPosition = positions.remove((random.nextInt(positions.size())));
        getAgentList().add(new Avatar(getEnv(), Color.GREEN, avatarPosition));

        //init Hunters
        int huntersNumber = params.getHunterNumber();
        for(int h = 0; h < huntersNumber; h++){
            Position hunterPosition = positions.remove(random.nextInt(positions.size()));
            getAgentList().add(new Hunter(getEnv(), Color.RED, hunterPosition));
        }

        //init Defenders
        int defendersNumber = 0;
        for(int d = 0; d < defendersNumber; d++){
            Position defenderposition = positions.remove(random.nextInt(positions.size()));
            //TODO add Hunter
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
    public void endTickAction() {

    }
}
