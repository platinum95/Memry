package com.platinum;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import com.platinum.graphics.Background;
import com.platinum.graphics.Waves;
import com.platinum.graphics.colour.Colour;
import com.platinum.graphics.display.Display;
import com.platinum.graphics.image.Image;
import com.platinum.graphics.modules.Button;
import com.platinum.graphics.modules.Module;
import com.platinum.graphics.modules.ModuleList;
import com.platinum.graphics.modules.Table;

public class Menu {
	private PGraphics menu, settings, store, scoreboard, maps;
	private Image tugBoat;
	private int offset;
	private PFont f;
	private Table scoreTable, testTable;
	private boolean map, stor, sett, scor, showMenu;
	private ModuleList storeMenu;
	private ModuleList scoreboardMenu;
	private ModuleList settingsMenu;
	private ModuleList mapsMenu, menuList;
	
	public Menu(Memry that){
		f = that.createFont("res/Fonts/fontA.ttf", 128, true);
		that.mainModules.addModule(new Button("Maps", Button.shape.RECTANGLE, new PVector(.5f, .35f),new PVector(.2f, .09f), Colour.yellow, f, that));
		that.mainModules.addModule(new Button("Store", Button.shape.RECTANGLE,new PVector(.5f, .45f),new PVector(.2f, .09f),  Colour.yellow, f, that));
		that.mainModules.addModule(new Button("Settings", Button.shape.RECTANGLE,new PVector(.5f, .55f),new PVector(.2f, .09f), Colour.yellow, f, that));
		that.mainModules.addModule(new Button("Scoreboard", Button.shape.RECTANGLE,new PVector(.5f, .65f),new PVector(.2f, .09f), Colour.yellow, f, that));
		
		this.storeMenu = new ModuleList();
		this.scoreboardMenu = new ModuleList();
		this.settingsMenu = new ModuleList();
		this.mapsMenu = new ModuleList();
		this.menuList = new ModuleList();
		
		this.menuList.addModule(new Button("Back", Button.shape.RECTANGLE,new PVector(.8f, .15f),new PVector(.15f, .05f), Colour.red, f, that));
		this.menuList.registerModules(that);
		this.menuList.getComponent("Back").setLock(false);
		
		this.menu = that.createGraphics((int) Display.getRes().x, (int) Display.getRes().y, Display.getRenderType());
		this.scoreboard = that.createGraphics((int) Display.getRes().x, (int) Display.getRes().y, Display.getRenderType());
		this.settings = that.createGraphics((int) Display.getRes().x, (int) Display.getRes().y, Display.getRenderType());
		this.store = that.createGraphics((int) Display.getRes().x, (int) Display.getRes().y, Display.getRenderType());
		this.maps = that.createGraphics((int) Display.getRes().x, (int) Display.getRes().y, Display.getRenderType());
		
		this.scoreTable = new Table(new PVector(0.1f, 0.12f), new PVector(0.8f, 0.4f), 10, 3, that);
		testTable = new Table(new PVector(0.1f, 0.12f), new PVector(0.8f, 0.4f), that);
		testTable.addColumn(0.3f);
		testTable.addColumn(0.2f);
		testTable.addColumn(0.5f);
		testTable.addEvenRows(20);
		testTable.updateTable();
		
		
		
		
		menu.beginDraw();
		menu.background(125);
		menu.endDraw();
		
		maps.beginDraw();
		maps.textFont(f, 80);
		maps.textAlign(PApplet.CENTER, PApplet.CENTER);
		maps.background(125);
		maps.fill(255, 255, 68);
		maps.text("Maps", Display.getRes().x/2, Display.getPosFromFloat(0.07f));
		maps.noFill();
		maps.endDraw();
		
		scoreboard.beginDraw();
		scoreboard.fill(255, 255, 68);
		scoreboard.textFont(f, 80);
		scoreboard.textAlign(PApplet.CENTER, PApplet.CENTER);
		scoreboard.background(125);
		scoreboard.text("Scoreboard", Display.getRes().x/2, Display.getPosFromFloat(0.07f));
		scoreboard.noFill();
		scoreboard.image(scoreTable.getTable(), scoreTable.getPos().x, scoreTable.getPos().y);
		scoreboard.endDraw();
		
		store.beginDraw();
		store.textFont(f, 80);
		store.textAlign(PApplet.CENTER, PApplet.CENTER);
		store.background(125);
		store.fill(255, 255, 68);
		store.text("Store", Display.getRes().x/2, Display.getPosFromFloat(0.07f));
		store.noFill();
		store.endDraw();
		
		settings.beginDraw();
		settings.textFont(f, 80);
		settings.textAlign(PApplet.CENTER, PApplet.CENTER);
		settings.background(125);
		settings.fill(255, 255, 68);
		settings.text("Settings", Display.getRes().x/2, Display.getPosFromFloat(0.07f));
		settings.noFill();
		settings.endDraw();
		
		tugBoat = new Image("res/Images/Boats/boat.png", new PVector(0, 0), that);
		
		tugBoat.setPos(new PVector(-tugBoat.getSize().x, that.introScene.getWave(3).getPos().y));
		this.offset =(int) -((Display.getRes().x * 1.25f) + this.tugBoat.getSize().x);
		this.showMenu = true;
		
		
	}
	
	public void draw(Memry that){
		updateButtons(that);
		if(this.menuList.getComponent("Back").getState())
			resetButtons(that);
		if(!this.stor && !this.sett && !this.scor && !this.map)
			drawMainMenu(that);
		else if(this.scor)
			drawScoreboard(that);
		else if (this.stor)
			drawStore(that);
		else if (this.sett)
			drawSettings(that);
		else if(this.map)
			drawMaps(that);
		else
			System.out.println("Error in Menu");
		
	}
	
	public void drawNoUpdate(Memry that){
		for(Module m: that.mainModules.getModuleList()){

			m.setPos(new PVector(Display.getRes().x/2 + offset, m.getPos().y));
		}
		
		that.image(that.introScene.getBackground().getSky(), 0, 0);
		that.image(that.introScene.getBackground().getBackground(), that.introScene.getBackground().getOffset(), 0);
		for(int i = that.introScene.getWaveNum() - 1; i >=0; i--){
			if(i == 3){
				that.imageMode(PApplet.CENTER);
				this.tugBoat.setOffset(new PVector(0, that.introScene.getWave(i).getOffset().y - (Display.getRes().x * .07f)));
				this.tugBoat.draw(that);
			} 
			that.introScene.getWave(i).update();
			that.imageMode(PApplet.CORNER);
			that.image(that.introScene.getWave(i).getWave(), that.introScene.getWave(i).getOverallPos().x, that.introScene.getWave(i).getOverallPos().y);
		}
		
		that.stroke(0);
		that.line(this.tugBoat.getOverallPos().x - this.tugBoat.getSize().x/2, this.tugBoat.getOverallPos().y + this.tugBoat.getSize().y/4, this.offset + this.menu.width, Display.getRes().y);
		that.line(this.tugBoat.getOverallPos().x - this.tugBoat.getSize().x/2, this.tugBoat.getOverallPos().y + this.tugBoat.getSize().y/4, this.offset + this.menu.width, 0);
		that.noStroke();
		drawMainMenu(that);
	}
	
	private void drawSettings(Memry that){
		that.image(this.settings, 0, 0);
		this.menuList.getComponent("Back").draw(that);
		this.testTable.draw(that);
	}
	
	private void drawScoreboard(Memry that){
		that.image(this.scoreboard, 0, 0);
		this.menuList.getComponent("Back").draw(that);
	}
	
	private void drawStore(Memry that){
		that.image(this.store, 0, 0);
		this.menuList.getComponent("Back").draw(that);
	}
	
	private void drawMaps(Memry that){
		that.image(this.maps, 0, 0);
		this.menuList.getComponent("Back").draw(that);
	}
	
	
	private void drawMainMenu(Memry that){
		that.pushMatrix();
		that.translate(offset, 0);
		that.image(menu, 0, 0);	
		that.popMatrix();	
		that.mainModules.getComponent("Store").draw(that);
		that.mainModules.getComponent("Maps").draw(that);
		that.mainModules.getComponent("Settings").draw(that);
		that.mainModules.getComponent("Scoreboard").draw(that);
		
	}
	
	private void updateButtons(Memry that){
		this.stor = that.mainModules.getComponent("Store").getState();
		this.map = that.mainModules.getComponent("Maps").getState();
		this.sett = that.mainModules.getComponent("Settings").getState();
		this.scor = that.mainModules.getComponent("Scoreboard").getState();
		
	}
	
	public int getOffset(){
		return offset;
	}
	public Image getBoat(){
		return this.tugBoat;
	}
	
	public void setOffset(int in){
		this.offset = in;
	}
	
	public boolean getShowMenu(){
		return showMenu;
	}
	
	private void resetButtons(Memry that){
		that.mainModules.getComponent("Store").setState(false);
		that.mainModules.getComponent("Maps").setState(false);
		that.mainModules.getComponent("Settings").setState(false);
		that.mainModules.getComponent("Scoreboard").setState(false);
		this.menuList.getComponent("Back").setState(false);
	}
	
	
}
