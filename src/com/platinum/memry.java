package com.platinum;


import com.platinum.graphics.Scene;
import com.platinum.graphics.modules.ModuleList;

import processing.core.PApplet;
import processing.core.PFont;


public class Memry extends Platbit{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Scene introScene;
	protected ModuleList mainModules;
	protected PFont f;
	protected Menu mainMenu;
	
	public static void main(String args[]){		
		PApplet.main(new String[] {com.platinum.Memry.class.getName()});		
	}
	
	public void setup(){
		this.initDisplay();
		this.setSize(renderTypes.P2D, this);
		this.setRatio(1920, 1080);
		
	}
	
	
	public void draw(){
		this.background(255);
		if(Setup.getDoSet()){
			Setup.doSetup(this);
			return;
		}
		if(this.mainMenu.getShowMenu()){
			this.mainMenu.draw(this);
		}
		textFont(f, 32);
		this.fill(0);
		this.text(frameRate, 100, 100);
		this.noFill();
		
		
		
		
		
	}
	
	
	
	
	
	

}
