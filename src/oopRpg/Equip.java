package oopRpg;

public class Equip extends Item {
    private int damage;

    public Equip(String name, int damage) {
        super(name);
        this.damage = damage;
    }

    int getDamage() {
        return this.damage;
    }

}
