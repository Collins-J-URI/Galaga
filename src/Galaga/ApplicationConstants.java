package Galaga;

public interface ApplicationConstants {

	int WINDOW_WIDTH = 640;
	int WINDOW_HEIGHT = 480;
	
	float WORLD_WIDTH = 1;
	
	float P2W = WORLD_WIDTH / (float)WINDOW_WIDTH;
	float W2P = 1.0f / P2W;
	
	float WORLD_HEIGHT = P2W * WINDOW_HEIGHT;
	
	float PIXEL_WIDTH = WORLD_HEIGHT/200.f;
	
	float CROSS_WORLD_TIME = 2;
	float STRAFE_SPEED = WORLD_WIDTH / CROSS_WORLD_TIME;
	float BULLET_SPEED = STRAFE_SPEED * 2;
}
