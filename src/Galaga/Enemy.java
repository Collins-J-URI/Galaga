package Galaga;

import processing.core.*;

public abstract class Enemy implements ApplicationConstants {

	private boolean destroyed;
	protected boolean hit;
	protected float x, y;
	protected float vx, vy;
	protected float r;

	protected PImage sprite1;
	protected PImage sprite2;
	protected PImage eSprite1, eSprite2, eSprite3, eSprite4, eSprite5;

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
		cycleCount = (int) (Math.random() * cycleLength);
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

		if (!animationState.explosionState()) {
			if (cycleCount == 0)
				animationState = animationState.getNext();
			cycleCount = (cycleCount + 1) % cycleLength;
		} else {
			if (cycleCount > cycleLength) {
				if (animationState == AnimationState.EXP_5)
					destroy();
				else
					animationState = animationState.getNext();
				cycleCount = 0;
			}
			cycleCount = (cycleCount + 15);
		}
	}

	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.noSmooth();

		switch (animationState) {
		case UP:
			g.translate(-sprite1.width / 2.0f, -sprite1.height / 2.0f);
			g.image(sprite1, 0, 0);
			break;
		case DOWN:
			g.translate(-sprite2.width / 2.0f, -sprite2.height / 2.0f);
			g.image(sprite2, 0, 0);
			break;
		case EXP_1:
			g.translate(-eSprite1.width / 2.0f, -eSprite1.height / 2.0f);
			g.image(eSprite1, 0, 0);
			break;
		case EXP_2:
			g.translate(-eSprite1.width / 2.0f, -eSprite1.height / 2.0f);
			g.image(eSprite2, 0, 0);
			break;
		case EXP_3:
			g.translate(-eSprite1.width / 2.0f, -eSprite1.height / 2.0f);
			g.image(eSprite3, 0, 0);
			break;
		case EXP_4:
			g.translate(-eSprite1.width / 2.0f, -eSprite1.height / 2.0f);
			g.image(eSprite4, 0, 0);
			break;
		case EXP_5:
			g.translate(-eSprite1.width / 2.0f, -eSprite1.height / 2.0f);
			g.image(eSprite5, 0, 0);
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
			hit();
			bullet.destroy();
		}
	}

	public boolean isHit() {
		return hit;
	}

	public void hit() {
		animationState = AnimationState.EXP_1;
		hit = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void destroy() {
		destroyed = true;
	}

	/**
	 * Return a bullet shot from the fighter
	 * 
	 * @return bullet shot from the fighter
	 */
	public Bullet shoot() {
		return new EnemyBullet(x, y, vx);
	}

	public abstract Enemy clone();

	protected void createSprite() {
		eSprite1 = (new PApplet()).loadImage("Sprites/enemy_explosion_1.png");
		eSprite2 = (new PApplet()).loadImage("Sprites/enemy_explosion_2.png");
		eSprite3 = (new PApplet()).loadImage("Sprites/enemy_explosion_3.png");
		eSprite4 = (new PApplet()).loadImage("Sprites/enemy_explosion_4.png");
		eSprite5 = (new PApplet()).loadImage("Sprites/enemy_explosion_5.png");
	}
}
