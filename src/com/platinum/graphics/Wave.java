package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PGraphics;


public class Wave {
	
	private PVector pos;
	public PVector offset, moveOffset;
	private PImage wave = new PImage();
	private float scale, speed;
	private PApplet g;
	private Colour Tint;
	private int numWave, degrees, waveMulti;
	private boolean isTint;
	public boolean move = false, forw;
	private PGraphics pg;
	private int count;
	
	
	
	public Wave(float xPos, float yPos, float scale, float sp, int multi, PApplet g, Colour tint){
		
		this.pos = new PVector(xPos * Display.res.x, yPos * Display.res.y);
		this.scale = scale;
		this.g = g;
		this.speed = sp;
		this.offset = new PVector(0, 0);
		this.moveOffset = new PVector(0, 0);
		this.wave = g.loadImage("res/waves.png");
		this.Tint = tint;
		this.isTint = true;
		this.waveMulti = multi;
		pg = g.createGraphics((int) (Display.res.x +( this.wave.width * this.scale * 0.4f)), this.wave.height, memry.P2D);
		
		pg.beginDraw();
		pg.scale(this.scale);
		pg.pushMatrix();
		pg.translate(-wave.width * 0.2f, 0);	
		pg.image(this.wave, 0, 0);	
		
		while((-(wave.width * this.scale)) + (numWave * wave.width * this.scale) < Display.res.x + (wave.width * this.scale)){
			this.numWave++;
			pg.translate(this.wave.width , 0);
			pg.image(this.wave, 0, 0);	
		}
		
		pg.popMatrix();		
		pg.endDraw();
		
		
		
	}
	
	
	public Wave(float xPos, float yPos, float scale, float sp, int multi, PApplet g){
		this.pos = new PVector(xPos * Display.res.x, yPos * Display.res.y);
		this.scale = scale;
		this.g = g;
		this.speed = sp;
		this.offset = new PVector(0, 0);
		this.wave = g.loadImage("res/waves.png");
		this.isTint = false;
		this.waveMulti = multi;
		this.moveOffset = new PVector(0, 0);
		pg = g.createGraphics((int) (Display.res.x + ( this.wave.width * this.scale * 0.4f) ), this.wave.height, memry.P2D);
		
		pg.beginDraw();
		pg.scale(this.scale);
		pg.image(this.wave, 0, 0);	
		
		//while((-(wave.width * this.scale * .2)) + (numWave * wave.width * this.scale) < Display.res.x + (wave.width * this.scale * .2)){
		while((-(wave.width * this.scale)) + (numWave * wave.width * this.scale) < Display.res.x + (wave.width * this.scale)){
			this.numWave++;
			pg.translate(this.wave.width , 0);
			pg.image(this.wave, 0, 0);	
		}
		
		pg.endDraw();
		
		
		
	}
	
	
	
	public void drawWave(){
			makeCircles();
			if(move){
				moveWave();
			}
			this.offset.x = this.offset.x + this.moveOffset.x;
			this.offset.y = this.offset.y + this.moveOffset.y;
			
			g.pushMatrix();
			g.translate(this.pos.x - (this.wave.width * this.scale * 0.2f), this.pos.y);
			
			if(this.isTint){
				g.tint(this.Tint.R, this.Tint.G, this.Tint.B, this.Tint.A);
			}
			g.image(pg, offset.x, offset.y);
			if(forw){
				g.image(pg, offset.x - pg.width, offset.y);
			}
			if(!forw){
				g.image(pg, offset.x + pg.width, offset.y);
			}
			
			
			
			
			g.noTint();
			g.popMatrix();	
		//}
		
	}
	
	private void makeCircles(){
		degrees = degrees % 360;
		
		if(degrees <= 360 && degrees >= 0){
			if(!move)
				offset.x = (float) (this.waveMulti *  Math.cos(Math.toRadians(speed * (degrees - 180))));
			else
				offset.x = (float) (this.waveMulti * 3 * Math.cos(Math.toRadians(speed * (degrees - 180))));
			 offset.y =  (float) (this.waveMulti * Math.sin(Math.toRadians(speed * (degrees - 180))));
			
		}
		else if(degrees > 180 && degrees < 360){
			 offset.x = (float) (1/(degrees - 180) * 180);
			 
			 offset.y = 0;
			
		}
		degrees++;
		if(degrees > (int) (360 * this.speed))
			degrees = 0;
		return;
		
	}
	
	private void moveWave(){
		if(forw){
			this.moveOffset.x++;
		}
		if(!forw){
			this.moveOffset.x--;
		}
		System.out.println(this.offset.x);
		
		
		count++;
		if(count >= this.pg.width){
			this.moveOffset.x = 0;
			count = 0;
		}
		
		if(count <= -(this.pg.width)){
			this.moveOffset.x = 0;
			count = 0;
		}
		
	}

}
