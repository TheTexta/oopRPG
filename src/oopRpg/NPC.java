package oopRpg;

public class NPC extends Character{
	

	public NPC(String name) {
		super();
		this.setHealth(1);
		this.setIsKillable(false);
		this.setName(name);
	}

	String talk(String msg) {
		return this.getName() + ": " + msg;
	}
}
