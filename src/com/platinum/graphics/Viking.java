package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PGraphics;


public class Viking {
	private PImage arm[] = new PImage[2], head, body;
	private PVector armOffset[] = new PVector[2];//, headOffset, bodyOffset;
	public PVector masterOffset;
	private PGraphics viki;
	public PVector mainPos;
	private PVector armPos[] = new PVector[2], headPos, bodyPos;
	private int count[] = new int[4];
	private float scale = 0f;
	private PApplet that;
	
	
	public Viking(float xPos, float yPos, float scale, PApplet g){
		this.mainPos = new PVector(xPos * Display.res.x, yPos * Display.res.y);
		this.that = g;
		armPos[0] = new PVector(90, 25);
		armPos[1] = new PVector(- 30,  25);
		headPos = new PVector(25, -30);
		bodyPos = new PVector(0, 0);
		armOffset[0] = new PVector(0, 0);
		armOffset[1] = new PVector(0, 0);
//		bodyOffset = new PVector(0, 0);
//		headOffset = new PVector(0, 0);
		arm[0] = that.loadImage("res/arm1.png");
		arm[1] = that.loadImage("res/arm2.png");
		head = that.loadImage("res/head.png");
		body = that.loadImage("res/body.png");
		
		masterOffset = new PVector(0,0);
		for(int i = 0; i< 4; i++){
			count[i] = 0;
		}
		this.scale = scale;
		
		
		this.viki = that.createGraphics((int) ((this.scale * arm[0].width) + (this.scale * arm[1].width) + (body.width * this.scale)),(int) ((body.height * this.scale) + (head.height * this.scale)), memry.P2D);
		viki.beginDraw();
		viki.scale(scale);
		viki.translate(30, 30);
		viki.image(arm[0], armPos[0].x, armPos[0].y);
		viki.image(arm[1], armPos[1].x, armPos[1].y);
		viki.image(body, bodyPos.x, bodyPos.y);
		viki.image(head, headPos.x, headPos.y);
		viki.endDraw();
		
		
	}
	
	
	public void drawViking(){
		that.image(viki, mainPos.x + masterOffset.x, mainPos.y + masterOffset.y);
				
	}
	
	
	
	
}
