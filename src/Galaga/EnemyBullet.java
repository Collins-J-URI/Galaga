package Galaga;

import processing.core.PApplet;

/**
 * Defines a bullet fired by an enemy
 * 
 * @author Christopher Glasz
 */
public class EnemyBullet extends Bullet {

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            initial x coordinate of the bullet
	 * @param y
	 *            initial y coordinate of the bullet
	 * @param vx
	 *            initial x velocity of the bullet
	 */
	public EnemyBullet(float x, float y, float vx) {
		super(x, y);
		this.vx = vx;
		vy = -BULLET_SPEED;
	}

	/**
	 * Loads the sprite
	 */
	protected void createSprite() {
		sprite = (new PApplet()).loadImage("Sprites/enemy_bullet.png");
	}

}
