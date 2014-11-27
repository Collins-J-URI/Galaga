package Galaga;

public enum AnimationState {
	UP {
		@Override
		public AnimationState getNext() {
			return DOWN;
		}
	},

	DOWN {
		@Override
		public AnimationState getNext() {
			return UP;
		}
	}, 

	EXP_1, EXP_2, EXP_3, EXP_4,

	EXP_5 {
		@Override
		public AnimationState getNext() {
			return EXP_1;
		}
	};

	public AnimationState getNext() {
		return values()[(ordinal() + 1) % values().length];
	}

	public static AnimationState random() {
		return values()[(int) (Math.random() * 2)];
	}
}
