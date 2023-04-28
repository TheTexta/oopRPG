package oopRpg;

import java.util.ArrayList;

public class Player extends Character implements Killable {
	private int wantedLvl;
	private Equip weapon;

	public Player(String name, Equip weapon) {
		super(name, true);
		this.setHealth(100);
		this.wantedLvl = 0;
		this.weapon = weapon;
	}

	public void loot(Enemy looted) {
		if (looted.isDead()) {
			this.addAllToInventory(looted.getInventory());
		} else
			throw new java.lang.RuntimeException("Player tried to loot an alived enemy!");
	}

	public void setWeapon(Equip weapon) {
		this.addItem(this.weapon);
		this.subtractItem(weapon);
		this.weapon = weapon;
	}

	public void attack(Character attacked) {
		attacked.setHealth(attacked.getHealth() - ((int) (Math.random() * (this.weapon.getDamage() * 0.5))
				+ this.weapon.getDamage()));
		/*
		 * Intentionally the same attack method in the enemy and player class.
		 * I want to have the attacks by any character to work the same way regardless
		 * of class. The only variable that can change relatiing to the attacking system
		 * is the health. 
		 */
	}

	public ArrayList<Item> listAndGetItems() {

		ArrayList<Item> items = new ArrayList<>();

		System.out.println("Inventory:");

		if (!this.getInventory().isEmpty()) {
			for (int i = 0; i < this.getInventory().size(); i++) {

				System.out.println((items.size() + 1) + ". " + this.getInventory().get(i).getName());
				items.add(this.getInventory().get(i));

			}
		}
		System.out.println("\nWeapon: " + this.weapon.getName());
		return items;
	}

	public int actionMultiplier() {
		return this.wantedLvl;
		// TODO implement a system where wanted lvl increases after killing a cop or
		// citizen
	}

	public Equip getWeapon() {
		return this.weapon;
	}

}
