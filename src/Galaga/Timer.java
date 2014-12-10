package Galaga;

import processing.core.PApplet;

/**
 * Defines a timer 
 * 
 * @author Christopher Glasz
 */
public class Timer {
	
	/**
	 * Applet to get the time from
	 */
	private PApplet app;
	
	/**
	 * Time set on the timer
	 */
	private float time;
	
	/**
	 * Time the timer was started at
	 */
	private float startTime;
	
	/**
	 * Constructor initializes variables
	 * @param app Applet to get the time from
	 */
	public Timer(PApplet app) {
		this.app = app;
		time = 0;
		startTime = 0;
	}
	
	/**
	 * Start the timer with the passed in time set
	 * @param time time to run the timer for
	 */
	public void start(float time) {
		startTime = app.millis();
		this.time = time;
	}
	
	/**
	 * Returns true if the time is up
	 * @return true if the time is up
	 */
	public boolean isDone() {
		return (app.millis() - startTime) * 0.001 > time;
	}
}
