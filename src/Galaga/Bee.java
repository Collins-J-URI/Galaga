package Galaga;

import processing.core.*;

public class Bee extends Enemy {

	public Bee(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

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
		sprite1 = (new PApplet()).loadImage("Sprites/bee");
		sprite2 = (new PApplet()).loadImage("Sprites/bee2");
	}

}
