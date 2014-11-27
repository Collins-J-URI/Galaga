package Galaga;

import processing.core.*;

public class Bee extends Enemy {

	public Bee(float x, float y) {
		super(x, y);
		r = 7 * PIXEL_WIDTH;
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

	protected void createSprite() {
		sprite1 = (new PApplet()).loadImage("Sprites/bee.png");
		sprite2 = (new PApplet()).loadImage("Sprites/bee2.png");
	}

}
