package Galaga;

import processing.core.PApplet;

public class Timer {
	private PApplet app;
	private float time;
	private float startTime;
	
	public Timer(PApplet app) {
		this.app = app;
		time = 0;
		startTime = 0;
	}
	
	public void start(float time) {
		startTime = app.millis();
		this.time = time;
	}
	
	public boolean isDone() {
		return (app.millis() - startTime) * 0.001 > time;
	}
}
