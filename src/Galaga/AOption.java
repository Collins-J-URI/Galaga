package Galaga;

import java.awt.Color;

import processing.core.PApplet;

public class AOption {
	
	private static int selected = 1;
	private static int curID = 0;
	protected int ID;
	protected String text;
	protected float x;
	protected float y;
	private Color color;
	
	public AOption(String string,Color color, float x, float y){
		text = string;
		this.x = x;
		this.y = y;
		this.color = color;
		ID = ++curID;
	}
	
	public void draw(PApplet theApp){
		theApp.fill(color.getRGB());
		if(isSelected()){
			theApp.fill(Color.RED.getRGB());
		}
		theApp.textSize(24);
		theApp.text(text,x,y);
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public boolean isSelected(){
		return selected == ID;
	}
	
	public void changeSelected(int num){
		selected = num;
	}
}
