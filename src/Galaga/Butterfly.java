package Galaga;

import processing.core.PApplet;

/**
 * Defines a butterfly, the middle section of the Galaga formation
 * 
 * @author Christopher Glasz
 */
public class Butterfly extends Enemy {

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Butterfly(float x, float y) {
		super(x, y);
		formationScore = 80;
		attackingScore = 160;
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
		sprite1 = (new PApplet()).loadImage("Sprites/butterfly.png");
		sprite2 = (new PApplet()).loadImage("Sprites/butterfly2.png");
	}

}
