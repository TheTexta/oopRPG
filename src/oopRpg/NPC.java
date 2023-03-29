package oopRpg;

public class NPC extends Character{
	

	public NPC(String name) {
		super();
		this.health=1;
		this.isKillable=false;
		this.name=name;
	}

	String talk(String msg) {
		return this.name + ": " + msg;
	}
}
