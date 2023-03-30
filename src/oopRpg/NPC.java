package oopRpg;

public class NPC extends Character{
	

	public NPC(String name) {
		super(name, false);
		this.setHealth(1);
	}

	String talk(String msg) {
		return this.getName() + ": " + msg;
	}
}
