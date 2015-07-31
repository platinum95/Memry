package com.platinum.graphics;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.platinum.Platbit;
import com.platinum.graphics.colour.Colour;
import com.platinum.graphics.manipulation.Manipulations;

public class Scene {
	private Waves[] waves;
	private Background background;
	private int waveNum;
	private PGraphics wavBlur[], backBlur, skyBlur;
	private boolean blur;
	
	public Scene(int waveNum, Platbit p){
		this.waveNum = waveNum;
		waves = new Waves[waveNum];
		background = new Background("res/Images/Scene/background.png", "res/Images/Scene/sky.png", p);
		for(int i = 0; i < waveNum; i++){
			waves[i] = new Waves((float) (.45f - i*.04f), (float) (.8 - .1*i), (float) (5 - i*.5f), (float) (20 - i*6), new Colour(255 - i* 20), p);
		}
		
		
		
	}
	
	public Scene(int waveNum, boolean blur, Platbit p){
		this.waveNum = waveNum;
		waves = new Waves[waveNum];
		wavBlur = new PGraphics[waveNum];
		this.blur = blur;
		
		background = new Background("res/Images/Scene/background.png", "res/Images/Scene/sky.png", p);
		backBlur = Manipulations.blurise(background.getBackground(), 10f, 20, p); 
		skyBlur = Manipulations.blurise(background.getSky(), 10f, 20, p); 
		for(int i = 0; i < waveNum; i++){
			waves[i] = new Waves((float) (.45f - i*.028f), (float) (.8 - .1*i), (float) (3 - i*.5f), (float) (20 - i*6), new Colour(255 - i* 20), p);
			wavBlur[i] = Manipulations.blurise(waves[i].getWave(), 10f, 20, p);
		}
		
		
		
		
		
	}
	
	public void drawScene(PApplet g){
		if(blur){
			g.image(skyBlur, 0, 0);
			g.image(backBlur, background.getOffset(), 0);
			
			
			for(int i = waveNum - 1; i >= 0; i--){			
				waves[i].update();
				g.imageMode(PApplet.CORNER);
				g.image(wavBlur[i], waves[i].getOverallPos().x, waves[i].getOverallPos().y);
			}
		}
		else{
			g.image(background.getSky(), 0, 0);
			g.image(background.getBackground(), background.getOffset(), 0);
			for(int i = waveNum - 1; i >= 0; i--){			
				waves[i].update();
				g.imageMode(PApplet.CORNER);
				g.image(waves[i].getWave(), waves[i].getOverallPos().x, waves[i].getOverallPos().y);
			}		
		}
	}
	
	public Waves getWave(int i){
		return waves[i];
	}
	public Waves[] getWave(){
		return waves;
	}
	public Background getBackground(){
		return background;
	}
	
	public boolean getBlur(){
		return this.blur;
	}
	public int getWaveNum(){
		return this.waveNum;
	}

	public void setBlur(boolean in){
		this.blur = in;
	}
}
