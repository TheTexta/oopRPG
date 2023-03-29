package oopRpg;

import java.util.ArrayList;

public class Enemy extends Character implements Killable {

	protected Equip weapon;

	protected boolean knowsPlayer;

	public Enemy(int health, String name, boolean isKillable, ArrayList<Item> inventory, Equip weapon) {
		this.health = health;
		this.name = name;
		this.isKillable = isKillable;
		this.inventory = inventory;
		this.weapon=weapon;
		// TODO Auto-generated constructor stub
	}

	String yell(String msg) {
		return (this.name + " yells: " + msg);
	}

	public void attack(Character attacked) {
		attacked.health -=  (int)(Math.random() * (this.weapon.getDamage() * 0.5)) + this.weapon.getDamage();
		// TODO ask about whether this counts as a repeating method
	}

	@Override
	public void die() {
		this.health = 0;

	}
}
