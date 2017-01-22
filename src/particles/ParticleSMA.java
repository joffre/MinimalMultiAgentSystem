package particles;

import core.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Geoffrey on 18/01/2017.
 */
public class ParticleSMA extends SMA {

    @Override
    public void initAgents() {

        List<Position> positions = new ArrayList<Position>();

        for(int x = 0; x < PropertiesReader.getInstance().getGridSizeX(); x++){
            for(int y = 0; y < PropertiesReader.getInstance().getGridSizeY(); y++){
                positions.add(new Position(x, y));
            }
        }

        for(int i = 0; i < PropertiesReader.getInstance().getPartNumber(); i++){
            Random r = new Random();
            Position tmp = positions.remove(r.nextInt(positions.size()));
            Particule agent = createRandomAgent(tmp);
            getAgentList().add(agent);
        }

        positions.clear();

        getEnv().putAgents(getAgentList());
    }

    private Particule createRandomAgent(Position position){
        Random r = new Random();
        Color color=new Color(r.nextInt(230),r.nextInt(230),r.nextInt(230));

        int pasX = r.nextInt(3)-1;
        int pasY = r.nextInt(3)-1;

        if(pasY == 0 && pasX == 0){
            pasX +=1;
            pasY -=1;
        }

        return new Particule(getEnv(), color, position, pasX, pasY);
    }

    @Override
    public void startTickAction() {

    }

    @Override
    public void endTickAction() {

    }
}
