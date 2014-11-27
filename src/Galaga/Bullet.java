package Galaga;

import processing.core.*;

/**
 * Defines a bullet
 * 
 * @author Christopher Glasz
 */
public abstract class Bullet implements ApplicationConstants {

	/**
	 * Coordinates of the bullet
	 */
	protected float x, y;

	/**
	 * Sprite to draw
	 */
	protected PImage sprite;

	/**
	 * Boolean to keep track of whether the bullet is destroyed
	 */
	private boolean destroyed;

	/**
	 * COnstructor initializes variables
	 * 
	 * @param x
	 *            initial x-coordinate of the bullet
	 * @param y
	 *            initial y-coordinate of the bullet
	 */
	public Bullet(float x, float y) {
		this.x = x;
		this.y = y;
		this.destroyed = false;
		createSprite();
	}

	/**
	 * Destroy the bullet
	 */
	public void destroy() {
		destroyed = true;
	}

	/**
	 * Returns true if the bullet is destroyed
	 * 
	 * @return true if the bullet is destroyed
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * Accessor method for the x coordinate of the bullet
	 * 
	 * @return the x coordinate of the bullet
	 */
	public float getX() {
		return x;
	}

	/**
	 * Accessor method for the y coordinate of the bullet
	 * 
	 * @return the y coordinate of the bullet
	 */
	public float getY() {
		return y;
	}

	public abstract void update(float elapsed);

	public abstract void render(PApplet g);

	protected abstract void createSprite();
}
