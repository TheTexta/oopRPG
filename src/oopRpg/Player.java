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
		// TODO ask about whether this counts as a repeating method
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
