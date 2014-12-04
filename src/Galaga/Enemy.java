package Galaga;

import processing.core.*;

public abstract class Enemy implements ApplicationConstants {

	protected boolean destroyed;
	protected boolean hit;
	protected float x, y;
	protected float vx, vy;
	protected float r;

	protected PImage sprite1;
	protected PImage sprite2;

	/**
	 * Explosion sprites to draw
	 */
	private PImage[] eSprites;

	protected AnimationState animationState;
	protected AttackingState state;
	
	protected float cycleCount;

	public Enemy(float x, float y) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.r = 7 * PIXEL_WIDTH;
		
		state = AttackingState.inFormation;
		destroyed = false;
		cycleCount = (float)Math.random() * ANIMATION_FRAME;
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

		if (!hit) {
			cycleCount += elapsed * 0.001f;
			if (cycleCount > ANIMATION_FRAME) {
				cycleCount = 0;
				animationState = animationState.getNext();
			}
		} else {
			if (animationState == AnimationState.EXP_5)
				destroy();

			cycleCount += elapsed * 0.001f;
			if (cycleCount > EXPLOSION_FRAME) {
				cycleCount = 0;
				animationState = animationState.getNext();
			}
		}
	}

	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.noSmooth();

		switch (animationState) {
		case UP:
			g.image(sprite1, 0, 0);
			break;
		case DOWN:
			g.image(sprite2, 0, 0);
			break;
		case EXP_1:
			g.image(eSprites[0], 0, 0);
			break;
		case EXP_2:
			g.image(eSprites[1], 0, 0);
			break;
		case EXP_3:
			g.image(eSprites[2], 0, 0);
			break;
		case EXP_4:
			g.image(eSprites[3], 0, 0);
			break;
		case EXP_5:
			g.image(eSprites[4], 0, 0);
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
		eSprites = new PImage[5];
		eSprites[0] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_1.png");
		eSprites[1] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_2.png");
		eSprites[2] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_3.png");
		eSprites[3] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_4.png");
		eSprites[4] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_5.png");
	}
	
	/**
	 * 
	 * @return the score of the enemy dying.
	 */
	public abstract int getScore();
	
	protected enum AttackingState {
		diving,inFormation;
	}
}
