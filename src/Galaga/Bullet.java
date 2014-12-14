package Galaga;

import processing.core.*;

/**
 * Defines a bullet
 * 
 * @author Christopher Glasz
 */
public abstract class Bullet implements ApplicationConstants {

	/**
	 * X velocity of the bullet
	 */
	protected float vx, vy;

	/**
	 * Coordinates of the bullet
	 */
	protected float x, y;
	
	protected float theta;

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
		this.theta = 0;
		this.destroyed = false;
		createSprite();
	}

	/**
	 * Update position of bullet
	 * 
	 * @param elapsed
	 *            time since last draw
	 */
	public void update(float elapsed) {
		y += vy * elapsed * 0.001;
		x += vx * elapsed * 0.001;
		
		if (y > WORLD_HEIGHT || y < 0)
			destroy();
	}

	/**
	 * Draws the bullet to the passed PApplet
	 * 
	 * @param g
	 *            PApplet to draw to
	 */
	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.scale(PIXEL_WIDTH);
		g.rotate(theta);
		g.noSmooth();
		g.imageMode(PConstants.CENTER);

		g.image(sprite, 0, 0);

		g.popMatrix();
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

	/**
	 * Loads the sprite for the bullet
	 */
	protected abstract void createSprite();
}
