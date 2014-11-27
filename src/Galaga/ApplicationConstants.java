package Galaga;

public interface ApplicationConstants {

	int WINDOW_WIDTH = 800;
	int WINDOW_HEIGHT = 800;
	
	float WORLD_WIDTH = 1;
	
	float P2W = WORLD_WIDTH / (float)WINDOW_WIDTH;
	float W2P = 1.0f / P2W;
	
	float WORLD_HEIGHT = P2W * WINDOW_HEIGHT;
	
	float PIXEL_WIDTH = WORLD_HEIGHT/200.f;
	
	// Time it takes to travel from one side of the screen to the other
	float CROSS_WORLD_TIME = 2;
	float STRAFE_SPEED = WORLD_WIDTH / CROSS_WORLD_TIME;
	
	// Time it takes to travel from the top of the screen to the bottom
	float DOWN_WORLD_TIME = 1;
	float BULLET_SPEED = WORLD_HEIGHT / DOWN_WORLD_TIME;
}
