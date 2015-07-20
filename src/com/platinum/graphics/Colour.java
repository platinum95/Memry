package com.platinum.graphics;


public class Colour {
	public int R, G, B, A = 255;
	
	public static Colour red 	= new Colour(221, 88, 88);
	public static Colour green 	= new Colour(94, 255, 94);
	public static Colour blue 	= new Colour(15, 120, 255);
	public static Colour cyan 	= new Colour(34, 238, 238);
	public static Colour purple	= new Colour(180, 100, 221);
	public static Colour white 	= new Colour(255, 255, 255);
	public static Colour yellow = new Colour(255, 255, 68);
	public static Colour orange	= new Colour(255, 204, 51);	
	
	public static Colour redorange= new Colour(255, 187, 153);
	public static Colour darkgreen= new Colour(109, 225, 116);
	public static Colour darkcyan = new Colour(153, 221, 204);
	
	public static Colour black 	= new Colour(0, 0, 0);
	public static Colour grey 	= new Colour(90, 90, 90);
	public static Colour lightgrey= new Colour(200, 200, 200);
	public static Colour darkgrey= new Colour(45, 45, 45);
	
	public Colour(int r, int g, int b){
		this.R = r;
		this.G = g;
		this.B = b;
		this.A = 255;
	}
	public Colour(int r, int g, int b, int a){
		this.R = r;
		this.G = g;
		this.B = b;
		this.A = a;
	}
	public Colour(int grey){
		this.R = this.G = this.B = grey;
		this.A = 255;
	}
	public Colour(int grey, int a){
		this.R = this.G = this.B = grey;
		this.A = a;
	}
	
	
}
