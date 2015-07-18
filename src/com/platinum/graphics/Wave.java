package com.platinum.graphics;

import java.security.SecureRandom;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PGraphics;
import processing.opengl.PShader;

public class Wave {
	private PShader blur2;
	public PVector pos;
	public PVector offset, moveOffset;
	private PImage wave = new PImage();
	private float scale, speed;
	private PApplet g;
	private Colour Tint;
	private int numWave, degrees, waveMulti;
	private boolean isTint;
	public boolean move = false, forw;
	public PGraphics pg, pgBlur;
	private int count, syncInt = 0;
	private SecureRandom rand = new SecureRandom();
	
	
	
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
		this.syncInt = rand.nextInt(180);
		
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
public Wave(float xPos, float yPos, float scale, float sp, int multi, PApplet g, Colour tint, int blur){
		
		this.blur2 = g.loadShader("res/blur.glsl");
		this.pos = new PVector(xPos * Display.res.x, yPos * Display.res.y);
		this.scale = scale;
		this.g = g;
		this.speed = sp;
		this.offset = new PVector(0, 0);
		this.moveOffset = new PVector(0, 0);
		this.wave = g.loadImage("res/waves.png");
		this.syncInt = rand.nextInt(180);
		this.Tint = tint;
		this.isTint = true;
		this.waveMulti = multi;
		
		pg = g.createGraphics((int) (Display.res.x +( this.wave.width * this.scale * 0.4f)), this.wave.height, memry.P2D);
		pgBlur = g.createGraphics((int) (Display.res.x +( this.wave.width * this.scale * 0.4f)), this.wave.height, memry.P2D);
		System.out.println("in wave: being draw");
		pg.beginDraw();
		
		

		pg.scale(this.scale);
		pg.pushMatrix();
		pg.translate(-wave.width * 0.2f, 0);
		
		pg.image(this.wave, 0, 0);	

		
		while((-(wave.width * this.scale)) + (numWave * wave.width * this.scale) < Display.res.x + (wave.width * this.scale)){
			this.numWave++;
			System.out.println("in wave: 6");
			pg.translate(this.wave.width , 0);
			pg.image(this.wave, 0, 0);	
		}
		System.out.println("in wave: 7");
		
		
		System.out.println("in wave: 8");
		
		pg.popMatrix();	
		System.out.println("in wave: 9");
		pg.endDraw();
		pgBlur = blurise(pg);
		System.out.println("in wave: end draw");
		
		
		
		
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
		syncInt = rand.nextInt(180);
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
		update();
		
		g.pushMatrix();
		g.translate(this.pos.x - (this.wave.width * this.scale * 0.2f), this.pos.y);
		
		if(this.isTint){
			g.tint(this.Tint.R, this.Tint.G, this.Tint.B, this.Tint.A);
		}
		g.image(pg, offset.x, offset.y);
		
		
		if(move){
			if(forw){
				g.image(pg, offset.x - pg.width, offset.y);
			}
			if(!forw){
				g.image(pg, offset.x + pg.width, offset.y);
			}
		}
		g.noTint();
		g.popMatrix();
			
			
			
			
				
		//}
		
	}
	
	public void drawWaveBlur(){
		update();
		
		g.pushMatrix();
		g.translate(this.pos.x - (this.wave.width * this.scale * 0.2f), this.pos.y);
		
		if(this.isTint){
			g.tint(this.Tint.R, this.Tint.G, this.Tint.B, this.Tint.A);
		}
		g.image(pgBlur, offset.x, offset.y);
		
		
		if(move){
			if(forw){
				g.image(pgBlur, offset.x - pg.width, offset.y);
			}
			if(!forw){
				g.image(pgBlur, offset.x + pg.width, offset.y);
			}
		}
			
		g.noTint();
		g.popMatrix();
			
			
				
		
		
	}
	public void update(){
		
		makeCircles();
		if(move){
			moveWave();
		}
		this.offset.x = this.offset.x + this.moveOffset.x;
		this.offset.y = this.offset.y + this.moveOffset.y;
		
		
	}
	
	private void makeCircles(){
		//degrees = degrees % 360;
		
		if(degrees >= 0){
			if(!move)
				offset.x = (float) (this.waveMulti *  Math.cos(Math.toRadians(speed * (degrees - syncInt))));
			else
				offset.x = (float) (this.waveMulti * 3 * Math.cos(Math.toRadians(speed * (degrees - syncInt))));
			 offset.y =  (float) (this.waveMulti * Math.sin(Math.toRadians(speed * (degrees - syncInt))));
			
		}
		//else if(degrees > 180 && degrees < 360){
		//	 offset.x = (float) (1/(degrees - 180) * 180);
			 
		//	 offset.y = 0;
			
		//}
		degrees++;
		//if(degrees == (int) (360))
		//	degrees =  1;
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
	
	public PGraphics blurise(PGraphics src){
		PGraphics pass1, pass2;
		pass1 = g.createGraphics(src.width, src.height, memry.P2D);
		pass1.noSmooth();
		pass2 = g.createGraphics(src.width, src.height, memry.P2D);
		pass2.noSmooth();
		blur2.set("blurSize", 20);
		blur2.set("sigma", 10.0f);
		
		blur2.set("horizontalPass", 0);
		 pass1.beginDraw();            
		 pass1.shader(blur2);  
		 pass1.image(src, 0, 0);
		 pass1.endDraw();
		  
		 // Applying the blur shader along the horizontal direction      
		 blur2.set("horizontalPass", 1);
		 pass2.beginDraw();            
		 pass2.shader(blur2);  
		 pass2.image(pass1, 0, 0);
		 pass2.endDraw();   
		
		
		return pass2;
	}

}
