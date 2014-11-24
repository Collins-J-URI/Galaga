package Galaga;

import java.util.ArrayList;

import processing.core.*;

public class Galaga extends PApplet implements ApplicationConstants {
	
	private Fighter fighter;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	
	private final int numStars = 200;
	private float[] starx;
	private float[] stary;
	private float[] starvy;
	
	private float lastDrawTime;
	
	public void setup() {
		size(WINDOW_WIDTH, WINDOW_HEIGHT);
		fighter = Fighter.instance();
		
		// Instantiate the stars
		starx = new float[numStars];
		stary = new float[numStars];
		starvy = new float[numStars];
		for (int i = 0; i < numStars; i++) {
			starx[i] = random(-WORLD_WIDTH/2, WORLD_WIDTH/2);
			stary[i] = random(WORLD_HEIGHT);
			starvy[i] = random(P2W, 5 * P2W);
		}
		
		lastDrawTime = millis();
	}
	
	public void draw() {
		update();
		render();
	}
	
	public void update() {
		
		float drawTime = millis();
		float elapsed = drawTime - lastDrawTime;
		lastDrawTime = drawTime;
		
		fighter.update(elapsed);
	}
	
	public void render() {
		background(0);
		scale(W2P);
		translate(WORLD_WIDTH / 2, WORLD_HEIGHT);
		scale(1, -1);
		
		// Draw the stars first
		stroke(255);
		fill(255);
		g.strokeWeight(2 * P2W);
		for (int i = 0; i < numStars; i++) {
			point(starx[i],WORLD_HEIGHT - stary[i]);
			stary[i] = (stary[i] + starvy[i]) % WORLD_HEIGHT;
		}
		
		fighter.render(this);
	}

	// TODO: Handle multiple keys being pressed at once
	public void keyPressed() {
		if (key == CODED) {
			switch (keyCode) {
			case LEFT:
				fighter.left();
				break;

			case RIGHT:
				fighter.right();
				break;
			default:
				// do something (or ignore)
				break;
			}
		}
	}
	
	// TODO: Handle multiple keys being pressed at once
	public void keyReleased() {
		if (key == CODED) {
			switch (keyCode) {
			case LEFT:
				fighter.center();
				break;

			case RIGHT:
				fighter.center();
				break;
			default:
				// do something (or ignore)
				break;
			}
		}
	}

}
