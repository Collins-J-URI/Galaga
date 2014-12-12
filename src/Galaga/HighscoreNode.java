package Galaga;

public class HighscoreNode {
	
	private HighscoreEntry highscore;

	
	private HighscoreNode _next;
	private HighscoreNode _previous;
	
	
	public HighscoreNode(){
		highscore = new HighscoreEntry();
		_next = null;
	}
	
	public HighscoreNode(String name, int score){
		highscore = new HighscoreEntry(name,score);
		_next = null;
	}
	
	public HighscoreNode(String name, int score, HighscoreNode next){
		highscore = new HighscoreEntry(name,score);
		_next = next;
	}
	
	public HighscoreNode(String name, int score, HighscoreNode prev, HighscoreNode next){
		highscore = new HighscoreEntry(name,score);
		_next = next;
		_previous = prev;
	}
	
	public void setItem(HighscoreEntry newHighscore){
		highscore = newHighscore;
	}

	
	public void setNext(HighscoreNode next){
		_next = next;
	}
	
	public void setPrevious(HighscoreNode prev){
		_previous = prev;
	}
	
	public HighscoreEntry getItem(){return highscore;}
	
	public HighscoreNode getNext(){return _next;}
	
	public HighscoreNode getPrevious(){return _previous;}
}
