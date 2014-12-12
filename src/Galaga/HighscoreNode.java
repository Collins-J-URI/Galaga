package Galaga;

/**
 * Defines a node in a highscore collection
 * 
 * @author Christopher Glasz
 */
public class HighscoreNode {

	/**
	 * Highscore entry
	 */
	private HighscoreEntry highscore;

	/**
	 * Next node in the linked list
	 */
	private HighscoreNode next;

	/**
	 * Previous node in the linked list
	 */
	private HighscoreNode previous;

	/**
	 * Constructor initializes variables
	 */
	public HighscoreNode() {
		highscore = new HighscoreEntry();
		this.next = null;
	}

	/**
	 * Constructor initializes variables
	 * 
	 * @param name
	 *            name
	 * @param score
	 *            high score
	 */
	public HighscoreNode(String name, int score) {
		highscore = new HighscoreEntry(name, score);
		this.next = null;
	}

	/**
	 * Constructor initializes variables
	 * 
	 * @param name
	 *            name
	 * @param score
	 *            high score
	 * @param next
	 *            next node in the linked list
	 */
	public HighscoreNode(String name, int score, HighscoreNode next) {
		highscore = new HighscoreEntry(name, score);
		this.next = next;
	}

	/**
	 * Constructor initializes variables
	 * 
	 * @param name
	 *            name
	 * @param score
	 *            high score
	 * @param prev
	 *            previous node in the linked list
	 * @param next
	 *            next node in the linked list
	 */
	public HighscoreNode(String name, int score, HighscoreNode prev,
			HighscoreNode next) {
		highscore = new HighscoreEntry(name, score);
		this.next = next;
		this.previous = prev;
	}

	/**
	 * Mutator method for highscore
	 * 
	 * @param newHighscore
	 *            new highscore
	 */
	public void setItem(HighscoreEntry newHighscore) {
		highscore = newHighscore;
	}

	/**
	 * Mutator method for next
	 * 
	 * @param next
	 *            next node in the linked list
	 */
	public void setNext(HighscoreNode next) {
		this.next = next;
	}

	/**
	 * Mutator method for previous
	 * 
	 * @param prev
	 *            previous node in the linked list
	 */
	public void setPrevious(HighscoreNode prev) {
		this.previous = prev;
	}

	/**
	 * Accessor method for high score
	 * 
	 * @return highscore
	 */
	public HighscoreEntry getItem() {
		return highscore;
	}

	/**
	 * Accessor method for next
	 * 
	 * @return next
	 */
	public HighscoreNode getNext() {
		return this.next;
	}

	/**
	 * Accessor method for previous
	 * 
	 * @return previous
	 */
	public HighscoreNode getPrevious() {
		return this.previous;
	}
}
