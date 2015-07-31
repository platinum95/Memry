package com.platinum;


import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import com.platinum.graphics.Scene;
import com.platinum.graphics.display.Display;
import com.platinum.graphics.modules.Button;
import com.platinum.graphics.modules.Module;
import com.platinum.graphics.modules.ModuleList;

public class Setup {

	private static int count = 0;
	private static boolean doSet = true;
	
	public static void doSetup(Memry that){	
		switch(count){
			case 0:
				PImage img = that.loadImage("res/Images/Misc/spritty.png");				
				PGraphics loading = that.createGraphics((int) (img.width * Display.getRatio().x), (int) (img.height * Display.getRatio().y), Display.getRenderType());
				loading.beginDraw();
				loading.image(img, 0, 0, loading.width, loading.height);
				loading.endDraw();
				that.background(125);
				that.imageMode(PApplet.CENTER);
				that.image(loading, Display.getRes().x/2, Display.getRes().y/2);
				that.imageMode(PApplet.CORNER);
				break;
			case 1:
				realSetup(that);
				img = null;
				loading = null;
				break;
			case 2:
				if(!that.mainModules.getComponent("initBut").getState()){
					that.introScene.drawScene(that);
					that.mainModules.getComponent("initBut").draw(that);
					return;
				}
				else{
					that.introScene.setBlur(false);
					that.introScene.drawScene(that);
					
					
				}
				break;
				
			case 3:
				that.mainModules.removeModule(that.mainModules.getComponent("initBut"));
				that.mainModules.registerModules(that);
				break;
			case 4:
				if(that.mainMenu.getOffset() < 0){
					PVector tempPos;
					that.mainMenu.setOffset(that.mainMenu.getOffset() + 5);
					if(that.mainMenu.getOffset() > 0)
						that.mainMenu.setOffset(0);
					tempPos = that.mainMenu.getBoat().getOffset2();
					that.mainMenu.getBoat().setOffset2(new PVector(tempPos.x + 5, tempPos.y));
					
					if(that.mousePressed){
						that.mainMenu.setOffset(0);
					}
					
					that.mainMenu.drawNoUpdate(that);
					if(that.mainMenu.getOffset() < 0)
						return;
				}
				
				for(Module m: that.mainModules.getModuleList()){
					m.setLock(false);
				}
				doSet = false;
				
				break;
			
		}
		count++;                                                                                                            
		
		
	}
	
	private static void realSetup(Memry that){
		that.setUp();
		that.introScene = new Scene(5, true, that);
		that.mainModules = new ModuleList();
		that.mainModules.addModule(new Button("initBut", new PVector(.5f, .5f), "res/Images/Buttons/initBut.png", that));
		that.mainModules.registerModules(that);
		that.mainModules.getComponent("initBut").setLock(false);
		that.f = that.createFont("Arial", 32, true);
		that.mainMenu = new Menu(that);
		
		that.frameRate(60);
	}
	
	public static boolean getDoSet(){
		return doSet;
	}
}
