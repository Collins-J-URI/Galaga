package Galaga;

public class Spawner {
	private Enemy prototype;
	
	public Spawner(Enemy prototype) {
		this.prototype = prototype;
	}
	
	public Enemy spawn() {
		return prototype.clone();
	}
}
