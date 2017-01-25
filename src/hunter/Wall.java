package hunter;

import core.Agent;
import core.Environment;
import core.Position;

import java.awt.*;

/**
 * Created by Geoffrey on 20/01/2017.
 */
public class Wall extends Agent{

    public Wall(Environment env, Color color, Position position) {
        super(env, color, position);
    }

    @Override
    public void update() {

    }

    @Override
    public void decide(int currentTick) {

    }

    @Override
    public String getCSVString() {
        return "WALL;"+getPosition().getPosX()+";"+getPosition().getPosY()+";";
    }
}
