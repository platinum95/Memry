package com.platinum.graphics;

import com.platinum.graphics.display.Display;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Background {

	private PGraphics Background, Sky;
	private PVector pos;
	private boolean move;
	private int offset;
	
	public Background(String backImg, String skyImg, PApplet g){
		this.offset = 0;
		PImage back, sky;
		back = g.loadImage(backImg);
		sky = g.loadImage(skyImg);
		Sky = g.createGraphics((int) (Display.getRes().x),(int) (sky.height * Display.getRatio().y), Display.getRenderType());
		Background = g.createGraphics((int) (Display.getRes().x),(int) ((back.height * Display.getRatio().y) + (Sky.height - 20)), Display.getRenderType());		
		Background.beginDraw();
		Background.image(back, 0, Sky.height - 20, Background.width, Background.height - (Sky.height - 20));
		Background.endDraw();
		Sky.beginDraw();
		Sky.image(sky, 0, 0, Sky.width, Sky.height);
		Sky.endDraw();
		this.pos = new PVector(0, Sky.height - 30);
		
	}

	public void setBackground(PGraphics background) {
		this.Background = background;
	}

	public void setSky(PGraphics sky) {
		this.Sky = sky;
	}

	public PGraphics getBackground() {
		return Background;
	}

	public PGraphics getSky() {
		return Sky;
	}


	public PVector getPos() {
		return pos;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void setMove(boolean in){
		this.move = in;
	}
	
	public boolean getMove(){
		return move;
	}
	
}
