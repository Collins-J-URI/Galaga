package Galaga;

import processing.core.*;

public abstract class Enemy implements ApplicationConstants {

	private boolean destroyed;
	protected float x, y;
	protected float vx, vy;
	protected float r;

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
	
	public void detectCollision(Bullet bullet) {
		float bx = bullet.getX();
		float by = bullet.getY();
		
		float dist2 = (bx - x) * (bx - x) + (by - y) * (by - y);
		if (dist2 < r * r) {
			destroy();
			bullet.destroy();
		}
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
