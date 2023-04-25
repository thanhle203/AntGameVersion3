package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public abstract class GameObjects implements IDrawable, ICollider {
	
	private int color;
	private Point location;
	private GameWorld gw;
	
   /* 
      Constructor method to create and instance of GameObjects, 
      creates a new vector and assigns a random value to its x and y position
   */
	public GameObjects() {
		
		Random rand = new Random();
		
		this.location = new Point(rand.nextFloat()*1000, rand.nextFloat()*1000);
		
	}
	
	// Constructor to create an instance of a game object at the specified x and y location
	public GameObjects(float x, float y) {
		this.location = new Point(x, y);
		
	}
	
	//Method to retrieve size of game object
	public int getSize() {
		return 0;
	}

   // Method to assign a color to the game object
	public void setColor(int newColor) {
		
		this.color = newColor;
		
	}
	
   // Method to retrieve the color of this game object
	public int getColor() {
		
		return this.color;
		
	}
	
   // Method to assign the location of the game object
	public void setLocation(float x, float y) {
		
		this.location.setX(x);
		this.location.setY(y);
		
	}
	
   // Method to retrieve x coordinate of the game object
	public float getLocationX() {
		
		return this.location.getX();
		
	}
	
	// Method to retrieve y coordinate of the game object
	public float getLocationY() {
			
		return this.location.getY();
			
	}
	
	public abstract void draw(Graphics g, com.codename1.ui.geom.Point pCmpRelPrnt);
	
	// determine whether this object has collided with another
		public boolean collidesWith(GameObjects obj) {
			
			boolean result = false;
			
			float thisCenterX = this.getLocationX() + (this.getSize()/2); // find centers
			float thisCenterY = this.getLocationY() + (this.getSize()/2);
			float otherCenterX = obj.getLocationX() + (obj.getSize()/2);
			float otherCenterY = obj.getLocationY() + (obj.getSize()/2);
			
			// find dist between centers (use square, to avoid taking roots)
			float dx = thisCenterX - otherCenterX;
			float dy = thisCenterY - otherCenterY;
			float distBetweenCentersSqr = (dx*dx + dy*dy);
			
			// find square of sum of radii
			float thisRadius = this.getSize()/2;
			float otherRadius = obj.getSize()/2;
			float radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius
			+ otherRadius*otherRadius);
			
			if (distBetweenCentersSqr <= radiiSqr) { 
				result = true ; 
			}
			return result;
			
		}


		public void handleCollision(GameObjects obj, GameWorld gw) {
			
			if(obj instanceof Spider) {
				gw.spiderCollision();
			}
			else if(obj instanceof Flag) {
				Flag flagNum = (Flag) obj;			
				gw.flag(flagNum.getFlag());
			}
			else if(obj instanceof FoodStations) {
				FoodStations food = (FoodStations) obj;
				gw.foodCollision(food);
			}
			
		}

		protected abstract void setCollided(boolean result);

		protected abstract boolean getCollided();
	
	
	
}