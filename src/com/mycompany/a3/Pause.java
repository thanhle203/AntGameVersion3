package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class Pause extends Command {
	
	private GameWorld gw;//reference to GameWorld class in order to invoke functions
	
	// method to instantiate the sound command
	public Pause(GameWorld realGW) {
		
		super("Pause");
		this.gw = realGW;
		
	}
	
	//method to invoke the required Sound toggle function after button or key press
	public void actionPerformed(ActionEvent e) {
		this.gw.setPause();
	}
	
}