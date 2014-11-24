package Galaga;

import processing.core.PApplet;

public class EnemyBullet extends Bullet {

	private float vx;
	
	public EnemyBullet(float x, float y, float vx) {
		super(x, y);
		this.vx = vx;
	}

	
	public void update(float elapsed) {
		y -= BULLET_SPEED * elapsed * 0.001;
		x += vx * elapsed * 0.001;
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
		sprite = (new PApplet()).loadImage("enemy_bullet.png");
	}

}
