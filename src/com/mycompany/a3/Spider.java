package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Spider extends MovableGameObject {
	
	private int size;
	private int color;
	private Point location;
	private boolean collided;
	private int timer;
	
   // Constructor method to create an instance of Spider, assigns a random value to size and sets its color
	public Spider() {
		
		super();
		
		Random rand = new Random();
		this.size = rand.nextInt(11) + 20;
		this.collided = false;
		this.timer = 0;
		this.setColor();
		
	}
	
	public int getCollisionTimer() {
		return this.timer;
	}
	
	public void setCollisionTimer(int x) {
		this.timer += x;
	}
	
	public boolean getCollided() {
		return this.collided;
	}
	
	public void setCollided(boolean collided) {
		this.collided = collided;
	}
	
	//Method to set the spider color
	public void setColor() {
		this.color = ColorUtil.rgb(0,0,0);
	}
	
   // Method to retrieve the size of the spider
	public int getSize() {
		
		return this.size;
		
	}
	
	// Method to assign the location of the game object
			public void setLocation(float x, float y) {
				
				super.setLocation(x, y);
				
			}
			
		   // Method to retrieve x coordinate of the game object
			public float getLocationX() {
				
				return super.getLocationX();
				
			}
			
			// Method to retrieve y coordinate of the game object
			public float getLocationY() {
					
				return super.getLocationY();
					
			}
	
   // Method to convert all of the spider's data into a string for output
	public String toString() {
		

		String spider = "Spider: loc=" + Math.round(this.getLocationX()) + "," + 
		Math.round(this.getLocationY()) + " color=[" + ColorUtil.red(this.getColor()) + ","
		+ ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) + "] heading=" 
		+ this.getHeading() + " speed=" + this.getSpeed() + " size=" + this.getSize();
		
		
		return spider;
		
	}

	@Override
	public void draw(Graphics g, com.codename1.ui.geom.Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int x = (int) this.getLocationX() + pCmpRelPrnt.getX();
		int y = (int) this.getLocationY() + pCmpRelPrnt.getY();
		
		int[] xCoord = {x-this.getSize(), x+this.getSize(), x};
		int[] yCoord = {y-this.getSize(), y-this.getSize(), y+this.getSize()};
		
		g.setColor(this.getColor());
		
		g.drawPolygon(xCoord, yCoord, 3);
		
	}

	

	
	
	
}