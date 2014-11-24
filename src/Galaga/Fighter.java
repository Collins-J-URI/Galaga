package Galaga;

import processing.core.*;

public class Fighter implements ApplicationConstants {

	private static Fighter instance;

	private float x, y;
	private float vx;
	private PImage sprite;

	private Joystick joystick;

	public static Fighter instance() {
		if (instance == null)
			instance = new Fighter();
		return instance;
	}

	private Fighter() {
		x = 0;
		y = WORLD_HEIGHT * 0.1f;
		vx = 0;
		joystick = Joystick.center;
	}

	public void update(float elapsed) {
		switch (joystick) {
		case left:
			if (x > -WORLD_WIDTH / 2 + PIXEL_WIDTH * 10)
				x -= FIGHTER_SPEED * elapsed * 0.001;
			break;
		case right:
			if (x < WORLD_WIDTH / 2 - PIXEL_WIDTH * 10)
				x += FIGHTER_SPEED  * elapsed * 0.001;
			break;
		default:
			break;
		}

		if (x < -WORLD_WIDTH / 2 + PIXEL_WIDTH * 10)
			x = -WORLD_WIDTH / 2 + PIXEL_WIDTH * 10;
		else if (x > WORLD_WIDTH / 2 - PIXEL_WIDTH * 10)
			x = WORLD_WIDTH / 2 - PIXEL_WIDTH * 10;
		// x += vx;
	}

	public void right() {
		joystick = Joystick.right;
	}

	public void left() {
		joystick = Joystick.left;
	}

	public void center() {
		joystick = Joystick.center;
	}
	
	public void createSprite(PApplet g) {
		sprite = g.createImage((int)(15 * PIXEL_WIDTH), (int)(15 * PIXEL_WIDTH), g.ARGB);
	}

	public void render(PApplet g) {
		g.translate(x, y);

		g.fill(255);
		g.stroke(255);
		g.strokeWeight(0.5f * P2W);
		g.noStroke();
		g.rectMode(g.CENTER);

		// nose
		g.translate(0, -PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, 3 * PIXEL_WIDTH);
		g.translate(0, -3 * PIXEL_WIDTH);
		g.rect(0, 0, 3 * PIXEL_WIDTH, 3 * PIXEL_WIDTH);

		// cock pit
		g.translate(0, -4 * PIXEL_WIDTH);
		g.rect(0, 0, 5 * PIXEL_WIDTH, 5 * PIXEL_WIDTH);

		// body
		g.translate(0, -PIXEL_WIDTH);
		g.rect(0, 0, 9 * PIXEL_WIDTH, 5 * PIXEL_WIDTH);

		// tail
		g.translate(0, -3 * PIXEL_WIDTH);
		g.rect(0, 0, 7 * PIXEL_WIDTH, PIXEL_WIDTH);
		g.translate(0, -PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, 3 * PIXEL_WIDTH);

		// guns
		g.translate(0, 2 * PIXEL_WIDTH);
		g.rect(0, 0, 15 * PIXEL_WIDTH, PIXEL_WIDTH);
		g.translate(-7 * PIXEL_WIDTH, PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, 9 * PIXEL_WIDTH);
		g.translate(14 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, 9 * PIXEL_WIDTH);
		g.translate(-3 * PIXEL_WIDTH, 2 * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, 7 * PIXEL_WIDTH);
		g.translate(-8 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, 7 * PIXEL_WIDTH);

		// fill in
		g.translate(-2 * PIXEL_WIDTH, -4 * PIXEL_WIDTH);
		g.rect(0, 0, 3 * PIXEL_WIDTH, PIXEL_WIDTH);
		g.translate(0, -PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, PIXEL_WIDTH);
		g.translate(0, 3 * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, PIXEL_WIDTH);
		g.translate(12 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, PIXEL_WIDTH);
		g.translate(0, -3 * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, PIXEL_WIDTH);
		g.translate(0, PIXEL_WIDTH);
		g.rect(0, 0, 3 * PIXEL_WIDTH, PIXEL_WIDTH);

		// red
		g.fill(255, 0, 0);
		g.stroke(255, 0, 0);
		g.rectMode(g.CORNER);

		// tail fins
		g.translate(-4.5f * PIXEL_WIDTH, 1.5f * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -3 * PIXEL_WIDTH);
		g.translate(-4 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, -3 * PIXEL_WIDTH);
		g.translate(-PIXEL_WIDTH, -PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(6 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(-4 * PIXEL_WIDTH, 4 * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(PIXEL_WIDTH, PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);

		// gun tips
		g.translate(3 * PIXEL_WIDTH, 5 * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(-8 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(-3 * PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(14 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);

		// blue bits
		g.fill(50, 50, 255);
		g.stroke(50, 50, 255);
		g.translate(-3 * PIXEL_WIDTH, -3 * PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(-8 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, -2 * PIXEL_WIDTH);
		g.translate(PIXEL_WIDTH, PIXEL_WIDTH);
		g.rect(0, 0, PIXEL_WIDTH, -PIXEL_WIDTH);
		g.translate(6 * PIXEL_WIDTH, 0);
		g.rect(0, 0, PIXEL_WIDTH, -PIXEL_WIDTH);
	}
}
