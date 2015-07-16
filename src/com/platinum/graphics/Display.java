package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PVector;
import processing.core.PApplet;

public class Display {
	
	public static PVector res;
	public static PVector ratio;
	
	public Display(PApplet g){
		res = new PVector(g.displayWidth, g.displayHeight);		
	}
	
	public Display(int x, int y){
		res = new PVector(x, y);
		
	}
	
	public void size(PApplet g){
		g.size((int) res.x, (int) res.y, memry.P2D);
		ratio = new PVector(res.x/1920, res.y/1080);
	}

}
