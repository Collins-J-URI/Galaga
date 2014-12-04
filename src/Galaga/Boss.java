package Galaga;

import processing.core.*;

/**
 * Defines a Boss Galaga enemy
 * 
 * @author Christopher Glasz
 */
public class Boss extends Enemy {

	/**
	 * Sprites for when we have been hit
	 */
	private PImage hitSprite1, hitSprite2;

	/**
	 * True if we havebeen hit once
	 */
	private boolean hitOnce;

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Boss(float x, float y) {
		super(x, y);
		hitOnce = false;
		formationScore = 130;
		attackingScore = 400;
	}

	/**
	 * Detects if there is a collision between the enemy and the passed bullet
	 * 
	 * @param bullet
	 *            the bullet to check against
	 */
	public void detectCollision(Bullet bullet) {
		float bx = bullet.getX();
		float by = bullet.getY();

		float dist2 = (bx - x) * (bx - x) + (by - y) * (by - y);
		if (dist2 < r * r) {
			hit();
			bullet.destroy();
		}
	}

	/**
	 * Hits the fighter
	 */
	public void hit() {
		if (!hitOnce) {
			hitOnce = true;
			sprite1 = hitSprite1;
			sprite2 = hitSprite2;
		} else {
			animationState = AnimationState.EXP_1;
			hit = true;
		}
	}

	/**
	 * Returns a clone of the Boss
	 * 
	 * @return a clone of the Boss
	 */
	public Enemy clone() {
		Boss temp = new Boss(x, y);
		if (hitOnce)
			temp.hit();
		if (hit)
			temp.hit();
		if (destroyed)
			temp.destroy();
		return temp;
	}

	/**
	 * Loads the sprites
	 */
	protected void createSprite() {
		super.createSprite();
		sprite1 = (new PApplet()).loadImage("Sprites/boss.png");
		sprite2 = (new PApplet()).loadImage("Sprites/boss2.png");
		hitSprite1 = (new PApplet()).loadImage("Sprites/boss_hit.png");
		hitSprite2 = (new PApplet()).loadImage("Sprites/boss2_hit.png");
	}

}
