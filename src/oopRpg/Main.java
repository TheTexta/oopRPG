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
		// Scanner object to read inputs
		Scanner in = new Scanner(System.in);

		// Weapons
		Equip shank = new Equip("Shank", 25, true);
		Equip g19 = new Equip("Glock 19", 50, true);
		Equip mp5 = new Equip("MP5", 100, true);
		Equip ak = new Equip("AK-47", 200, true);

		// Armour
		Equip kevlar = new Equip("Kevlar", 25, false);
		Equip tie = new Equip("'Gating master' - Special Armored Tie", 100, false);

		// Items
		Item tape = new Item("Tape");

		// Enemy kits
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

		// Player declaration takes name from the user input.
		Player player = new Player(name, shank);

		// Names - possible names of officers
		String[] names = { "A. Trinidad", "K. Hansen", "J. Briggs", "G. Serna", "S. Bowling", "K. Guest", "V. Montero",
				"D. Millan", "L. Akins", "D. McClintock", "N. Mahoney", "T. Lindsay", "K. Begay", "S. Tinsley",
				"D. Cornwell", "J. Fierro", "T. Newell", "X. Bearden", "M. Moffett", "E. Hooks" };

		// Enemies
		Police swat1 = new Police(200, "Special Forces Agent:" + names[((int) Math.random() * 19) + 1], true, swatKit,
				mp5);
		Police swat2 = new Police(200, "Special Forces Agent:" + names[((int) Math.random() * 19) + 1], true, swatKit,
				mp5);
		Criminal testBadGuy = new Criminal(50, "Gabe", true, swatKit, shank);
		Criminal op = new Criminal(100, "Enemy Gang Member:" + names[((int) Math.random() * 19) + 1], true, gangKit,
				g19);

		Criminal outpostBoss = new Criminal(200, "Kristopher Churchill", true, new ArrayList<>() {
			{
				add(tie);
			}
		}, ak);

		// Room Inventories
		ArrayList<Item> homeInv = new ArrayList<>() {
			{
				add(g19);
				add(tape);

			}
		};

		// TODO simplify inv declaration. homeInv is not necessary

		// Empty Inventory to set to looted characters
		ArrayList<Item> empty = new ArrayList<>();

		Location home = new Location("Home", homeInv, 5,
				"You walk downstairs and grab your keys as you head to your car");
		home.addCharacter(testBadGuy);

		Location car = new Location("Car", 3, "You get into your car");
		Location outpost = new Location("Outpost", 1,
				"Finally arriving at the runned down out of business 711,\n you call the contact your lawyer gave you. \n'Hello?'\n'I want out'\n'I need you to kill some pigs for me first'\n ");

		// outpost subLocations
		Location outpostDeclineChal = new Location("Outpost", 5, "You prepare to fight the boss");
		// TODO change where characters are added so they can be re initialised if the
		// player chooses to restart the game.
		outpostDeclineChal.addCharacter(outpostBoss);
		Location outpostAcceptChal = new Location("Alleyway", 5,
				"You accept the bosses challange. He walks you outside, down the road to a little allayway: 'Grangers Alley'\n'You kill the pigs down there you get your id'\nHe walks away leaving you alone with the enemy\n");
		outpostAcceptChal.addCharacter(op);

		Location border = new Location("Border", 5,
				"The blazing artificial lights shine you down as you approach the border\n");

		// Placeholder location for endgame
		Location endLocation = new Location("End", 0, "Game Over");

		Location[] locationArray = { home, car, outpost, border, endLocation };

		int position = 0;
		/*
		 * Linear position of the player. used to determine the current location the
		 * player is in through the use of the locationArray
		 */

		// TODO add a random event like a civilian seeing you and reporting you if you
		// dont shoot them
		// TODO potential add a talk method to negotiate out of dangerous situations

		boolean movementLocked = false;
		while (true) {

			PrintMethods.printLoading();
			PrintMethods.delayPrint(
					"You jolt awake to the sound of your phone buzzing on the nightstand,\nand as you groggily answer it, your worst fears are confirmed: \nthe police are on their way, and you've got to get out of the country, fast.\n");
			PrintMethods.printLoading();

			PrintMethods.delayPrint("\nWould you like to see the tutorial? (y or n): ");
			boolean tutorial = false; // Boolean to hold whether player chooses to see the tutorial
			if (validChoice("y", "n").equals("y")) {
				tutorial = true;
			} else {
				tutorial = false;
			}

			if (tutorial) {
				PrintMethods.printLoading();
				PrintMethods.delayPrint(
						"You have to flee the country!\nEvery action the police get closer to you\nIf you run out of actions they will find you\nBut you can escape!\nEvery time you change locations you throw them off\nGaining you valuable actions to use in as you run\n");

				tutorial = false;
			}
			System.out.println();
			while (!gameOver) {
				int choice = -1;
				boolean move = false;
				actions += locationArray[position].getActions();

				while (!move) {
					PrintMethods.printLoading();

					/*
					 * 2D string array holds the args to print to console via the PrintMethods such
					 * as player stats and equips
					 */
					String[][] toPrint = { { "HEALTH", "WEAPON", "ATKDMG", "ACTIONS", "ENEMIES" },
							{ Integer.toString(player.getHealth()), player.getWeapon().getName(),
									Integer.toString(player.getWeapon().getDamage()), Integer.toString(actions),
									Integer.toString(locationArray[position].getAttackables(true).size()) } };
					PrintMethods.printArray(toPrint, 16);

					locationArray[position].introduceLocation();

					locationArray[position].listNextAction(movementLocked);

					if (movementLocked) {
						// TODO look over this it doesnt look right
						if (locationArray[position].getAttackables(true).size() >= 1) {
							movementLocked = false;
						}
					}

					// TODO Check for death
					if (player.isDead()) {

					}

					boolean validChoice = false;

					while (!validChoice) {
						System.out.print("Your Choice: ");
						// TODO make 5 and invalid choice
						choice = in.nextInt();
						if ((choice == 2 && !movementLocked) || choice == 3 || choice == 4 || choice == 5
								|| (choice == 1 && (locationArray[position].getAttackables(true).size() > 0))) {
							validChoice = true;
						} else {
							PrintMethods.invalidChoice("any number from options above");
						}
					}

					System.out.println();

					// Method adds police and actions when actions reaches 0
					if (actions <= 0) {

						actions = 5;

						locationArray[position].addCharacter(swat1);
						locationArray[position].addCharacter(swat2);
					}

					// TODO seperate listAttackables and getAtttackables

					/*
					 * 1. Fight 2. Move 3. Inventory 4. Grab 5. Search
					 */

					if (choice == 1) {
						locationArray[position].listAttackables();
						PrintMethods.delayPrint("\n0. Back\n");
						choice = validChoice(0, locationArray[position].getAttackables(true).size());
						if (0 < choice && choice <= locationArray[position].getAttackables(true).size()) {
							player.attack(locationArray[position].getAttackables(true).get(choice - 1));
							actions = actions - (1 * player.actionMultiplier());
						} else if (choice == 0) {

						}
					}
					if (choice == 2) {
						move = !move;
						position++;
						actions = actions - (1 * player.actionMultiplier());
						if (locationArray[position] == endLocation) {
							// TODO check if gameover needs a rework
							gameOver = true;
						} else {
							// Do location progression checks
							if (locationArray[position].equals(outpost)) {
								// Lock player moverment so they cant move away from the fight
								movementLocked = true;
								// Print Banner
								PrintMethods.printArray(toPrint, 16);
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
					if (choice == 3) {
						boolean finishInv = false;
						while (!finishInv) {
							ArrayList<Item> inventory = player.listAndGetItems();
							System.out.println("\n0. Back");
							choice = validChoice(0, inventory.size());
							if (0 < choice) {
								String equipUse;
								if (inventory.get(choice - 1).getClass().equals(Equip.class))
									equipUse = "Equip";
								else
									equipUse = "Use";
								PrintMethods.delayPrintln(
										inventory.get(choice - 1).getName() + "\n1." + equipUse + "\n0.Back");
								// Stores the players choice whether to equip an item or go back
								// TODO maybe include a description for every item that is printed when you are about to equip an item
								int equipOrBack = validChoice(0, 1);
								if (equipOrBack == 1 && equipUse == "Equip") {
									// TODO need to account for armor types
									if ((((Equip) inventory.get(choice - 1)).getType()))
										player.setWeapon((Equip) inventory.get(choice - 1));
									else
										player.setArmor((Equip) inventory.get(choice - 1));
								} else if (equipOrBack == 1 && equipUse == "Use") {
									inventory.get(choice - 1).use();
								}
								

							} else if (choice == 0) {
								finishInv = true;
							}
						}
					}
					if (choice == 4) {

						// Bool to hold if the player has finished looting the area
						boolean finishLoot = false;

						// String to hold all
						String possibleLoots = "Search the:\n1. " + locationArray[position].getName() + "\n";

						/*
						 * The following for loop iterates through every possible lootable inv in the
						 * location and add the list
						 * to the possibleLoots String
						 */ for (int i = 0; i < locationArray[position].getAttackables(false).size(); i++) {
							possibleLoots += (i + 2) + ". "
									+ locationArray[position].getAttackables(false).get(i).getName();
						}

						while (!finishLoot) {
							PrintMethods.delayPrintln(possibleLoots);
							PrintMethods.delayPrint("\n0. Back\n");
							choice = validChoice(0, locationArray[position].getAttackables(false).size() + 1);

							// Choice number 1 will always be the current location
							if (choice == 1) {
								PrintMethods.delayPrintln("What would you like to pickup in the "
										+ locationArray[position].getName() + ":");

								ArrayList<Item> lootableInv = locationArray[position].getInventory();
								for (int i = 0; i < lootableInv.size(); i++) {
									PrintMethods.delayPrint((i + 1) + ". " + lootableInv.get(i).getName() + "\n");
								}
								PrintMethods.delayPrint("\n0. Back\n");
								choice = validChoice(0, lootableInv.size());
								if (0 < choice) {
									PrintMethods.delayPrint(
											"\n" + lootableInv.get(choice - 1).getName() + "\n1.Pickup\n\n0.Back\n");

									int pickup = validChoice(0, 1);
									if (pickup == 1) {
										player.addItem(lootableInv.get(choice - 1));
										locationArray[position].removeItem(choice - 1);
										actions = actions - (1 * player.actionMultiplier());
									}
								}
							} else if (choice != 0) {
								PrintMethods.delayPrint("You rumage around "
										+ locationArray[position].getAttackables(false).get(choice - 2).getName()
										+ "s pockets and find:\n");

								ArrayList<Item> itemsToAdd = locationArray[position].getAttackables(false)
										.get(choice - 2).loot();

								player.addAllToInventory(itemsToAdd);
								if (itemsToAdd.size() == 0)
									itemsToAdd.add(new Item("!!! Character already looted !!!"));
								PrintMethods.printArrayList(itemsToAdd);

							}

							if (choice == 0) {
								finishLoot = true;
							}

						}
					}
					if (choice == 5) {
						PrintMethods.delayPrintln("You rumage around the " + locationArray[position].getName());
						PrintMethods.printLoading();
						PrintMethods.delayPrintln(locationArray[position].reveal());
						actions = actions - (1 * player.actionMultiplier());
						// TODO intergrate search better with print methods
					}

					// wait for a number
					// do action correlating to number
					// -1 from actions if player chooses to loot/move/pickup

					/*
					 * Iterates through all attackers on scene and has them do damage accordingly
					 */

					 // TODO Implement an exception so the player cannot be attacked while in the outpost sub loc until they start fighting
					if (locationArray[position].getAttackables(true).size() > 0) {

						for (int i = 0; i < locationArray[position].getAttackables(true).size(); i++) {
							locationArray[position].getAttackables(true).get(i).attack(player);
						}
					}

				}
			}

			// TODO rework this
			if (locationArray[position] == endLocation) {
				endGame("You escaped!\n\n");
			} else {
				endGame("You died...");
			}
			position = 0;
			actions = 0;
		}

	}

	static void endGame(String msg) throws InterruptedException {
		PrintMethods.delayPrintln(msg, 50);

		PrintMethods.delayPrint("Would you like to play again?\n1. Yes\n2. No\nChoice: ");
		Scanner in = new Scanner(System.in);
		int playAgain = in.nextInt();
		if (playAgain == 1) {
			gameOver = false;
		} else if (playAgain == 2) {
			in.close();
			System.exit(0);
		} else {
			PrintMethods.delayPrintln("Invalid choice. Please enter 1 or 2: ");
		}

	}

	static int validChoice(int min, int max) throws InterruptedException {
		boolean validChoice = false;
		int selection = -1;
		Scanner in = new Scanner(System.in);

		PrintMethods.delayPrint("Your Choice: ");

		while (!validChoice) {
			selection = in.nextInt();
			if (min <= selection && selection <= max) {
				validChoice = true;
			} else {
				PrintMethods.invalidChoice("a number between (" + min + " - " + max + ")");
			}
		}

		System.out.println();
		return selection;
	}

	static String validChoice(String option1, String option2) {
		Scanner in = new Scanner(System.in);
		String choice = null;
		boolean validChoice = false;
		while (!validChoice) {
			choice = in.nextLine();
			if (choice.equals(option1) || choice.equals(option2)) {
				validChoice = true;
			} else {
				PrintMethods.invalidChoice("(y or n)");
			}
		}
		return choice;

	}

}
