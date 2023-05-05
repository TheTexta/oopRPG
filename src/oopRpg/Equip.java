package oopRpg;

public class Equip extends Item {
    private boolean weapon;
    private int damage;

    public Equip(String name, int damage, boolean weapon) {
        super(name);
        this.damage = damage;
        this.weapon = weapon;
    }

    int getDamage() {
        return this.damage;
    }

    boolean getType(){
        return this.weapon;
    }

}
