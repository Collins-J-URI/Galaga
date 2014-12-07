package Galaga;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.*;

/**
 * A Processing implementation of GALAGA, the classic 80's arcade game. The
 * objective of GALAGA is to destroy all the enemies on the screen by shooting
 * them from a ship on the bottom. The user can strafe the ship left and right,
 * and shoot bullets directly upward.
 * 
 * @author Christopher Glasz
 */
public class Galaga extends PApplet implements ApplicationConstants {

	/**
	 * Serial version UID to get rid of the warning
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The player's ship
	 */
	private static Fighter fighter;

	/**
	 * Array list of enemies
	 */
	private static ArrayList<Enemy> enemies;

	/**
	 * Array list of bullets shot my enemies
	 */
	private static ArrayList<Bullet> enemyBullets;

	/**
	 * Array list of bullets shot by fighter
	 */
	private static ArrayList<Bullet> fighterBullets;

	/**
	 * Number of stars to be drawn
	 */
	private final int numStars = 200;

	/**
	 * X coordinate of each star
	 */
	private float[] starx;

	/**
	 * Y coordinate of each star
	 */
	private float[] stary;

	/**
	 * Y velocity of each star
	 */
	private float[] starvy;

	/**
	 * Time of the last draw
	 */
	private float lastDrawTime;

	/**
	 * Current game state
	 */
	private static GameState gameState;

	/**
	 * Both menus
	 */
	private Menu main, postgame;

	/**
	 * Galaga logo
	 */
	private PImage logoSprite;

	/**
	 * Fighter sprite
	 */
	PImage lifeSprite;

	/**
	 * Player score
	 */
	private static int score;

	/**
	 * Score being displyed on the screen
	 */
	private static float scoreDisplay;

	/**
	 * Number of enemies hit
	 */
	private static int hits;

	/**
	 * Initializes all fields, including the stars, the array list of enemies,
	 * and the player ship
	 */
	public void setup() {
		size(WINDOW_WIDTH, WINDOW_HEIGHT);

		// Create the player ship
		fighter = Fighter.instance();

		// Somewhere to put bullets
		fighterBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();

		// Array list to hold enemies
		enemies = new ArrayList<Enemy>();

		// Four bosses up top
		for (int i = 0; i < NUM_BOSSES; i++)
			enemies.add(new Boss((i - NUM_BOSSES / 2) * ENEMY_BUFFER
					+ ENEMY_BUFFER / 2, BOSS_Y));

		// Sixteen butterflies in the middle
		for (int i = 0; i < NUM_BUTTERFLIES; i++)
			enemies.add(new Butterfly((i - NUM_BUTTERFLIES / 2) * ENEMY_BUFFER
					+ ENEMY_BUFFER / 2, BOSS_Y - ENEMY_BUFFER));
		for (int i = 0; i < NUM_BUTTERFLIES; i++)
			enemies.add(new Butterfly((i - NUM_BUTTERFLIES / 2) * ENEMY_BUFFER
					+ ENEMY_BUFFER / 2, BOSS_Y - 2 * ENEMY_BUFFER));

		// Twenty bees down under
		for (int i = 0; i < NUM_BEES; i++)
			enemies.add(new Bee((i - NUM_BEES / 2) * ENEMY_BUFFER
					+ ENEMY_BUFFER / 2, BOSS_Y - 3 * ENEMY_BUFFER));
		for (int i = 0; i < NUM_BEES; i++)
			enemies.add(new Bee((i - NUM_BEES / 2) * ENEMY_BUFFER
					+ ENEMY_BUFFER / 2, BOSS_Y - 4 * ENEMY_BUFFER));

		// Instantiate the stars
		starx = new float[numStars];
		stary = new float[numStars];
		starvy = new float[numStars];
		for (int i = 0; i < numStars; i++) {
			starx[i] = random(-WORLD_WIDTH / 2, WORLD_WIDTH / 2);
			stary[i] = random(WORLD_HEIGHT);
			starvy[i] = random(BULLET_SPEED / 16, BULLET_SPEED / 4);
		}

		// set to default gamestate
		gameState = GameState.MAIN_MENU;

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
		postgame = new Menu(gameOverOptions);

		logoSprite = loadImage("Sprites/galaga.png");
		lifeSprite = loadImage("Sprites/fighter.png");

		// Initialize the score
		score = 0;
		scoreDisplay = 0;
		hits = 0;

		// Initialize the draw time
		lastDrawTime = millis();
	}

	/**
	 * Method to be run at each frame
	 */
	public void draw() {

		// Update all positions
		update();

		// Only purge during the necessary game states
		switch (gameState) {
		case PLAYING:
		case READY:
		case GAMEOVER:
			purge();
			break;
		default:
			break;
		}

		// Draw everything to the window
		render();
	}

	/**
	 * Move all objects
	 */
	public void update() {

		// Get the elapsed time
		float drawTime = millis();
		float elapsed = drawTime - lastDrawTime;
		lastDrawTime = drawTime;

		// Update star position
		updateSpace(elapsed);

		switch (gameState) {

		// When playing, we want everything to be updated
		case PLAYING:

			// Move the player ship
			fighter.update(elapsed);

			// Move the bullets fired by the fighter
			for (Bullet b : fighterBullets)
				b.update(elapsed);

			// Move the bullets fired by the enemies
			for (Bullet b : enemyBullets)
				b.update(elapsed);

			// Move the enemies
			for (Enemy e : enemies)
				e.update(elapsed);

			// Have enemies fire bullets every once in a while
			for (Enemy e : enemies)
				if (random(1) < 0.005f)
					enemyBullets.add(e.shoot());

			// Check to see if enemies have been hit
			for (Enemy e : enemies)
				if (!e.isHit())
					for (Bullet b : fighterBullets)
						if (e.detectCollision(b))
							hits++;

			// Check to see if the player has been hit
			for (Bullet b : enemyBullets)
				if (!fighter.isHit())
					fighter.detectCollision(b);

			// Get points for enemies hit
			for (Enemy e : enemies)
				if (e.isHit())
					score += e.getScore();

			// Update the score to be displayed
			if (score != scoreDisplay) {
				scoreDisplay += map(score - scoreDisplay, 0, 400, 0.2f, 20);
				if (scoreDisplay >= score)
					scoreDisplay = score;
			}
			break;

		// When ready, we want everything to be updated, but not for the fighter
		// to be hit
		case READY:

			// Move the bullets fired by the fighter
			for (Bullet b : fighterBullets)
				b.update(elapsed);

			// Move the bullets fired by the enemies
			for (Bullet b : enemyBullets)
				b.update(elapsed);

			// Move the enemies
			for (Enemy e : enemies)
				e.update(elapsed);

			// Check to see if enemies have been hit
			for (Enemy e : enemies)
				if (!e.isHit())
					for (Bullet b : fighterBullets)
						if (e.detectCollision(b))
							hits++;

			// Get points for enemies hit
			for (Enemy e : enemies)
				if (e.isHit())
					score += e.getScore();

			// Update the score to be displayed
			if (score != scoreDisplay) {
				scoreDisplay += map(score - scoreDisplay, 0, 400, 0.2f, 20);
				if (scoreDisplay >= score)
					scoreDisplay = score;
			}
			break;

		// After the player is out of lives, only update enemies and bullets
		case GAMEOVER:
			for (Bullet b : fighterBullets)
				b.update(elapsed);

			for (Bullet b : enemyBullets)
				b.update(elapsed);

			for (Enemy e : enemies)
				e.update(elapsed);
			break;

		default:
			break;
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

		// If the fighter is destroyed, take a life and reset it
		if (fighter.isDestroyed() && fighter.lives() > 0) {
			gameState = GameState.READY;
			fighter.resetPosition();
			fighter.revive();
		}

		// If the fighter is destroyed with no lives left, game over
		else if (fighter.isDestroyed())
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
		noSmooth();

		// Draw stars
		renderSpace();

		switch (gameState) {

		// Draw the Galaga logo and the main menu
		case MAIN_MENU:
			pushMatrix();
			translate(0, 3 * WORLD_HEIGHT / 4);
			pushMatrix();
			scale(PIXEL_WIDTH, -PIXEL_WIDTH);
			imageMode(CENTER);
			image(logoSprite, 0, 0);
			popMatrix();
			scale(P2W, -P2W);

			translate(0, 200);

			main.render(this);

			popMatrix();
			break;

		// Draw all bullets, enemies, the fighter, the score, all that jazz
		case PLAYING:
			pushMatrix();
			fighter.render(this);
			for (Bullet b : fighterBullets)
				b.render(this);
			for (Bullet b : enemyBullets)
				b.render(this);
			for (Enemy e : enemies)
				e.render(this);

			renderScore();
			renderLives();

			popMatrix();
			break;

		// Draw all everything including the "READY" text
		case READY:
			pushMatrix();
			fighter.render(this);
			for (Bullet b : fighterBullets)
				b.render(this);
			for (Bullet b : enemyBullets)
				b.render(this);
			for (Enemy e : enemies)
				e.render(this);

			renderScore();
			renderLives();

			translate(0, WORLD_HEIGHT / 2);
			scale(P2W, -P2W);

			fill(4, 255, 222);
			textSize(18);
			textAlign(CENTER);
			noSmooth();
			translate(0, -textAscent());
			text("READY", 0, 0);

			popMatrix();
			break;

		// Only draw bullets and enemies, as well as 'GAME OVER'
		case GAMEOVER:
			pushMatrix();
			for (Bullet b : fighterBullets)
				b.render(this);
			for (Bullet b : enemyBullets)
				b.render(this);
			for (Enemy e : enemies)
				e.render(this);

			renderScore();

			translate(0, WORLD_HEIGHT / 2);
			scale(P2W, -P2W);

			fill(4, 255, 222);
			textSize(18);
			textAlign(CENTER);
			noSmooth();
			translate(0, -textAscent());
			text("GAME OVER", 0, 0);
			popMatrix();
			break;

		// Draw the player's hit-miss ratio
		case RESULTS:
			renderScore();
			pushMatrix();
			translate(0, WORLD_HEIGHT / 2);
			scale(P2W, -P2W);

			textSize(18);
			textAlign(CENTER);
			noSmooth();

			fill(255, 2, 4);
			translate(0, -textAscent());
			text("-Results-", 0, 0);

			translate(textWidth("-Results-") / 2, 0);

			fill(255, 255, 2);
			translate(0, 2 * textAscent());
			textAlign(RIGHT);
			text("Shots fired", 0, 0);
			textAlign(LEFT);
			text("   " + fighter.fired(), 0, 0);

			translate(0, 2 * textAscent());
			textAlign(RIGHT);
			text("Number of Hits", 0, 0);
			textAlign(LEFT);
			text("   " + hits, 0, 0);

			fill(218);
			translate(0, 2 * textAscent());
			textAlign(RIGHT);
			text("Hit miss ratio", 0, 0);
			textAlign(LEFT);
			if (fighter.fired() > 0) {
				float ratio = (int) ((hits / (float) fighter.fired()) * 1000) / 10.f;
				text("   " + ratio + " %", 0, 0);
			} else
				text("   0 %", 0, 0);

			popMatrix();
			break;

		// Draw the postgame menu
		case POSTGAME_MENU:
			pushMatrix();
			translate(0, WORLD_HEIGHT / 2);
			scale(P2W, -P2W);
			postgame.render(this);
			popMatrix();
			break;
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

	/**
	 * Draws stars and space going by
	 */
	public void renderLives() {
		pushMatrix();
		translate(-WORLD_WIDTH / 2, 0);
		scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		translate(0, -lifeSprite.height);
		imageMode(CORNER);
		for (int i = 0; i < fighter.lives(); i++)
			image(lifeSprite, i * lifeSprite.width + 2 * i, 0);
		popMatrix();
	}

	/**
	 * Draws score and high score
	 */
	public void renderScore() {
		pushMatrix();
		translate(0, WORLD_HEIGHT);
		PFont font = loadFont("Fonts/Emulogic-36.vlw");
		textFont(font);

		pushMatrix();
		translate(-WORLD_WIDTH / 2, 0);
		scale(P2W, -P2W);

		fill(255, 2, 4);
		textSize(18);
		translate(textWidth("999999"), textAscent() * 1.1f);
		textAlign(RIGHT);
		text("SCORE", 0, 0);

		fill(218);
		translate(0, textAscent() * 1.1f);
		text((int) scoreDisplay, 0, 0);
		popMatrix();

		scale(P2W, -P2W);
		fill(255, 2, 4);
		textSize(18);
		translate(0, textAscent() * 1.1f);
		textAlign(CENTER);
		text("HIGH SCORE", 0, 0);

		fill(218);
		translate(0, textAscent() * 1.1f);
		text(20000, 0, 0);

		popMatrix();
	}

	/**
	 * What do be done when the player presses keys
	 */
	public void keyPressed() {
		switch (gameState) {

		// Navigate the menu
		case MAIN_MENU:
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
					break;
				}
			} else {
				switch (key) {
				case ' ':
				case ENTER:
					main.execute();
					break;
				}
			}
			break;

		// Control the ship
		case PLAYING:
			if (key == CODED) {
				switch (keyCode) {
				case LEFT:
					fighter.push(Joystick.LEFT);
					break;

				case RIGHT:
					fighter.push(Joystick.RIGHT);
					break;
				default:
					break;
				}
			} else {
				switch (key) {
				case ' ':
					if (!fighter.isHit() && fighterBullets.size() < 2)
						fighterBullets.add(fighter.shoot());
					break;
				}
			}
			break;

		// Control the ship
		case READY:
			if (key == ENTER)
				gameState = GameState.PLAYING;
			break;

		// Go to next game state when any key is pressed
		case GAMEOVER:
			gameState = GameState.RESULTS;
			break;

		case RESULTS:
			gameState = GameState.POSTGAME_MENU;
			break;

		// Navigate the menu
		case POSTGAME_MENU:
			if (key == CODED) {
				switch (keyCode) {
				case UP:
					postgame.cycle(Joystick.UP);
					break;
				case DOWN:
					postgame.cycle(Joystick.DOWN);
					break;
				case LEFT:
					postgame.cycle(Joystick.LEFT);
					break;
				case RIGHT:
					postgame.cycle(Joystick.RIGHT);
					break;
				default:
					break;
				}
			} else {
				switch (key) {
				case ' ':
				case ENTER:
					postgame.execute();
					break;
				}
			}
			break;
		}
	}

	/**
	 * What to be done when the user releases keys
	 */
	public void keyReleased() {

		// Control the ship
		if (gameState == GameState.PLAYING && fighter.peek() != Joystick.CENTER) {
			switch (keyCode) {
			case LEFT:
				fighter.pop(Joystick.LEFT);
				break;

			case RIGHT:
				fighter.pop(Joystick.RIGHT);
				break;
			default:
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
			hits = 0;
			Fighter.resetInstance();
			fighter = Fighter.instance();

			fighterBullets = new ArrayList<Bullet>();
			enemyBullets = new ArrayList<Bullet>();
			enemies = new ArrayList<Enemy>();

			// Four bosses up top
			for (int i = 0; i < NUM_BOSSES; i++)
				enemies.add(new Boss((i - NUM_BOSSES / 2) * ENEMY_BUFFER
						+ ENEMY_BUFFER / 2, BOSS_Y));

			// Sixteen butterflies in the middle
			for (int i = 0; i < NUM_BUTTERFLIES; i++)
				enemies.add(new Butterfly((i - NUM_BUTTERFLIES / 2)
						* ENEMY_BUFFER + ENEMY_BUFFER / 2, BOSS_Y
						- ENEMY_BUFFER));
			for (int i = 0; i < NUM_BUTTERFLIES; i++)
				enemies.add(new Butterfly((i - NUM_BUTTERFLIES / 2)
						* ENEMY_BUFFER + ENEMY_BUFFER / 2, BOSS_Y - 2
						* ENEMY_BUFFER));

			// Twenty bees down under
			for (int i = 0; i < NUM_BEES; i++)
				enemies.add(new Bee((i - NUM_BEES / 2) * ENEMY_BUFFER
						+ ENEMY_BUFFER / 2, BOSS_Y - 3 * ENEMY_BUFFER));
			for (int i = 0; i < NUM_BEES; i++)
				enemies.add(new Bee((i - NUM_BEES / 2) * ENEMY_BUFFER
						+ ENEMY_BUFFER / 2, BOSS_Y - 4 * ENEMY_BUFFER));

			gameState = GameState.MAIN_MENU;
		}
	}

}
