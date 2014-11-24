package Galaga;

import processing.core.PApplet;

public abstract class Bullet implements ApplicationConstants {
	
	protected float x, y;
	
	public Bullet(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public abstract void update(float elapsed);
	
	public abstract void render(PApplet g);
}
