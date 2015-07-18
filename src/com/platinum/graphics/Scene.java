package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PShader;

public class Scene {
	
	private PImage treeImg, backImg, skyImg, arrow;
	private PShader blur2;
	private PGraphics tree, sky, skyBlur, backgroundSrc, backgroundBlur;
	public PGraphics  up, down, left, right;
	private PApplet that;
	public Wave waves[] = new Wave[6];
	private PVector backgroundOffset, backMove;
	private Viking viki;
	public Boat boat, evilBoat;
	
	
	public Scene(PApplet g){
		this.that = g;
		blur2 = that.loadShader("res/blur.glsl");
		
		skyImg = that.loadImage("res/sky.png");
		this.arrow = that.loadImage("res/arrow.png");
		System.out.println("making waves");
		for(int i = 0; i < 5; i++){
			waves[i] = new Wave(0f,(float) (.8f - i*.1f),(float) (.6 - .1*i),(float) (3 - i*.5f), 20 - i*6, that, new Colour(255 - i* 20), 20);
		//waves[0] = new Wave(0f,(float) (.8f),(float) (1),(float) (3), 40, that, new Colour(255), 5);
	}
		waves[2].pos.y = waves[2].pos.y + 30;
		waves[3].pos.y = waves[3].pos.y + 60;
		waves[4].pos.y = waves[4].pos.y + 115;
		viki = new Viking(0f, .73f, .5f, that);
		boat = new Boat(0f, 1f, 0.4f, "res/boat.png", that);
		evilBoat = new Boat(0f, 1f, 0.4f, "res/evilboat.png", that);
		evilBoat.pos.y = this.waves[2].pos.y + 20;
		evilBoat.pos.x =- (evilBoat.size.x + Display.res.x/4);
		boat.pos.y = this.waves[2].pos.y - 40;
		boat.pos.x =- boat.size.x;
		
		up = makeArrow(Colour.red, 0);
		down = makeArrow(Colour.red, 180);
		left = makeArrow(Colour.red, 270);
		right = makeArrow(Colour.red, 90);
		
		System.out.println("offsets");
		backgroundOffset = new PVector(0, 0);
		treeImg = that.loadImage("res/tree.png");
		backImg = that.loadImage("res/background.png");
		System.out.println("tree");
		tree = that.createGraphics((int) treeImg.width, (int) treeImg.height, memry.P2D);
		tree.beginDraw();
		tree.image(treeImg, 0, 0, treeImg.width * Display.ratio.x, treeImg.height * Display.ratio.y);
		tree.endDraw();
		
		sky = that.createGraphics((int) Display.res.x, (int) (skyImg.height * (Display.res.x/1920)));
		sky.beginDraw();
		sky.image(skyImg, 0, 0, sky.width, sky.height);
		sky.endDraw();
		skyBlur = blurise(sky);
		System.out.println("background");
		blur2.set("blurSize", 20);
		blur2.set("sigma", 10.0f);
		backgroundSrc = that.createGraphics((int) Display.res.x, (int) ((backImg.height * (Display.res.x/1920)) + (sky.height - 20)) , memry.P2D);
		
		
		backgroundSrc.beginDraw();
		backgroundSrc.image(backImg, 0, sky.height - 20, backgroundSrc.width, (backImg.height * Display.ratio.y) - 100);
		backgroundSrc.image(tree, 40, 150);
		backgroundSrc.image(tree, 200, 200);
		backgroundSrc.image(tree, 700, 100);
		backgroundSrc.image(tree, 1500, 200);
		backgroundSrc.endDraw();
		backgroundBlur = blurise(backgroundSrc);
		backMove = new PVector(0, 0);
		
		
		
		
	}
	
	private PGraphics makeArrow(Colour tint, int rotation){
		PGraphics output;
		output = that.createGraphics((int) (arrow.width * 0.3f),(int) (arrow.height * 0.3f), memry.P2D);
		
		output.beginDraw();
		output.pushMatrix();
		output.translate(output.width/2, output.height/2);
		output.rotate((float) (rotation*(2*Math.PI)/360));
		output.image(arrow, -output.width/2, -output.height/2, output.width, output.height);
		output.tint(tint.R, tint.G, tint.B, tint.A);
		output.popMatrix();
		output.endDraw();
		return output;		
	}
	
	public void drawSceneBlur(){
		for(int i = 0; i < 5; i++){
			this.waves[i].update();
		}
		this.backgroundOffset.x = this.waves[4].moveOffset.x;
		that.image(skyBlur, 0,0);
		that.image(backgroundBlur, backgroundOffset.x, backgroundOffset.y);
				
		for(int i = 4; i >= 0; i--){
			this.waves[i].drawWaveBlur();
		}
	}
	
	public void drawScene(){

		updateScene();
		
	}
	
	public void updateScene(){
		
		for(int i = 0; i < 5; i++){
			this.waves[i].update();
		}

		//this.backgroundOffset.x = this.waves[4].moveOffset.x;
		that.image(sky, 0,0);
		that.image(backgroundSrc, backgroundOffset.x + backMove.x, backgroundOffset.y);
		if(this.waves[4].move){
			
			if(!this.waves[4].forw){
				
				that.image(backgroundSrc, (backgroundOffset.x + this.backMove.x) + backgroundSrc.width - 10, backgroundOffset.y);
				this.backMove.x--;
			}
			
			if(this.waves[4].forw){
				
				that.image(backgroundSrc, (backgroundOffset.x + backMove.x) - backgroundSrc.width, backgroundOffset.y);
				this.backMove.x++;
				
			}
			if(this.backMove.x == backgroundSrc.width || this.backMove.x == -backgroundSrc.width)
				backMove.x = 0;
			
		}

		for(int i = 4; i > 2; i--){
			this.waves[i].drawWave();
		}
		


		boat.offset.y = this.waves[2].offset.y - 100;
		evilBoat.offset.y = this.waves[2].offset.y - 100;
		viki.mainPos.x = boat.pos.x + 5;
		viki.masterOffset.y = boat.offset.y;
		viki.mainPos.y = boat.pos.y + 110;
		
		viki.drawViking();
		boat.drawBoat();
		evilBoat.drawBoat();
		
		
		for(int i = 2; i >=0; i--){
			this.waves[i].drawWave();
		}
		
		
	}

	public PGraphics blurise(PGraphics src){
		PGraphics pass1, pass2;
		pass1 = that.createGraphics(src.width, src.height, memry.P2D);
		pass1.noSmooth();
		pass2 = that.createGraphics(src.width, src.height, memry.P2D);
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
