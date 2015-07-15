package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Window {
	

	public PVector pos, size;
	private int Shape;
	private Colour c;
	public PGraphics window;
	private PApplet that;
	
	public Window(float xScale, float yScale, int Shape, float xSize, float ySize, Colour C, PApplet g){
		this.pos = new PVector(xScale * Display.res.x, yScale * Display.res.y);
		this.size = new PVector(xSize * Display.res.x, ySize * Display.res.y);
		this.c = C;
		this.Shape = Shape;
		this.that = g;
		
		window = that.createGraphics((int) this.size.x + 60, (int) this.size.y ,memry.P2D);
		window.beginDraw();
		window.stroke(0);
	//	window.fill(C.R, C.G, C.B, C.A);
		window.rect(30, 0, this.size.x, this.size.y-1);
		window.noStroke();
		window.fill(255);
		window.rect(0, 0, 29, this.size.y);
		window.rect(31 + this.size.x, 0, 30, this.size.y);
		window.noFill();
		window.endDraw();
		
		
	}
	
	
	public void drawWindow(){
		that.image(window, this.pos.x, this.pos.y);
		
	}
}
