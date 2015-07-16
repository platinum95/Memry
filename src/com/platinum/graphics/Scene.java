package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PShader;

public class Scene {
	
	private PImage treeImg, backImg, waveImg, skyImg;
	private PShader blur2;
	private PGraphics screen, tree, sky, backgroundSrc, backgroundBlur;//, waves[] = new PGraphics[6];
	private PApplet that;
	private Wave waves[] = new Wave[6];
	private PVector backgroundOffset, treeOffset, waveOffset[] = new PVector[6], circlyOffset;
	private int degrees;
	
	
	public Scene(PApplet g){
		this.that = g;
		blur2 = that.loadShader("res/blur.glsl");
		waveImg = that.loadImage("res/waves.png");
		skyImg = that.loadImage("res/sky.png");
		System.out.println("making waves");
		for(int i = 0; i < 5; i++){
			waves[i] = new Wave(0f,(float) (.8f - i*.1f),(float) (1 - .1*i),(float) (3 - i*.5), 40 - i*6, that, new Colour(255 - i* 20), 20);
		//waves[0] = new Wave(0f,(float) (.8f),(float) (1),(float) (3), 40, that, new Colour(255), 5);
	}

	
		
		System.out.println("offsets");
		backgroundOffset = new PVector(0, 0);
		treeOffset = new PVector(0, 0);
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
		System.out.println("background");
		blur2.set("blurSize", 20);
		blur2.set("sigma", 10.0f);
		backgroundSrc = that.createGraphics((int) Display.res.x, (int) ((backImg.height * (Display.res.x/1920)) + (sky.height - 20)) , memry.P2D);
		
		
		backgroundSrc.beginDraw();
		backgroundSrc.image(sky, 0, 0);
		backgroundSrc.image(backImg, 0, sky.height - 20, backgroundSrc.width, (backImg.height * Display.ratio.y) - 100);
		backgroundSrc.image(tree, 40, 150);
		backgroundSrc.image(tree, 200, 200);
		backgroundSrc.image(tree, 700, 100);
		backgroundSrc.image(tree, 1500, 200);
		backgroundSrc.endDraw();
		backgroundBlur = blurise(backgroundSrc);
		
		
		
		
		
	}
	
	
	public void drawSceneBlur(){
		for(int i = 0; i < 5; i++){
			this.waves[i].update();
		}
		this.backgroundOffset.x = this.waves[4].moveOffset.x;
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
		this.backgroundOffset.x = this.waves[4].moveOffset.x;
		that.image(backgroundSrc, backgroundOffset.x, backgroundOffset.y);
				
		for(int i = 4; i >= 0; i--){
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
