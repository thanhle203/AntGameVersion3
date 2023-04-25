package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.MapView;

public class SelectObject extends Command {
	
	private GameWorld gw;//reference to GameWorld class in order to invoke functions
	private GameObjectCollection goi;
	private IIterator gi;
	private MapView mv;
	
	
	public SelectObject(GameWorld realGW, MapView mv) {
		
		super("Select Object");
		this.gw = realGW;
		this.mv = mv;
		
	}
	
	
	public void actionPerformed(ActionEvent e) { 
		
		int x = e.getX()-mv.getParent().getAbsoluteX()-mv.getX();
		int y = e.getY()-mv.getParent().getAbsoluteY()-mv.getY();
		
		Point p = new Point(x,y);
		
		System.out.println(" point is located at: " + p);
		
		gw.objectSelected(p);
		
		
	}
	
}