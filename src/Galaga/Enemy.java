package Galaga;

import processing.core.*;

public abstract class Enemy implements ApplicationConstants {

	private boolean destroyed;
	protected float x, y;
	protected float vx, vy;

	protected PImage sprite1;
	protected PImage sprite2;
	
	public Enemy(float x, float y) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		destroyed = false;
		createSprite();
	}
	
	public Enemy(float x, float y, float vx, float vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		destroyed = false;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public abstract void update(float elapsed);
	public abstract void render(PApplet g);
	public abstract void shoot(Bullet bullet);
	public abstract Enemy clone();
	protected abstract void createSprite();
}
