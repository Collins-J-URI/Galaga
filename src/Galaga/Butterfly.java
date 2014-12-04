package Galaga;

import Galaga.Enemy.AttackingState;
import processing.core.PApplet;

public class Butterfly extends Enemy {

	public Butterfly(float x, float y) {
		super(x, y);
	}

	@Override
	public void update(float elapsed) {
		super.update(elapsed);

	}

	@Override
	public Enemy clone() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void createSprite() {
		super.createSprite();
		sprite1 = (new PApplet()).loadImage("Sprites/butterfly.png");
		sprite2 = (new PApplet()).loadImage("Sprites/butterfly2.png");
	}

	@Override
	public int getScore() {
		if(state == AttackingState.inFormation){
			return 50;
		}else if (state == AttackingState.diving){
			return 100;
		}
		
		return 0;
	}
	

}
