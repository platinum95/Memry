package com.platinum;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PShader;









import java.security.SecureRandom;

import com.platinum.gameplay.Level;
import com.platinum.graphics.Boat;
import com.platinum.graphics.Button;
import com.platinum.graphics.Colour;
import com.platinum.graphics.Display;
import com.platinum.graphics.Scene;
import com.platinum.graphics.Viking;
import com.platinum.graphics.Wave;
import com.platinum.graphics.Window;
import com.platinum.sounds.Sound;


public class memry extends PApplet{
	private static final long serialVersionUID = 1l;
	private int count, score, pattern[][], charSpace, chars[] = {8592, 8593, 8594, 8595}, lives = 5, patternOld[], speed, boatProg, boatSpeed, keySet[] = {37, 38, 39, 40}, settingUp = 0;
	private SecureRandom rand = new SecureRandom();
	public Display disp = new Display(1366, 700);
	public Button playButton, menuButton, redPlay, greenSettings, blueBack;
	public Level currentGame;
	public Button settingsButton, initPlayButton;
	private Button playMore = new Button(0.1f, 0.8f, 0, .1f, .07f, new Colour(255, 255, 255), "Again", "Again");
	private Button exit = new Button(0.8f, 0.8f, 0, .1f, .07f, new Colour(255, 255, 255), "Exit", "Exit");
	public Button[] cursors = new Button[4], keyBinders = new Button[5];
	public Window mainWindow;
	public Window progress;
	private boolean failed, once, twice, readCopy, show[][], keyLock, thrice, frice, won, keyBinding, menuOnce = true;
	public static PFont f = new PFont();
	private Sound drums[] = new Sound[4], fail, win;
	private PImage boat, loadi;
	
	private Boat boot;
	private Viking viking;
	private Wave wave[] = new Wave[6];
	private Scene introScene;
	private PGraphics loading;
	
	


	
    
    
    
	
    
	public static void main(String args[]){
		PApplet.main(new String[] {com.platinum.memry.class.getName()});
	}
//	public boolean sketchFullScreen() {
//		  return true;
//		}
	

	
	public void setup(){
		
		disp.size(this);

		if(frame != null){
			frame.setResizable(true);
		}
		frameRate(60);
		f = new PFont();
		f = createFont("GNU Unifont", 128, true);
		loadi = loadImage("res/spritty.png");
		loading = createGraphics((int) Display.res.x, (int) Display.res.y, P2D);
		loading.beginDraw();
		loading.background(122);
		loading.image(loadi, (int) ((Display.res.x/2) - (loadi.width/2)), (int) ((Display.res.y/2) - (loadi.height/2)));
		loading.textFont(f, 32);
		loading.textAlign(CENTER, CENTER);
		loading.fill(0);
		loading.text("A", Display.res.x/2, Display.res.y/4);
		loading.text("Production", Display.res.x/2, 3* Display.res.y/4);
		loading.noFill();
		loading.endDraw();
		
		
		
		
	}
	
	  
	  
	public void doSetup(){
		

		viking = new Viking(.08f, .73f, .25f, this);
		
		boot = new Boat(.05f, .65f, 0.2f, "res/boat.png", this);

		mainWindow = new Window(0f, 0f, 1, .3f, .1f, new Colour(255, 255, 255), this);
		mainWindow.pos.x = (Display.res.x/2) - mainWindow.size.x/2;
		mainWindow.pos.y = 0 - mainWindow.size.y;
		progress = new Window(.2f,.8f, 1, .6f, .05f, new Colour(255,255,255), this);
		settingsButton = new Button(0.8f, 0.1f, .2f, "res/settings.png", "res/settings.png", this);
		initPlayButton = new Button(0.5f, 0.5f, .5f, "res/initBut.png", "res/initBut.png", this);
		initPlayButton.pos.x = initPlayButton.pos.x - (initPlayButton.butto.width/2);
		initPlayButton.pos.y = initPlayButton.pos.y - (initPlayButton.butto.height/2);
		menuButton = new Button(0.8f, 0.1f, .2f, "res/initBut.png", "res/initBut.png", this);
		playButton = new Button(0.5f, 0.7f, .5f, "res/initBut.png", "res/initBut.png", this);
		redPlay = new Button(.2f, .1f, Display.ratio.x * 1.5f, "res/redPlay.png", "res/redPlay.png", this);
		blueBack = new Button(0.8f, 0.1f, Display.ratio.x, "res/blueBack.png", "res/blueBack.png", this);
		greenSettings = new Button(.7f, .1f, Display.ratio.x * 1.5f, "res/greenSettings.png", "res/greenSettings.png", this);
		System.out.println("intro scene");
		introScene = new Scene(this);
		count = 1;
		System.out.println("drums");
		drums[0] = new Sound("/res/crash.wav");
		drums[1] = new Sound("/res/kick.wav");
		drums[2] = new Sound("/res/snare.wav");
		drums[3] = new Sound("/res/tom.wav");
		fail = new Sound("/res/fail.wav");
		win = new Sound("/res/win.wav");
		boat = loadImage("res/boat.jpg");
		boatProg =(int) progress.pos.x + 2;
		boatSpeed = 30;
		
		wave[0] = new Wave(0f, .77f, .5f, 5f, 10, this);
		wave[1] = new Wave(0f, .72f, .4f, 3f, 5, this, new Colour(110, 225));
		wave[2] = new Wave(0f, .68f, .3f, 3f, 2, this, new Colour(170, 225));
//		for(int i = 0; i < 3; i++){
//			wave[i].move = true;
//			wave[i].forw = true;
//		}
		
	    viking.mainPos.x = boot.pos.x + 10;
	    viking.mainPos.y = boot.pos.y + 60;
	    
	     

        
		menuButton.State = true;
		
		charSpace = (int) (Display.res.y * 0.2f);
		cursors[0] = new Button(0.1f, 0.3f, 1, .05f, .04f, new Colour(0, 0, 255), "UP", "UP");
		cursors[1] = new Button(0.1f, 0.7f, 1, .05f, .04f, new Colour(0, 255, 0), "DOWN", "DOWN");
		cursors[2] = new Button(0.8f, 0.3f, 1, .05f, .04f, new Colour(255, 0, 0), "LEFT", "LEFT");
		cursors[3] = new Button(0.8f, 0.7f, 1, .05f, .04f, new Colour(125, 125, 125), "RIGHT", "RIGHT");
		keyBinders[0] = new Button(0.6f, 0.4f, 0, .1f, .04f, new Colour(255, 255, 255), "LEFT", "LEFT");
		keyBinders[1] = new Button(0.7f, 0.3f, 0, .1f, .04f, new Colour(255, 255, 255), "UP", "UP");
		keyBinders[2] = new Button(0.8f, 0.4f, 0, .1f, .04f, new Colour(255, 255, 255), "RIGHT", "RIGHT");
		keyBinders[3] = new Button(0.7f, 0.5f, 0, .1f, .04f, new Colour(255, 255, 255), "DOWN", "DOWN");
		keyBinders[4] = new Button(0.73f, 0.4f, 0, .04f, .04f, new Colour(255, 255, 255), "CANCEL", "CANCEL");
		once = true;
		twice = true;
		thrice = true;
		frice = true;

		failed = false;
		speed = 4;
		
		
	}
	
	public void draw(){
		if(settingUp == 1){
			doSetup();
			settingUp++;
			return;			
		}
		
		else if(settingUp == 0){
			this.background(122);
			image(loading,0, 0);
			settingUp++;
			return;
		}
				
		if(settingsButton.State){
			settings();
		}
		
		else if (playButton.State){
		 
			playTest2();
		}
		else if (menuButton.State){
			 
			menu();
		}
		
		
		/*
		else if(failed){
			fail();
		}
		else if(won){
			won();
		}
		*/
		
		
	}
	
	private void sceneSetup(){
			this.introScene.boat.pos.x += 3;
			this.introScene.evilBoat.pos.x = (this.introScene.boat.pos.x - Display.res.x/4 - this.introScene.boat.size.x);
			this.fill(0);
			text(frameRate, 100, 100);
			this.noFill();
			this.introScene.drawScene();
			if(this.introScene.mainWindow.pos.y < 0.35f * Display.res.y){
				this.introScene.mainWindow.pos.y += 3;
		}
			else
				this.introScene.showWindow = true;
	}
	
	private void playTest2(){
		this.background(255);
		
		if(this.introScene.boat.pos.x < Display.res.x/2){
			sceneSetup();
			return;				
			}
				
		
		if(this.introScene.boat.pos.x >= Display.res.x/2 && this.introScene.waves[0].move == false){
			for(int i = 0; i < 5; i++){
				this.introScene.waves[i].move = true;
				this.introScene.waves[i].forw = false;
			}
		}
		
		this.introScene.drawScene();
		playGame();
		
		this.fill(0);
		this.textFont(f, 32);
		text(frameRate, 100, 100);
		this.noFill();
		this.introScene.boat.offset.x = currentGame.score;
		
		//this.mainWindow.drawWindow();
		
	}
	private void playGame(){
		if(frice){
			pattern = new int[count][3];
			patternOld = new int[count];
			for(int i = 0; i < count; i++){
				pattern[i][0] = rand.nextInt(4);
				pattern[i][1] = (int) (mainWindow.pos.x + mainWindow.size.x + (i * charSpace));
				patternOld[i] = pattern[i][0];
				
			}
		}
		
		if(!failed && !won){
					
					
					
					if(once && !frice){
						
						
						if(twice){
							
							pattern = new int[count][3];
							show = new boolean[count][2];
							pattern[count - 1][0] = rand.nextInt(4);
							
						}
						
						for(int i = 0; i < count; i++){
							if(twice && i < count - 1)
								pattern[i][0] = patternOld[i];
							pattern[i][1] = (int) (mainWindow.pos.x + mainWindow.size.x + (i * charSpace));
						}
						if(twice){
							patternOld = new int[count];
							for(int j = 0; j < count; j++){
								patternOld[j] = pattern[j][0];
							}
						}
						once = false;	
						twice = false;
					}
					else if(frice)
						frice = false;
					
					
					
					
					for(int j = 0; j < count && !readCopy; j++){
						textFont(f, 32);
						text("READ!", Display.res.x/2, .7f * Display.res.y);
						
						
						introScene.drawArrow(pattern[j][1], pattern[j][0], true);
						
						if(pattern[j][1] == mainWindow.pos.x + (mainWindow.size.x/2))
							drums[pattern[j][0]].play();
						
						pattern[j][1] = pattern[j][1] - speed;
					}
					
					for(int j = 0; j < count && readCopy; j++){
						textFont(f, 32);
						textAlign(CENTER, CENTER);
						text("COPY!", Display.res.x/2, .7f * Display.res.y);
		
						
						if(pattern[j][1] < mainWindow.pos.x + mainWindow.size.x && pattern[j][1] >= mainWindow.pos.x ){
		
							
							introScene.drawArrow(pattern[j][1], pattern[j][0], show[j][0]);
	
							
							
		
							if(pattern[j][1] < (int) (Display.res.x/2 - 15) && !show[j][0]){
								
								score--;
								show[j][0] = true;
								show[j][1] = false;
							}
							
							
						}
						
						
						
						pattern[j][1] = pattern[j][1]- speed;
						
						
					}
					
					if(this.keyPressed){
						//System.out.println(thrice);
						if(readCopy && keyLock && thrice){
							
							for(int j = 0; j < count; j++){
							//	System.out.println(this.keyCode - 37 + " " + pattern[j][0]);
								if(pattern[j][1] > Display.res.x/2 - 15 && pattern[j][1] < Display.res.x/2 + 15){
									if(this.keyCode == keySet[pattern[j][0]] ){
										show[j][0] = true;
										show[j][1] = true;
										drums[pattern[j][0]].play();
										if(boatSpeed != 1){
											if (boatSpeed - 1 < 1)
												boatSpeed = 1;
											else if(boatSpeed - 1 >= 1)
												boatSpeed = boatSpeed - 1;
										}
									}
										
										
									else if(this.keyCode != keySet[pattern[j][0]] ){
										lives--;
										show[j][0] = true;
										show[j][1] = false;
										if(boatSpeed != 30){
											if(boatSpeed + 1 > 30)
												boatSpeed = 30;
											else if(boatSpeed + 1 <= 30)
												boatSpeed = boatSpeed + 1;
										}
								}
			
								}
								thrice = false;
							}
							
						}
					
					}
					
					if(pattern[count - 1][1] <= mainWindow.pos.x){
						
						if(readCopy){
							count++;
							twice = true;
						}
						
						once = true;
						
						readCopy = !readCopy;
					}
					
					if(lives <= 0){
						failed = true;
						once = true;
					}
					if(boatProg + 30 > progress.pos.x + progress.size.x){
						won = true;
						once = true;
						
					}
					
					
					textFont(f, 16);
								
					
					
				}
	}
	
	private void menu(){
		this.background(255);
		
		if(menuOnce){		
			
			this.introScene.drawSceneBlur();
			initPlayButton.drawButtonImg();
			this.textFont(f, 32);
			this.fill(0);
			text(frameRate, 100, 100);
			this.noFill();
			return;
		}
		
		this.introScene.drawScene();
		this.textFont(f, 32);
		this.fill(0);
		text(frameRate, 100, 100);
		this.noFill();
		redPlay.drawButtonImg();
		greenSettings.drawButtonImg();
		
		
	}
	private void playTest(){
		this.background(255);
		mainWindow.drawWindow();
		settingsButton.drawButtonImg();
		wave[2].drawWave();
		wave[1].drawWave();
		
		viking.masterOffset.y = wave[1].offset.y;
		boot.offset.y = wave[1].offset.y;
		
		//wave[1].drawWave();
		viking.drawViking();
		
		boot.drawBoat();
		//wave[0].drawWave();
		if(viking.mainPos.x < Display.res.x/2){
			viking.mainPos.x++;
			boot.pos.x++;
		}
		else{
			for(int i = 0; i < 3; i++){
				wave[i].move = true;
				wave[i].forw = false;
			}
		}
		wave[1].drawWave();
		wave[0].drawWave();
		
		
		
		textAlign(CENTER, CENTER);
		this.textFont(f, 64);
		
		if(frice){
			pattern = new int[count][3];
			patternOld = new int[count];
			for(int i = 0; i < count; i++){
				pattern[i][0] = rand.nextInt(4);
				pattern[i][1] = (int) (mainWindow.pos.x + mainWindow.size.x + (i * charSpace));
				patternOld[i] = pattern[i][0];
				
			}
		}
		
		if(!failed && !won){
			
			this.fill(0);
			this.textFont(f, 90);
			textAlign(CENTER, CENTER);
			text("[ ]", mainWindow.pos.x + mainWindow.window.width/2,  mainWindow.pos.y + mainWindow.window.height/2 - 23);
			//for(int i = 0; i < 4; i ++){
			//	cursors[i].drawButton(this);
			//}
			
			
			
			if(once && !frice){
				
				
				if(twice){
					
					pattern = new int[count][3];
					show = new boolean[count][2];
					pattern[count - 1][0] = rand.nextInt(4);
					
				}
				
				for(int i = 0; i < count; i++){
					if(twice && i < count - 1)
						pattern[i][0] = patternOld[i];
					pattern[i][1] = (int) (mainWindow.pos.x + mainWindow.size.x + (i * charSpace));
				}
				if(twice){
					patternOld = new int[count];
					for(int j = 0; j < count; j++){
						patternOld[j] = pattern[j][0];
					}
				}
				once = false;	
				twice = false;
			}
			else if(frice)
				frice = false;
			
			
			
			
			for(int j = 0; j < count && !readCopy; j++){
				textFont(f, 32);
				text("READ!", Display.res.x/2, .7f * Display.res.y);
				
				if(pattern[j][1] < mainWindow.pos.x + mainWindow.size.x && pattern[j][1] > mainWindow.pos.x)
					text((char) chars[pattern[j][0]], pattern[j][1], mainWindow.pos.y + (mainWindow.size.y/2));
				
				if(pattern[j][1] == mainWindow.pos.x + (mainWindow.size.x/2))
					drums[pattern[j][0]].play();
				
				pattern[j][1] = pattern[j][1] - speed;
			}
			
			for(int j = 0; j < count && readCopy; j++){
				textFont(f, 32);
				textAlign(CENTER, CENTER);
				text("COPY!", Display.res.x/2, .7f * Display.res.y);

				
				if(pattern[j][1] < mainWindow.pos.x + mainWindow.size.x && pattern[j][1] >= mainWindow.pos.x ){

					fill(0);
					if(!show[j][0])
						text("___", pattern[j][1], mainWindow.pos.y + (mainWindow.size.y/2));
					
					if(show[j][1])
						fill(0, 255, 0);
					
					else
						fill(255, 0, 0);
					
					if(show[j][0])
						text((char) chars[pattern[j][0]], pattern[j][1], mainWindow.pos.y + (mainWindow.size.y/2));	
					
					fill(0);

					if(pattern[j][1] < (int) (Display.res.x/2 - 15) && !show[j][0]){
						lives--;
						show[j][0] = true;
						show[j][1] = false;
					}
					
					
				}
				
				
				
				pattern[j][1] = pattern[j][1]- speed;
				
				
			}
			
			if(this.keyPressed){
				//System.out.println(thrice);
				if(readCopy && keyLock && thrice){
					
					for(int j = 0; j < count; j++){
					//	System.out.println(this.keyCode - 37 + " " + pattern[j][0]);
						if(pattern[j][1] > Display.res.x/2 - 15 && pattern[j][1] < Display.res.x/2 + 15){
							if(this.keyCode == keySet[pattern[j][0]] ){
								show[j][0] = true;
								show[j][1] = true;
								drums[pattern[j][0]].play();
								if(boatSpeed != 1){
									if (boatSpeed - 1 < 1)
										boatSpeed = 1;
									else if(boatSpeed - 1 >= 1)
										boatSpeed = boatSpeed - 1;
								}
							}
								
								
							else if(this.keyCode != keySet[pattern[j][0]] ){
								lives--;
								show[j][0] = true;
								show[j][1] = false;
								if(boatSpeed != 30){
									if(boatSpeed + 1 > 30)
										boatSpeed = 30;
									else if(boatSpeed + 1 <= 30)
										boatSpeed = boatSpeed + 1;
								}
						}
	
						}
						thrice = false;
					}
					
				}
			
			}
			
			if(pattern[count - 1][1] <= mainWindow.pos.x){
				
				if(readCopy){
					count++;
					twice = true;
				}
				
				once = true;
				
				readCopy = !readCopy;
			}
			
			if(lives <= 0){
				failed = true;
				once = true;
			}
			if(boatProg + 30 > progress.pos.x + progress.size.x){
				won = true;
				once = true;
				
			}
			
			
			textFont(f, 16);
			text("SCORE: " + count, Display.res.x/2, 150);
			text("LIVES: " + lives, Display.res.x/2, 175);
			text("BOAT SPEED: " + (30 - boatSpeed), Display.res.x/2, 430);
			
			
			
			
			
		}
		
		if(failed){
			count = 0;
			fail();
		}
		if(won){
			
			won();
		}
	
		mainWindow.drawWindow();
		
		
		
	}
	
	
	private void play(){
		this.background(180);
		settingsButton.drawButtonImg();
		mainWindow.drawWindow();
		//progress.drawWindow();
		
		textFont(f, 64);
		
		textAlign(CENTER, CENTER);
		if(frameCount % boatSpeed == 0){
			boatProg++;
		}
		
		if(frice){
			pattern = new int[count][3];
			patternOld = new int[count];
			for(int i = 0; i < count; i++){
				pattern[i][0] = rand.nextInt(4);
				pattern[i][1] = (int) (mainWindow.pos.x + mainWindow.size.x + (i * charSpace));
				patternOld[i] = pattern[i][0];
				
			}
		}
		
		
		if(!failed && !won){
			
			image(boat, boatProg, progress.pos.y + 1, 30 , progress.size.y - 1);
			this.fill(0);
			text("[ ]", mainWindow.pos.x + mainWindow.size.x/2,  mainWindow.pos.y + mainWindow.size.y/2 - 16);
			for(int i = 0; i < 4; i ++){
				cursors[i].drawButton(this);
			}
			
			if(boatProg + 30 > progress.pos.x + progress.size.x){
				won = true;
				once = true;
				
			}
			
			if(once && !frice){
				
				
				if(twice){
					
					pattern = new int[count][3];
					show = new boolean[count][2];
					pattern[count - 1][0] = rand.nextInt(4);
					
				}
				for(int i = 0; i < count; i++){
					if(twice && i < count - 1)
						pattern[i][0] = patternOld[i];
					pattern[i][1] = (int) (mainWindow.pos.x + mainWindow.size.x + (i * charSpace));
				}
				if(twice){
					patternOld = new int[count];
					for(int j = 0; j < count; j++){
						patternOld[j] = pattern[j][0];
					}
				}
				once = false;	
				twice = false;
			}
			else if(frice)
				frice = false;
			
			
			
			
			for(int j = 0; j < count && !readCopy; j++){
				textFont(f, 32);
				text("READ!", Display.res.x/2, .7f * Display.res.y);
				
				if(pattern[j][1] < mainWindow.pos.x + mainWindow.size.x && pattern[j][1] > mainWindow.pos.x)
					text((char) chars[pattern[j][0]], pattern[j][1], mainWindow.pos.y + (mainWindow.size.y/2));
				
				if(pattern[j][1] == mainWindow.pos.x + (mainWindow.size.x/2))
					drums[pattern[j][0]].play();
				
				pattern[j][1] = pattern[j][1] - speed;
			}
			
			for(int j = 0; j < count && readCopy; j++){
				textFont(f, 32);
				textAlign(CENTER, CENTER);
				text("COPY!", Display.res.x/2, .7f * Display.res.y);

				//if(pattern[j][1] > Display.res.x/2 - 15 && pattern[j][1] < Display.res.x/2 + 15 && this.mousePressed){
				//	show[j] = true;
				//}
				if(pattern[j][1] < mainWindow.pos.x + mainWindow.size.x && pattern[j][1] >= mainWindow.pos.x ){

					fill(0);
					if(!show[j][0])
						text("___", pattern[j][1], mainWindow.pos.y + (mainWindow.size.y/2));
					
					if(show[j][1])
						fill(0, 255, 0);
					
					else
						fill(255, 0, 0);
					
					if(show[j][0])
						text((char) chars[pattern[j][0]], pattern[j][1], mainWindow.pos.y + (mainWindow.size.y/2));	
					
					fill(0);

					if(pattern[j][1] < (int) (Display.res.x/2 - 15) && !show[j][0]){
						lives--;
						show[j][0] = true;
						show[j][1] = false;
					}
					
					
				}
				
				
				
				pattern[j][1] = pattern[j][1]- speed;
				
				
			}
			
			if(this.keyPressed){
				//System.out.println(thrice);
				if(readCopy && keyLock && thrice){
					
					for(int j = 0; j < count; j++){
					//	System.out.println(this.keyCode - 37 + " " + pattern[j][0]);
						if(pattern[j][1] > Display.res.x/2 - 15 && pattern[j][1] < Display.res.x/2 + 15){
							if(this.keyCode == keySet[pattern[j][0]] ){
								show[j][0] = true;
								show[j][1] = true;
								drums[pattern[j][0]].play();
								if(boatSpeed != 1){
									if (boatSpeed - 1 < 1)
										boatSpeed = 1;
									else if(boatSpeed - 1 >= 1)
										boatSpeed = boatSpeed - 1;
								}
							}
								
								
							else if(this.keyCode != keySet[pattern[j][0]] ){
								lives--;
								show[j][0] = true;
								show[j][1] = false;
								if(boatSpeed != 30){
									if(boatSpeed + 1 > 30)
										boatSpeed = 30;
									else if(boatSpeed + 1 <= 30)
										boatSpeed = boatSpeed + 1;
								}
						}
	
						}
						thrice = false;
					}
					
				}
			
			}
			
			if(pattern[count - 1][1] <= mainWindow.pos.x){
				
				if(readCopy){
					count++;
					twice = true;
				}
				
				once = true;
				
				readCopy = !readCopy;
			}
			
			if(lives <= 0){
				failed = true;
				once = true;
			}
			if(boatProg + 30 > progress.pos.x + progress.size.x){
				won = true;
				once = true;
				
			}
			
			
			textFont(f, 16);
			text("SCORE: " + count, Display.res.x/2, 150);
			text("LIVES: " + lives, Display.res.x/2, 175);
			text("BOAT SPEED: " + (30 - boatSpeed), Display.res.x/2, 430);
			
			
			
			
			
		}
		
		if(failed){
			count = 0;
			fail();
		}
		if(won){
			
			won();
		}
	
	}
	
	private void fail(){
		count++;
		if(once){
			count = 0;
			fail.play();
			once = false;
		}
		if(count < 120){
			textFont(f, 128);
			fill(255, 0, 0);
			textAlign(CENTER, CENTER);
			text("YOU LOSE", Display.res.x/2, Display.res.y/2);
		}
		else{
			this.background(100);
			textFont(f, 128);
			fill(255, 0, 0);
			textAlign(CENTER, CENTER);
			text("YOU LOSE", Display.res.x/2, Display.res.y/2);
			 
			playMore.drawButton(this);
			exit.drawButton(this);
			
		}
		if(playMore.State){
			failed = false;
			won = false;
			boatProg =(int) progress.pos.x + 2;
			boatSpeed = 30;
			once = true;
			twice = true;
			thrice = true;
			count = 1;
			lives = 5;
			readCopy = false;
			playMore.State = false;
		}
		if(exit.State){
			System.exit(2);
		}
		
		

	}
	
	private void won(){
		count++;
		//System.out.println(once);
		if(once){
			win.play();
			//System.out.println("playing win");
			once = false;
		}
		if(count < 120){
			textFont(f, 128);
			fill(0, 255, 0);
			textAlign(CENTER, CENTER);
			text("YOU WIN!", Display.res.x/2, Display.res.y/2);
		}
		else{
			this.background(100);
			textFont(f, 128);
			fill(0, 255, 0);
			textAlign(CENTER, CENTER);
			text("YOU WIN!", Display.res.x/2, Display.res.y/2);
			 
			playMore.drawButton(this);
			exit.drawButton(this);
			
		}
		if(playMore.State){
			boatProg =(int) progress.pos.x + 2;
			boatSpeed = 30;
			failed = false;
			won = false;
			once = true;
			twice = true;
			thrice = true;
			count = 1;
			lives = 5;
			readCopy = false;
			playMore.State = false;
		}
		if(exit.State){
			System.exit(2);
		}
		
	}
	
	private void settings(){
		this.background(100);
		blueBack.drawButtonImg();
		for(int i = 0; i < 5; i++){
			keyBinders[i].drawButton(this);
		}
		
		if(this.keyPressed && keyBinding){
			for(int i = 0; i < 4; i ++){
				if(keyBinders[i].State){
					keySet[i] = this.keyCode;
					keyBinders[i].State = false;
					keyBinders[i].change = false;
					keyBinding = false;
				}
			}
			
		}
		
		
		if(!once){
			boatProg =(int) progress.pos.x + 2;
			boatSpeed = 30;
			failed = false;
			won = false;
			once = true;
			twice = true;
			thrice = true;
			count = 1;
			lives = 5;
			readCopy = false;
			playMore.State = false;
		}
		
	}
	
	
	@Override
	public void mousePressed(){
		
		if(this.mouseX > settingsButton.pos.x && this.mouseY > settingsButton.pos.y && this.mouseX < settingsButton.pos.x + settingsButton.size.x && this.mouseY < settingsButton.pos.y + settingsButton.size.y){
			settingsButton.change = true;
			System.out.println("settings ubtton");
		}
		if(menuButton.State){
			if(menuOnce)
				if(this.mouseX > initPlayButton.pos.x && this.mouseY > initPlayButton.pos.y && this.mouseX < initPlayButton.pos.x + initPlayButton.size.x && this.mouseY < initPlayButton.pos.y + initPlayButton.size.y){
					initPlayButton.change = true;				
				}
			if(this.mouseX > redPlay.pos.x && this.mouseY > redPlay.pos.y && this.mouseX < redPlay.pos.x + redPlay.size.x && this.mouseY < redPlay.pos.y + redPlay.size.y){
				redPlay.change = true;
			}
			if(this.mouseX > greenSettings.pos.x && this.mouseY > greenSettings.pos.y && this.mouseX < greenSettings.pos.x + greenSettings.size.x && this.mouseY < greenSettings.pos.y + greenSettings.size.y){
				greenSettings.change = true;
			}
			
		}
		
		if(!settingsButton.State && !failed){
			
			
			for(int i = 0; i < 4; i ++){
				if(this.mouseX > cursors[i].pos.x && this.mouseY > cursors[i].pos.y && this.mouseX < cursors[i].pos.x + cursors[i].size.x && this.mouseY < cursors[i].pos.y + cursors[i].size.y){
					cursors[i].change = true;
				}
			}
		}
		
		if(!settingsButton.State && failed){
		
			if(this.mouseX > playMore.pos.x && this.mouseY > playMore.pos.y && this.mouseX < playMore.pos.x + playMore.size.x && this.mouseY < playMore.pos.y + playMore.size.y){
				playMore.State = true;
			}
			if(this.mouseX > exit.pos.x && this.mouseY > exit.pos.y && this.mouseX < exit.pos.x + exit.size.x && this.mouseY < exit.pos.y + exit.size.y){
				exit.State = true;
			}
		}
		
		if(!settingsButton.State && won){
			
			if(this.mouseX > playMore.pos.x && this.mouseY > playMore.pos.y && this.mouseX < playMore.pos.x + playMore.size.x && this.mouseY < playMore.pos.y + playMore.size.y){
				playMore.State = true;
			}
			if(this.mouseX > exit.pos.x && this.mouseY > exit.pos.y && this.mouseX < exit.pos.x + exit.size.x && this.mouseY < exit.pos.y + exit.size.y){
				exit.State = true;
			}
		}
		
		if(settingsButton.State){
			if(!keyBinding){
				for(int i = 0; i < 4; i ++){
					if(this.mouseX > keyBinders[i].pos.x && this.mouseY > keyBinders[i].pos.y && this.mouseX < keyBinders[i].pos.x + keyBinders[i].size.x && this.mouseY < keyBinders[i].pos.y + keyBinders[i].size.y){
						keyBinders[i].State = true;
						keyBinders[i].change = true;
						keyBinding = true;
					}
					
				
				}
			}
			if(this.mouseX > keyBinders[4].pos.x && this.mouseY > keyBinders[4].pos.y && this.mouseX < keyBinders[4].pos.x + keyBinders[4].size.x && this.mouseY < keyBinders[4].pos.y + keyBinders[4].size.y){
				keyBinders[4].change = true;
			}
			if(this.mouseX > blueBack.pos.x && this.mouseY > blueBack.pos.y && this.mouseX < blueBack.pos.x + blueBack.size.x && this.mouseY < blueBack.pos.y + blueBack.size.y){
				blueBack.change = true;
			}
			
		}		
	}
	
	@Override
	public void mouseReleased(){
		if(settingsButton.change){
			settingsButton.State = !settingsButton.State;
			settingsButton.change = false;
		}
		
		if(playMore.change){
			playMore.State = false;		
		}
		if(initPlayButton.change){
			menuOnce = false;
		}
		if(redPlay.change){
			falsify();
			playButton.State = true;
			redPlay.change = false;
			currentGame = new Level(10, 10, 10);
		}
		if(greenSettings.change){
			falsify();
			settingsButton.State = true;
			greenSettings.change = false;
		}
		if(blueBack.change){
			falsify();
			menuButton.State = true;
			blueBack.change = false;
		}
		if(exit.change){
			exit.State = false;
		}
		for(int i = 0; i < 4; i++){
			if(cursors[i].change){
				cursors[i].State = !cursors[i].State;
				cursors[i].change = false;
			}
		}

		if(keyBinders[4].change){
			for(int i = 0; i < 4; i++){
				keyBinders[i].State = false;
				keyBinders[i].change = false;
				
			}
			keyBinding = false;
			keyBinders[4].change = false;
		}
		
	}
	
	@Override
	public void keyPressed(){		
			
		keyLock = true;
		
	}
	
	@Override
	public void keyReleased(){
		keyLock = false;
		thrice = true;
		
		
	}
	
	private void falsify(){
		settingsButton.State = false;
		playButton.State = false;
		menuButton.State = false;
	}
	
	
		
	
	
	
}
