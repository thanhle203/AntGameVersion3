package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class FoodStations extends FixedGameObject {
	
	private int size;
	private int capacity;
	private boolean collided;
	private boolean selected;
	
   /* 
      Constructor method to create an instance of food station,
      assigns a random value to its size and assigns its color   
   */
	public FoodStations() {
		
		Random rand = new Random();
		this.selected = false;
		this.collided = false;
		this.size = rand.nextInt(20) + 10;
		this.capacity = this.size;
		this.setColor(ColorUtil.rgb(0,255,0));
		
	}
	
	public boolean getCollided() {
		return this.collided;
	}
	
	public void setCollided(boolean collided) {
		
	}
	
   // Method to retrieve the size of the food station
	public int getFoodSize() {
		
		return this.size;
		
	}
	
   // Method to retrieve the capacity of the food station
	public int getFoodCapacity() {
		
		return this.capacity;
		
	}
	
   // Method to update the amount of food in the food station's capacity
	public void updateFoodCapacity(int x) {
		
		this.capacity -= x;
		
	}
	
   // Method to convert all of the data in this Food Station to string for output
	public String toString() {
		
		String food = "FoodStation: loc=" + Math.round(this.getLocationX()) + "," + Math.round(this.getLocationY()) + " color=[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) + "] size=" + this.size + " capacity=" + this.capacity;
		
		return food;
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int x = (int) this.getLocationX() + pCmpRelPrnt.getX();
		int y = (int) this.getLocationY() + pCmpRelPrnt.getY();
		
		int[] xCoord = {x-this.getFoodSize()-5, x+this.getFoodSize()+5, x+this.getFoodSize()+5, x-this.getFoodSize()-5};
		int[] yCoord = {y-this.getFoodSize()-5, y-this.getFoodSize()-5, y+this.getFoodSize()+5, y+this.getFoodSize()+5};
		
		g.setColor(this.getColor());
		
		g.drawPolygon(xCoord, yCoord, 4);
		
		if(selected == false) {
			g.fillPolygon(xCoord, yCoord, 4);
		}
		
		String s = "" + this.getFoodSize();
		
		g.setColor(ColorUtil.BLACK);
		g.drawString(s, x-20, y-20);
		
	}

	


	@Override
	public Boolean contains(Point p) {
		// TODO Auto-generated method stub
		
		int leftSide = (int) this.getLocationX() - this.getFoodSize();
		int rightSide = (int) this.getLocationX() + this.getFoodSize();
		int topSide = (int) this.getLocationY() + this.getFoodSize();
		int botSide = (int) this.getLocationY() - this.getFoodSize();
		
		int px = p.getX();
		int py = p.getY();
		
		boolean result;
		
		if(px >= leftSide && px <= rightSide && py >= botSide && py <= topSide) {
			result = true;
		}
		else {
			result = false;
		}
		
		return result;
	}

	@Override
	public void setSelected(Boolean select) {
		// TODO Auto-generated method stub
		this.selected = select;
	}

	@Override
	public Boolean isSelected() {
		// TODO Auto-generated method stub
		return this.selected;
	}

	

	
	
}