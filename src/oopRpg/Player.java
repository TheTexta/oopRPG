package oopRpg;

import java.util.ArrayList;

public class Player extends Character implements Killable {
	// An integer representing the player's wanted level
	private int wantedLvl;
	// An object representing the player's equipped weapon
	private Equip weapon;
	// An object representing the player's armor equipped
	private Equip armor;
	// An integer to store the difficulty selected. The value stored here is used as
	// a delay when calling the printmethods damageBar loop.
	private int difficulty;
	// Int representing the max health a player can have
	private int maxHealth;

	// Constructor method for the Player class
	public Player(String name, Equip weapon, int difficulty) {
		// Call the constructor of the parent class to set the player's name and set
		// them as alive
		super(name, true,100);
		this.maxHealth = 100;
		// Set the player's wanted level to 0
		this.wantedLvl = 1;
		// Set the player's equipped weapon
		this.weapon = weapon;
		// Set the player's difficulty
		this.difficulty = difficulty;

		
	}

	// Method to set the players health
	public void setHealth(int health){
		super.setHealth(health);
		if (this.getHealth()>maxHealth){
			super.setHealth(maxHealth);
		}
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
	public void setArmor(Equip armor) {
		if (armor == null) {
			throw new IllegalArgumentException("Armor cannot be null!");
		}
		if (this.armor != null)
			this.addItem(this.armor);
		this.subtractItem(armor);
		this.armor = armor;
	}

	// Method for the player to attack another character
	public void attack(Character attacked) throws Exception {
		// Get the attack multi by calling the damageBar method.
		double attackMulti = PrintMethods.damageBar(difficulty);
		int attackDamage = ((int) ((((int) (Math.random() * (this.weapon.getDamage() * 0.5))
				+ this.weapon.getDamage())) * attackMulti));
		// Reduce the attacked character's health by a random value between 0 and the
		// player's weapon's damage
		attacked.setHealth(attacked.getHealth() - attackDamage);

		if (attacked.isDead()) {
			PrintMethods.printWrapped("You use the " + this.weapon.getName() + " and kill " + attacked.getName());

		} else {
			PrintMethods.printWrapped("You use the " + this.weapon.getName() + " to attack " + attacked.getName() + ": "
					+ attackDamage + " DMG");
		}

	}

	// Method to list the items in the player's inventory and equipped weapon
	public ArrayList<Item> listAndGetItems() throws Exception {
		// Create a new ArrayList to hold the items
		ArrayList<Item> items = new ArrayList<>();

		int listInvSize = this.getInventory().size() + 5;
		// String to hold listInv
		String[] listInv = new String[listInvSize];

		// Print the header for the inventory
		listInv[0] = "Inventory:";
		listInv[1] = PrintMethods.genString(PrintMethods.getOffset(), "-");

		// If the player's inventory is not empty
		if (!this.getInventory().isEmpty()) {
			// Loop through each item in the inventory
			for (int i = 0; i < this.getInventory().size(); i++) {
				// Print the name of the item with a number indicating its position in the list
				listInv[i + 2] = (items.size() + 1) + ". " + this.getInventory().get(i).getName();
				// Add the item to the ArrayList
				items.add(this.getInventory().get(i));
			}
		}

		// Add a divider
		listInv[listInvSize - 3] = PrintMethods.genString(PrintMethods.getOffset(), "-");

		// Print the name of the player's equipped weapon
		listInv[listInvSize - 2] = "Weapon: " + this.weapon.getName();
		// Print the name of the player's equipped armor. Checks to make sure the armor
		// equipped isnt null first.
		listInv[listInvSize - 1] = "Armor: ------";
		if (this.armor != null)
			listInv[listInvSize - 1] = "Armor: " + this.armor.getName();

		PrintMethods.printArray(listInv);
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

	// Getter method for players wanted rating
	public int getWanted(){
		return this.wantedLvl;
	}
}