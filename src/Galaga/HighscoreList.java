package Galaga;

/**
 * Defines a collection queue of high score entries
 * 
 * @author Christopher Glasz
 */
public class HighscoreList {

	/**
	 * Head of the linked list
	 */
	private HighscoreNode head = null;

	/**
	 * Tail of the linked list
	 */
	private HighscoreNode tail = null;

	/**
	 * Selected node
	 */
	private HighscoreNode selected = null;

	/**
	 * Default constructor does nothing
	 */
	public HighscoreList() {

	}

	/**
	 * Adds names and scores to the collection in a QUEUE
	 * 
	 * @param name
	 *            the name of the player
	 * @param score
	 *            the score of the player
	 */
	public void add(String name, int score) {

		// if empty list create first node
		if (isEmpty()) {
			head = new HighscoreNode(name, score);
			selected = tail = head;
		} else if (selected == tail) {
			HighscoreNode newNode = new HighscoreNode(name, score, selected,
					null);
			selected.setNext(newNode);
			tail = selected = selected.getNext();
		}

	}

	/**
	 * Inserts the given score before the selected node
	 * 
	 * @param name
	 *            the name of the player
	 * @param score
	 *            the score of the player
	 */
	public void insert(String name, int score) {

		if (selected == head) {
			HighscoreNode newNode = new HighscoreNode(name, score, null,
					selected);
			head = newNode;
			selected = head;
		} else {
			HighscoreNode newNode = new HighscoreNode(name, score,
					selected.getPrevious(), selected);
			selected.getPrevious().setNext(newNode);
			selected = newNode;
		}

	}

	/**
	 * removes the currently selected node
	 */
	public void remove() {
		if (selected == head && selected != null) {
			head = head.getNext();
			selected = null;
		} else if (selected != null) {
			selected.getPrevious().setNext(selected.getNext());
			selected = null;
		}

	}

	/**
	 * resets selected to the first node of the collection
	 */
	public void reset() {
		selected = head;
	}

	/**
	 * Changes selected to the Node given
	 * 
	 * @param selectThis
	 *            the Node to be selected
	 */
	public void reset(HighscoreEntry selectThis) {
		selected = head;

		while (selected.getItem() != selectThis && selected != null) {
			selected = selected.getNext();
		}
	}

	/**
	 * Returns the current selected node increments selected to the next node
	 * 
	 * @return the current selected node increments selected to the next node
	 */
	public HighscoreEntry next() {
		HighscoreNode returnMe = selected;
		if (selected != null) {
			selected = selected.getNext();
			return returnMe.getItem();
		}

		return null;
	}

	/**
	 * Determines whether there is a selected item (in other words, returns
	 * true, if an invocation to next would return an item).
	 * 
	 * @return true if something is selected
	 */
	public boolean hasNext() {
		if (selected == null) {
			return false;
		}
		return true;
	}

	/**
	 * Tests to see if the list is empty
	 * 
	 * @return true if the list is empty
	 */
	public boolean isEmpty() {
		return head == null;
	}

}
