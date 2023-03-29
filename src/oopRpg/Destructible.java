package oopRpg;

public class Destructible extends Item {
	int durability;

	public Destructible(String name, int durability) {
		super(name);
		this.durability = durability;
	}

}
