package oopRpg;

import java.util.ArrayList;

abstract class Character {

	protected int health;
	protected String name;
	protected boolean isKillable;
	protected ArrayList<Item> inventory = new ArrayList<>();
	
	// TODO ask if above should be static, private, or public. 

	boolean isDead() {
		if (health == 0)
			return true;
		else
			return false;
	}
	
	String getName() {
		return this.name;
	}
	int getHealth() {
		return this.health;
	}

}
