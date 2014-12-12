package Galaga;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Defines an option in a menu
 * 
 * @author Christopher Glasz
 */
public class Option {

	/**
	 * Display text
	 */
	private String text;

	/**
	 * Action associated with this option
	 */
	private SelectAction action;

	/**
	 * Indicates whether the option is selected
	 */
	private boolean selected;

	/**
	 * Constructor initializes the display text and the action associated with
	 * the option
	 * 
	 * @param text
	 *            display text
	 * @param action
	 *            action associated with option
	 */
	public Option(String text, SelectAction action) {
		this.text = text;
		this.action = action;
	}

	/**
	 * Accessor method for display text
	 * 
	 * @return display text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Select the option
	 */
	public void select() {
		selected = true;
	}

	/**
	 * Deselect the option
	 */
	public void deselect() {
		selected = false;
	}

	/**
	 * Execute the action associated with the option
	 */
	public void execute() {
		action.execute();
	}

	/**
	 * Accessor method for the action
	 * 
	 * @return select action
	 */
	public SelectAction getAction() {
		return action;
	}

	/**
	 * Renders the option to the passed in PApplet
	 * 
	 * @param g
	 *            PApplet to draw to
	 */
	public void render(PApplet g) {
		int color = selected ? g.color(0, 255, 0) : g.color(255, 0, 0);
		g.fill(color);
		g.textAlign(PConstants.CENTER);
		g.text(text, 0, 0);
	}

}
