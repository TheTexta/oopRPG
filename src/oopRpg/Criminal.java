package oopRpg;

import java.util.ArrayList;

public class Criminal extends Enemy {
    /*
     * Criminal class is a subclass of the Enemy class. It adds no additional
     * functionality to its superclass other than existing as a subtype of
     * Enemy.
     */

    public Criminal(int health, String name, boolean isKillable, ArrayList<Item> inventory, Equip weapon) {
        /*
         * Constructor method for creating a new Criminal object. It initializes the
         * fields inherited from the Enemy superclass using the super() method.
         * 
         * @param health: integer representing the initial health of the Criminal
         * 
         * @param name: string representing the name of the Criminal
         * 
         * @param isKillable: boolean representing whether the Criminal can be killed
         * or not
         * 
         * @param inventory: an ArrayList of Item objects representing the items the
         * Criminal is carrying
         * 
         * @param weapon: an Equip object representing the weapon the Criminal is
         * holding
         */

        super(health, name, isKillable, inventory, weapon);
    }
}
