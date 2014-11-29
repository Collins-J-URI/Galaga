package Galaga;

public enum AnimationState {
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

	EXP_1, EXP_2, EXP_3, EXP_4, 
	
	EXP_5 {
		@Override
		public AnimationState getNext() {
			return EXP_5;
		}
	};

	public AnimationState getNext() {
		return values()[(ordinal() + 1) % values().length];
	}

	public static AnimationState random() {
		return values()[(int) (Math.random() * 2)];
	}

	public boolean explosionState() {
		return true;
	}
}
