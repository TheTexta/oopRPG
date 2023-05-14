package oopRpg;

// interface for all characters which can be killed by players
public interface Killable {
	// All players who can be killed should also be able to kill characters
	void attack(Character attacked) throws InterruptedException, Exception;
}
