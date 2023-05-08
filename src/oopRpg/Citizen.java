package oopRpg;

import java.util.ArrayList;
// TODO decide whether to keep this class
public class Citizen extends Enemy implements Reporter {
	boolean evil;

	public Citizen(int health, String name, boolean isKillable, ArrayList<Item> inventory, boolean evil, Equip weapon) {
		super(health, name, isKillable, inventory, weapon);
		this.evil = evil;
		}

	@Override
	public void report(Character reported) {
		if (!evil) {
// TODO do i keep this??
		}

	}

}
