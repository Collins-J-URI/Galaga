package Galaga;

import processing.core.*;

public abstract class Enemy implements ApplicationConstants {

	private boolean destroyed;
	protected float x, y;
	protected float vx, vy;
	protected float r;

	protected PImage sprite1;
	protected PImage sprite2;

	protected AnimationState animationState;
	protected int cycleCount;
	protected int cycleLength;

	public Enemy(float x, float y) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.r = 7 * PIXEL_WIDTH;
		destroyed = false;
		cycleLength = 40;
		cycleCount = (int)(Math.random() * cycleLength);
		animationState = AnimationState.random();
		createSprite();
	}

	public Enemy(float x, float y, float vx, float vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		destroyed = false;
	}

	public void update(float elapsed) {
		if (cycleCount==0) 
			animationState = animationState.getNext();
		
		cycleCount = (cycleCount+1)%cycleLength;
	}

	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.translate(-7.5f, 0);
		g.noSmooth();

		switch (animationState) {
		case up:
			g.image(sprite1, 0, 0);
			break;
		case down:
			g.image(sprite2, 0, 0);
			break;
		default:
			break;
		}

		g.popMatrix();

	}

	public void detectCollision(Bullet bullet) {
		float bx = bullet.getX();
		float by = bullet.getY();

		float dist2 = (bx - x) * (bx - x) + (by - y) * (by - y);
		if (dist2 < r * r) {
			destroy();
			bullet.destroy();
		}
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void destroy() {
		destroyed = true;
	}
	
	/**
	 * Return a bullet shot from the fighter
	 * @return bullet shot from the fighter
	 */
	public Bullet shoot() {
		return new EnemyBullet(x, y, vx);
	}

	public abstract Enemy clone();

	protected abstract void createSprite();
}
