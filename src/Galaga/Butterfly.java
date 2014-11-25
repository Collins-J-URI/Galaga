package Galaga;

import processing.core.PApplet;

public class Butterfly extends Enemy {

	public Butterfly(float x, float y) {
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
		sprite1 = (new PApplet()).loadImage("Sprites/butterfly");
		sprite2 = (new PApplet()).loadImage("Sprites/butterfly2");
	}

}
