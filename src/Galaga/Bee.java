package Galaga;

import processing.core.*;

public class Bee extends Enemy {

	public Bee(float x, float y) {
		super(x, y);
		r = 7 * PIXEL_WIDTH;
	}

	@Override
	public void update(float elapsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.translate(-7.5f, 0);
		g.noSmooth();

		g.image(sprite1, 0, 0);

		g.popMatrix();

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
		sprite1 = (new PApplet()).loadImage("Sprites/bee.png");
		sprite2 = (new PApplet()).loadImage("Sprites/bee2.png");
	}

}
