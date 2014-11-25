package Galaga;

import processing.core.*;

public class Boss extends Enemy {

	private PImage hitSprite1, hitSprite2;
	
	public Boss(float x, float y) {
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
		sprite1 = (new PApplet()).loadImage("Sprites/boss.png");
		sprite2 = (new PApplet()).loadImage("Sprites/boss2.png");
		hitSprite1 = (new PApplet()).loadImage("Sprites/boss_hit.png");
		hitSprite2 = (new PApplet()).loadImage("Sprites/boss2_hit.png");
	}

}
