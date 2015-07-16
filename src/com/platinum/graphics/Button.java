package com.platinum.graphics;

import java.awt.Color;

import com.platinum.memry;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PApplet;





import com.platinum.graphics.Display;

public class Button {
	
	private static final float PI = 3.145f;
	public PVector pos, size;
	private int Shape, textSize = 1;
	public boolean State, change, textDone = true;
	public boolean changeState;
	private Colour c;
	private String s[] = new String[2];
	public PGraphics butto;
	private PImage imgUp, imgDown;
	private float scale;
	private PApplet that;
	
	public Button(float xScale, float yScale, int Shape, float xSize, float ySize, Colour C){
		
		if(Shape == 0){
			size = new PVector(Display.res.x * xSize, Display.res.y * ySize);
			pos = new PVector(Display.res.x * xScale, Display.res.y * yScale);
		}
		else if(Shape == 1){
			size = new PVector(Display.res.x * xSize, Display.res.x * xSize);
			pos = new PVector(Display.res.x * xScale, Display.res.y * yScale);
		}
		this.Shape = Shape;
		this.State = false;
		this.c = C;
		this.change = false;
		s[0] = "";
		s[1] = "";
		
		
		
	}
	
	public Button(float xScale, float yScale, float scale, String pimgUp, String pimgDown, Colour C, PApplet g){
		
		pos = new PVector(Display.res.x * xScale, Display.res.y * yScale);
		this.that = g;
		imgUp = that.loadImage(pimgUp);
		imgDown = that.loadImage(pimgDown);
		this.scale = scale;
		this.c = C;
		
		butto = g.createGraphics((int) (imgUp.width * this.scale), (int) (imgUp.height * this.scale), memry.P2D);
		
		butto.beginDraw();
		butto.scale(this.scale);
		butto.image(this.imgUp, 0, 0);
		butto.tint(c.R, c.G, c.B, c.A);
		butto.endDraw();
		
		
		this.State = false;
		this.c = C;
		this.change = false;
		
		
		
	}
	
	public Button(float xScale, float yScale, float scale, String pimgUp, String pimgDown, PApplet g){
		
		pos = new PVector(Display.res.x * xScale, Display.res.y * yScale);
		this.that = g;
		imgUp = that.loadImage(pimgUp);
		imgDown = that.loadImage(pimgDown);
		this.scale = scale;
		
		
		butto = g.createGraphics((int) (imgUp.width * this.scale), (int) (imgUp.height * this.scale), memry.P2D);
		
		butto.beginDraw();
		butto.scale(this.scale);
		butto.image(this.imgUp, 0, 0);
		butto.endDraw();
		
		
		this.State = false;
		this.size = new PVector(imgUp.width * this.scale, imgUp.height * this.scale);
		this.changeState = false;
		this.change = false;
		
	}
	
	public Button(float xScale, float yScale, int Shape, float xSize, float ySize, Colour C, String textPress, String textUp){
		if(Shape == 0){
			size = new PVector(Display.res.x * xSize, Display.res.y * ySize);
			pos = new PVector(Display.res.x * xScale, Display.res.y * yScale);
		}
		else if(Shape == 1){
			size = new PVector(Display.res.x * xSize, Display.res.x * xSize);
			pos = new PVector(Display.res.x * xScale, Display.res.y * yScale);
		}
		this.Shape = Shape;
		this.State = false;
		this.c = C;
		this.change = false;
		s[0] = textUp;
		s[1] = textPress;
		
		
		
		
		
		
		
	}
	
	public void blur(float amount){
		butto.beginDraw();
		butto.filter(memry.BLUR, amount);
		butto.endDraw();
	}
	
	public void drawButtonImg(){
		if(this.changeState){
			if(this.State){
				butto.beginDraw();
				butto.scale(this.scale);
				butto.image(this.imgUp, 0, 0);
				butto.tint(c.R, c.G, c.B, c.A);
				butto.endDraw();
				this.changeState = false;
			}
			if(!this.State){
				butto.beginDraw();
				butto.scale(this.scale);
				butto.image(this.imgDown, 0, 0);
				butto.tint(c.R, c.G, c.B, c.A);
				butto.endDraw();				
				this.changeState = false;
			}
			
		}
		
		that.image(this.butto, this.pos.x, this.pos.y);
	}
	

	public void drawButton(PApplet g){
		
		
		while(textDone){
			//System.out.println(g.textWidth(this.s[0]));
			//System.out.println(g.textWidth(this.s[1]));
			//System.out.println(this.size.x * 0.8f);
			textSize++;
			g.textFont(memry.f, textSize);
			if(g.textWidth(this.s[0]) > (this.size.x * 0.8f) || (g.textWidth(this.s[1]) > (this.size.x * 0.8f)) || textSize > this.size.y * 0.9f){
				textDone = false;
			}
			
		}
		g.noFill();
		
		
		
	
		
		if(!this.change && this.Shape == 0){
			for(int i = 1; i < 5; i++){
				g.stroke(0, 100 - (255/5 * i));
				g.rect(pos.x, pos.y + size.y + i, size.x, 1);
			}
		}
		
		if(!this.change && this.Shape == 1){
			for(int i = 1; i < 5; i++){
				g.stroke(0, 200 - (255/5 * i));
				g.arc(this.pos.x, this.pos.y, this.size.x, this.size.x + i, 0, PI);
			}
		}
		
		
		
		g.stroke(0);
		g.fill(c.R, c.G, c.B);
		if(this.Shape ==0){
			g.rect(pos.x, pos.y, size.x , size.y );
		}
		if(this.Shape == 1){
			g.ellipse(this.pos.x, this.pos.y, this.size.x, this.size.x);
		}
		
		g.noStroke();
		g.fill(0);
		
		g.textFont(memry.f, textSize);
		g.textAlign(memry.CENTER, memry.CENTER);
		if(this.Shape == 0){
			if(State){
				g.text(s[1], pos.x + (size.x/2), pos.y + (size.y / 2) - 1);
			}
			if(!State){
				g.text(s[0], pos.x + (size.x/2), pos.y + (size.y / 2) - 1);
			}
		}
		if(this.Shape == 1){
			if(State){
				g.text(s[1], pos.x, pos.y);
			}
			if(!State){
				g.text(s[0], pos.x, pos.y);
			}
		}
		
	}

}
