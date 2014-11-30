package Galaga;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

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
	private GameState gameState;
	private AOption[] menuOptions;
	private AOption[] GameOverOptions;
	public void setup() {
		size(WINDOW_WIDTH, WINDOW_HEIGHT);
		fighter = Fighter.instance();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		
		for (int i = 0; i < 4; i++)
			enemies.add(new Boss(-WORLD_WIDTH / 2 + 7*WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.95f));
		
		for (int i = 0; i < 8; i++)
			enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3*WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.875f));
		for (int i = 0; i < 8; i++)
			enemies.add(new Butterfly(-WORLD_WIDTH / 2 + 3*WORLD_WIDTH / 20 + i
					* WORLD_WIDTH / 10, WORLD_HEIGHT * 0.8f));
		
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
		
		//set to default gamestate
		//main menu
		gameState = GameState.MENU;
		menuOptions = new AOption[3];
		
		menuOptions[1] = new AOption("Play",Color.white,0,0);
		menuOptions[2] = new AOption("Quit",Color.white,0,0);
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
		for (Bullet b : bullets)
			b.update(elapsed);

		for (Enemy e : enemies)
			e.update(elapsed);

		for (Enemy e : enemies)
			for (Bullet b : bullets)
				e.detectCollision(b);
	}

	/**
	 * Remove destroyed enemies, bullets, and handle destroyed fighter
	 */
	public void purge() {
		
		// Get rid of bullets once they're outside the window
		Iterator<Bullet> bit = bullets.iterator();
		while (bit.hasNext())
			if (bit.next().isDestroyed())
				bit.remove();
		
		// Get rid of bullets once they're outside the window
		Iterator<Enemy> eit = enemies.iterator();
		while (eit.hasNext())
			if (eit.next().isDestroyed())
				eit.remove();

	}

	/**
	 * Render scene
	 */
	public void render() {
		background(0);
		scale(W2P);
		translate(WORLD_WIDTH / 2, WORLD_HEIGHT);
		scale(1, -1);
		
		//render to the screen depending on the GameState;
		System.out.println(gameState);
		//TODO: Create Menu and GameOver
		if(gameState == GameState.MENU){
			
			//draw some stars and space
			drawSpace();
			
			//draw the Title inthe center top of screen.
			pushMatrix();
			String title = "GALAGA";
			translate(0,3*WORLD_HEIGHT/4);
			PFont font = loadFont("Emulogic-36.vlw");
			textFont(font,36);
			textAlign(CENTER);
			scale(P2W,-P2W);
			fill(0,255,0);
			stroke(0);
			text(title,0,0);
			//shift the play button just a bit
			for(int i = 1; i < menuOptions.length; i++){
				menuOptions[i].setY(2*i*36);
				menuOptions[i].draw(this);
			}

			popMatrix();
			
			//draw the menu selections
			//Play or Quit
			
		}else if(gameState == GameState.PLAYING){
			// Draw the stars first
			drawSpace();
			
			//then draw everything else
			fighter.render(this);
			for (Bullet b : bullets)
				b.render(this);
			for (Enemy e : enemies)
				e.render(this);
		}else if(gameState == GameState.GAMEOVER){
			//draw some states and space
			drawSpace();
			
			//draw GameOver
			
			//Ask to play again
		}

	}

	public void drawSpace(){
		stroke(255);
		fill(255);
		g.strokeWeight(2 * P2W);
		for (int i = 0; i < numStars; i++) {
			point(starx[i], WORLD_HEIGHT - stary[i]);
			stary[i] = (stary[i] + starvy[i]) % WORLD_HEIGHT;
		}
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
			case UP:
				if(menuOptions[1].isSelected()){
					menuOptions[2].changeSelected(2);
				}else{
					menuOptions[1].changeSelected(1);
				}
				break;
			case DOWN:
				if(menuOptions[1].isSelected()){
					menuOptions[2].changeSelected(2);
				}else{
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
				if(gameState ==GameState.MENU){
					if(menuOptions[1].isSelected()){
						//we want to play our game so
						gameState = GameState.PLAYING;
					}else{
						//we want to quit
						System.exit(0);
					}
				}else if (gameState == GameState.PLAYING){
					bullets.add(fighter.shoot());
				}
				
				break;
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
