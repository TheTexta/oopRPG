package oopRpg;

import java.util.ArrayList;

public class Enemy extends Character implements Killable {

	private Equip weapon;

	private boolean knowsPlayer;

	public Enemy(int health, String name, boolean isKillable, ArrayList<Item> inventory, Equip weapon) {
		super(name, isKillable);
		this.setInventory(inventory);
		this.weapon = weapon;
	}

	String yell(String msg) {
		return (this.getName() + " yells: " + msg);
	}

	public void attack(Character attacked) {
		attacked.setHealth(attacked.getHealth() - (int) (Math.random() * (this.weapon.getDamage() * 0.5))
				+ this.weapon.getDamage());
		// TODO ask about whether this counts as a repeating method
	}

	@Override
	public void die() {
		this.setHealth(0);

	}
}
