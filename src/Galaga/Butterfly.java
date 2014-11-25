package Galaga;

import processing.core.PApplet;

public class Butterfly extends Enemy {

	public Butterfly(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
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
		sprite1 = (new PApplet()).loadImage("Sprites/butterfly.png");
		sprite2 = (new PApplet()).loadImage("Sprites/butterfly2.png");
	}

}
