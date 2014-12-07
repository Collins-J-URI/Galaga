package Galaga;

/**
 * Enumeration to describe possible game states
 * 
 * @author Christopher Glasz
 */
public enum GameState {
	
	/**
	 * Main menu
	 */
	MAIN_MENU, 
	
	/**
	 * Playing the game
	 */
	PLAYING, 
	
	/**
	 * Between lives
	 */
	READY, 
	
	/**
	 * Game over
	 */
	GAMEOVER, 
	
	/**
	 * Results screen
	 */
	RESULTS, 
	
	/**
	 * Postgame menu
	 */
	POSTGAME_MENU;
}
