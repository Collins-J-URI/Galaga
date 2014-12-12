package Galaga;

public class Item {
	
	private String _name;
	private int _score;
	
	public Item() {
		// TODO Auto-generated constructor stub
	}
	public Item(String name, int score){
		_name = name;
		_score = score;
	}
	


	public String getName(){return _name;}
	
	public int getScore(){return _score;}
	
	public void setName(String name){
		_name = name;
	}
	
	public void setScore(int score){
		_score = score;
	}
}
