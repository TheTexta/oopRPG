package oopRpg;

import java.util.ArrayList;

abstract class Character {
	/*
	 * Abstract character class is a base for all characters in the game. Every
	 * character has a name, health, inventory and a isKillable bool to give
	 * immunity to certain npcs
	 */
	private int health; // The health of the character
	private String name; // The name of the character
	private boolean isKillable; // A boolean to show whether or not the character can be killed
	private ArrayList<Item> inventory; // ArrayList to hold the items in the character's inventory
	private boolean isLooted; // A boolean to show if the character's inventory has been looted or not

	// Constructor for creating a new character
	public Character(String name, boolean isKillable) {
		this.health = 0;
		this.name = name;
		this.isKillable = isKillable;
		this.inventory = new ArrayList<>();
		this.isLooted = false;
	}

	// Overloaded constructor to create a new character with a specific health value
	public Character(String name, boolean isKillable, int health) {
		this.health = health;
		this.name = name;
		this.isKillable = isKillable;
		this.inventory = new ArrayList<>();
		this.isLooted = false;
	}

	// Method to check if the character is dead or not
	boolean isDead() {
		if (health == 0)
			return true;
		else
			return false;
	}

	// Getter method for the character's name
	String getName() {
		return this.name;
	}

	// Getter method for the character's health
	int getHealth() {
		return this.health;
	}

	// Getter method for the character's inventory
	ArrayList<Item> getInventory() {
		return this.inventory;
	}

	// Setter method for the character's health
	public void setHealth(int health) {
		this.health = health;
		if (this.health <= 0) {
			this.health = 0;
		}
	}

	// Setter method for the character's name
	public void setName(String name) {
		this.name = name;
	}

	// Getter method for the character's isKillable attribute
	public boolean getIsKillable() {
		return this.isKillable;
	}

	// Setter method for the character's isKillable attribute
	public void setIsKillable(boolean isKillable) {
		this.isKillable = isKillable;
	}

	// Setter method for the character's inventory
	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}

	// Getter method for the character's isLooted attribute
	public boolean getIsLooted() {
		return this.isLooted;
	}

	// Takes an arraylist of items and adds them to the character's inventory
	public void addAllToInventory(ArrayList<Item> inventory) {
		this.inventory.addAll(inventory);
		this.inventory.trimToSize();
	}

	// Takes an item and adds it to the character's inventory
	public void addItem(Item item) {
		this.inventory.add(item);
	}

	// Takes an item and subtracts it from the character's inventory and trims
	// arraylist
	public void subtractItem(Item item) {
		this.inventory.remove(item);
		this.inventory.trimToSize();
	}

	// Method to loot the character's inventory
	public ArrayList<Item> loot() {
		if (!isLooted) {
			isLooted = true;
			return this.inventory;
		} else {
			return (new ArrayList<Item>());
		}
	}

}
