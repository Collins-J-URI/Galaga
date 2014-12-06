package Galaga;

/**
 * Enumeration to describe the different animation state for entities. An
 * entity's animation state defines which sprite will be rendered at the next
 * frame
 * 
 * @author Christopher Glasz
 */
public enum AnimationState {

	/**
	 * Wings up
	 */
	UP {
		@Override
		public AnimationState getNext() {
			return DOWN;
		}

		@Override
		public boolean explosionState() {
			return false;
		}
	},

	/**
	 * Wings down
	 */
	DOWN {
		@Override
		public AnimationState getNext() {
			return UP;
		}

		@Override
		public boolean explosionState() {
			return false;
		}
	},

	/**
	 * Explosion state
	 */
	EXP_1,

	/**
	 * Explosion state
	 */
	EXP_2,

	/**
	 * Explosion state
	 */
	EXP_3,

	/**
	 * Explosion state
	 */
	EXP_4,

	/**
	 * Explosion state
	 */
	EXP_5 {
		@Override
		public AnimationState getNext() {
			return EXP_5;
		}
	};

	/**
	 * Returns the next animation state in the cycle
	 * 
	 * @return the next animation state in the cycle
	 */
	public AnimationState getNext() {
		return values()[(ordinal() + 1) % values().length];
	}

	/**
	 * Returns either UP or DOWN
	 * 
	 * @return either UP or DOWN
	 */
	public static AnimationState random() {
		return values()[(int) (Math.random() * 2)];
	}

	/**
	 * Returns true if the state is an explosion state
	 * 
	 * @return true if the state is an explosion state
	 */
	public boolean explosionState() {
		return true;
	}
}
