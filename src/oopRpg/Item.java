package oopRpg;

public class Item {
	// Declare the name field as private so it can only be accessed within the Item class
	private String name;
	private String description;
	
	// Constructor for creating an Item object with a given name
	public Item(String name) {
		this.name = name;
		this.description = "A generic " + name;
	}

	// Constructor including description
	public Item(String name, String description){
		this.name=name;
		this.description=description;
	}

	// Method for using an item, which prints a message indicating that the item has been used
	void use(Character user) throws Exception {
		PrintMethods.printWrapped("Used " + name);
	}

	String getDescription(){
		return this.description;
	}

	// Getter method for getting the name of an item
	String getName() {
		return this.name;
	}

}