package Galaga;

import processing.core.PApplet;

public class Menu {

	Node head;
	Node selected;

	public Menu(String[] options) {
		head = new Node();

		Node first = new Node(new Option(options[0]));
		first.setNext(first);
		first.setPrev(first);
		head.setNext(first);
		
		for (int i = 1; i < options.length; i++) {
			add(new Option(options[i]));
		}

		selected = first;
		selected.getOption().select();
	}

	public Menu(Option[] options) {
		head = new Node();

		Node first = new Node(options[0]);
		first.setNext(first);
		first.setPrev(first);
		head.setNext(first);
		
		for (int i = 1; i < options.length; i++) {
			add(options[i]);
		}

		selected = first;
		selected.getOption().select();
	}

	public void add(Option o) {
		Node first = head.getNext();
		Node last = first.getPrev();
		Node add = new Node(o, last, first);
		first.setPrev(add);
		last.setNext(add);
	}

	private void up() {
		selected.getOption().deselect();
		selected = selected.getPrev();
		selected.getOption().select();
	}

	private void down() {
		selected.getOption().deselect();
		selected = selected.getNext();
		selected.getOption().select();
	}

	public void cycle(Joystick j) {
		switch (j) {
		case up:
			up();
			break;
		case down:
			down();
			break;
		}
	}

	public void execute() {
		selected.getOption().execute();
	}

	public void render(PApplet g) {
		Node current = head.getNext();
		do {
			current.getOption().render(g);
			g.translate(0, 80);
			current = current.getNext();
		} while (!current.equals(head.getNext()));
	}

	public class Node {
		Node next, prev;
		Option option;

		public Node() {
		}

		public Node(Option option) {
			this.option = option;
		}

		public Node(Option option, Node prev) {
			this.option = option;
			this.prev = prev;
		}

		public Node(Option option, Node prev, Node next) {
			this.option = option;
			this.prev = prev;
			this.next = next;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public Node getPrev() {
			return prev;
		}

		public void setPrev(Node prev) {
			this.prev = prev;
		}

		public Option getOption() {
			return option;
		}

		public void setOption(Option option) {
			this.option = option;
		}
	}
}
