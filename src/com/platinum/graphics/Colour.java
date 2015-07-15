package com.platinum.graphics;

public class Colour {
	public int R, G, B, A = 255;
	
	public Colour(int r, int g, int b){
		this.R = r;
		this.G = g;
		this.B = b;
	}
	public Colour(int r, int g, int b, int a){
		this.R = r;
		this.G = g;
		this.B = b;
		this.A = a;
	}
	public Colour(int grey){
		this.R = this.G = this.B = grey;
	}
	public Colour(int grey, int a){
		this.R = this.G = this.B = grey;
		this.A = a;
	}
	
}
