package com.platinum.graphics;

import java.util.Random;

import com.platinum.Platbit;
import com.platinum.graphics.colour.Colour;
import com.platinum.graphics.display.Display;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Waves {
	
	private PGraphics wave;
	private PImage img;
	private float speed, size, Multi;
	private PVector offset, pos;
	private int moveOffset, syncInt, degrees, count;
	private Random rand;
	private boolean move, forw;
	
	public Waves(float yPos, float scale, float Speed, float multi, Colour tint, Platbit g){
		int waveCount = 0;
		this.speed = Speed;
		this.size = scale;
		this.Multi = multi;
		offset = new PVector(0, 0);
		moveOffset = 0;
		img = g.loadImage("res/Images/Scene/waves.png");
		pos = new PVector(-(img.width * scale * Display.getRatio().x * 0.2f), Display.getPosFromFloat(yPos));
		wave = g.createGraphics((int) (Display.getRes().x + (Display.getRes().x * .4)), (int) (img.height * scale * Display.getRatio().y), Display.getRenderType());
		wave.beginDraw();
		wave.tint(tint.R, tint.G, tint.B, tint.A);
		while(img.width * scale * Display.getRatio().x * waveCount < wave.width){
			wave.image(img, img.width * scale * Display.getRatio().x * waveCount, 0, img.width * scale * Display.getRatio().x, wave.height);
			waveCount++;
		}
		wave.noTint();
		wave.endDraw();	
		rand = new Random();
		this.syncInt = rand.nextInt(180);
	}
	
	public PGraphics getWave(){
		return wave;
	}
	
	public void setWave(PGraphics wa){
		this.wave = wa;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public float getSize(){
		return size;
	}
	
	public float getMulti(){
		return Multi;
	}
	public PVector getOverallPos(){
		return new PVector(pos.x + moveOffset + offset.x, pos.y + offset.y);
	}
	public PVector getPos(){
		return this.pos;
	}
	public PVector getOffset(){
		return this.offset;
	}
	
	public void update(){
		makeCircles();
		if(move)
			moveWave();
	}
	
	private void makeCircles(){
		//degrees = degrees % 360;
		
		if(degrees >= 0){
			if(!move)
				offset.x = (float) ((int) (this.Multi) *  3 * Math.cos(Math.toRadians(speed * (degrees - syncInt))));
			else
				offset.x = (float) ((int) (this.Multi) * 3 * Math.cos(Math.toRadians(speed * (degrees - syncInt))));
			 offset.y =  (float) ((int) (this.Multi) * Math.sin(Math.toRadians(speed * (degrees - syncInt))));
			
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
				this.moveOffset++;
			}
			if(!forw){
				this.moveOffset--;
			}
			//System.out.println(this.offset.x);
			
			
			count++;
			if(count >= this.wave.width){
				this.moveOffset = 0;
				count = 0;
			}
			
			if(count <= -(this.wave.width)){
				this.moveOffset = 0;
				count = 0;
			}
			
		
	}

}
