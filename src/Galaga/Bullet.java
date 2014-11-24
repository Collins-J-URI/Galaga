package Galaga;

import processing.core.*;

public abstract class Bullet implements ApplicationConstants {
	
	protected float x, y;
	protected PImage sprite;
	
	public Bullet(float x, float y) {
		this.x = x;
		this.y = y;
		createSprite();
	}

	public abstract void update(float elapsed);
	
	public abstract void render(PApplet g);
	
	protected abstract void createSprite();
}
