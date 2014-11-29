package Galaga;

import processing.core.*;

public class Boss extends Enemy {

	private PImage hitSprite1, hitSprite2;
	private boolean hitOnce;

	public Boss(float x, float y) {
		super(x, y);
		hitOnce = false;
	}

	@Override
	public void update(float elapsed) {
		super.update(elapsed);

	}

	public void detectCollision(Bullet bullet) {
		float bx = bullet.getX();
		float by = bullet.getY();

		float dist2 = (bx - x) * (bx - x) + (by - y) * (by - y);
		if (dist2 < r * r) {
			hit();
			bullet.destroy();
		}
	}

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

	@Override
	public Enemy clone() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void createSprite() {
		super.createSprite();
		sprite1 = (new PApplet()).loadImage("Sprites/boss.png");
		sprite2 = (new PApplet()).loadImage("Sprites/boss2.png");
		hitSprite1 = (new PApplet()).loadImage("Sprites/boss_hit.png");
		hitSprite2 = (new PApplet()).loadImage("Sprites/boss2_hit.png");
	}

}
