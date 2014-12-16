package Galaga;

import Galaga.Enemy.EnemyState;
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
	 * True if the boss has been hit once
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
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param goalX
	 *            the starting destination
	 * @param goalY
	 *            the starting destination
	 */
	public Boss(float x, float y, float goalX, float goalY) {
		super(x, y, goalX, goalY);
		hitOnce = false;
		formationScore = 130;
		attackingScore = 400;
	}

	@Override
	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.rotate(theta);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.noSmooth();
		g.imageMode(PConstants.CENTER);

		switch (animationState) {
		case UP:
			if (!hitOnce)
				g.image(sprite1, 0, 0);
			else
				g.image(hitSprite1, 0, 0);
			break;
		case DOWN:
			if (!hitOnce)
				g.image(sprite2, 0, 0);
			else
				g.image(hitSprite2, 0, 0);
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

	/**
	 * Hits the fighter
	 */
	public void hit() {
		if (!hitOnce) {
			hitOnce = true;
		} else {
			animationState = AnimationState.EXP_1;
			hit = true;
		}
	}

	public void revive() {
		super.revive();
		hitOnce = false;
	}

	@Override
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

	@Override
	protected void createSprite() {
		super.createSprite();
		sprite1 = (new PApplet()).loadImage("Sprites/boss.png");
		sprite2 = (new PApplet()).loadImage("Sprites/boss2.png");
		hitSprite1 = (new PApplet()).loadImage("Sprites/boss_hit.png");
		hitSprite2 = (new PApplet()).loadImage("Sprites/boss2_hit.png");
	}

}
