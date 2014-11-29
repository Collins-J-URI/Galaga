package Galaga;

import processing.core.PApplet;

public class Butterfly extends Enemy {

	public Butterfly(float x, float y) {
		super(x, y);
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
		super.createSprite();
		sprite1 = (new PApplet()).loadImage("Sprites/butterfly.png");
		sprite2 = (new PApplet()).loadImage("Sprites/butterfly2.png");
	}

}
