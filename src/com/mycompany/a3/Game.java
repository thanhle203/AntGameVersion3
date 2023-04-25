package com.mycompany.a3;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;
import java.util.Random;

import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable {
	
	private GameWorld gw;
	private GameWorldProxy gwp;
	private MapView mv;
	private ScoreView sv;
	private UITimer timer;
	private Random r;
	private ButtonStyle accb;
	private ButtonStyle bkb;
	private ButtonStyle rtb;
	private ButtonStyle ltb;
	private ButtonStyle psb;
	private Accelerate acc;
	private SoundCommand snd;
	private Pause pse;
	private About abt;
	private Exit ex;
	private RightTurn rt;
	private LeftTurn lt;
	private Brake bk;
	private Help hp;
	private Position ps;
	private SelectObject sl;
	private PlacePosition pp;
	
	public static int clock_ms = 20;
	
	// method to instantiate a game
	public Game() {
		
		// create new instances of game world, game world proxy, map view, and score view
		gw = new GameWorld();
		gwp = new GameWorldProxy(gw);
		mv = new MapView(gwp);
		sv = new ScoreView(gwp);
		
		timer = new UITimer(this);
		timer.schedule(clock_ms, true, this);
		
		// add the appropriate classes to their observable
		gw.addObserver(gwp);
		gwp.addObserver(mv);
		gwp.addObserver(sv);
		
		// Set layout for form and create a new toolbar
		this.setLayout(new BorderLayout());
		
		Toolbar tool = new Toolbar();
		this.setToolbar(tool);
		tool.setTitle("Ant Game Version 3");
		
		// create new containers for buttons on south, east, and west of borders
		Container cpNorth = new Container(new FlowLayout(Component.CENTER));
		Container cpSouth = new Container(new FlowLayout(Component.CENTER));
		Container cpEast = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Container cpWest = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		cpEast.getAllStyles().setPadding(Component.TOP, 50);
		cpWest.getAllStyles().setPadding(Component.TOP, 50);
		
		
		
		// create new instances of all commands
		acc = new Accelerate(gw);
		snd = new SoundCommand(gw);
		pse = new Pause(gw);
		abt = new About(gw);
		ex = new Exit(gw);
		rt = new RightTurn(gw);
		lt = new LeftTurn(gw);
		bk = new Brake(gw);
		hp = new Help(gw);
		ps = new Position(gw);
		sl = new SelectObject(gw, mv);
		pp = new PlacePosition(gw, mv);
		
		// create a check box for toggling sound
		CheckBox soundChk = new CheckBox("Sound");
		soundChk.setSelected(true);
		soundChk.setCommand(snd);
		
		// add the appropriate commands to the side menu on the left of toolbar
		tool.addCommandToLeftSideMenu(acc);
		tool.addComponentToLeftSideMenu(soundChk);
		tool.addCommandToLeftSideMenu(abt);
		tool.addCommandToLeftSideMenu(ex);
		
		
		// add appropriate command to the right side of the toolbar
		tool.addCommandToRightBar(pse);
		tool.addCommandToRightBar(hp);
		
		// create buttons for all of the commands necessary to game
		accb = new ButtonStyle("Accelerate");
		bkb = new ButtonStyle("Brake");
		rtb = new ButtonStyle("Right Turn");
		ltb = new ButtonStyle("Left Turn");
		psb = new ButtonStyle("Position");
		
		// assign the buttons to their respective commands
		accb.setCommand(acc);
		bkb.setCommand(bk);
		rtb.setCommand(rt);
		ltb.setCommand(lt);
		psb.setCommand(ps);
		
		// add buttons to the correct panel around center container
		cpEast.add(bkb);
		cpEast.add(rtb);
		
		cpWest.add(accb);
		cpWest.add(ltb);
		
		cpSouth.add(psb);
		
		cpNorth.add(sv);
		
		// add key binding listener to invoke commands according to the key pressed
		addKeyListener((int)'a', acc);
		addKeyListener((int)'b', bk);
		addKeyListener((int)'l', lt);
		addKeyListener((int)'r', rt);
		addKeyListener((int)'p', pse);
		
		this.mv.addPointerPressedListener(this.sl);
		
		// add map view, score view, and panels to their respective locations
		this.add(BorderLayout.NORTH, cpNorth);
		this.add(BorderLayout.CENTER, mv);
		this.add(BorderLayout.SOUTH, cpSouth);
		this.add(BorderLayout.EAST, cpEast);
		this.add(BorderLayout.WEST, cpWest);
		
		
		
		this.show();
		
		// set dimensions of game world to dimensions of map view container
		this.gw.setDimensions(mv.getWidth(), mv.getHeight());
		
		
		
	}
	
	
	public void run() {
		
		this.pauseGame();
		
		if(!this.gwp.getPaused()) {
		
			this.gwp.clock();
			
			this.gwp.collision();
			
		}
		
			
		
	}
	
	public void pauseGame() {
		
		if(this.gwp.getPaused() == true) {
			
			accb.setEnabled(false);
			bkb.setEnabled(false);
			rtb.setEnabled(false);
			ltb.setEnabled(false);
			psb.setEnabled(true);
			
			removeKeyListener((int)'a', acc);
			removeKeyListener((int)'b', bk);
			removeKeyListener((int)'l', lt);
			removeKeyListener((int)'r', rt);
			
			if(this.gw.getPositionButtonPressed()) {
				this.mv.removePointerPressedListener(this.sl);
				this.mv.addPointerPressedListener(this.pp);
			}
			else if(!this.gw.getPositionButtonPressed()){
				this.mv.removePointerPressedListener(this.pp);
				this.mv.addPointerPressedListener(this.sl);
			}
			
		}
		
		else {
			
			accb.setEnabled(true);
			bkb.setEnabled(true);
			rtb.setEnabled(true);
			ltb.setEnabled(true);
			psb.setEnabled(false);
			
			addKeyListener((int)'a', acc);
			addKeyListener((int)'b', bk);
			addKeyListener((int)'l', lt);
			addKeyListener((int)'r', rt);
			
			this.mv.removePointerPressedListener(this.pp);
		}
		
	}
		
	
	
	
	
}

