package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Window {
	

	public PVector pos, size;
//	private int Shape;
//	private Colour c;
	public PGraphics window, whiteWindow;
	private PApplet that;
	
	public Window(float xScale, float yScale, int Shape, float xSize, float ySize, Colour C, PApplet g){
		this.pos = new PVector(xScale * Display.res.x, yScale * Display.res.y);
		this.size = new PVector(xSize * Display.res.x, ySize * Display.res.y);
		//this.c = C;
		//this.Shape = Shape;
		this.that = g;
		
		window = that.createGraphics((int) this.size.x, (int) this.size.y ,memry.P2D);
		window.beginDraw();
		window.stroke(0);
		window.fill(C.R, C.G, C.B, C.A);
		window.tint(C.R, C.G, C.B, C.A);
		window.rect(0, 0, this.size.x - 2, this.size.y - 1); 
		window.noTint();
		window.noFill();
		window.noStroke();
		window.endDraw();
		
		whiteWindow = that.createGraphics((int) this.size.x, (int) this.size.y ,memry.P2D);
		whiteWindow.beginDraw();
		whiteWindow.stroke(0);
		whiteWindow.fill(255);
		whiteWindow.rect(0, 0, this.size.x -1, this.size.y - 1); 
		whiteWindow.noStroke();
		whiteWindow.endDraw();
		
		
	}
	
	
	public void drawWindow(){
		that.image(window, this.pos.x, this.pos.y);
		
	}
	public void drawWindowWhite(){
		that.image(whiteWindow, this.pos.x, this.pos.y);
		
	}
}
