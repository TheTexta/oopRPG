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
	private String enterMsg;

	public Location(String name, ArrayList<Item> inventory, int actions, ArrayList<Character> characters,
			ArrayList<Item> hiddenInventory, String enterMsg) {
		this.name = name;
		this.inventory = inventory;
		this.hiddenInventory = hiddenInventory;
		this.actions = actions;
		this.characters = characters;
		this.enterMsg = enterMsg;
	}

	public Location(String name, ArrayList<Item> inventory, int actions, ArrayList<Character> characters,
			String enterMsg) {
		this.name = name;
		this.inventory = inventory;
		this.actions = actions;
		this.characters = characters;
		this.enterMsg = enterMsg;
	}

	public Location(String name, ArrayList<Item> inventory, int actions, String enterMsg) {
		this.name = name;
		this.inventory = inventory;
		this.actions = actions;
		this.enterMsg = enterMsg;
	}

	public Location(String name, ArrayList<Item> inventory, ArrayList<Item> hiddenInventory, int actions,
			String enterMsg) {
		this.name = name;
		this.inventory = inventory;
		this.actions = actions;
		this.enterMsg = enterMsg;
		this.hiddenInventory = hiddenInventory;
	}

	public Location(String name, int actions, String enterMsg) {
		this.name = name;
		this.actions = actions;
		this.enterMsg = enterMsg;
	}

	/*
	 * The reveal () function takes all the hidden items in the location and adds
	 * them to the public inventory for the player to loot. The method returns a
	 * string to print the the console to indicate to the players what, if any,
	 * items were revealed from searching the location
	 */
	public String reveal() {
		inventory.addAll(hiddenInventory);

		String addedItems = "";
		this.isSearched = true;
		for (int i = 0; i < hiddenInventory.size(); i++) {
			if (i == 0)
				addedItems += hiddenInventory.get(i).getName();
			else
				addedItems += ", " + hiddenInventory.get(i).getName();
		}
		if (this.hiddenInventory.isEmpty()) {
			return "You dont find anything";
		}

		return "You find " + addedItems + "!";
	}

	public boolean getIsLooted() {
		return this.isSearched;
	}

	// A simple getter function returns the name of the location
	public String getName() {
		return this.name;
	}

	// A simple getter method for returning the inventory of a location
	public ArrayList<Item> getInventory() {
		return this.inventory;
	}

	/*
	 * A getter method to return the number of actions the location adds to the
	 * total count. This method is unique in that it sets the locations number of
	 * available actions to zero before returning so that if the method is called
	 * again additional actions are not added which would lead to an infinite amount
	 * of actions.
	 */
	public int getActions() {
		int actions = this.actions;
		this.actions = 0;
		return actions;
	}

	/*
	 * A simple method utilising the printMethods delayPrintLn function to introduce
	 * the location to the player along with the visible items contained within said
	 * location.
	 */
	public void introduceLocation() throws Exception {
		String[] printMsg = PrintMethods.genSepperatedString(this.enterMsg, PrintMethods.getOffset() - 2);
		int printOffset = 3 + printMsg.length;
		String[] toPrint = new String[this.getInventory().size() + printOffset];
		String genSpacer = PrintMethods.genString(PrintMethods.getOffset(), "-");

		for (int i = 0; i < printMsg.length; i++) {
			toPrint[i] = printMsg[i];
		}

		toPrint[printOffset - 3] = genSpacer;

		toPrint[printOffset - 2] = "Location: " + this.getName();

		toPrint[printOffset - 1] = genSpacer;

		for (int i = 0; i < this.getInventory().size(); i++) {
			toPrint[i + printOffset] = this.getInventory().get(i).getName();
		}
		if (this.getInventory().size() == 0) {
			toPrint[printOffset - 1] = "There are no items in " + this.getName();
		}

		PrintMethods.printArray(toPrint);

	}

	// A simple setter method to add characters to a location
	public void addCharacter(Character c) {
		this.characters.add(c);
	}

	/*
	 * This method prints all the actions a player might take within the location
	 * based upon whether or not the location has attackable characters or is
	 * searched.
	 */
	public void listNextAction(boolean inBetweenLoc) throws Exception {
		String toPrint[][] = { { "------", "------", "3. Inventory", "4. Loot", "------" } };

		if (getAttackables(true).size() > 0) {
			toPrint[0][0] = "1. Fight";
		}
		if (!this.isSearched) {
			toPrint[0][4] = "5. Search";
		}
		if (!inBetweenLoc) {
			toPrint[0][1] = "2. Move";
		}
		PrintMethods.print2dArray(toPrint, 16);

		// get all possible actions
		// list them
	}

	/*
	 * Method returns all characters which the character can kill through an
	 * ArrayList.
	 */

	public ArrayList<Enemy> getAttackables(boolean alive) {
		ArrayList<Enemy> attackers = new ArrayList<>();

		for (int i = 0; i < this.characters.size(); i++) {
			// If the character is not dead and alive is requested
			if (((!this.characters.get(i).isDead() && alive) || (this.characters.get(i).isDead() && !alive))
					&& this.characters.get(i).getClass().getSuperclass().equals(Enemy.class))
			// Goes through all characters in location and adds those who arent dead and can
			// be attacked
			{
				attackers.add((Enemy) this.characters.get(i));

			}

		}
		return attackers;
	}

	// Method lists all attackable enemies with an itterating number concatonated
	// with each name.
	public void listAttackables() throws Exception {
		ArrayList<Enemy> attackers = getAttackables(true);

		String[][] toPrint = new String[getAttackables(true).size()][1];

		for (int i = 0; i < attackers.size(); i++) {
			toPrint[i][0] = (i + 1) + ". " + attackers.get(i).getName() + " - " + attackers.get(i).getHealth() + "hp";
		}

		PrintMethods.print2dArray(toPrint, PrintMethods.getOffset());

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
