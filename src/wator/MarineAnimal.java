package wator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import core.Agent;
import core.Environment;
import core.Position;
import core.PropertiesReader;

public abstract class MarineAnimal extends Agent {
	int currentBreedTime; //0
	boolean baby;
	protected boolean alive;
	int age;
	
	protected MarineAnimal(Environment environment,Color color, Position position, boolean baby) {
		super(environment, color, position);
		this.baby = baby;
		this.currentBreedTime = 0;
		this.alive = true;
		this.age = 0;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public void kill(){
		this.alive = false;
	}
	
	public String toString(){
		return alive+"";
	}
}
