package Galaga;

/**
 * Defines a spawner capable of spawning clones of an enemy
 * 
 * @author Christopher Glasz
 */
public class Spawner {

	/**
	 * Prototype enemy to be spawned
	 */
	private Enemy prototype;

	/**
	 * Constructor initializes the prototype
	 * 
	 * @param prototype
	 *            enemy to be spawned
	 */
	public Spawner(Enemy prototype) {
		this.prototype = prototype;
	}

	/**
	 * Returns an instance of the prototype
	 * 
	 * @return an instance of the prototype
	 */
	public Enemy spawn() {
		return prototype.clone();
	}
}
