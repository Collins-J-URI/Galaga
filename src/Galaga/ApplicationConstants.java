package Galaga;

/**
 * Interface to hold all the constants for the game
 * 
 * @author Christopher Glasz
 */
public interface ApplicationConstants {

	/**
	 * Dimensions of the window
	 */
	int WINDOW_WIDTH = 800;

	/**
	 * Dimensions of the window
	 */
	int WINDOW_HEIGHT = 680;

	/**
	 * Width of the world
	 */
	float WORLD_WIDTH = 1;

	/**
	 * Pixel to world scaling coefficient
	 */
	float P2W = WORLD_WIDTH / (float) WINDOW_WIDTH;

	/**
	 * World to pixel scaling coefficient
	 */
	float W2P = 1.0f / P2W;

	/**
	 * Height of the world
	 */
	float WORLD_HEIGHT = P2W * WINDOW_HEIGHT;

	/**
	 * Width of one game pixel
	 */
	float PIXEL_WIDTH = WORLD_HEIGHT / 255.f;

	/**
	 * Time it takes to strafe from one side of the screen to the other
	 */
	float CROSS_WORLD_TIME = 2;

	/**
	 * Speed at which the fighter strafes
	 */
	float STRAFE_SPEED = WORLD_WIDTH / CROSS_WORLD_TIME;

	/**
	 * Time it takes a bullet to travel from the top of the screen to the bottom
	 */
	float DOWN_WORLD_TIME = 1;

	/**
	 * Speed of the bullets
	 */
	float BULLET_SPEED = WORLD_HEIGHT / DOWN_WORLD_TIME;

	/**
	 * Time it takes an entity to explode
	 */
	float EXPLOSION_TIME = 0.5f;

	/**
	 * Length of a frame of an explosion in seconds
	 */
	float EXPLOSION_FRAME = EXPLOSION_TIME / 5;

	/**
	 * Time it takes to complete one animation loop
	 */
	float LOOP_TIME = 0.7f;

	/**
	 * Length of a frame of an explosion in seconds
	 */
	float ANIMATION_FRAME = LOOP_TIME / 2;
}
