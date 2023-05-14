package oopRpg;

public class Equip extends Item {
    private boolean weapon; // Indicates if the equipment is a weapon or not
    private int damage; // The amount of damage the equipment provides

    public Equip(String name, int damage, boolean weapon) {
        super(name); // Call the parent class's constructor to set the item name
        this.damage = damage; // Set the damage value
        this.weapon = weapon; // Set the weapon type
    }

    public Equip(String name, int damage, boolean weapon, String description) {
        super(name, description); // Call the parent class's constructor to set the item name and description
        this.damage = damage; // Set the damage value
        this.weapon = weapon; // Set the weapon type
    }

    int getDamage() {
        return this.damage; // Get the amount of damage the equipment provides
    }

    boolean getType() {
        return this.weapon; // Get the weapon type (true if it's a weapon, false otherwise)
    }
}
