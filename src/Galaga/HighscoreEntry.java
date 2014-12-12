package Galaga;

/**
 * Defines an entry in the highscore list
 * 
 * @author Christopher Glasz
 */
public class HighscoreEntry {

	/**
	 * Name of the player
	 */
	private String name;

	/**
	 * High score
	 */
	private int score;

	/**
	 * Default constructor does nothing
	 */
	public HighscoreEntry() {
	}

	/**
	 * COnstructor initilaizes variables
	 * 
	 * @param name
	 *            name of the player
	 * @param score
	 *            high score
	 */
	public HighscoreEntry(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * Accessor method for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Accessor method for score
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Matator method for name
	 * 
	 * @param name
	 *            name of the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Mutator method for the score
	 * 
	 * @param score
	 *            high score
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
