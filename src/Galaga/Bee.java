package Galaga;

import processing.core.*;

/**
 * Defines a Bee, the front line of the Galaga force
 * 
 * @author Christopher Glasz
 */
public class Bee extends Enemy {

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Bee(float x, float y) {
		super(x, y);
		formationScore = 50;
		attackingScore = 100;
	}

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Bee(float x, float y, float goalX, float goalY) {
		super(x, y, goalX, goalY);
		formationScore = 50;
		attackingScore = 100;
	}

	@Override
	public void update(float elapsed) {
		super.update(elapsed);

	}

	@Override
	public Enemy clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void createSprite() {
		super.createSprite();
		sprite1 = (new PApplet()).loadImage("Sprites/bee.png");
		sprite2 = (new PApplet()).loadImage("Sprites/bee2.png");
	}

}
