package Galaga;

public class LinkedDataCollection {
	
	private Node head = null;
	private Node tail = null;
	private Node selected = null;
	public LinkedDataCollection(){
		
	}
	
	/**
	 * Adds names and scores to the collection in a QUEUE
	 * @param name the name/Initials of teh player
	 * @param score the score of the player
	 */
	public void add(String name, int score){
		
		//if empty list create first node
		if(isEmpty()){
			head = new Node(name,score);
			selected = tail = head;
		}else if (selected == tail){ 
			Node newNode = new Node(name,score,selected,null);
			selected.setNext(newNode);
			tail = selected = selected.getNext();
		}
			
		
	}

	public void insert(String name, int score){	
		
		if(selected == head){
			Node newNode = new Node(name,score,null,selected);
			head = newNode;
			selected = head;
		}else{
			Node newNode = new Node(name,score,selected.getPrevious(),selected);
			selected.getPrevious().setNext(newNode);
			selected = newNode;
		}
		
	}
	
	/**
	 * removes the currently selected node
	 * @return 
	 */
	public void remove(){
		if(selected == head && selected != null){
			head = head.getNext();
			selected = null;
		}else if (selected != null){
			selected.getPrevious().setNext(selected.getNext());
			selected = null;
		}
		
	}
	
	/**
	 * resets selected to the first node of the collection
	 */
	public void reset(){
		selected = head;
	}
	
	/**
	 * Changes selected to the Node given
	 * @param selectThis the Node to be selected
	 */
	public void reset(Item selectThis){
		selected = head;
		
		while (selected.getItem() != selectThis && selected != null){
			selected = selected.getNext();
		}
	}
	/**
	 * returns the current selected node
	 * increments selected to the next node
	 * @return
	 */
	public Item next(){
		Node returnMe = selected;
		if(selected != null){
			selected = selected.getNext();
			return returnMe.getItem();
		}
		
		return null;
	}
	
	/**
	 * Determines whether there is a selected item
	 * (in other words, returns true,
	 * if an invocation to next would return an item).
	 */
	public boolean hasNext(){
		if(selected == null){
			return false;
		}
		return true;
	}
	
	/**
	 * Tests to see if the list is empty
	 * @return
	 */
	public boolean isEmpty(){
		return head == null;
	}
	

}
