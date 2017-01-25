package wator;

import core.*;
import output.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Geoffrey on 18/01/2017.
 */
public class WatorSMA extends SMA {

    int sharkNumber = 0;
    int fishNumber = 0;

    @Override
    public void initAgents() {

        List<Position> positions = new ArrayList<Position>();

        for(int x = 0; x < PropertiesReader.getInstance().getGridSizeX(); x++){
            for(int y = 0; y < PropertiesReader.getInstance().getGridSizeY(); y++){
                positions.add(new Position(x, y));
            }
        }

        for(int f = 0; f < PropertiesReader.getInstance().getFishNumber(); f++){
            Random r = new Random();
            Position position = positions.remove(r.nextInt(positions.size()));
            Fish fish = new Fish(getEnv(), position);
            getAgentList().add(fish);
        }

        for(int s = 0; s < PropertiesReader.getInstance().getSharkNumber(); s++){
            Random r = new Random();
            Position position = positions.remove(r.nextInt(positions.size()));
            Shark shark = new Shark(getEnv(), position);
            getAgentList().add(shark);
        }

        positions.clear();

        getEnv().putAgents(getAgentList());
    }

    @Override
    public void startTickAction() {

    }

    @Override
    protected boolean isPaused() {
        return false;
    }

    @Override
    public void endTickAction() {
        sharkNumber = 0;
        fishNumber = 0;
        for(Agent agent : getAgentList()){
            MarineAnimal mAnim = (MarineAnimal) agent;
            if(mAnim instanceof Fish){
                fishNumber++;
            } else if(mAnim instanceof Shark){
                sharkNumber++;
            }
        }

        Log.info("TICK;FISH;"+sharkNumber+";FISH;"+fishNumber+";");
    }

    public int getSharkNumber(){
        return sharkNumber;
    }

    public int getFishNumber() {
        return fishNumber;
    }
}
