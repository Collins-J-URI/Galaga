package Galaga;

import processing.core.PApplet;

/**
 * Defines a menu composed of one or more options. Options are stored in a
 * closed loop linked list, and can be cycled through and executed.
 * 
 * @author Christopher Glasz
 */
public class Menu {

	/**
	 * Head of the linked list
	 */
	Node head;

	/**
	 * Selected node in the linked list
	 */
	Node selected;

	/**
	 * Constructor populates menu with options.
	 * 
	 * @param options
	 *            options in the menu
	 */
	public Menu(Option[] options) {
		head = new Node(null);

		// Head points to the first option, which points only to itself
		Node first = new Node(options[0]);
		first.setNext(first);
		first.setPrev(first);
		head.setNext(first);

		// Add the rest of the options to the loop
		for (int i = 1; i < options.length; i++)
			add(options[i]);

		// Select the top option
		selected = first;
		selected.getOption().select();
	}

	/**
	 * Add the passed in option to the loop at the bottom of the list
	 * 
	 * @param option
	 *            option to add to the menu
	 */
	public void add(Option option) {
		Node first = head.getNext();
		Node last = first.getPrev();
		Node add = new Node(option, last, first);
		first.setPrev(add);
		last.setNext(add);
	}

	/**
	 * Select the option above the currently selected option
	 */
	private void up() {
		selected.getOption().deselect();
		selected = selected.getPrev();
		selected.getOption().select();
	}

	/**
	 * Select the option below the currently selected option
	 */
	private void down() {
		selected.getOption().deselect();
		selected = selected.getNext();
		selected.getOption().select();
	}

	/**
	 * Move selected option according to the passed in joystick position
	 * 
	 * @param j
	 *            joystick position
	 */
	public void cycle(Joystick j) {
		switch (j) {
		case UP:
			up();
			break;
		case DOWN:
			down();
			break;
		case LEFT:
			up();
			break;
		case RIGHT:
			down();
			break;
		default:
			break;
		}
	}

	/**
	 * Execute the selected option
	 */
	public void execute() {
		selected.getOption().execute();
	}

	/**
	 * Render the menu to the passed in PApplet
	 * 
	 * @param g
	 *            PApplet to draw to
	 */
	public void render(PApplet g) {
		Node current = head.getNext();
		do {
			current.getOption().render(g);
			g.translate(0, g.textAscent() * 2);
			current = current.getNext();
		} while (!current.equals(head.getNext()));
	}

	/**
	 * Defines a node in a linked list which points to an option
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
		 * The option that this node points to
		 */
		private Option option;

		/**
		 * Constructor initializes option
		 * 
		 * @param option
		 *            option pointed to by the node
		 */
		public Node(Option option) {
			this.option = option;
		}

		/**
		 * Constructor initializes option and previous node
		 * 
		 * @param option
		 *            option pointed to by the node
		 * @param prev
		 *            previous node in the list
		 */
		public Node(Option option, Node prev) {
			this.option = option;
			this.prev = prev;
		}

		/**
		 * Constructor initializes option, previous node, and next node
		 * 
		 * @param option
		 *            option pointed to by the node
		 * @param prev
		 *            previous node in the list
		 * @param next
		 *            next node in the list
		 */
		public Node(Option option, Node prev, Node next) {
			this.option = option;
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
		 * Accessor method for the option
		 * 
		 * @return option pointed to by this node
		 */
		public Option getOption() {
			return option;
		}

		/**
		 * Mutator method for the option
		 * 
		 * @param option
		 *            option pointed to by this node
		 */
		public void setOption(Option option) {
			this.option = option;
		}
	}
}
