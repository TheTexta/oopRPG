package oopRpg;

import java.util.*;

public class Main {
	static int actions;

	/*
	 * every location has a set number of actions that it adds to your total actions
	 * actions are the number of turns you have before the police find you. similar
	 * to FTL (a steam game)
	 * 
	 * Every location has a custom welcome message that relates to the items in the
	 * inv of the location when you select Grab you get to choose from a list of
	 * items in the room
	 * 
	 */

	static boolean gameOver = false;
	/*
	 * Boolean to hold whether the game has ended due to completion / death. Its
	 * important that this is a global var as methods outside of main use this to
	 * restart the game if the player chooses to do so.
	 */

	public static void main(String[] args) throws Exception {
		while (true) {

			// Scanner object to read inputs
			Scanner in = new Scanner(System.in);

			// Weapons
			Equip shank = new Equip("Shank", 25, true, "A rusty old knife - 25ATK");
			Equip g19 = new Equip("Glock 19", 50, true, "A shiny new handgun - 50ATK");
			Equip mp5 = new Equip("MP5", 100, true, "A lightweight high power weapon - 100ATK");
			Equip ak = new Equip("AK-47", 200, true, "Unstoppable force of power - 200ATK");

			// Food
			Destructible apple = new Food("Apple", 15,4, "a juicy apple" );

			// Armour
			Equip kevlar = new Equip("Kevlar", 25, false, "A synthetic kevlar vest providing moderate protection");
			Equip tie = new Equip("'Gating master' - Special Armored Tie", 100, false, "'God was scared of gatings'");

			// Items
			Item tape = new Item("Tape");

			// Enemy kits - does not include the enemies weapon as that is in a sepperate
			// slot
			ArrayList<Item> swatKit = new ArrayList<>() {
				{
					add(kevlar);
				}
			};

			ArrayList<Item> gangKit = new ArrayList<>() {
				{
					add(shank);
				}
			};

			System.out.print("Enter your characters name (Enter for default name: Alex): ");

			String name = in.nextLine();
			if (name.isEmpty())
				name = "Alex";

			System.out.print("Select difficulty (1-Easy, 2-Challenge, 3-Demon): ");
			// Difficulty is just the delay between each itteration of the printMethods
			// damageBar animation/mini game
			int difficulty = validChoice(1, 3);
			if (difficulty == 1) {
				difficulty = 10;
			} else if (difficulty == 2) {
				difficulty = 6;
			} else {
				difficulty = 3;
			}

			// Player declaration takes name from the user input.
			Player player = new Player(name, shank, difficulty);

			// Names - possible names of officers
			String[] names = { "A. Trinidad", "K. Hansen", "J. Briggs", "G. Serna", "S. Bowling", "K. Guest",
					"V. Montero",
					"D. Millan", "L. Akins", "D. McClintock", "N. Mahoney", "T. Lindsay", "K. Begay", "S. Tinsley",
					"D. Cornwell", "J. Fierro", "T. Newell", "X. Bearden", "M. Moffett", "E. Hooks" };

			// Enemies
			Police swat1 = new Police(200, "Special Forces Agent:" + names[((int) Math.random() * 19) + 1], true,
					swatKit,
					mp5);
			Police swat2 = new Police(200, "Special Forces Agent:" + names[((int) Math.random() * 19) + 1], true,
					swatKit,
					mp5);
			
			Criminal op = new Criminal(100, "Enemy Gang Member: " + names[((int) Math.random() * 19) + 1], true, gangKit,
					g19);

			Criminal outpostBoss = new Criminal(200, "Kristopher Churchill", true, new ArrayList<>() {
				{
					add(tie);
				}
			}, ak);

			// Locations
			Location home = new Location("Home", new ArrayList<>() {
				{
					add(g19);
					add(tape);

				}
			}, 5,
					"You walk downstairs and grab your keys as you head to your car");
			

			Location car = new Location("Car",new ArrayList<>() {
				{
					add(apple);

				}
			},new ArrayList<>() {
				{
					add(mp5);

				}
			}, 3, "You get into your car");
			Location outpost = new Location("Outpost", 1,
					"Finally arriving at the runned down out of business 711,\n you call the contact your lawyer gave you. \n'Hello?'\n'I want out'\n'I need you to kill some pigs for me first'\n ");

			// outpost subLocations
			Location outpostDeclineChal = new Location("Outpost", 5, "You prepare to fight the boss");

			outpostDeclineChal.addCharacter(outpostBoss);
			Location outpostAcceptChal = new Location("Alleyway", 5,
					"You accept the bosses challange. He walks you outside and takes you to: Grangers Alley. 'You kill the pigs down there you get your id.' He walks away leaving you alone with the enemy");
			outpostAcceptChal.addCharacter(op);

			Location border = new Location("Border", 5,
					"The blazing artificial lights shine you down as you approach the border");

			// Placeholder location for endgame
			Location endLocation = new Location("End", 0, "Game Over");

			/*
			 * Location array stores all the locations a player will go through in the order
			 * that they will encounter them. this makes it easy to move from location to
			 * location as the player progressses.
			 */
			Location[] locationArray = { home, car, outpost, border, endLocation };

			int position = 0;
			/*
			 * Linear position of the player. used to determine the current location the
			 * player is in through the use of the locationArray
			 */

			
			// Boolean stores the state of the player movement. if set to true player
			// cannot progress to next location through increasing position var.
			boolean movementLocked = false;

			PrintMethods.printLoading();
			PrintMethods.delayPrint(
					"You jolt awake to the sound of your phone buzzing on the nightstand,\nand as you groggily answer it, your worst fears are confirmed: \nthe police are on their way, and you've got to get out of the country, fast.\n");
			PrintMethods.printLoading();

			PrintMethods.delayPrint("\nWould you like to see the tutorial? (y or n): ");

			if (validChoice("y", "n").equals("y")) {
				PrintMethods.printLoading();
				PrintMethods.delayPrint(
						"You have to flee the country!\nEvery action the police get closer to you\nIf you run out of actions they will find you and try to kill you\n\nEvery time you change locations you throw them off\nGaining you valuable actions to use in as you run\n\nUse fight to attack enemies at your location\nUse Move to go to the next location\nOpen your inventory to use and equip items you have picked up\nLoot a location to loot items you found at the location and any dead bodies\nYou can Search a location for hidden items which you can later pickup by Looting them");
			}
			System.out.println();

			// continue playing the game until the player wins or loses
			while (!gameOver) {
				// Initialize variables for the player's choice and whether they have moved
				int choice = -1;
				boolean move = false;

				// Adds up the actions from the current location to the player's total actions
				actions += locationArray[position].getActions();

				// This inner while loop runs as long as the player hasn't moved and is not dead
				while (!move && !player.isDead()) {
					// Print loading animation to the console
					PrintMethods.printLoading();

					/*
					 * 2D string array holds the args to print to console via the PrintMethods such
					 * as player stats and attack dmg
					 */
					String[][] toPrint = { { "HEALTH", "WEAPON", "ATKDMG", "ACTIONS", "ENEMIES" },
							{ Integer.toString(player.getHealth()), player.getWeapon().getName(),
									Integer.toString(player.getWeapon().getDamage()), Integer.toString(actions),
									Integer.toString(locationArray[position].getAttackables(true).size()) } };
					PrintMethods.print2dArray(toPrint, 16);

					// Prints the introduction to the location
					locationArray[position].introduceLocation();
					// If movement is locked and there are attackers in the current location, then
					// unlock the movement
					if (movementLocked) {
						if (locationArray[position].getAttackables(true).size() > 0) {
							movementLocked = true;
						} else {
							movementLocked = false;
						}
					}
					// Lists the next action that the player can take based on their current
					// location
					locationArray[position].listNextAction(movementLocked);

					// Boolean to hold whether the player's choice is valid or not
					boolean validChoice = false;

					// This inner while loop runs as long as the player's choice is not valid
					while (!validChoice) {
						System.out.print("Your Choice: ");
						choice = in.nextInt();
						if ((choice == 2 && !movementLocked) || choice == 3 || choice == 4
								|| (choice == 5 && !locationArray[position].getIsLooted())
								|| (choice == 1 && (locationArray[position].getAttackables(true).size() > 0))) {
							validChoice = true;
						} else {
							PrintMethods.invalidChoice("any number from options above");
						}
					}

					System.out.println();

					// If the player's actions are less than or equal to 0, add actions and police
					// to the current location
					if (actions <= 0) {

						actions = 5;

						locationArray[position].addCharacter(swat1);
						locationArray[position].addCharacter(swat2);

						player.setWanted(player.getWanted()+1);
					}

					/*
					 * Check the player's choice and take the corresponding action:
					 * 
					 * 1. Fight 2. Move 3. Inventory 4. Grab 5. Search
					 */

					if (choice == 1) {
						// Lists all the attackers in the current location that the player can fight
						locationArray[position].listAttackables();
						choice = validChoice(1, locationArray[position].getAttackables(true).size());

						// Attacks the selected attacker and subtracts an action from the player's total
						// actions
						player.attack(locationArray[position].getAttackables(true).get(choice - 1));
						actions = actions - (1 * player.actionMultiplier());

					}
					if (choice == 2) {
						// If the player chooses to move, update their position and actions
						if (locationArray[position].getAttackables(true).size() > 0) {
							PrintMethods.printLoading();
							int healthDif = player.getHealth();
							locationArray[position].getAttackables(true).get(0).attack(player);
							locationArray[position].getAttackables(true).get(0).attack(player);
							healthDif -= player.getHealth();
							PrintMethods.printWrapped(
									"As you run away " + locationArray[position].getAttackables(true).get(0).getName()
											+ " knocks you over!");
							PrintMethods.printWrapped("You take " + healthDif + " DMG");
						}
						move = !move;
						position++;
						actions = actions - (1 * player.actionMultiplier());

						if (locationArray[position] == endLocation) {
							gameOver = true;
						} else {
							// Check if the new location has any special progression requirements
							if (locationArray[position].equals(outpost)) {
								// Lock player movement so they can't move away from the fight
								movementLocked = true;
								// Print banner and present the player with a challenge
								PrintMethods.print2dArray(toPrint, 16);
								PrintMethods.printLoading();
								PrintMethods.delayPrint(
										"Walking into the classic crackhouse escape, you spot the man himself. \nKristopher Churchill stands before you. You turn around, no fake ID is worth \ntrying to reason with this man. The second you turn around the door slams shut. \n'Where do you think your going?' Churchill growls, 'Theres only 1 way your getting out of here alive.'\n'You either agree to my challange or you get yourself more into more trouble then even right now'\n\nDo you accept the challange (y or n): ");
								if (validChoice("y", "n").equals("y")) {
									locationArray[position] = outpostAcceptChal;
								} else {
									locationArray[position] = outpostDeclineChal;
								}
								;

							}
						}

					}
					// If player chooses option 3, they enter the inventory screen loop
					if (choice == 3) {
						boolean finishInv = false;
						while (!finishInv) {
							// Get the player's inventory and list all the items
							ArrayList<Item> inventory = player.listAndGetItems();
							System.out.println("\n0. Back");
							// Let the player choose which item they want to interact with
							choice = validChoice(0, inventory.size());
							if (0 < choice) {
								String equipUse;
								// If the chosen item is an equipable item, set the equipUse variable to "Equip"
								// Otherwise, set it to "Use"
								if (inventory.get(choice - 1).getClass().equals(Equip.class))
									equipUse = "Equip";
								else
									equipUse = "Use";
								// Print the item name and the options to equip or use the item, or go back
								PrintMethods.printArray(new String[] { inventory.get(choice - 1).getName(),
										inventory.get(choice - 1).getDescription(), "1. " + equipUse });
								System.out.println("0. Back");

								// Get the player's choice whether to equip an item or go back
								int equipOrBack = validChoice(0, 1);
								if (equipOrBack == 1 && equipUse == "Equip") {
									// If the player chooses to equip an item, check the item type and set it
									// accordingly
									if ((((Equip) inventory.get(choice - 1)).getType()))
										player.setWeapon((Equip) inventory.get(choice - 1));
									else
										player.setArmor((Equip) inventory.get(choice - 1));
								} else if (equipOrBack == 1 && equipUse == "Use") {
									// If the player chooses to use an item, call the use() method of the item
									inventory.get(choice - 1).use(player);
								}

							} else if (choice == 0) {
								// If the player chooses to go back, exit the inventory screen loop
								finishInv = true;
							}
						}
					}

					// If player chooses to loot the location
					if (choice == 4) {

						// Bool to hold if the player has finished looting the area
						boolean finishLoot = false;

						String[] arrayPrint = new String[locationArray[position].getAttackables(false).size() + 2];
						arrayPrint[0] = "Loot which of the following:";
						arrayPrint[1] = "1. " + locationArray[position].getName();

						/*
						 * The following for loop iterates through every possible lootable inv in the
						 * location and add the list to the arrayPrint
						 */
						for (int i = 0; i < locationArray[position].getAttackables(false).size(); i++) {
							arrayPrint[(i + 2)] = (i + 2) + ". "
									+ locationArray[position].getAttackables(false).get(i).getName();
						}

						// while the player hasn't finished looting
						while (!finishLoot) {
							// Display all lootable items/characters in the location
							PrintMethods.printArray(arrayPrint);
							PrintMethods.delayPrint("\n0. Back\n");

							// validate the user input
							choice = validChoice(0, locationArray[position].getAttackables(false).size() + 1);

							// Choice number 1 will always be the current location
							if (choice == 1) {

								PrintMethods.printWrapped("What would you like to pickup in the "
										+ locationArray[position].getName() + ":");

								// Show all the lootable items in the location
								ArrayList<Item> lootableInv = locationArray[position].getInventory();
								if (locationArray[position].getInventory().size()!=0){
									PrintMethods.printArrayList(lootableInv, true);
								}
								
								PrintMethods.delayPrint("\n0. Back\n");

								// validate the user input
								choice = validChoice(0, lootableInv.size());

								// If player chose an item to pick up
								if (0 < choice) {

									// Add the item to the player's inventory and remove it from the location's
									// inventory
									player.addItem(lootableInv.get(choice - 1));
									locationArray[position].removeItem(choice - 1);
									actions = actions - (1 * player.actionMultiplier());

								}
							} else if (choice != 0) { // If player chose to loot a character
														// Display the items found in the character's pockets and add
														// them to the player's inventory
								PrintMethods.printWrapped("You rumage around "
										+ locationArray[position].getAttackables(false).get(choice - 2).getName()
										+ "s pockets and find:");

								ArrayList<Item> itemsToAdd = locationArray[position].getAttackables(false)
										.get(choice - 2).loot();

								player.addAllToInventory(itemsToAdd);

								// If there were no items to be looted, add a message
								if (itemsToAdd.size() == 0)
									itemsToAdd.add(new Item("!!! Character already looted !!!"));
								PrintMethods.printArrayList(itemsToAdd, false);
								finishLoot = true;

							}

							if (choice == 0) { // If player chose to go back
								finishLoot = true;
							}

						}
					}
					// Check if the player chose to search the current location
					if (choice == 5) {
						// Print a message indicating that the player is searching the location
						PrintMethods.printWrapped("You rumage around the " + locationArray[position].getName());
						PrintMethods.printLoading();
						PrintMethods.printWrapped(locationArray[position].reveal());
						// Deduct an action point from the player's remaining actions
						actions = actions - (1 * player.actionMultiplier());
					}

					// wait for a number
					// do action correlating to number
					// -1 from actions if player chooses to loot/move/pickup

					/*
					 * Iterates through all attackers on scene and has them do damage accordingly.
					 * Only does damage if the player hasnt moved that turn. (cant take damage while
					 * moving)
					 * Also constructs an array of concatonated strings of names of the characters
					 * attacking the player. Prints using the printarray method
					 */

					if (!move) {
						if (locationArray[position].getAttackables(true).size() > 0) {
							PrintMethods.printLoading();
							String[] charsAttacking = new String[locationArray[position].getAttackables(true).size()
									+ 2];
							int damageTaken = player.getHealth();
							for (int i = 0; i < locationArray[position].getAttackables(true).size(); i++) {
								charsAttacking[i] = locationArray[position].getAttackables(true).get(i).getName()
										+ " attacks you!";
								locationArray[position].getAttackables(true).get(i).attack(player);
							}
							damageTaken -= player.getHealth();
							charsAttacking[charsAttacking.length - 2] = PrintMethods.genString(PrintMethods.getOffset(),
									"-");
							charsAttacking[charsAttacking.length - 1] = "You take " + damageTaken + " dmg";
							PrintMethods.printArray(charsAttacking);
						}
						
					}

				}
				if (player.isDead()) {
					gameOver = true;
				}
			}
			// checks if the player arrived at the final loc before gameOver is true
			if (locationArray[position] == endLocation) {
				endGame("You escaped!\n\n");
			} else {
				endGame("You died...");
			}
			// reset global vars if player keeps on playing
			position = 0;
			actions = 0;
		}

	}

	// This method is called when the game ends, and asks the user if they want to
	// play again.
	static void endGame(String msg) throws InterruptedException {
		// Prints the message with a 50ms delay between each character to simulate
		// typing.
		PrintMethods.delayPrintln(msg, 50);

		// Asks the user for input on whether they want to play again.
		PrintMethods.delayPrint("Would you like to play again?\n1. Yes\n2. No\nChoice: ");

		// Creates a new Scanner object to read user input from the console.
		Scanner in = new Scanner(System.in);

		// Reads the user's input as an integer.
		int playAgain = in.nextInt();

		// Checks if the user wants to play again or exit the program.
		if (playAgain == 1) {
			// If the user wants to play again, sets the gameOver flag to false.
			gameOver = false;
		} else if (playAgain == 2) {
			// If the user wants to exit the program, closes the Scanner and exits with a
			// status code of 0.
			in.close();
			System.exit(0);
		} else {
			// If the user inputs an invalid option, prints an error message.
			PrintMethods.delayPrintln("Invalid choice. Please enter 1 or 2: ");
		}
	}

	// This method validates that the user's input is within a given range.
	static int validChoice(int min, int max) throws InterruptedException {
		// Initializes variables for the user's selection and whether it is valid.
		boolean validChoice = false;
		int selection = -1;

		// Creates a new Scanner object to read user input from the console.
		Scanner in = new Scanner(System.in);

		// Asks the user for input and prints the valid range of options.
		PrintMethods.delayPrint("Your Choice: ");

		// Loops until the user inputs a valid selection.
		while (!validChoice) {
			// Reads the user's input as an integer.
			selection = in.nextInt();

			// Checks if the user's selection is within the valid range.
			if (min <= selection && selection <= max) {
				validChoice = true;
			} else {
				// If the user inputs an invalid selection, prints an error message.
				PrintMethods.invalidChoice("a number between (" + min + " - " + max + ")");
			}
		}

		// Prints a blank line for formatting and returns the user's selection.
		System.out.println();
		return selection;
	}

	// This method validates that the user's input is one of two specified options.
	static String validChoice(String option1, String option2) {
		// Creates a new Scanner object to read user input from the console.
		Scanner in = new Scanner(System.in);

		// Initializes variables for the user's selection and whether it is valid.
		String choice = null;
		boolean validChoice = false;

		// Loops until the user inputs a valid selection.
		while (!validChoice) {
			// Reads the user's input as a string.
			choice = in.nextLine();

			// Checks if the user's selection matches one of the valid options.
			if (choice.equals(option1) || choice.equals(option2)) {
				validChoice = true;
			} else {
				// If the user inputs an invalid selection, prints an error message.
				PrintMethods.invalidChoice("(y or n)");
			}
		}

		// Returns the user's selection.
		return choice;
	}
}
