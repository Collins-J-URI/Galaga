package Galaga;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import Jama.Matrix;
import processing.core.*;

/**
 * Defines an enemy
 * 
 * @author Christopher Glasz
 */
public abstract class Enemy implements ApplicationConstants {

	/**
	 * Boolean to keep track of whether the enemy is destroyed
	 */
	protected boolean destroyed;

	/**
	 * Boolean to keep track of whether the enemy has been hit
	 */
	protected boolean hit;

	/**
	 * Coordinates in the world
	 */
	protected float x, y;

	protected float theta;

	protected float goalX, goalY;

	boolean goalReached;

	float[][] waypoints;

	private float[][] ax;
	private float[][] ay;
	private float[][] at;
	private float ut;

	/**
	 * X and Y components of velocity
	 */
	protected float vx, vy;

	/**
	 * Radius of disk collider
	 */
	protected float r;

	/**
	 * Sprites to be rendered at each frame
	 */
	protected PImage sprite1, sprite2;

	/**
	 * How much an enemy is worth when destroyed
	 */
	protected int formationScore, attackingScore;

	/**
	 * Explosion sprites to draw
	 */
	private PImage[] eSprites;

	/**
	 * The current state in the animation cycle
	 */
	protected AnimationState animationState;

	/**
	 * The time since the animation state last changed
	 */
	protected float animationTimer;

	/**
	 * The state of the enemy's movement
	 */
	protected EnemyState state;

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Enemy(float x, float y) {
		this.x = x;
		this.y = y;
		this.theta = 0;
		this.goalX = x;
		this.goalY = y;
		this.vx = 0;
		this.vy = 0;
		this.r = 7 * PIXEL_WIDTH;

		state = EnemyState.ASSUME_POSITION;
		destroyed = false;
		goalReached = false;
		animationTimer = (float) Math.random() * ANIMATION_FRAME;
		animationState = AnimationState.random();
		createSprite();
	}

	/**
	 * Constructor initializes variables
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public Enemy(float x, float y, float goalX, float goalY) {
		this.x = x;
		this.y = y;
		this.theta = 0;
		this.goalX = goalX;
		this.goalY = goalY;
		this.vx = 0;
		this.vy = 0;
		this.r = 7 * PIXEL_WIDTH;

		state = EnemyState.ASSUME_POSITION;
		destroyed = false;
		goalReached = false;
		animationTimer = (float) Math.random() * ANIMATION_FRAME;
		animationState = AnimationState.random();
		createSprite();
	}

	/**
	 * Updates the enemy status based on time since last update
	 * 
	 * @param elapsed
	 *            time since last update
	 */
	public void update(float elapsed) {

		if (!hit) {
			animationTimer += elapsed * 0.001f;
			if (animationTimer > ANIMATION_FRAME) {
				animationTimer = 0;
				animationState = animationState.getNext();
			}
		} else {
			if (animationState == AnimationState.EXP_5)
				destroy();

			animationTimer += elapsed * 0.001f;
			if (animationTimer > EXPLOSION_FRAME) {
				animationTimer = 0;
				animationState = animationState.getNext();
			}
		}

		if (!goalReached)
			assume(elapsed);

		x += vx;
		y += vy;

	}

	protected void assume(float elapsed) {
		float dx = goalX - x;
		float dy = goalY - y;
		float phi = PApplet.atan2(dy, dx);
		theta = phi;
		float dist2 = dx * dx + dy * dy;

		// If far away, travel at strafe speed
		if (dist2 > PIXEL_WIDTH) {
			vx = STRAFE_SPEED * PApplet.cos(phi) * elapsed * 0.001f;
			vy = STRAFE_SPEED * PApplet.sin(phi) * elapsed * 0.001f;

			// Slow down upon approach
		} else if (dist2 > PIXEL_WIDTH * 0.01f) {
			float speed = STRAFE_SPEED * (dist2 / PIXEL_WIDTH);
			vx = speed * PApplet.cos(phi) * elapsed * 0.001f;
			vy = speed * PApplet.sin(phi) * elapsed * 0.001f;

			// Arrive
		} else {
			goalReached = true;
			x = goalX;
			y = goalY;
			vx = 0;
			vy = 0;
			theta = -PConstants.PI / 2;
		}
	}

	protected void formation() {

	}

	protected void dive() {

	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Render the enemy to the PApplet
	 * 
	 * @param g
	 *            the PApplet to draw to
	 */
	public void render(PApplet g) {
		g.pushMatrix();
		g.translate(x, y);
		if (theta > 0)
			g.rotate(theta - PConstants.PI / 2);
		g.scale(PIXEL_WIDTH, -PIXEL_WIDTH);
		g.noSmooth();
		g.imageMode(PConstants.CENTER);

		switch (animationState) {
		case UP:
			g.image(sprite1, 0, 0);
			break;
		case DOWN:
			g.image(sprite2, 0, 0);
			break;
		case EXP_1:
			g.image(eSprites[0], 0, 0);
			break;
		case EXP_2:
			g.image(eSprites[1], 0, 0);
			break;
		case EXP_3:
			g.image(eSprites[2], 0, 0);
			break;
		case EXP_4:
			g.image(eSprites[3], 0, 0);
			break;
		case EXP_5:
			g.image(eSprites[4], 0, 0);
			break;
		default:
			break;
		}

		g.popMatrix();

	}

	/**
	 * Detects if the passed in bullet is colliding with the enemy. If it is,
	 * the enemy is hit, the bullet is destroyed, and the method returns true.
	 * Otherwise, it returns false
	 * 
	 * @param bullet
	 *            the bullet to check collision with
	 * @return true if the bullet and enemy are colliding
	 */
	public boolean detectCollision(Bullet bullet) {
		boolean h = false;
		float bx = bullet.getX();
		float by = bullet.getY();

		float dist2 = (bx - x) * (bx - x) + (by - y) * (by - y);
		if (dist2 < r * r) {
			h = true;
			hit();
			bullet.destroy();
		}
		return h;
	}

	/**
	 * Accessor method for hit
	 * 
	 * @return hit
	 */
	public boolean isHit() {
		return hit;
	}

	/**
	 * Hits (but does not destroy) the enemy
	 */
	public void hit() {
		animationState = AnimationState.EXP_1;
		hit = true;
	}

	/**
	 * Accessor method for destroyed
	 * 
	 * @return destroyed
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * Destroys the enemy
	 */
	public void destroy() {
		destroyed = true;
	}

	/**
	 * Return a bullet shot from the fighter
	 * 
	 * @return bullet shot from the fighter
	 */
	public Bullet shoot() {
		return new EnemyBullet(x, y, theta);
	}

	/**
	 * Returns a clone of the enemy
	 * 
	 * @return a clone of the enemy
	 */
	public abstract Enemy clone();

	/**
	 * Loads the sprites to be rendered at each frame
	 */
	protected void createSprite() {
		eSprites = new PImage[5];
		eSprites[0] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_1.png");
		eSprites[1] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_2.png");
		eSprites[2] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_3.png");
		eSprites[3] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_4.png");
		eSprites[4] = (new PApplet())
				.loadImage("Sprites/enemy_explosion_5.png");
	}

	/**
	 * Returns the number of points that the enemy is worth
	 * 
	 * @return the number of points that the enemy is worth
	 */
	public int getScore() {
		int score = 0;
		switch (state) {
		case ASSUME_POSITION:
		case FORMATION:
			score = formationScore;
			break;
		case DIVE:
		case RETURN:
			score = attackingScore;
			break;
		default:
			break;

		}

		// The enemy is not worth anything after being hit once
		formationScore = attackingScore = 0;

		return score;
	}

	public void startPath(EnemyState s) {
		ut = 0;
		switch (s) {
		case ASSUME_POSITION:
			break;
		case DIVE:
			break;
		case FORMATION:
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}

	private void followPath() {
		final int NB_WAY_PTS = waypoints.length;

		// which interval do we fall into?
		for (int i = 1; i < NB_WAY_PTS; i++) {
			if (waypoints[i][3] >= ut) {
				// we fall in the interval [i-1, i]

				float tau = (ut - waypoints[i - 1][3])
						/ (waypoints[i][3] - waypoints[i - 1][3]);
				float tau2 = tau * tau;
				float tau3 = tau2 * tau;

				float x = ax[i - 1][3] * tau3 + ax[i - 1][2] * tau2
						+ ax[i - 1][1] * tau + ax[i - 1][0];
				float y = ay[i - 1][3] * tau3 + ay[i - 1][2] * tau2
						+ ay[i - 1][1] * tau + ay[i - 1][0];
				float angle = at[i - 1][3] * tau3 + at[i - 1][2] * tau2
						+ at[i - 1][1] * tau + at[i - 1][0];

				// compute interpolation factor
				float s = (ut - waypoints[i - 1][3])
						/ (waypoints[i][3] - waypoints[i - 1][3]);

				this.x = x;
				this.y = y;
				this.theta = angle;

				break;
			}
		}
	}

	public void calculateA() {

		final int NB_SEGMENTS = waypoints.length - 1;
		final int NB_WAY_PTS = waypoints.length;

		// ----------------------------------------------------
		// 1. Initialize the TNT matrices of the SLE
		// ----------------------------------------------------
		double[][] mat = new double[4 * NB_SEGMENTS][4 * NB_SEGMENTS];
		double[][] b = new double[4 * NB_SEGMENTS][3];

		// ------------------------------------------------------------
		// initialize the first two and last two rows of the matrix.
		// These correspond to the conditions at the path's endpoints.
		// ------------------------------------------------------------

		// 1 0 0 0 ... 0
		mat[0][0] = 1;

		// Initial location
		b[0][0] = waypoints[0][0]; // x0
		b[0][1] = waypoints[0][1]; // y0
		b[0][2] = waypoints[0][2]; // theta0

		// 0 1 0 0 0 ... 0
		mat[1][1] = 1;

		// Initial speed
		b[1][0] = 1; // vx0
		b[1][1] = 2; // vy0
		b[1][2] = -3; // vtheta0

		// 0 ... 0 0 0 1 1 1 1
		mat[4 * NB_SEGMENTS - 2][4 * NB_SEGMENTS - 4] = 1;
		mat[4 * NB_SEGMENTS - 2][4 * NB_SEGMENTS - 3] = 1;
		mat[4 * NB_SEGMENTS - 2][4 * NB_SEGMENTS - 2] = 1;
		mat[4 * NB_SEGMENTS - 2][4 * NB_SEGMENTS - 1] = 1;

		// End location
		b[4 * NB_SEGMENTS - 2][0] = waypoints[NB_WAY_PTS - 1][0];
		b[4 * NB_SEGMENTS - 2][1] = waypoints[NB_WAY_PTS - 1][1];
		b[4 * NB_SEGMENTS - 2][2] = waypoints[NB_WAY_PTS - 1][2];

		// 0 ... 0 0 0 1 2 3
		mat[4 * NB_SEGMENTS - 1][4 * NB_SEGMENTS - 3] = 1;
		mat[4 * NB_SEGMENTS - 1][4 * NB_SEGMENTS - 2] = 2.0;
		mat[4 * NB_SEGMENTS - 1][4 * NB_SEGMENTS - 1] = 3.0;

		// End speed
		b[4 * NB_SEGMENTS - 1][0] = 0;
		b[4 * NB_SEGMENTS - 1][1] = 0;
		b[4 * NB_SEGMENTS - 1][2] = 0;

		// --------------------------------------------------------------
		// Now fill in the values for the connections at interior points
		// --------------------------------------------------------------

		// 0 ... 0 1 1 1 1 0 0 0 0 0 ... 0 < 4(i - 1) + 2
		// 0 ... 0 0 0 0 0 1 0 0 0 0 ... 0
		// 0 ... 0 0 1 2 3 0 -1 0 0 0 ... 0
		// 0 ... 0 0 0 2 6 0 0 -2 0 0 ... 0
		// ^ 4(i - 1)
		for (int i = 1; i < NB_WAY_PTS - 1; i++) {
			int k = 4 * (i - 1) + 2;
			int l = 4 * (i - 1);

			// 1 1 1 1 0 0 0 0
			mat[k][l] = 1;
			mat[k][l + 1] = 1;
			mat[k][l + 2] = 1;
			mat[k][l + 3] = 1;

			// 0 0 0 0 1 0 0 0
			mat[k + 3][l + 4] = 1;

			// 0 1 2 3 0 -1 0 0
			mat[k + 1][l + 1] = 1.0;
			mat[k + 1][l + 2] = 2.0;
			mat[k + 1][l + 3] = 3.0;
			mat[k + 1][l + 5] = -1.0;

			// 0 0 2 6 0 0 -2 0
			mat[k + 2][l + 2] = 2.0;
			mat[k + 2][l + 3] = 6.0;
			mat[k + 2][l + 6] = -2.0;

			// Location
			b[k][0] = waypoints[i][0];
			b[k][1] = waypoints[i][1];
			b[k][2] = waypoints[i][2];

			// 0
			b[k + 1][0] = 0;
			b[k + 1][1] = 0;
			b[k + 1][2] = 0;

			// 0
			b[k + 2][0] = 0;
			b[k + 2][1] = 0;
			b[k + 2][2] = 0;

			// Location
			b[k + 3][0] = waypoints[i][0];
			b[k + 3][1] = waypoints[i][1];
			b[k + 3][2] = waypoints[i][2];
		}

		// ----------------------------------------------------
		// 2. Solve the SLE
		// ----------------------------------------------------
		Matrix M = new Matrix(mat);
		Matrix B = new Matrix(b);
		Matrix Axyt = M.solve(B);

		// ----------------------------------------------------
		// 3. Copy the results in my instance variables
		// ----------------------------------------------------
		ax = new float[NB_SEGMENTS][4];
		ay = new float[NB_SEGMENTS][4];
		at = new float[NB_SEGMENTS][4];

		for (int i = 0; i < NB_SEGMENTS; i++) {
			for (int j = 0; j < 4; j++) {
				ax[i][j] = (float) Axyt.get(4 * i + j, 0);
				ay[i][j] = (float) Axyt.get(4 * i + j, 1);
				at[i][j] = (float) Axyt.get(4 * i + j, 2);
			}
		}

		ut = 0;

	}

	/**
	 * Enumeration to describe the possible movement states
	 * 
	 * @author Christopher Glasz
	 */
	protected enum EnemyState {
		ASSUME_POSITION, FORMATION, DIVE, RETURN;
	}
}
