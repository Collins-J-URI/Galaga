package Galaga;

public enum AnimationState {
	up, down;

	public AnimationState getNext() {
		return values()[(ordinal() + 1) % values().length];
	}

	public static AnimationState random() {
		return values()[(int) (Math.random() * values().length)];
	}
}
