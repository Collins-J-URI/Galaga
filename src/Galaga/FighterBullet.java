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
	}

	/**
	 * Update position of bullet
	 * 
	 * @param elapsed
	 *            time since last draw
	 */
	public void update(float elapsed) {
		y += BULLET_SPEED * elapsed * 0.001;
		if (y > WORLD_HEIGHT)
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
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.translate(-7.5f, 0);
		g.noSmooth();

		g.image(sprite, 0, 0);

		g.popMatrix();
	}

	/**
	 * Loads the sprite
	 */
	protected void createSprite() {
		sprite = (new PApplet()).loadImage("Sprites/fighter_bullet.png");
	}

}
