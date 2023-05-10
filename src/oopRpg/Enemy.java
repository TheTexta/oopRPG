package oopRpg;

import java.util.ArrayList;

public class Enemy extends Character implements Killable {

	private Equip weapon;
	private int health;

	public Enemy(int health, String name, boolean isKillable, ArrayList<Item> inventory, Equip weapon) {
		super(name, isKillable, health);
		this.setInventory(inventory);
		this.weapon = weapon;
	}

	String yell(String msg) {
		return (this.getName() + " yells: " + msg);
	}

	public void attack(Character attacked) {
		
		attacked.setHealth(attacked.getHealth() - ((int) (Math.random() * (this.weapon.getDamage() * 0.5))
				+ this.weapon.getDamage()));
		/*
		 * Intentionally the same attack method in the enemy and player class.
		 * I want to have the attacks by any character to work the same way regardless
		 * of class. The only variable that can change relatiing to the attacking system
		 * is the health.
		 */
		}

	public ArrayList<Item> getInventory() {
		// Get original inv without weapon item
		ArrayList<Item> combinedInv = super.getInventory();
		// Add the weapon item
		combinedInv.add(weapon);
		// Return the combined arraylist
		return combinedInv;
	}

	public ArrayList<Item> loot() {
		if (!this.getIsLooted())
			this.addItem(weapon);

		return super.loot();
	}
}
