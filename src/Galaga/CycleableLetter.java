package Galaga;

import processing.core.PApplet;
import processing.core.PConstants;

public class CycleableLetter {

	/**
	 * The letter
	 */
	private char letter;

	/**
	 * Indicates whether the option is selected
	 */
	private boolean selected;

	/**
	 * Constructor initializes the character
	 * 
	 * @param letter
	 *            character
	 */
	public CycleableLetter(char letter) {
		this.letter = letter;
	}

	/**
	 * Accessor method for letter
	 * 
	 * @return display text
	 */
	public char getLetter() {
		return letter;
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

	public void setLetter(char l) {
		if (l >= 'A' && l <= 'Z')
			l -= ('A' - 'a');
		
		if ((l >= 'a' && l <= 'z') || l == ' ' || l == '.') 
			this.letter = l;
	}

	public void cycleLetter(Joystick j) {
		switch (j) {
		case UP:
			if (letter == 'z')
				letter = ' ';
			else if (letter == ' ')
				letter = '.';
			else if (letter == '.')
				letter = 'a';
			else
				letter += 1;
			break;
		case DOWN:
			if (letter == 'a')
				letter = '.';
			else if (letter == '.')
				letter = ' ';
			else if (letter == ' ')
				letter = 'z';
			else
				letter -= 1;
			break;
		default:
			break;
		}
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
		g.text(letter, 0, 0);
	}

}
