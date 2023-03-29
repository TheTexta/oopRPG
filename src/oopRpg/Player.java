package oopRpg;

import java.util.ArrayList;

public class Player extends Character implements Killable {
	protected int experience;
	protected int wantedLvl;
	protected Equip weapon;

	public Player(String name, Equip weapon) {
		this.health = 100;
		this.isKillable = true;
		this.name = name;
		this.experience = 0;
		this.wantedLvl = 0;
		this.weapon = weapon;
	}

	public void loot(Enemy looted) {
		if (looted.isDead()) {
			this.inventory.addAll(looted.inventory);
		} else
			throw new java.lang.RuntimeException("Player tried to loot an alived enemy!");
	}

	public void setWeapon(Equip weapon) {
		this.inventory.add(this.weapon);
		this.inventory.remove(weapon);
		this.inventory.trimToSize();
		this.weapon = weapon;
	}

	@Override
	public void die() {

	}

	public void attack(Character attacked) {
		attacked.health -= ((int) Math.random() * (this.weapon.getDamage() * 0.5)) + this.weapon.getDamage();
	}

	public ArrayList<Item> listAndGetItems() {

		ArrayList<Item> items = new ArrayList<>();

		System.out.println("Inventory:");

		if (!this.inventory.isEmpty()) {
			for (int i = 0; i < this.inventory.size(); i++) {

				System.out.println((items.size() + 1) + ". " + this.inventory.get(i).getName());
				items.add(this.inventory.get(i));

			}
		}
		System.out.println("\nWeapon: " + this.weapon.getName());
		return items;
	}

	public void addItem(Item item) {
		this.inventory.add(item);
	}

	public int actionMultiplier() {
		return this.wantedLvl;
		//TODO implement a system where wanted lvl increases after killing a cop or citizen
	}

	public Equip getWeapon() {
		return this.weapon;
	}

	public int getHealth() {
		return this.health;
	}
}
