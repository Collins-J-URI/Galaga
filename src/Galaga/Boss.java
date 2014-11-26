package Galaga;

import processing.core.*;

public class Boss extends Enemy {

	private PImage hitSprite1, hitSprite2;
	private boolean isHit;

	public Boss(float x, float y) {
		super(x, y);
		r = 8 * PIXEL_WIDTH;
		isHit = false;
	}

	@Override
	public void update(float elapsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.translate(-7.5f, 0);
		g.noSmooth();

		if (isHit)
			g.image(hitSprite2, 0, 0);
		else
			g.image(sprite2, 0, 0);

		g.popMatrix();
	}

	public void detectCollision(Bullet bullet) {
		float bx = bullet.getX();
		float by = bullet.getY();

		float dist2 = (bx - x) * (bx - x) + (by - y) * (by - y);
		if (dist2 < r * r) {
			if (isHit)
				destroy();
			else
				isHit = true;
			bullet.destroy();
		}
	}

	@Override
	public void shoot(Bullet bullet) {
		// TODO Auto-generated method stub

	}

	@Override
	public Enemy clone() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void createSprite() {
		sprite1 = (new PApplet()).loadImage("Sprites/boss.png");
		sprite2 = (new PApplet()).loadImage("Sprites/boss2.png");
		hitSprite1 = (new PApplet()).loadImage("Sprites/boss_hit.png");
		hitSprite2 = (new PApplet()).loadImage("Sprites/boss2_hit.png");
	}

}
