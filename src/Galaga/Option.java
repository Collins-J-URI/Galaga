package Galaga;

import java.awt.Color;

import processing.core.PApplet;

public class Option {

	private String text;
	private SelectAction action;
	private boolean selected;

	public Option(String text) {
		this.text = text;
	}

	public Option(String text, SelectAction action) {
		this.text = text;
		this.action = action;
	}
	
	public String getText() {
		return text;
	}
	
	public void select() {
		selected = true;
	}
	
	public void deselect() {
		selected = false;
	}
	
	public void execute() {
		action.execute();
	}

	public void render(PApplet g) {
		int color = selected ? g.color(255, 0, 0) : g.color(0, 255, 0);
		g.fill(color);
		g.textSize(24);
		g.text(text, 0, 0);
	}

}
