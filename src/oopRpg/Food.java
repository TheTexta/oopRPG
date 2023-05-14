package oopRpg;

public class Food extends Destructible {

    private int healVal; // The amount of health this food item restores

    public Food(String name, int health, int uses, String description) {
        super(name, uses, description);
        this.healVal = health;
    }

    public void use(Character eater) throws Exception {
        super.use(eater); // Call the parent class's use method
        int health = eater.getHealth(); // Get the current health of the eater
        eater.setHealth(eater.getHealth() + this.healVal); // Increase eater's health by the healVal
        health = eater.getHealth() - health; // Calculate the amount of health gained
        PrintMethods.printWrapped("You regain " + health + "HP"); // Print the amount of health regained
    }
}
