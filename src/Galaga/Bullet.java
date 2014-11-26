package Galaga;

import processing.core.*;

public abstract class Bullet implements ApplicationConstants {
	
	protected float x, y;
	protected PImage sprite;
	private boolean destroyed;
	
	public Bullet(float x, float y) {
		this.x = x;
		this.y = y;
		this.destroyed =false;
		createSprite();
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	public abstract void update(float elapsed);
	
	public abstract void render(PApplet g);
	
	protected abstract void createSprite();
}
