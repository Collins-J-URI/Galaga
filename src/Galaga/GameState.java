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
	PLAYING {
		@Override
		public boolean playing() {
			return true;
		}
	}, 
	
	ASSUMING_POSITIONS {
		@Override
		public boolean playing() {
			return true;
		}
	},
	
	IN_FORMATION {
		@Override
		public boolean playing() {
			return true;
		}
	},
	
	DIVING {
		@Override
		public boolean playing() {
			return true;
		}
	},
	
	/**
	 * Between lives
	 */
	READY {
		@Override
		public boolean playing() {
			return true;
		}
	}, 
	
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
	POSTGAME_MENU,
	
	/**
	 * HighScore Screen
	 */
	ENTER_NAME,
	
	/**
	 * HighScore Screen
	 */
	HIGHSCORE_LIST;

	/**
	 * Returns the next game state in the cycle
	 * 
	 * @return the next game state in the cycle
	 */
	public GameState getNext() {
		return values()[(ordinal() + 1) % values().length];
	}

	/**
	 * Returns true if the state is during the game cycle
	 * 
	 * @return true if the state is during the game cycle
	 */
	public boolean playing() {
		return false;
	}
}
