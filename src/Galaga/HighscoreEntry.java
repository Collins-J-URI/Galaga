package Galaga;

public class HighscoreEntry {
	
	private String _name;
	private int _score;
	
	public HighscoreEntry() {
		// TODO Auto-generated constructor stub
	}
	public HighscoreEntry(String name, int score){
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
