package oopRpg;

public class Destructible extends Item {
	// This integer field represents the durability of the Destructible item.
	private int durability;

	// This constructor initializes the name and durability of the Destructible
	// item.
	public Destructible(String name, int durability, String description) {
		// Call the constructor of the superclass to set the name.
		super(name, description);
		this.durability = durability;
	}

	/*
	 * This method overrides the use method in the superclass. When a destructible
	 * item is used, it generates a message to the console indicating that the item
	 * has been used and decrements the durability by 1. If the durability is 0 or
	 * less, the breakItem method is called to generate a message that the item has
	 * broken.
	 */
	public void use() {
		if (this.durability > 0) {
			// Call the use method in the superclass to generate the standard message.
			super.use();
			// Decrement the durability.
			this.durability--;
		}
		// If the durability is 0 or less, call the breakItem method.
		if (this.durability == 0)
			breakItem();
	}

	/*
	 * This method generates a message to the console indicating that the item has
	 * broken into a million pieces.
	 */
	private void breakItem() {
		System.out.println(this.getName() + " shatters into a million pieces");
	}
}