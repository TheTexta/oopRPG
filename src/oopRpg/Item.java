package oopRpg;

public class Item {
	// TODO ask if my item system counts as complication
	// Declare the name field as private so it can only be accessed within the Item class
	private String name;
	
	// Constructor for creating an Item object with a given name
	public Item(String name) {
		this.name = name;
	}

	// Method for using an item, which prints a message indicating that the item has been used
	void use() {
		System.out.println("Used " + name);
	}

	// Getter method for getting the name of an item
	String getName() {
		return this.name;
	}

}