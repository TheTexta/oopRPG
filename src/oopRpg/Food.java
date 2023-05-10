package oopRpg;
import java.util.Objects;

public class Food extends Destructible {

    private int healVal;

    public Food(String name, int health, int uses, String description) {
        super(name, uses,description);
        this.healVal = health;
    }

    public void use (Player eater){
        eater.setHealth(eater.getHealth()+this.healVal);
    }

    
}
