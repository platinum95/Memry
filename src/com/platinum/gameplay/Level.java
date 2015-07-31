package com.platinum.gameplay;

public class Level {
	
	private int length, diff, money, pos, offsetX;
	
	public Level(int leng, int dif){
		this.length = leng;
		this.diff = dif;
	}
	
	public void setMoney(int mon){
		this.money = mon;
	}
	
	public void setPos(int po){
		this.pos = po;
	}
	
	public void setOffsetX(int off){
		this.offsetX = off;
	}
	
	public int getOffsetX(){
		return this.offsetX;
	}
	
	public int getPos(){
		return pos;
	}

	public int getMoney(){
		return money;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public int getDiff(){
		return this.diff;
	}
	
	
}
