package com.mycompany.a3;

import java.util.Observer;
import java.util.Observable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {
	
	private GameWorldProxy gwp;
	private GameObjectCollection goi;
	private IIterator gi;
	
	// method to instantiate a Map View instance
	public MapView(GameWorldProxy gwp) {
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS)); // Set a box layout. 
		this.gwp = gwp;
		this.goi = gwp.getCollection();
		this.gi = goi.getIterator();
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255,0,0)));// create a red border around the center container
		
	}
	
	// method to update and refresh the map view after any function is invoked
	public void update (Observable o, Object arg) {
		
		IGameWorld igw = (IGameWorld) arg;
		
		this.goi = igw.getCollection();
		
		this.gi = goi.getIterator();
		
		System.out.println("");
		System.out.println(this.gwp.map());
		System.out.println("");
		
		this.repaint();

		
	}
	
	@Override 
	public void paint(Graphics g) {
		
		super.paint(g);
		while (this.gi.hasNext()) {
			GameObjects go = this.gi.getNext();
			Point pCmpRelPrnt = new Point(this.getX(), this.getY());
			go.draw(g, pCmpRelPrnt);
		}
		
	}
	
}