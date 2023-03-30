package oopRpg;

import java.util.ArrayList;

public class Criminal extends Enemy {

	public Criminal(int health, String name, boolean isKillable, ArrayList<Item> inventory, Equip weapon) {
		super(health, name, isKillable, inventory, weapon);
	}
}
