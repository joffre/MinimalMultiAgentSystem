package hunter;

import core.Agent;
import core.Environment;
import core.Position;

import java.awt.*;

/**
 * Created by escobedo on 24/01/17.
 */
public class Defender extends Agent {

    int life;

    public Defender(Environment env, Color color, Position position, int life) {
        super(env, color, position);
        this.life = life;
    }

    public void update() {
        if(!isAlive()){
            env.removeAgent(this);
        }
        life--;
    }

    public void decide(int currentTick) {
    }

    @Override
    public String getCSVString() {
        return "DEFENDER;"+getPosition().getPosX()+";"+getPosition().getPosY()+";"+(isAlive()?"ALIVE":"DEAD")+";";
    }

    public boolean isAlive(){
        return life > 0;
    }
}
