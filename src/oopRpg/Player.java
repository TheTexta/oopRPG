package oopRpg;

import java.util.ArrayList;

public class Player extends Character implements Killable {
	// An integer representing the player's wanted level
	private int wantedLvl;
	// An object representing the player's equipped weapon
	private Equip weapon;
	// An object representing the player's armor equipped
	private Equip armor;

	// Constructor method for the Player class
	public Player(String name, Equip weapon) {
		// Call the constructor of the parent class to set the player's name and set them as alive
		super(name, true);
		// Set the player's health to 100
		this.setHealth(100);
		// Set the player's wanted level to 0
		this.wantedLvl = 0;
		// Set the player's equipped weapon
		this.weapon = weapon;
	}
	
	// Method for the player to loot an enemy's inventory after they are killed
	public void loot(Enemy looted) {
		if (looted.isDead()) {
			// Add all items in the enemy's inventory to the player's inventory
			this.addAllToInventory(looted.getInventory());
		} else {
			// Throw an exception if the player tries to loot a living enemy
			throw new java.lang.RuntimeException("Player tried to loot an alive enemy!");
		}
	}
	
	// Method for the player to change their equipped weapon
	public void setWeapon(Equip weapon) {
		if (weapon == null) {
            throw new IllegalArgumentException("Armor cannot be null!");
        }
		// Add the player's current weapon to their inventory
		this.addItem(this.weapon);
		// Remove the new weapon from the player's inventory
		this.subtractItem(weapon);
		// Set the new weapon as the player's equipped weapon
		this.weapon = weapon;
	}

	// Method for the player to set there armor type
	public void setArmor(Equip armor){
		if (armor == null) {
            throw new IllegalArgumentException("Armor cannot be null!");
        }
		if (this.armor!=null)
			this.addItem(this.armor);
		this.subtractItem(armor);
		this.armor=armor;
	}
	
	// Method for the player to attack another character
	public void attack(Character attacked) {
		// Reduce the attacked character's health by a random value between 0 and the player's weapon's damage
		attacked.setHealth(attacked.getHealth() - ((int) (Math.random() * (this.weapon.getDamage() * 0.5))
				+ this.weapon.getDamage()));
		/*
		 * Intentionally the same attack method in the enemy and player class.
		 * I want to have the attacks by any character to work the same way regardless
		 * of class. The only variable that can change relating to the attacking system
		 * is the health. 
		 */
	}
	
	// Method to list the items in the player's inventory and equipped weapon
	public ArrayList<Item> listAndGetItems() {
		// Create a new ArrayList to hold the items
		ArrayList<Item> items = new ArrayList<>();
	
		// Print the header for the inventory
		System.out.println("Inventory:");
	
		// If the player's inventory is not empty
		if (!this.getInventory().isEmpty()) {
			// Loop through each item in the inventory
			for (int i = 0; i < this.getInventory().size(); i++) {
				// Print the name of the item with a number indicating its position in the list
				System.out.println((items.size() + 1) + ". " + this.getInventory().get(i).getName());
				// Add the item to the ArrayList
				items.add(this.getInventory().get(i));
			}
		}
	
		// Print the name of the player's equipped weapon
		System.out.println("\nWeapon: " + this.weapon.getName());
		// Print the name of the player's equipped armor.  Checks to make sure the armor equipped isnt null first. Does not print anything if it is
		if (this.armor!=null)
			System.out.println("\nArmor: "+ this.armor.getName());
		// Return the ArrayList of items
		return items;
	}
	
	// Method to return the player's current action multiplier
	public int actionMultiplier() {
		return this.wantedLvl;
		// TODO implement a system where wanted level increases
		// after killing a cop or citizen
		// If wanted lvl too high you get recognized at the border
	}
	
	// Getter method for the player's equipped weapon
	public Equip getWeapon() {
		return this.weapon;
	}
}