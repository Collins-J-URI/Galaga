package Galaga;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import processing.core.*;

public class Galaga extends PApplet implements ApplicationConstants {

	private static Fighter fighter;
	private static ArrayList<Enemy> enemies;
	private static ArrayList<Bullet> enemyBullets;
	private static ArrayList<Bullet> fighterBullets;

	private final int numStars = 200;
	private float[] starx;
	private float[] stary;
	private float[] starvy;

	private float lastDrawTime;
	private static GameState gameState;
	private Menu main, gameOver;

	private PImage sprite;

	private static int score;
	private static int scoreDisplay;

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
			starvy[i] = random(BULLET_SPEED / 6, BULLET_SPEED / 4);
		}

		// set to default gamestate
		gameState = GameState.MENU;

		// Different options for the menus
		Option play = new Option("Play", new Play());
		Option quit = new Option("Quit", new Quit());
		Option highscore = new Option("High Scores", new HighScore());
		Option returnToMenu = new Option("Return to Menu", new Return());

		// Initialize main menu
		Option[] mainOptions = { play, highscore, quit };
		main = new Menu(mainOptions);

		// Initialize game over menu
		Option[] gameOverOptions = { returnToMenu, quit };
		gameOver = new Menu(gameOverOptions);

		sprite = loadImage("Sprites/galaga.png");

		// initialize the score
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
		updateSpace(elapsed);

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

		for (Enemy e : enemies)
			if (e.isHit())
				score += e.getScore();
		
		//Update the score to be displayed
		if(score != scoreDisplay){
			scoreDisplay += random(1,2);
			if(scoreDisplay >= score){
				scoreDisplay = score;
			}
		}
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

		// Get rid of bullets once they're outside the window
		Iterator<Enemy> eit = enemies.iterator();
		while (eit.hasNext())
			if (eit.next().isDestroyed())
				eit.remove();

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
		renderSpace();
		noSmooth();

		if (gameState == GameState.MENU) {
			pushMatrix();
			translate(0, 3 * WORLD_HEIGHT / 4);
			pushMatrix();
			scale(PIXEL_WIDTH, -PIXEL_WIDTH);
			imageMode(CENTER);
			image(sprite, 0, 0);
			popMatrix();
			scale(P2W, -P2W);

			translate(0, 200);

			main.render(this);
			popMatrix();

		} else if (gameState == GameState.PLAYING) {

			fighter.render(this);
			for (Bullet b : fighterBullets)
				b.render(this);
			for (Bullet b : enemyBullets)
				b.render(this);
			for (Enemy e : enemies)
				e.render(this);

			pushMatrix();
			translate(-WORLD_WIDTH / 2, WORLD_HEIGHT);
			textAlign(LEFT);

			scale(P2W, -P2W);
			textSize(18);
			translate(0, textAscent() * 1.1f);
			text("SCORE " + scoreDisplay, 0, 0);
			popMatrix();

		} else if (gameState == GameState.GAMEOVER) {
			pushMatrix();
			translate(0, 3 * WORLD_HEIGHT / 4);
			pushMatrix();
			scale(PIXEL_WIDTH, -PIXEL_WIDTH);
			imageMode(CENTER);
			image(sprite, 0, 0);
			popMatrix();
			scale(P2W, -P2W);

			translate(0, 200);

			gameOver.render(this);
			popMatrix();
		}
	}

	/**
	 * Draws stars and space going by
	 * 
	 * @param elapsed
	 *            time elapsed since last update
	 */
	public void updateSpace(float elapsed) {
		for (int i = 0; i < numStars; i++) {
			stary[i] = (stary[i] + starvy[i] * elapsed * 0.001f) % WORLD_HEIGHT;
		}
	}

	/**
	 * Draws stars and space going by
	 */
	public void renderSpace() {
		stroke(255);
		fill(255);
		strokeWeight(2 * P2W);
		noSmooth();
		for (int i = 0; i < numStars; i++) {
			point(starx[i], WORLD_HEIGHT - stary[i]);
		}
	}

	public void keyPressed() {
		if (gameState == GameState.MENU) {
			if (key == CODED) {
				switch (keyCode) {
				case UP:
					main.cycle(Joystick.UP);
					break;
				case DOWN:
					main.cycle(Joystick.DOWN);
					break;
				case LEFT:
					main.cycle(Joystick.LEFT);
					break;
				case RIGHT:
					main.cycle(Joystick.RIGHT);
					break;
				default:
					// do something (or ignore)
					break;
				}
			} else
				switch (key) {
				case ' ':
					main.execute();
					break;
				}

		} else if (gameState == GameState.PLAYING) {
			if (key == CODED) {
				switch (keyCode) {
				case LEFT:
					fighter.push(Joystick.LEFT);
					break;

				case RIGHT:
					fighter.push(Joystick.RIGHT);
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
			if (key == CODED) {
				switch (keyCode) {
				case UP:
					gameOver.cycle(Joystick.UP);
					break;
				case DOWN:
					gameOver.cycle(Joystick.DOWN);
					break;
				case LEFT:
					gameOver.cycle(Joystick.LEFT);
					break;
				case RIGHT:
					gameOver.cycle(Joystick.RIGHT);
					break;
				default:
					// do something (or ignore)
					break;
				}
			} else
				switch (key) {
				case ' ':
					gameOver.execute();
					break;
				}
		}
	}

	public void keyReleased() {
		if (gameState == GameState.PLAYING) {
			if (key == CODED) {
				switch (keyCode) {
				case LEFT:
					fighter.pop(Joystick.LEFT);
					break;

				case RIGHT:
					fighter.pop(Joystick.RIGHT);
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

	/**
	 * Select action associated with Play
	 * 
	 * @author Christopher Glasz
	 */
	private static class Play implements SelectAction {
		public void execute() {
			gameState = GameState.PLAYING;
		}
	}

	/**
	 * Select action associated with Quit
	 * 
	 * @author Christopher Glasz
	 */
	private static class Quit implements SelectAction {
		public void execute() {
			System.exit(0);
		}
	}

	/**
	 * Select action associated with Quit
	 * 
	 * @author Christopher Glasz
	 */
	private static class HighScore implements SelectAction {
		public void execute() {
			System.exit(0);
		}
	}

	/**
	 * Select action associated with Return to Main Menu
	 * 
	 * @author Christopher Glasz
	 */
	private static class Return implements SelectAction {
		public void execute() {

			score = 0;
			Fighter.reset();
			fighter = Fighter.instance();

			fighterBullets = new ArrayList<Bullet>();
			enemyBullets = new ArrayList<Bullet>();
			enemies = new ArrayList<Enemy>();

			for (int i = 0; i < 4; i++)
				enemies.add(new Boss(-WORLD_WIDTH / 2 + 7 * WORLD_WIDTH / 20
						+ i * WORLD_WIDTH / 10, WORLD_HEIGHT * 0.95f));

			for (int i = 0; i < 8; i++)
				enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3 * WORLD_WIDTH
						/ 20 + i * WORLD_WIDTH / 10, WORLD_HEIGHT * 0.875f));
			for (int i = 0; i < 8; i++)
				enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3 * WORLD_WIDTH
						/ 20 + i * WORLD_WIDTH / 10, WORLD_HEIGHT * 0.8f));

			for (int i = 0; i < 10; i++)
				enemies.add(new Bee(-WORLD_WIDTH / 2 + WORLD_WIDTH / 20 + i
						* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.725f));
			for (int i = 0; i < 10; i++)
				enemies.add(new Bee(-WORLD_WIDTH / 2 + WORLD_WIDTH / 20 + i
						* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.65f));
			gameState = GameState.MENU;
		}
	}

}
