package Galaga;

import processing.core.*;

public class Boss extends Enemy {

	private PImage hitSprite1, hitSprite2;
	
	public Boss(float x, float y) {
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
		sprite1 = (new PApplet()).loadImage("Sprites/boss");
		sprite2 = (new PApplet()).loadImage("Sprites/boss2");
		hitSprite1 = (new PApplet()).loadImage("Sprites/boss_hit");
		hitSprite2 = (new PApplet()).loadImage("Sprites/boss2_hit");
	}

}
