package com.platinum.graphics;


import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;


public class Boat {
	private PImage boat = new PImage();
	public PVector pos = new PVector(0, 0);
	public PVector offset = new PVector(0, 0);
	private PGraphics pgboat;
	public PVector size;
	private PApplet that;
	private float scale;
	
	
	public Boat(float xPos, float yPos, float scale, String img, PApplet g){
		this.that = g;
		boat = that.loadImage(img);
		
		this.pos.x = xPos * Display.res.x;
		this.pos.y = yPos * Display.res.y;
		this.offset.x = 0;
		this.offset.y = 0;
		this.scale = scale;
		
		pgboat = that.createGraphics((int) (boat.width * scale), (int) (boat.height * this.scale), memry.P2D);
		pgboat.beginDraw();
		pgboat.scale(scale);
		pgboat.image(boat, 0, 0);
		pgboat.endDraw();
		
		this.size = new PVector(pgboat.width, pgboat.height);
		
	}
	
	public void drawBoat(){
		that.image(pgboat, pos.x + offset.x, pos.y + offset.y);
		
	}

	
}
