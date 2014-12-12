package Galaga;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * Defines a field for name entry using cycleable letters
 * 
 * @author Christopher Glasz
 */
public class NameEntry {

	/**
	 * Head of the linked list
	 */
	Node head;

	/**
	 * Selected node in the linked list
	 */
	Node selected;

	/**
	 * Default constructor populates name entry with a's.
	 */
	public NameEntry() {
		head = new Node(null);

		// Head points to the first letter, which points only to itself
		Node first = new Node(new CycleableLetter('a'));
		first.setNext(first);
		first.setPrev(first);
		head.setNext(first);

		// Add the rest of the letters to the loop
		for (int i = 1; i < 3; i++)
			add(new CycleableLetter('a'));

		// Select the first letter
		selected = first;
		selected.getLetter().select();
	}

	/**
	 * Constructor populates name entry with letters.
	 * 
	 * @param letters
	 *            letters in the name entry
	 */
	public NameEntry(CycleableLetter[] letters) {
		head = new Node(null);

		// Head points to the first letter, which points only to itself
		Node first = new Node(letters[0]);
		first.setNext(first);
		first.setPrev(first);
		head.setNext(first);

		// Add the rest of the letters to the loop
		for (int i = 1; i < letters.length; i++)
			add(letters[i]);

		// Select the first letter
		selected = first;
		selected.getLetter().select();
	}

	/**
	 * Constructor populates name entry with letters.
	 * 
	 * @param letters
	 *            letters in the name entry
	 */
	public NameEntry(char[] letters) {
		head = new Node(null);

		// Head points to the first letter, which points only to itself
		Node first = new Node(new CycleableLetter(letters[0]));
		first.setNext(first);
		first.setPrev(first);
		head.setNext(first);

		// Add the rest of the letters to the loop
		for (int i = 1; i < letters.length; i++)
			add(new CycleableLetter(letters[i]));

		// Select the first letter
		selected = first;
		selected.getLetter().select();
	}

	/**
	 * Add the passed in letter to the loop at the bottom of the list
	 * 
	 * @param letter
	 *            letter to add to the menu
	 */
	public void add(CycleableLetter letter) {
		Node first = head.getNext();
		Node last = first.getPrev();
		Node add = new Node(letter, last, first);
		first.setPrev(add);
		last.setNext(add);
	}

	/**
	 * Select the letter to the left the currently selected letter
	 */
	private void left() {
		selected.getLetter().deselect();
		selected = selected.getPrev();
		selected.getLetter().select();
	}

	/**
	 * Select the letter to the right of the currently selected letter
	 */
	private void right() {
		selected.getLetter().deselect();
		selected = selected.getNext();
		selected.getLetter().select();
	}

	/**
	 * Move selected letter according to the passed in joystick position
	 * 
	 * @param j
	 *            joystick position
	 */
	public void cycle(Joystick j) {
		switch (j) {
		case UP:
		case DOWN:
			selected.getLetter().cycleLetter(j);
			break;
		case LEFT:
			left();
			break;
		case RIGHT:
			right();
			break;
		default:
			break;
		}
	}

	/**
	 * Set the selected letter to the passed in one
	 * 
	 * @param letter
	 *            new letter
	 */
	public void setLetter(char letter) {
		selected.getLetter().setLetter(letter);
	}

	/**
	 * Render the menu to the passed in PApplet
	 * 
	 * @param g
	 *            PApplet to draw to
	 */
	public void render(PApplet g) {
		PFont font = g.loadFont("Fonts/Emulogic-36.vlw");
		g.textFont(font, 18);
		Node current = head.getNext();
		do {
			current.getLetter().render(g);
			g.translate(g.textWidth(current.getLetter().getLetter()), 0);
			current = current.getNext();
		} while (!current.equals(head.getNext()));
	}

	/**
	 * Returns the entered name in the form of a string
	 * 
	 * @return the entered name in the form of a string
	 */
	public String getName() {
		String name = "";

		Node current = head.getNext();
		do {
			name = name + current.getLetter().getLetter();
			current = current.getNext();
		} while (!current.equals(head.getNext()));
		return name;
	}

	/**
	 * Defines a node in a linked list which points to an cycleable letter
	 * 
	 * @author Christopher Glasz
	 */
	public class Node {

		/**
		 * Next node in the linked list
		 */
		private Node next;

		/**
		 * Previous node in the linked list
		 */
		private Node prev;

		/**
		 * The letter that this node points to
		 */
		private CycleableLetter letter;

		/**
		 * Constructor initializes letter
		 * 
		 * @param letter
		 *            letter pointed to by the node
		 */
		public Node(CycleableLetter letter) {
			this.letter = letter;
		}

		/**
		 * Constructor initializes letter and previous node
		 * 
		 * @param letter
		 *            letter pointed to by the node
		 * @param prev
		 *            previous node in the list
		 */
		public Node(CycleableLetter letter, Node prev) {
			this.letter = letter;
			this.prev = prev;
		}

		/**
		 * Constructor initializes letter, previous node, and next node
		 * 
		 * @param letter
		 *            letter pointed to by the node
		 * @param prev
		 *            previous node in the list
		 * @param next
		 *            next node in the list
		 */
		public Node(CycleableLetter letter, Node prev, Node next) {
			this.letter = letter;
			this.prev = prev;
			this.next = next;
		}

		/**
		 * Accessor method for next node
		 * 
		 * @return next node in the list
		 */
		public Node getNext() {
			return next;
		}

		/**
		 * Mutator method for the next node
		 * 
		 * @param next
		 *            next node in the list
		 */
		public void setNext(Node next) {
			this.next = next;
		}

		/**
		 * Accessor method for the previous node
		 * 
		 * @return previous node in the list
		 */
		public Node getPrev() {
			return prev;
		}

		/**
		 * Mutator method for the previous node
		 * 
		 * @param prev
		 *            previous node in the list
		 */
		public void setPrev(Node prev) {
			this.prev = prev;
		}

		/**
		 * Accessor method for the letter
		 * 
		 * @return letter pointed to by this node
		 */
		public CycleableLetter getLetter() {
			return letter;
		}

		/**
		 * Mutator method for the letter
		 * 
		 * @param letter
		 *            letter pointed to by this node
		 */
		public void setLetter(CycleableLetter letter) {
			this.letter = letter;
		}
	}

}
