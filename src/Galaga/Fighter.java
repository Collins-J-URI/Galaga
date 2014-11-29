package Galaga;

import java.util.Stack;

import processing.core.*;

/**
 * Defines a fighter. Only one instance of this class can exist at a time.
 * 
 * @author Christopher Glasz
 */
public class Fighter implements ApplicationConstants {

	/**
	 * Instance of the fighter
	 */
	private static Fighter instance;

	/**
	 * Coordinates of the fighter
	 */
	private float x, y;

	/**
	 * Radius of disk collider
	 */
	private float r;

	/**
	 * Current state in the animation cycle
	 */
	private AnimationState animationState;

	/**
	 * Current position in the animation cycle
	 */
	private float cycleCount;

	/**
	 * Length of a single animation cycle in frames
	 */
	private int cycleLength;

	/**
	 * Sprite to draw
	 */
	private PImage sprite;

	/**
	 * Explosion sprites to draw
	 */
	private PImage[] eSprites;

	/**
	 * Joystick position to define which direction the fighter should move
	 */
	private Joystick joystick;
	private Stack<Joystick> commands;

	/**
	 * To keep track of whether the fighter is destroyed
	 */
	private boolean destroyed;

	/**
	 * To keep track of whether the fighter has been hit
	 */
	private boolean hit;

	/**
	 * Fetch the one instance of the fighter. If the instance does not exist,
	 * create it.
	 * 
	 * @return instance of the fighter
	 */
	public static Fighter instance() {
		if (instance == null)
			instance = new Fighter();
		return instance;
	}

	/**
	 * Private constructor ensures that only once instance of the fighter exists
	 */
	private Fighter() {
		x = 0;
		y = WORLD_HEIGHT * 0.1f;
		r = 7 * PIXEL_WIDTH;
		destroyed = false;
		hit = false;
		cycleLength = 5;
		cycleCount = (int) (Math.random() * cycleLength);
		joystick = Joystick.center;
		commands = new Stack<Joystick>();
		commands.push(Joystick.center);
		animationState = AnimationState.random();
		createSprite();
	}

	/**
	 * Update position of fighter according to current position of the joystick
	 * 
	 * @param elapsed
	 *            time since last draw
	 */
	public void update(float elapsed) {

		if (!hit) {
			// Move fighter according joystick position
			switch (commands.peek()) {
			case left:
				if (x > -WORLD_WIDTH / 2 + PIXEL_WIDTH * 10)
					x -= STRAFE_SPEED * elapsed * 0.001f;
				break;
			case right:
				if (x < WORLD_WIDTH / 2 - PIXEL_WIDTH * 10)
					x += STRAFE_SPEED * elapsed * 0.001f;
				break;
			default:
				break;
			}

			// Ensure that the fighter doesn't get past the edge of the screen
			if (x < -WORLD_WIDTH / 2 + PIXEL_WIDTH * 10)
				x = -WORLD_WIDTH / 2 + PIXEL_WIDTH * 10;
			else if (x > WORLD_WIDTH / 2 - PIXEL_WIDTH * 10)
				x = WORLD_WIDTH / 2 - PIXEL_WIDTH * 10;

		} else {
			if (animationState == AnimationState.EXP_5)
				destroy();

			cycleCount += elapsed * 0.001f;
			if (cycleCount > EXPLOSION_FRAME) {
				cycleCount = 0;
				animationState = animationState.getNext();
			}
		}
	}

	/**
	 * Detects if there is a collision between the fighter and the passed bullet
	 * 
	 * @param bullet
	 */
	public void detectCollision(Bullet bullet) {
		float bx = bullet.getX();
		float by = bullet.getY();

		float dist2 = (bx - x) * (bx - x) + (by - y) * (by - y);
		if (dist2 < r * r) {
			hit();
			bullet.destroy();
		}
	}

	/**
	 * Returns true of the fighter is destroyed
	 * 
	 * @return true of the fighter is destroyed
	 */
	public boolean isHit() {
		return hit;
	}

	/**
	 * Destroys the fighter
	 */
	public void hit() {
		animationState = AnimationState.EXP_1;
		cycleCount = 0;
		hit = true;
	}

	/**
	 * Returns true of the fighter is destroyed
	 * 
	 * @return true of the fighter is destroyed
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * Destroys the fighter
	 */
	public void destroy() {
		destroyed = true;
	}

	/**
	 * Set the joystick to the right position
	 */
	public void right() {
		joystick = Joystick.right;
	}

	/**
	 * Set the joystick to the left position
	 */
	public void left() {
		joystick = Joystick.left;
	}

	/**
	 * Set the joystick to the center position
	 */
	public void center() {
		joystick = Joystick.center;
	}

	public void push(Joystick j) {
		if (commands.peek() != j)
			commands.push(j);
	}

	public void pop(Joystick j) {
		if (commands.peek() == j)
			commands.pop();
		else {
			Joystick temp = commands.pop();
			commands.pop();
			commands.push(temp);
		}
	}

	/**
	 * Return a bullet shot from the fighter
	 * 
	 * @return bullet shot from the fighter
	 */
	public Bullet shoot() {
		return new FighterBullet(x, y);
	}

	/**
	 * Loads the sprite
	 */
	private void createSprite() {
		sprite = (new PApplet()).loadImage("Sprites/fighter.png");
		eSprites = new PImage[5];
		eSprites[0] = (new PApplet()).loadImage("Sprites/fighter_explosion_1.png");
		eSprites[1] = (new PApplet()).loadImage("Sprites/fighter_explosion_2.png");
		eSprites[2] = (new PApplet()).loadImage("Sprites/fighter_explosion_3.png");
		eSprites[3] = (new PApplet()).loadImage("Sprites/fighter_explosion_4.png");
		eSprites[4] = (new PApplet()).loadImage("Sprites/fighter_explosion_5.png");
	}

	/**
	 * Draws the fighter to the passed PApplet
	 * 
	 * @param g
	 *            PApplet to draw to
	 */
	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.noSmooth();

		switch (animationState) {
		case EXP_1:
			g.translate(-eSprites[0].width / 2.0f, -eSprites[0].height / 2.0f);
			g.image(eSprites[0], 0, 0);
			break;
		case EXP_2:
			g.translate(-eSprites[0].width / 2.0f, -eSprites[0].height / 2.0f);
			g.image(eSprites[1], 0, 0);
			break;
		case EXP_3:
			g.translate(-eSprites[0].width / 2.0f, -eSprites[0].height / 2.0f);
			g.image(eSprites[2], 0, 0);
			break;
		case EXP_4:
			g.translate(-eSprites[0].width / 2.0f, -eSprites[0].height / 2.0f);
			g.image(eSprites[3], 0, 0);
			break;
		case EXP_5:
			g.translate(-eSprites[0].width / 2.0f, -eSprites[0].height / 2.0f);
			g.image(eSprites[4], 0, 0);
			break;
		default:
			g.translate(-sprite.width / 2.0f, -sprite.height / 2.0f);
			g.image(sprite, 0, 0);
			break;
		}

		g.popMatrix();
	}

	/**
	 * Manually draws fighter to PApplet, rather than using a sprite
	 * 
	 * @param g
	 *            PApplet to draw to
	 */
	public void manualRender(PApplet g) {
		g.translate(x, y);

		g.fill(255);
		g.stroke(255);
		g.strokeWeight(0.5f * P2W);
		g.noStroke();
		g.rectMode(PConstants.CENTER);

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
		g.rectMode(PConstants.CORNER);

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
