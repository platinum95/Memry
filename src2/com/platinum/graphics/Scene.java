package com.platinum.graphics;

import com.platinum.memry;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PShader;

public class Scene {
	
	private PImage treeImg, backImg, skyImg, arrowImg;
	private PShader blur2;
	private PGraphics tree, sky, skyBlur, backgroundSrc, backgroundBlur;
	public Window mainWindow;
	public PGraphics  arrow[] = new PGraphics[4];
	private PApplet that;
	public Wave waves[] = new Wave[6];
	private PVector backgroundOffset, backMove;
	private Viking viki;
	public Boat boat, evilBoat;
	public boolean showWindow = false;
	private boolean once = true;
	private int j = 0;
	
	
	public Scene(PApplet g){
		this.that = g;
		blur2 = that.loadShader("res/blur.glsl");
		
		mainWindow = new Window(0f, 0f, 1, .3f, .1f, new Colour(255, 255, 255, 0), that);
		mainWindow.pos.x = (Display.res.x/2) - mainWindow.size.x/2;
		mainWindow.pos.y = 0 - mainWindow.size.y;
		
		skyImg = that.loadImage("res/sky.png");
		this.arrowImg = that.loadImage("res/arrow.png");
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
		
		arrow[1] = makeArrow(Colour.blue, 0);
		arrow[3] = makeArrow(Colour.yellow, 180);
		arrow[0] = makeArrow(Colour.green, 270);
		arrow[2] = makeArrow(Colour.red, 90);
		
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
		backgroundSrc.stroke(0);
		backgroundSrc.image(tree, 40, 150);
		backgroundSrc.image(tree, 200, 200);
		backgroundSrc.image(tree, 700, 100);
		backgroundSrc.image(tree, 1500, 200);
		backgroundSrc.tint(0, 0);
		backgroundSrc.fill(0, 100);
		//backgroundSrc.rect(Display.res.x/2, Display.res.y/2, this.mainWindow.size.x, this.mainWindow.size.y);
		backgroundSrc.noStroke();
		backgroundSrc.noTint();
		backgroundSrc.endDraw();
		backgroundBlur = blurise(backgroundSrc);
		backMove = new PVector(0, 0);
		
		
		
		
	}
	
	private PGraphics makeArrow(Colour tint, int rotation){
		PGraphics output;
		output = that.createGraphics((int) (arrowImg.width * 0.3f),(int) (arrowImg.height * 0.3f), memry.P2D);
		
		output.beginDraw();
		output.pushMatrix();
		output.translate(output.width/2, output.height/2);
		output.rotate((float) (rotation*(2*Math.PI)/360));
		output.tint(tint.R, tint.G, tint.B, tint.A);
		output.image(arrowImg, -output.width/2, -output.height/2, output.width, output.height);		
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
		
//		that.background(124);
		updateScene();
		
		
		this.mainWindow.drawWindow();
		
			
			
	}
	
	public void drawArrow(int pos, int type, boolean show){
		if(pos > this.mainWindow.pos.x && pos <= this.mainWindow.pos.x + this.mainWindow.size.x - this.arrow[type].width){
			if(show){	
				that.image(arrow[type], pos, this.mainWindow.pos.y );
			}
			else if(!show){
				that.textFont(memry.f, 64);
				that.fill(0);
				that.text("___", pos, this.mainWindow.pos.y);
				that.noFill();
			}
		}
		
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
