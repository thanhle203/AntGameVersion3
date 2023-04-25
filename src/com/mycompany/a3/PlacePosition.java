package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Point;

public class PlacePosition extends Command {
	
	private GameWorld gw;//reference to GameWorld class in order to invoke functions
	private MapView mv;
	
	
	public PlacePosition(GameWorld realGW, MapView mv) {
		
		super("Place Position");
		this.gw = realGW;
		this.mv = mv;
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		float x = e.getX()-mv.getParent().getAbsoluteX()-mv.getX();
		float y = e.getY()-mv.getParent().getAbsoluteY()-mv.getY();
		
		this.gw.setPosition(x, y);
	}
	
}