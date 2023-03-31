package oopRpg;

public class Destructible extends Item {
	private int durability;

	public Destructible(String name, int durability) {
		super(name);
		this.durability = durability;
	}

	/*
	 * When a destructible is used, generate same message as a normal item with the
	 * addition of a -1 to the durability variable. If this reaches 0, run the
	 * itemBreak method which generates a break message in the console
	 */
	public void use() {
		super.use();
		this.durability--;
		if (this.durability <= 0)
			breakItem();

	}

	// Generates a message to send to console when an item breaks
	public void breakItem() {
		System.out.println(this.getName() + " shatters into a millino peices");
	}
}
