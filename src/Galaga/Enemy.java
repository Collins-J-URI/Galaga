package Galaga;

public abstract class Enemy {

	private boolean destroyed;
	private float x, y;
	
	public Enemy() {
		destroyed = false;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public abstract void update();
	public abstract void render();
	public abstract void shoot(Bullet bullet);
	public abstract Enemy clone();
}
