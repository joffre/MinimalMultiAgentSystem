package hunter;

import core.Agent;
import core.Environment;
import core.MColor;
import core.Position;

/**
 * Created by escobedo on 24/01/17.
 */
public class Winner extends Agent {

    public Winner(Environment env, Position position) {
        super(env, MColor.GOLD, position);
    }

    public void update() {

    }

    public void decide(int currentTick) {

    }

    @Override
    public String getCSVString() {
        return "WINNER;"+getPosition().getPosX()+";"+getPosition().getPosY()+";";
    }
}
