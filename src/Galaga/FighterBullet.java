package Galaga;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Defines a bullet fired by the fighter
 * 
 * @author Christopher Glasz
 */
public class FighterBullet extends Bullet {

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            initial x coordinate of the bullet
	 * @param y
	 *            initial y coordinate of the bullet
	 */
	public FighterBullet(float x, float y) {
		super(x, y);
		vx = 0;
		vy = BULLET_SPEED;
	}

	/**
	 * Loads the sprite
	 */
	protected void createSprite() {
		sprite = (new PApplet()).loadImage("Sprites/fighter_bullet.png");
	}

}
