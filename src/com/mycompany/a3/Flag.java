package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Flag extends FixedGameObject {
	
	private int size = 25;
	@SuppressWarnings("unused")
	private int color;
	private int seqNum;
	private Point location;
	private boolean selected;
	
   // Constructor method for creating an instance of Flag
	public Flag(int x) {
		
		this.selected = false;
		
      // Sets sequence number of this instance of Flag by adding 1 to integer parameter
		seqNum = x + 1;
      
      // Sets color of instance of Flag
		this.setColor();
		
	}
	
	//Method to set color of Flag
	public void setColor() {
		this.color = ColorUtil.rgb(0,0,255);
	}
	
	public int getColor() {
		return this.color;
	}
		
	
   // Method to retrieve the sequence number of this instance of Flag
	public int getFlag() {
		
		return this.seqNum;
		
	}
	
   // Method to retrieve size of this instance of Flag
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
	
	
   // Method to convert all data of this instance of Flag into a string for display
	public String toString() {
		
		String flag = "Flag: loc=" + Math.round(this.getLocationX()) + "," + Math.round(this.getLocationY())
		+ " color=[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + 
		ColorUtil.blue(this.getColor()) + "] size=" + this.getSize() + " seqNum=" + this.seqNum;
		
		return flag;
		
	}

	@Override
	public void draw(Graphics g, com.codename1.ui.geom.Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		
		int x = (int) this.getLocationX() + pCmpRelPrnt.getX();
		int y = (int) this.getLocationY() + pCmpRelPrnt.getY();
		
		int[] xCoord = {x-this.getSize(), x+this.getSize(), x};
		int[] yCoord = {y-this.getSize(), y-this.getSize(), y+this.getSize()};
		
		g.setColor(this.getColor());
		
		String s = "" + this.seqNum;
		
		g.drawPolygon(xCoord, yCoord, 3);
		if(selected == false) {
			g.fillPolygon(xCoord, yCoord, 3);
		}
		
		g.setColor(ColorUtil.WHITE);
		g.drawString(s, x-this.getSize()/2+2, y-this.getSize()/2-7);
		
	}

	

	@Override
	public Boolean contains(com.codename1.ui.geom.Point p) {
		// TODO Auto-generated method stub
		int leftSide = (int) this.getLocationX() - this.getSize();
		int rightSide = (int) this.getLocationX() + this.getSize();
		int topSide = (int) this.getLocationY() + this.getSize();
		int botSide = (int) this.getLocationY() - this.getSize();
		
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

	@Override
	protected void setCollided(boolean result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean getCollided() {
		// TODO Auto-generated method stub
		return false;
	}
	
}