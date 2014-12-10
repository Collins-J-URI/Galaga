package Galaga;

public class Node {

	private String _name;
	private int _score;
	
	private Node _next;
	private Node _previous;
	
	
	public Node(){
		_next = null;
	}
	
	public Node(String name, int score){
		_name = name;
		_score = score;
		_next = null;
	}
	
	public Node(String name, int score, Node next){
		_name = name;
		_score = score;
		_next = next;
	}
	
	public Node(String name, int score, Node prev, Node next){
		_name = name;
		_score = score;
		_next = next;
		_previous = prev;
	}
	
	public void setName(String name){
		_name = name;
	}
	
	public void setScore(int score){
		_score = score;
	}
	
	public void setNext(Node next){
		_next = next;
	}
	
	public void setPrevious(Node prev){
		_previous = prev;
	}
	
	public String getName(){return _name;}
	
	public int getScore(){return _score;}
	
	public Node getNext(){return _next;}
	
	public Node getPrevious(){return _previous;}
}
