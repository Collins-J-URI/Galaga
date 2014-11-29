package Galaga;

import processing.core.PApplet;

/**
 * Defines a bullet fired by an enemy
 * 
 * @author Christopher Glasz
 */
public class EnemyBullet extends Bullet {

	/**
	 * X velocity of the bullet
	 */
	private float vx;

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
	}

	/**
	 * Update position of bullet
	 * 
	 * @param elapsed
	 *            time since last draw
	 */
	public void update(float elapsed) {
		y -= BULLET_SPEED * elapsed * 0.001;
		x += vx * elapsed * 0.001;
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
		g.translate(-7.5f, 0);
		g.noSmooth();

		g.image(sprite, 0, 0);

		g.popMatrix();
	}

	/**
	 * Loads the sprite
	 */
	protected void createSprite() {
		sprite = (new PApplet()).loadImage("Sprites/enemy_bullet.png");
	}

}
