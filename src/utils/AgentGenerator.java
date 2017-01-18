package utils;

import java.awt.Color;
import java.util.Random;

import particules.Particule;

public class AgentGenerator {
	
	public static Particule createRandomAgent(int x, int y){
		Random r = new Random();
		Color color=new Color(r.nextInt(230),r.nextInt(230),r.nextInt(230));
		
		int posX = x;
		int posY = y;
		
		int pasX = r.nextInt(3)-1;
		int pasY = r.nextInt(3)-1;
		
		if(pasY == 0 && pasX == 0){
			pasX +=1;
			pasY -=1;
		}
		
		return new Particule(color, posX, posY, pasX, pasY);
	}

}
