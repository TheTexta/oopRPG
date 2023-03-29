package oopRpg;

import java.util.ArrayList;

public class Location {
	private String name; // Name of the location
	private ArrayList<Item> inventory = new ArrayList<>();
	// Inventory ArrayList to hold all the items visible to the player in the
	// location
	private ArrayList<Item> hiddenInventory = new ArrayList<>();
	/*
	 * Hidden inventory ArrayList to hold all items un-revealed to the player. This
	 * is just there to be added to the Inventory ArrayList when the player chooses
	 * to reveal hidden items
	 */
	private int actions; // The number of actions the player gets added to there total available actions
							// when they enter this location
	private boolean isSearched = false; // a boolean to show whether the location has been searched or not. locations
										// can only be searched once
	private ArrayList<Character> characters = new ArrayList<>(); // An ArrayList to hold all the characters a player
																	// could interact with in the location

	public Location(String name, ArrayList<Item> inventory, int actions, ArrayList<Character> characters,
			ArrayList<Item> hiddenInventory) {
		this.name = name;
		this.inventory = inventory;
		this.hiddenInventory = hiddenInventory;
		this.actions = actions;
		this.characters = characters;
	}

	public Location(String name, ArrayList<Item> inventory, int actions, ArrayList<Character> characters) {
		this.name = name;
		this.inventory = inventory;
		this.actions = actions;
		this.characters = characters;
	}

	public Location(String name, ArrayList<Item> inventory, int actions) {
		this.name = name;
		this.inventory = inventory;
		this.actions = actions;
	}

	public Location(String name, int actions) {
		this.name = name;
		this.actions = actions;
	}

	/*
	 * The reveal () function takes all the hidden items in the location and adds
	 * them to the public inventory for the player to loot. The method returns a
	 * string to print the the console to indicate to the players what, if any,
	 * items were revealed from searching the location
	 */
	String reveal() {
		inventory.addAll(hiddenInventory);

		String addedItems = "";
		this.isSearched = true;
		for (int i = 0; i < hiddenInventory.size(); i++) {
			if (i == 0)
				addedItems += hiddenInventory.get(i);
			else
				addedItems += ", " + hiddenInventory.get(i);
		}
		if (this.hiddenInventory.isEmpty()) {

			return "You dont find anything";
		}

		return "You find " + addedItems + "!";
	}

	// A simple getter function returns the name of the location
	String getName() {
		return this.name;
	}

	/*
	 * ListItems() is a method for generating a string to display to the console of
	 * all potentially lootable items a location contains. The method will iterate
	 * through every item in the Inventory Arraylist and will generate an
	 * accompanying msg along with all the items said location contains.
	 */
	String listItems() {
		String itemList = "You spot some items lying around in the " + this.name + ": \n";
		if (this.inventory.size() > 1) {
			for (Item i : this.inventory) {

				itemList += " - " + i.getName() + "\n";

			}
			return itemList;
		} else if (this.inventory.size() == 1) {
			return "As look look around the " + this.name + ", you spot a " + this.inventory.get(1).getName();
		} else {
			return this.name + " hasnt got any items";
		}
	}

// A simple getter method for returning the inventory of a location
	ArrayList<Item> getInventory() {
		return this.inventory;
	}

	/*
	 * A getter method to return the number of actions the location adds to the
	 * total count. This method is unique in that it sets the locations number of
	 * available actions to zero before returning so that if the method is called
	 * again additional actions are not added which would lead to an infinite amount
	 * of actions.
	 */
	int getActions() {
		int actions = this.actions;
		this.actions = 0;
		return actions;
	}

	/*
	 * A simple method utilising the printMethods delayPrintLn function to introduce
	 * the location to the player along with the visible items contained within said
	 * location.
	 */
	void introduceLocation() throws InterruptedException {
		PrintMethods.delayPrintln("You entered: " + this.getName());
		System.out.println(this.listItems());

	}

	// A simple setter method to add characters to a location
	void addCharacter(Character c) {
		this.characters.add(c);
	}

	/*
	 * This method prints all the actions a player might take within the location
	 * based upon whether or not the location has attackable characters or is
	 * searched.
	 */
	public void listNextAction() throws InterruptedException {
		String toPrint[][] = { { "------", "2. Move", "3. Inventory", "4. Grab", "------" } };

		if (hasAttackables()) {
			toPrint[0][0] = "1. Fight";
		}
		if (!this.isSearched) {
			toPrint[0][4] = "5. Search";
		}
		PrintMethods.printArray(toPrint, 16);

		// get all possible actions
		// list them
	}

	/*
	 * Method to determine if the location has characters which the player can
	 * attack. Since all attackable characters share the same superclass of Enemy,
	 * the method simply iterates through each character and checks whether or not
	 * the superclass of the character is of the enemy type. Additionally, the
	 * method tests if the character is dead or not as a dead character cannot be
	 * further attacked.
	 */
	public boolean hasAttackables() {
		if (this.characters.size() > 0) {
			for (int i = 0; i < this.characters.size(); i++) {
				if (this.characters.get(i).getClass().getSuperclass().equals(Enemy.class) && !this.characters.get(i).isDead()) // TODO review this
					return true;
			}
			return false;
		} else {
			return false;
		}
	}
	/*
	 * Method returns all characters which the character can kill through an
	 * ArrayList. Similar to the hasAttackables method
	 */
	// TODO simplify the getAttackables and hasAttackables methods and
	// listAttackables.

	public ArrayList<Enemy> getAttackables(boolean alive) {
		ArrayList<Enemy> attackers = new ArrayList<>();

		for (int i = 0; i < this.characters.size(); i++) {
			// If the character is not dead and alive is requested
			if (((!this.characters.get(i).isDead()&&alive)||(this.characters.get(i).isDead()&&!alive))
					&& this.characters.get(i).getClass().getSuperclass().equals(Enemy.class))
			// Goes through all characters in location and adds those who arent dead and can
			// be attacked
			{
				attackers.add((Enemy) this.characters.get(i));

			}

		}
		return attackers;
	}

	public void listAttackables() throws InterruptedException {
		ArrayList<Enemy> attackers = getAttackables(true);

		String[][] toPrint = new String[getAttackables(true).size()][1];

		for (int i = 0; i < attackers.size(); i++) {
			toPrint[i][0] = (i + 1) + ". " + attackers.get(i).getName() + " - " + attackers.get(i).health + "hp";
		}

		// TODO ask if 84 needs to be a variable
		PrintMethods.printArray(toPrint, 84);

	}

	/*
	 * Returns the number of attackable characters in the location.
	 */
	public int getNumOfAttackers() {
		int numOfAttackers = 0;
		for (int i = 0; i < this.characters.size(); i++) {

			if (!this.characters.get(i).isDead()
					&& this.characters.get(i).getClass().getSuperclass().equals(Enemy.class)) {
				numOfAttackers++;
			}

		}
		return numOfAttackers;
	}

	/*
	 * This method removes an item from the location inventory and trims the
	 * subsequent inventory to size to allow for proper calculations when using the
	 * size() ArrayList method.
	 */

	public void removeItem(int i) {
		this.inventory.remove(i);
		this.inventory.trimToSize();
	}

}
