package Galaga;

import processing.core.PApplet;
import processing.core.PConstants;

public class FighterBullet extends Bullet {
	
	public FighterBullet(float x, float y) {
		super(x, y);
	}

	
	public void update(float elapsed) {
		y += BULLET_SPEED * elapsed * 0.001;
	}

	
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
		sprite = (new PApplet()).loadImage("fighter_bullet.png");
	}

}
