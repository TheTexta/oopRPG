package oopRpg;

import java.util.ArrayList;

abstract class Character {

	private int health;
	private String name;
	private boolean isKillable;
	private ArrayList<Item> inventory;

	public Character(String name, boolean isKillable) {
		this.health = 0;
		this.name = name;
		this.isKillable = isKillable;
		this.inventory = new ArrayList<>();
	}

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

	ArrayList<Item> getInventory() {
		return this.inventory;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsKillable() {
		return this.isKillable;
	}

	public void setIsKillable(boolean isKillable) {
		this.isKillable = isKillable;
	}

	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}

	// Takes an arraylist of items and adds them to inventory
	public void addAllToInventory(ArrayList<Item> inventory) {
		this.inventory.addAll(inventory);
	}

	// Takes an item and adds it to inventory
	public void addItem(Item item) {
		this.inventory.add(item);
	}

	// Takes an item and subtracts it from inventory and trims arraylist
	public void subtractItem(Item item) {
		this.inventory.remove(item);
		this.inventory.trimToSize();
	}

}
