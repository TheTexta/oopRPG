package oopRpg;

public class NPC extends Character{
	
	// Constructor for creating an NPC with a given name
	public NPC(String name) {
		// Call the constructor of the superclass (Character) with the given name and 'false' for the isPlayer parameter
		super(name, false);
		// Set the health of the NPC to 1
		this.setHealth(1);
	}

	// Method for an NPC to talk, which takes a message as a parameter and returns the NPC's name concatenated with the message
	String talk(String msg) {
		return this.getName() + ": " + msg;
	}
}
