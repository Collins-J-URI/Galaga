package Galaga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
	 * Array list of enemies yet to be added
	 */
	private static ArrayList<Enemy> onDeckPrototype;

	/**
	 * Array list of enemies yet to be added
	 */
	private static ArrayList<Enemy> onDeck;

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
	private static int scoreDisplay;

	/**
	 * The Highest Score
	 */
	private static int topScore;

	private static NameEntry nameEntry;

	/**
	 * Current Highscores
	 */
	private static HighscoreList highscoreList;

	/**
	 * keeps track of new lives
	 */
	private static int newLifeScore;

	/**
	 * Number of enemies hit
	 */
	private static int hits;

	/**
	 * Timer for the READY game state
	 */
	private Timer readyTimer;

	/**
	 * Timer to control the addition of enemies
	 */
	private Timer nextEnemyTimer;

	/**
	 * Options for menus
	 */
	private Option play, quit, highscore, returnToMenu;

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

		populatePrototype();

		// Array list to hold enemies
		onDeck = new ArrayList<Enemy>(onDeckPrototype);
		enemies = new ArrayList<Enemy>();

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
		play = new Option("Play", new Play());
		quit = new Option("Quit", new Quit());
		highscore = new Option("High Scores", new HighScore());
		returnToMenu = new Option("Return to Menu", new Return());

		// Initialize main menu
		Option[] mainOptions = { play, highscore, quit };
		main = new Menu(mainOptions);

		// Initialize game over menu
		Option[] gameOverOptions = { returnToMenu, quit };
		postgame = new Menu(gameOverOptions);

		// Initializes name entry
		nameEntry = new NameEntry();

		logoSprite = loadImage("Sprites/galaga.png");
		lifeSprite = loadImage("Sprites/fighter.png");

		// Initialize the score
		score = 0;
		scoreDisplay = 0;
		newLifeScore = 0;
		hits = 0;

		// Initialize the HighScores
		try {
			loadScores();
		} catch (IOException e) {

			System.out.println("Error loading highscores from file");
			e.printStackTrace();
		}

		readyTimer = new Timer(this);
		nextEnemyTimer = new Timer(this);
		nextEnemyTimer.start(SPAWN_TIME);

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
		case ASSUMING_POSITIONS:
		case IN_FORMATION:
		case DIVING:
		case READY:
		case GAMEOVER:
			purge();
			gameStateTransition();
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

		case ASSUMING_POSITIONS:

			// Move the player ship
			fighter.update(elapsed);

			// Move the bullets fired by the fighter
			for (Bullet b : fighterBullets)
				b.update(elapsed);

			// Move the bullets fired by the enemies
			for (Bullet b : enemyBullets)
				b.update(elapsed);

			if (!onDeck.isEmpty() && nextEnemyTimer.isDone()) {
				nextEnemyTimer.start(SPAWN_TIME);
				enemies.add(onDeck.remove(0));
			}

			// Move the enemies
			for (Enemy e : enemies)
				e.update(elapsed);

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
			for (Enemy e : enemies) {
				if (e.isHit()) {
					int tempScore = e.getScore();
					score += tempScore;
					newLifeScore += tempScore;
				}
			}

			break;
		case IN_FORMATION:

			// Move the player ship
			fighter.update(elapsed);

			// Move the bullets fired by the fighter
			for (Bullet b : fighterBullets)
				b.update(elapsed);

			// Move the bullets fired by the enemies
			for (Bullet b : enemyBullets)
				b.update(elapsed);

			if (!onDeck.isEmpty() && nextEnemyTimer.isDone()) {
				nextEnemyTimer.start(SPAWN_TIME);
				enemies.add(onDeck.remove(0));
			}

			// Move the enemies
			for (Enemy e : enemies)
				e.update(elapsed);

			// Have enemies fire bullets every once in a while
			for (Enemy e : enemies)
				if (random(1) < 0.05f)
					e.dive();

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
			for (Enemy e : enemies) {
				if (e.isHit()) {
					int tempScore = e.getScore();
					score += tempScore;
					newLifeScore += tempScore;
				}
			}

			break;
		case DIVING:

			// Move the player ship
			fighter.update(elapsed);

			// Move the bullets fired by the fighter
			for (Bullet b : fighterBullets)
				b.update(elapsed);

			// Move the bullets fired by the enemies
			for (Bullet b : enemyBullets)
				b.update(elapsed);

			if (!onDeck.isEmpty() && nextEnemyTimer.isDone()) {
				nextEnemyTimer.start(SPAWN_TIME);
				enemies.add(onDeck.remove(0));
			}

			// Move the enemies
			for (Enemy e : enemies)
				e.update(elapsed);

			// Have enemies fire bullets every once in a while
			for (Enemy e : enemies)
				if (e.getState() == Enemy.EnemyState.DIVE)
					if (random(1) < 0.05f)
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
			for (Enemy e : enemies) {
				if (e.isHit()) {
					int tempScore = e.getScore();
					score += tempScore;
					newLifeScore += tempScore;
				}
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
			for (Enemy e : enemies) {
				if (e.isHit()) {
					int tempScore = e.getScore();
					score += tempScore;
					newLifeScore += tempScore;
				}
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

		// Update the score to be displayed
		if (score != scoreDisplay) {
			scoreDisplay += map(score - scoreDisplay, 0, 400, 1f, 20);
			if (scoreDisplay >= score)
				scoreDisplay = score;
		}

		// add a life if score is reached
		if (newLifeScore != 0 && newLifeScore >= NEW_LIFE_SCORE) {
			newLifeScore -= NEW_LIFE_SCORE;
			fighter.addLife();
		}

		if (onDeck.size() == 0 && enemies.size() == 0) {
			newLevel();
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

	public static void syncFormation(Enemy toSync) {
		for (Enemy e : enemies)
			if (!toSync.equals(e)) {
				toSync.syncFormation(enemies.get(0));
				break;
			}
	}

	/**
	 * Remove destroyed enemies and bullets
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

		// Get rid of enemies if they're destroyed
		Iterator<Enemy> eit = enemies.iterator();
		while (eit.hasNext()) {
			Enemy e = eit.next();
			if (e.isDestroyed()) {
				e.revive();
				eit.remove();
			}
		}
	}

	/**
	 * Handle game state transition
	 */
	public void gameStateTransition() {

		// Game state switching is dependent on what state we're in
		switch (gameState) {
		case PLAYING:
		case ASSUMING_POSITIONS:
			// If all enemies are in formation, switch game state
			if (onDeck.isEmpty()) {
				gameState = GameState.IN_FORMATION;
				for (Enemy e : enemies)
					if (!e.getState().inFormation()) {
						gameState = GameState.ASSUMING_POSITIONS;
						break;
					}
			}

			// If the fighter is destroyed, take a life and reset it
			if (fighter.isDestroyed() && fighter.lives() > 0) {
				gameState = GameState.READY;
				fighter.resetPosition();
				fighter.revive();
				readyTimer.start(READY_TIME);
			}

			// If the fighter is destroyed with no lives left, game over
			else if (fighter.isDestroyed())
				gameState = GameState.GAMEOVER;

			break;

		case IN_FORMATION:
			// If any enemies are diving, switch game state
			for (Enemy e : enemies)
				if (e.getState() == Enemy.EnemyState.DIVE)
					gameState = GameState.DIVING;

			// If the fighter is destroyed, take a life and reset it
			if (fighter.isDestroyed() && fighter.lives() > 0) {
				gameState = GameState.READY;
				fighter.resetPosition();
				fighter.revive();
				readyTimer.start(READY_TIME);
			}

			// If the fighter is destroyed with no lives left, game over
			else if (fighter.isDestroyed())
				gameState = GameState.GAMEOVER;

			break;

		case DIVING:
			// If all enemies are in formation, switch game state
			gameState = GameState.IN_FORMATION;
			for (Enemy e : enemies)
				if (!e.getState().inFormation()) {
					gameState = GameState.DIVING;
					break;
				}

			// If the fighter is destroyed, take a life and reset it
			if (fighter.isDestroyed() && fighter.lives() > 0) {
				gameState = GameState.READY;
				fighter.resetPosition();
				fighter.revive();
				readyTimer.start(READY_TIME);
			}

			// If the fighter is destroyed with no lives left, game over
			else if (fighter.isDestroyed())
				gameState = GameState.GAMEOVER;

			break;

		case READY:
			// Resume play after a short wait
			if (readyTimer.isDone())
				gameState = GameState.PLAYING;

			break;
		default:
			break;
		}

		if (onDeck.size() == 0 && enemies.size() == 0) {
			newLevel();
		}

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
		case ASSUMING_POSITIONS:
		case IN_FORMATION:
		case DIVING:
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

		// Show the highscore name entry stuff
		case ENTER_NAME:
			renderScore();
			renderNameEntry();
			break;

		// Draw the postgame menu
		case POSTGAME_MENU:
			pushMatrix();
			translate(0, WORLD_HEIGHT / 2);
			scale(P2W, -P2W);
			postgame.render(this);
			popMatrix();
			break;

		// draw the HighScores Page
		case HIGHSCORE_LIST:

			pushMatrix();

			translate(0, WORLD_HEIGHT / 1.5f);
			scale(P2W, -P2W);

			textSize(32);
			textAlign(CENTER);
			text("-HIGHSCORES-", 0, 0);

			fill(255, 255, 127);

			// reset to the start of our highscores
			highscoreList.reset();

			// display the Top 3 highscores
			int count = 0;
			while (highscoreList.hasNext() && count < 3) {
				HighscoreEntry current = highscoreList.next();
				translate(0, 2 * textAscent());
				textAlign(RIGHT);
				text(current.getName() + "  ", 0, 0);

				textAlign(LEFT);
				text(current.getScore(), 0, 0);

				count++;
			}
			popMatrix();
			break;
		default:
			break;
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

		// Render the current score if it's higher than the stored high score
		if (score > topScore) {
			if (scoreDisplay > topScore) {
				text(scoreDisplay, 0, 0);
			} else {
				text(topScore, 0, 0);
			}

		} else {
			text(topScore, 0, 0);
		}

		popMatrix();
	}

	/**
	 * Renders that the player has received a new high score.
	 */
	private void renderNameEntry() {
		pushMatrix();
		translate(0, 3 * WORLD_HEIGHT / 4);
		scale(P2W, -P2W);

		int i = frameCount % 32;
		float theta = TWO_PI * i / 32f;

		float r = 127 + 127 * sin(theta);
		float g = 127 + 127 * sin(theta + TWO_PI / 3);
		float b = 127 + 127 * sin(theta + 2 * TWO_PI / 3);

		fill(r, g, b);
		textAlign(CENTER);
		textSize(18);
		text("Enter your initials!", 0, 0);

		translate(0, 2 * textAscent());

		fill(218);
		textAlign(RIGHT);
		text("SCORE    ", 0, 0);
		textAlign(LEFT);
		text("    NAME", 0, 0);

		translate(0, 1.5f * textAscent());
		textAlign(RIGHT);
		text(score + "    ", 0, 0);
		textAlign(LEFT);
		pushMatrix();
		translate(textWidth("    "), 0);
		nameEntry.render(this);
		popMatrix();

		translate(0, 2 * textAscent());

		fill(r, g, b);
		textAlign(CENTER);
		text("Press [ENTER] WHEN FINISHED", 0, 0);
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
		case ASSUMING_POSITIONS:
		case IN_FORMATION:
		case DIVING:
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

		// Go to next game state when any key is pressed
		case GAMEOVER:
			gameState = GameState.RESULTS;
			break;

		// Move to next game state depending on if the player scored highscore
		case RESULTS:
			if (checkScore())
				gameState = GameState.ENTER_NAME;
			else
				gameState = GameState.POSTGAME_MENU;
			break;

		// Control name entry screen
		case ENTER_NAME:
			if (key == CODED) {
				switch (keyCode) {
				case UP:
					nameEntry.cycle(Joystick.UP);
					break;
				case DOWN:
					nameEntry.cycle(Joystick.DOWN);
					break;
				case LEFT:
					nameEntry.cycle(Joystick.LEFT);
					break;
				case RIGHT:
					nameEntry.cycle(Joystick.RIGHT);
					break;
				default:
					break;
				}
			} else {
				switch (key) {
				case ENTER:
					insertHighscore();
					gameState = GameState.POSTGAME_MENU;
					break;
				default:
					nameEntry.setLetter(key);
				}
			}
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

		// Go back to the menu
		case HIGHSCORE_LIST:
			gameState = GameState.MAIN_MENU;

		default:
			break;
		}
	}

	/**
	 * What to be done when the user releases keys
	 */
	public void keyReleased() {

		switch (gameState) {

		// Control the ship
		case PLAYING:
		case ASSUMING_POSITIONS:
		case IN_FORMATION:
		case DIVING:
		case READY:
			if (gameState.playing() && fighter.peek() != Joystick.CENTER) {
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
			break;

		default:
			break;
		}

	}

	/**
	 * Determines if the player has beaten any of the top 3 high scores
	 * 
	 * @return true if it the player beats a high score; false otherwise;
	 */
	private boolean checkScore() {

		highscoreList.reset();

		// Confirm that the player's score is 1 of the top 3
		int count = 0;
		while (highscoreList.hasNext() && count < 3) {
			HighscoreEntry current = highscoreList.next();

			if (score > current.getScore()) {
				return true;
			}

			count++;
			current = highscoreList.next();
		}
		return false;
	}

	/**
	 * Load all the highscores from save file And set the HighestScore for
	 * Display
	 * 
	 * @throws IOException
	 */
	private void loadScores() throws IOException {

		// Create a reader to read in the file
		BufferedReader reader = createReader("data/highscores.txt");
		String temp = null;

		// Create a new list to store the highscores
		highscoreList = new HighscoreList();

		// Read the first line
		temp = reader.readLine();

		// Until End of File, add lines to highscoreList
		while (temp != null) {

			// Split each line using the ',' delimiter
			String[] line = temp.split(",");
			highscoreList.add(line[0], Integer.parseInt(line[1]));

			temp = reader.readLine();
		}

		// Set the HighestScore
		highscoreList.reset();
		topScore = highscoreList.next().getScore();
		reader.close();
	}

	/**
	 * Inserts the player's name and score into the highscore list
	 */
	private void insertHighscore() {

		// First lets capitalize the playerName
		String playerName = nameEntry.getName();
		playerName = playerName.toUpperCase();

		boolean found = false;
		highscoreList.reset();

		while (highscoreList.hasNext() && !found) {
			HighscoreEntry current = highscoreList.next();

			if (score > current.getScore()) {
				found = true;
				highscoreList.reset(current);
				highscoreList.insert(playerName, score);
			}
		}

		saveScores();
	}

	/**
	 * Save the current Highscores to the highscores text file;
	 */
	private void saveScores() {
		PrintWriter writer = createWriter("data/highscores.txt");
		highscoreList.reset();

		while (highscoreList.hasNext()) {

			// Get the score
			HighscoreEntry tempItem = highscoreList.next();

			// Write score to file
			writer.println(tempItem.getName() + "," + tempItem.getScore());
		}

		// Write the rest of the data
		writer.flush();
		writer.close();
	}

	private void newLevel() {
		// Array list to hold enemies
		onDeck = new ArrayList<Enemy>(onDeckPrototype);
		enemies = new ArrayList<Enemy>();

		gameState = GameState.ASSUMING_POSITIONS;
		nextEnemyTimer.start(SPAWN_TIME);
	}

	private void populatePrototype() {

		onDeckPrototype = new ArrayList<Enemy>();

		// Four bosses up top
		for (int i = 0; i < NUM_BOSSES; i++)
			onDeckPrototype.add(new Boss(WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 2,
					(i - NUM_BOSSES / 2) * ENEMY_BUFFER + ENEMY_BUFFER / 2,
					BOSS_Y));

		// Sixteen butterflies in the middle
		for (int i = 0; i < NUM_BUTTERFLIES; i++)
			onDeckPrototype.add(new Butterfly(WORLD_WIDTH / 1.5f,
					WORLD_HEIGHT / 2, (i - NUM_BUTTERFLIES / 2) * ENEMY_BUFFER
							+ ENEMY_BUFFER / 2, BOSS_Y - ENEMY_BUFFER));
		for (int i = 0; i < NUM_BUTTERFLIES; i++)
			onDeckPrototype.add(new Butterfly(WORLD_WIDTH / 1.5f,
					WORLD_HEIGHT / 2, (i - NUM_BUTTERFLIES / 2) * ENEMY_BUFFER
							+ ENEMY_BUFFER / 2, BOSS_Y - 2 * ENEMY_BUFFER));

		// Twenty bees down under
		for (int i = 0; i < NUM_BEES; i++)
			onDeckPrototype.add(new Bee(WORLD_WIDTH, WORLD_HEIGHT / 2,
					(i - NUM_BEES / 2) * ENEMY_BUFFER + ENEMY_BUFFER / 2,
					BOSS_Y - 3 * ENEMY_BUFFER));
		for (int i = 0; i < NUM_BEES; i++)
			onDeckPrototype.add(new Bee(WORLD_WIDTH, WORLD_HEIGHT / 2,
					(i - NUM_BEES / 2) * ENEMY_BUFFER + ENEMY_BUFFER / 2,
					BOSS_Y - 4 * ENEMY_BUFFER));

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
	 * Select action associated with High Scores
	 * 
	 * @author Christopher Glasz
	 */
	private static class HighScore implements SelectAction {
		public void execute() {
			gameState = GameState.HIGHSCORE_LIST;
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
			nameEntry = new NameEntry();

			fighterBullets = new ArrayList<Bullet>();
			enemyBullets = new ArrayList<Bullet>();
			enemies = new ArrayList<Enemy>();

			// Array list to hold enemies
			onDeck = new ArrayList<Enemy>();
			enemies = new ArrayList<Enemy>();

			// Four bosses up top
			for (int i = 0; i < NUM_BOSSES; i++)
				onDeck.add(new Boss(WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 2,
						(i - NUM_BOSSES / 2) * ENEMY_BUFFER + ENEMY_BUFFER / 2,
						BOSS_Y));

			// Sixteen butterflies in the middle
			for (int i = 0; i < NUM_BUTTERFLIES; i++)
				onDeck.add(new Butterfly(WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 2,
						(i - NUM_BUTTERFLIES / 2) * ENEMY_BUFFER + ENEMY_BUFFER
								/ 2, BOSS_Y - ENEMY_BUFFER));
			for (int i = 0; i < NUM_BUTTERFLIES; i++)
				onDeck.add(new Butterfly(WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 2,
						(i - NUM_BUTTERFLIES / 2) * ENEMY_BUFFER + ENEMY_BUFFER
								/ 2, BOSS_Y - 2 * ENEMY_BUFFER));

			// Twenty bees down under
			for (int i = 0; i < NUM_BEES; i++)
				onDeck.add(new Bee(WORLD_WIDTH, WORLD_HEIGHT / 2,
						(i - NUM_BEES / 2) * ENEMY_BUFFER + ENEMY_BUFFER / 2,
						BOSS_Y - 3 * ENEMY_BUFFER));
			for (int i = 0; i < NUM_BEES; i++)
				onDeck.add(new Bee(WORLD_WIDTH, WORLD_HEIGHT / 2,
						(i - NUM_BEES / 2) * ENEMY_BUFFER + ENEMY_BUFFER / 2,
						BOSS_Y - 4 * ENEMY_BUFFER));

			gameState = GameState.MAIN_MENU;
		}
	}

}
