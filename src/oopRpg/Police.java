package oopRpg;

import java.util.ArrayList;

public class Police extends Enemy {
	public Police(int health, String name, boolean isKillable, ArrayList<Item> inventory, Equip weapon) {
		super(health, name, isKillable, inventory, weapon);
	}
}
