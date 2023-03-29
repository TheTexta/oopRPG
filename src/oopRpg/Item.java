package oopRpg;

public class Item {
	private String name;
	

	public Item(String name) {
		this.name = name;
	}

	void use() {
		System.out.println("Used " + name);
	}

	String getName() {
		return this.name;
	}
}
