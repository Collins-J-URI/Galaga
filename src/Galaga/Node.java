package Galaga;

public class Node {
	
	private Item highscore;

	
	private Node _next;
	private Node _previous;
	
	
	public Node(){
		highscore = new Item();
		_next = null;
	}
	
	public Node(String name, int score){
		highscore = new Item(name,score);
		_next = null;
	}
	
	public Node(String name, int score, Node next){
		highscore = new Item(name,score);
		_next = next;
	}
	
	public Node(String name, int score, Node prev, Node next){
		highscore = new Item(name,score);
		_next = next;
		_previous = prev;
	}
	
	public void setItem(Item newHighscore){
		highscore = newHighscore;
	}

	
	public void setNext(Node next){
		_next = next;
	}
	
	public void setPrevious(Node prev){
		_previous = prev;
	}
	
	public Item getItem(){return highscore;}
	
	public Node getNext(){return _next;}
	
	public Node getPrevious(){return _previous;}
}
