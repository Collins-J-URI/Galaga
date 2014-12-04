package Galaga;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import processing.core.*;

public class Galaga extends PApplet implements ApplicationConstants {

	private Fighter fighter;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> enemyBullets;
	private ArrayList<Bullet> fighterBullets;

	private final int numStars = 200;
	private float[] starx;
	private float[] stary;
	private float[] starvy;

	private float lastDrawTime;
	private GameState gameState;
	private AOption[] menuOptions;
	private AOption[] GameOverOptions;

	private int score;
	public void setup() {
		size(WINDOW_WIDTH, WINDOW_HEIGHT);
		fighter = Fighter.instance();
		fighterBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();

		for (int i = 0; i < 4; i++)
			enemies.add(new Boss(-WORLD_WIDTH / 2 + 7 * WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.95f));

		for (int i = 0; i < 8; i++)
			enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3 * WORLD_WIDTH / 20
					+ i * WORLD_WIDTH / 10, WORLD_HEIGHT * 0.875f));
		for (int i = 0; i < 8; i++)
			enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3 * WORLD_WIDTH / 20
					+ i * WORLD_WIDTH / 10, WORLD_HEIGHT * 0.8f));

		for (int i = 0; i < 10; i++)
			enemies.add(new Bee(-WORLD_WIDTH / 2 + WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.725f));
		for (int i = 0; i < 10; i++)
			enemies.add(new Bee(-WORLD_WIDTH / 2 + WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.65f));

		// Instantiate the stars
		starx = new float[numStars];
		stary = new float[numStars];
		starvy = new float[numStars];
		for (int i = 0; i < numStars; i++) {
			starx[i] = random(-WORLD_WIDTH / 2, WORLD_WIDTH / 2);
			stary[i] = random(WORLD_HEIGHT);
			starvy[i] = random(P2W, 5 * P2W);
		}

		// set to default gamestate
		// main menu
		gameState = GameState.MENU;
		menuOptions = new AOption[3];

		menuOptions[1] = new AOption("Play", Color.white, 0, 0);
		menuOptions[2] = new AOption("Quit", Color.white, 0, 0);
		
		//set the initial score to 0
		score = 0;
		lastDrawTime = millis();
	}

	public void draw() {
		update();
		purge();
		render();
	}

	/**
	 * Move all objects
	 */
	public void update() {
		float drawTime = millis();
		float elapsed = drawTime - lastDrawTime;
		lastDrawTime = drawTime;
		
		fighter.update(elapsed);

		for (Bullet b : fighterBullets)
			b.update(elapsed);

		for (Bullet b : enemyBullets)
			b.update(elapsed);

		for (Enemy e : enemies)
			e.update(elapsed);

		for (Enemy e : enemies)
			if (random(1) < 0.001f)
				enemyBullets.add(e.shoot());

		for (Enemy e : enemies)
			if (!e.isHit())
				for (Bullet b : fighterBullets)
					e.detectCollision(b);


		for (Bullet b : enemyBullets)
			if (!fighter.isHit())
				fighter.detectCollision(b);
	}

	/**
	 * Remove destroyed enemies, bullets, and handle destroyed fighter
	 */
	public void purge() {

		// Get rid of bullets once they're outside the window
		Iterator<Bullet> bit = fighterBullets.iterator();
		while (bit.hasNext())
			if (bit.next().isDestroyed())
				bit.remove();

		// Get rid of bullets once they're outside the window
		bit = enemyBullets.iterator();
		while (bit.hasNext())
			if (bit.next().isDestroyed())
				bit.remove();

		// Get rid of enemies once they're destroyed
		Iterator<Enemy> eit = enemies.iterator();
		while (eit.hasNext()){
			Enemy temp = eit.next();
			if (temp.isDestroyed()){
				score += temp.getScore();
				System.out.println("SCORE: " + score + temp.getClass());
				eit.remove();
			}
		}
		if (fighter.isDestroyed())
			gameState = GameState.GAMEOVER;

	}

	/**
	 * Render scene
	 */
	public void render() {
		background(0);
		scale(W2P);
		translate(WORLD_WIDTH / 2, WORLD_HEIGHT);
		scale(1, -1);

		// TODO: Create Menu and GameOver
		if (gameState == GameState.MENU) {
			drawSpace();

			pushMatrix();
			String title = "GALAGA";
			translate(0, 3 * WORLD_HEIGHT / 4);
			PFont font = loadFont("Fonts/Emulogic-36.vlw");
			textFont(font, 36);
			textAlign(CENTER);
			scale(P2W, -P2W);
			fill(0, 255, 0);
			stroke(0);
			text(title, 0, 0);
			// shift the play button just a bit
			for (int i = 1; i < menuOptions.length; i++) {
				menuOptions[i].setY(2 * i * 36);
				menuOptions[i].draw(this);
			}

			popMatrix();

		} else if (gameState == GameState.PLAYING) {
			drawSpace();

			fighter.render(this);
			for (Bullet b : fighterBullets)
				b.render(this);
			for (Bullet b : enemyBullets)
				b.render(this);
			for (Enemy e : enemies)
				e.render(this);

		} else if (gameState == GameState.GAMEOVER) {
			drawSpace();

			pushMatrix();
			String title = "GAME OVER";
			translate(0, WORLD_HEIGHT / 2);
			PFont font = loadFont("Fonts/Emulogic-36.vlw");
			textFont(font, 36);
			textAlign(CENTER);
			scale(P2W, -P2W);
			fill(255, 0, 0);
			stroke(0);
			text(title, 0, 0);

			popMatrix();
		}
	}

	public void drawSpace() {
		stroke(255);
		fill(255);
		g.strokeWeight(2 * P2W);
		for (int i = 0; i < numStars; i++) {
			point(starx[i], WORLD_HEIGHT - stary[i]);
			stary[i] = (stary[i] + starvy[i]) % WORLD_HEIGHT;
		}
	}
	
	public void reset() {
		score = 0;
		Fighter.reset();
		fighter = Fighter.instance();
		
		fighterBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();

		for (int i = 0; i < 4; i++)
			enemies.add(new Boss(-WORLD_WIDTH / 2 + 7 * WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.95f));

		for (int i = 0; i < 8; i++)
			enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3 * WORLD_WIDTH / 20
					+ i * WORLD_WIDTH / 10, WORLD_HEIGHT * 0.875f));
		for (int i = 0; i < 8; i++)
			enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3 * WORLD_WIDTH / 20
					+ i * WORLD_WIDTH / 10, WORLD_HEIGHT * 0.8f));

		for (int i = 0; i < 10; i++)
			enemies.add(new Bee(-WORLD_WIDTH / 2 + WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.725f));
		for (int i = 0; i < 10; i++)
			enemies.add(new Bee(-WORLD_WIDTH / 2 + WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.65f));
		gameState = GameState.MENU;
		
	}

	public void keyPressed() {
		if (gameState == GameState.MENU) {
			if (key == CODED) {
				switch (keyCode) {
				case UP:
					if (menuOptions[1].isSelected()) {
						menuOptions[2].changeSelected(2);
					} else {
						menuOptions[1].changeSelected(1);
					}
					break;
				case DOWN:
					if (menuOptions[1].isSelected()) {
						menuOptions[2].changeSelected(2);
					} else {
						menuOptions[1].changeSelected(1);
					}
					break;
				default:
					// do something (or ignore)
					break;
				}
			} else
				switch (key) {
				case ' ':
					if (menuOptions[1].isSelected())
						gameState = GameState.PLAYING;
					else
						System.exit(0);
					break;
				}

		} else if (gameState == GameState.PLAYING) {
			if (key == CODED) {
				switch (keyCode) {
				case LEFT:
					fighter.push(Joystick.left);
					break;

				case RIGHT:
					fighter.push(Joystick.right);
					break;
				default:
					// do something (or ignore)
					break;
				}
			} else
				switch (key) {
				case ' ':
					if (!fighter.isHit() && fighterBullets.size() < 2)
						fighterBullets.add(fighter.shoot());
					break;
				}
		} else if (gameState == GameState.GAMEOVER) {
			switch (key) {
			case ' ':
				reset();
				break;
			}
		}
	}

	public void keyReleased() {
		if (gameState == GameState.PLAYING) {
			if (key == CODED) {
				switch (keyCode) {
				case LEFT:
					fighter.pop(Joystick.left);
					break;

				case RIGHT:
					fighter.pop(Joystick.right);
					break;
				default:
					// do something (or ignore)
					break;
				}
			} else
				switch (key) {
				case ' ':
					break;
				}
		}
	}

}
