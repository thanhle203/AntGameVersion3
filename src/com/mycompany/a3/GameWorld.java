package com.mycompany.a3;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Point;

public class GameWorld extends Observable implements IGameWorld {
	
	private int lives;
	private int clock = 0;
	private playerAnt myAnt;
	@SuppressWarnings("unused")
	private ArrayList<GameObjects> gameObject;
	private int b = 20;
	private int g = 20;
	private boolean sound;
	private GameObjectCollection gameObjectCollection;
	private IIterator goi;
	private int worldWidth;
	private int worldHeight;
	private boolean pause;
	private BGSound bgMusic;
	private Sound spiderCollision;
	private Sound foodCollision;
	private Sound flagCollision;
	private Sound gameOver;
	private Sound win;
	private boolean positionButtonPress;
	private int selectedCount;
	private int maxLives = 3;
	private GameObjects selectedObj;
	
	
		// Constructor that defaults sound to false and initializes the game world
		public GameWorld() {
			sound = true;
			pause = false;
			positionButtonPress = false;
			selectedCount = 0;
			selectedObj = null;
			this.win = new Sound("winscreen.wav");
			this.gameOver = new Sound("lose.wav");
			this.bgMusic = new BGSound("brilliant-life-15sec.wav");
			this.spiderCollision = new Sound("TOON62.wav");
			this.foodCollision = new Sound("crunch.wav");
			this.flagCollision = new Sound("applause.wav");
			this.bgMusic.run();
			this.init();
			this.setChanged();
			this.notifyObservers(this);
		}
	
		// Initiates game world and constructs all game objects necessary to world
		public void init() {
			
			GameWorld world;
			
			if(this.myAnt == null) {
				this.myAnt = playerAnt.getPlayerAnt();
			}
			
			this.lives = maxLives;
			this.gameObjectCollection= new GameObjectCollection();
			
			for(int i = 0; i < 9; i++) {
				
				this.gameObjectCollection.add(new Flag(i));
				
			}
			
			
			this.gameObjectCollection.add(this.myAnt);
			
				
			this.gameObjectCollection.add(new Spider());	
			this.gameObjectCollection.add(new Spider());
				
			this.gameObjectCollection.add(new FoodStations());
			this.gameObjectCollection.add(new FoodStations());
			
			this.setChanged();
			this.notifyObservers(this);
		}
		
		// method to retrieve the amount of lives player ant has
		public int getLives() {
			return this.lives;
		}
		
		//Method to retrieve if sound is on or off
		public boolean getSound() {
			return this.sound;
		}
		
		// Method to set game sound
		public void setSound() {
			this.sound = !this.sound;
			
			if(this.sound && this.pause) {
				this.bgMusic.pause();
			}
			else if(this.sound && !this.pause) {
				this.bgMusic.run();
			}
			else if(!this.sound && this.pause) {
				this.bgMusic.pause();
			}
			else if(!this.sound && !this.pause){
				this.bgMusic.pause();
			}
			
			this.setChanged();
			this.notifyObservers(this);
		}
		
		public boolean getPaused() {
			return this.pause;
		}
		
		public void setPause() {
			this.pause = !this.pause;
			
			if(this.sound && this.pause) {
				this.bgMusic.pause();
			}
			else if(this.sound && !this.pause) {
				this.bgMusic.run();
			}
			else if(!this.sound && this.pause) {
				this.bgMusic.pause();
			}
			else if(!this.sound && !this.pause){
				this.bgMusic.pause();
			}
			
			
			
			this.setChanged();
			this.notifyObservers(this);
		}
		
      // Method to increment the ant's speed
		public void accelerate() {
			
			this.myAnt.setSpeed(this.myAnt.getSpeed() + 1);
			this.setChanged();
			this.notifyObservers(this);
			
		}
		
      // Method to decrement the ant's speed
		public void brake() {
			
			this.myAnt.setSpeed(this.myAnt.getSpeed() - 1);
			this.setChanged();
			this.notifyObservers(this);
		}
		
      /*
         Method to PRETEND that ant has collided with flag number x,
		 Updates the last flag reached for the ant and displays an 
         error message if the flag is not next in the sequence
      */
      public void flag(int x) {
    	  
    	  
			
			GameObjects go;
			int num = 0;
			
			goi = gameObjectCollection.getIterator();
			
			while(goi.hasNext()) {
				go = goi.getNext();
				
				if(go instanceof Flag) {
					Flag flagNum = (Flag) go;
					if(flagNum.getFlag() == x) {
						num = flagNum.getFlag();
					}
				}
			}
			
			
			if(num > this.myAnt.getLastFlagReached() && Math.abs(this.myAnt.getLastFlagReached()-num) == 1) {
				
				this.myAnt.updateLastFlagReached();
				this.flagCollision.play();
			}
			else {
				System.out.println("Cannot Update Flag. You have reached a flag that is not next in the sequence.");
			}
			
			if(this.myAnt.getLastFlagReached() == 9) {
				this.win.play();
				Dialog.show("Game Over, You Win!", "Total time: " + this.clock/100, null, null);
				this.yes();
			}
			
			this.setChanged();
			this.notifyObservers(this);
		}
		
      /*
         Method that pretends that the ant has collided with a food station.
         The method will pick a random food station for the ant to consume and 
         then add a new food station to the list of game objects in the world
         while fading the food station that was consumed to a light green.
      
      */
		public void foodCollision(FoodStations obj) {
			
			this.foodCollision.play();
			
			this.myAnt.updateFoodLevel(obj.getFoodCapacity());
			
			obj.updateFoodCapacity(0);
			
			obj.setColor(ColorUtil.rgb(144, 238, 144));
			
			gameObjectCollection.remove(obj);
			
			gameObjectCollection.add(new FoodStations());
			
			this.setChanged();
			this.notifyObservers(this);
		}
		
		/*
		 	Method to simulate any colliding with a spider,
		 	if health reaches zero, all game objects will clear except for ant 
		 	and they will be reinitialized, ant will maintain position in world
		 	but will reset food level and health and lose a life. Clock ticks will
		 	remain the same as well. Otherwise, ant just loses 1 health point, maximum speed
		 	of ant will be set to health level due to being hurt, and will 
		 	gradually fade in color.
		*/
		public void spiderCollision() {
			
			this.spiderCollision.play();
			
			GameObjects go;
			
			
			
			this.myAnt.setHealthLevel(this.myAnt.getHealthLevel() - 1);
			
			if(this.myAnt.getHealthLevel() == 0) {
				
				this.maxLives -= 1;
				
				goi = gameObjectCollection.getIterator();
				
				while(goi.hasNext()) {
					go = goi.getNext();
					gameObjectCollection.remove(go);
				}
				
				g = 20;
				b = 20;
				
				this.myAnt.setSpeed(5);
				this.myAnt.setColor(ColorUtil.rgb(255,0,0));
				this.myAnt.setHealthLevel(10);
				this.myAnt.setFoodLevel(10);
				
				if(this.maxLives == 0) {
					this.gameOver.play();
					Dialog.show("Game Over", "Time Taken: " + this.clock/10 + "\nHighest Flag Reached: " + this.myAnt.getLastFlagReached(), null, null);
					
				}
				
				this.init();
				
			}
			else {
				
				g += 20;
				b += 20;
				
				this.myAnt.setColor(ColorUtil.rgb( 255, g, b));
				
				if(this.myAnt.getSpeed() > this.myAnt.getHealthLevel()) {
					
					this.myAnt.setSpeed(this.myAnt.getHealthLevel());
					
				}
				
				
			}
			
			this.setChanged();
			this.notifyObservers(this);
			
		}
		
		// Method to steer the ant to the left by 5 degrees
		public void left() {
			
			int newHeading = this.myAnt.getHeading() + 20;
			
			if(newHeading < 0) {
				
				this.myAnt.steer(newHeading + 360);
				
			}
			else {
				this.myAnt.steer(newHeading);
			}
			
			this.setChanged();
			this.notifyObservers(this);
			
		}
		
		// Method to steer the ant to the right by 5 degrees
		public void right() {
			
			int newHeading = this.myAnt.getHeading() - 20;
			
			if(newHeading > 359) {
				
				this.myAnt.steer(newHeading - 360);
				
			}
			else {
				this.myAnt.steer(newHeading);
			}
			
			this.setChanged();
			this.notifyObservers(this);
		}
		
		/*
		 	Method to signify that the game world's clock has been ticked and 
		 	all movable game objects will move according to their heading and speed.
		 	Spider will change their heading randomly by 5 degrees to avoid straight line.
		 	This will check if spider will collide with out of bounds world limit as well
		 	when it moves and then resets its heading to move it back towards the game world.
		 	Ant's food level will decrease according to food consumption rate.
		 */
		public void clock() {
			
			clock++;
			GameObjects go;
			
			goi = gameObjectCollection.getIterator();
			while (goi.hasNext()) {
				go = goi.getNext();
				if(go instanceof Spider) {
					((Spider) go).setCollisionTimer(1);
					if(clock%100 == 0) {
						((Spider) go).setCollided(false);
					}
				}
			}
			
			Random rand = new Random();
			
			goi = gameObjectCollection.getIterator();
			int decider = rand.nextInt(2);
			if(decider == 0) {
				while(goi.hasNext()) {
					go = goi.getNext();
					if(go instanceof Spider) {
						MovableGameObject temp = (MovableGameObject) go;
						temp.setHeading(temp.getHeading()+5);
					}
				}
				
			}
			else {
				goi = gameObjectCollection.getIterator();
				while(goi.hasNext()) {
					go = goi.getNext();
					if(go instanceof Spider) {
						MovableGameObject temp = (MovableGameObject) go;
						temp.setHeading(temp.getHeading()-5);
					}
				}
				
				
			}
			goi = gameObjectCollection.getIterator();
			while(goi.hasNext()) {
				go = goi.getNext();
				if(go instanceof MovableGameObject) {
					
					MovableGameObject temp = (MovableGameObject) go;
					temp.move(this.worldWidth, this.worldHeight);
					
				}
				
			}
			
			if(this.clock % 1000 == 0) {
				this.myAnt.updateFoodLevel(-(this.myAnt.getFoodConsumptionRate()));
			}
			
			if(this.myAnt.getFoodLevel() == 0) {
				this.maxLives -= 1;
				
				goi = gameObjectCollection.getIterator();
				
				while(goi.hasNext()) {
					go = goi.getNext();
					gameObjectCollection.remove(go);
				}
				
				g = 20;
				b = 20;
				this.myAnt.setSpeed(5);
				this.myAnt.setColor(ColorUtil.rgb(255,0,0));
				this.myAnt.setHealthLevel(10);
				this.myAnt.setFoodLevel(10);
				
				if(this.maxLives == 0) {
					
					Dialog.show("No lives remaining. Game Over.", "Total Time: " + this.getClock()/100, null, null);
					
				}
				
				this.init();
				
			}
			
			this.setChanged();
			this.notifyObservers(this);
			
		}
		
		// Method to display the current score or stats of the Ant
		public void display() {
			
			System.out.print("Lives: " + this.lives);
			System.out.print(" | Clock: " + this.clock);
			System.out.print(" | Highest Flag Reached: " + this.myAnt.getLastFlagReached());
			System.out.print(" | Food Level: " + this.myAnt.getFoodLevel());
			System.out.print(" | Health Level: " + this.myAnt.getHealthLevel() + "\n");
			
			this.setChanged();
			this.notifyObservers(this);
		}
		
		// Method to display the location of all game objects in the game world
		public String map() {
			
			GameObjects go;
			String str = "";
			String newLine = System.getProperty("line.separator");
			
			goi = gameObjectCollection.getIterator();
			
			while(goi.hasNext()) {
				go = goi.getNext();
				str = str + go.toString() + newLine;
				
			}
			
			
			return str;
		}
		
		// Method that asks the user if they want to quit the game
		public void exit() {
			
			this.yes();
			this.setChanged();
			this.notifyObservers(this);
		}
		
		// Method to quit game if user chooses yes to quitting game
		public void yes() {
			
			Display.getInstance().exitApplication();
			
		}
		
		// Method to continue game if user chooses to say no when asked if they wanted to quit the game
		public void no() {
			
			System.out.println("You have denied exiting. Continuing game.");
			this.setChanged();
			this.notifyObservers(this);
		}

		// method to retrieve the clock time
		@Override
		public int getClock() {
			// TODO Auto-generated method stub
			return this.clock;
		}

		// method to retrieve the last flag reached by player ant
		@Override
		public int getFlag() {
			// TODO Auto-generated method stub
			return this.myAnt.getLastFlagReached();
		}

		// method to retrieve food level of ant
		@Override
		public int getFoodLevel() {
			// TODO Auto-generated method stub
			return this.myAnt.getFoodLevel();
		}

		// method to retrieve health level of ant
		@Override
		public int getHealthLevel() {
			// TODO Auto-generated method stub
			return this.myAnt.getHealthLevel();
		}

		// method to set the dimensions of the world to the MapView container size
		public void setDimensions(int width, int height) {
			// TODO Auto-generated method stub
			this.worldWidth = width;
			this.worldHeight = height;
			this.setChanged();
			this.notifyObservers(this);
		}
		
		// method to retrieve the width of the world
		public int getWidth() {
			return this.worldWidth;
		}
		
		// method to retrieve the height of the world
		public int getHeight() {
			return this.worldHeight;
		}

	
		public void collision() {
			
			GameObjects go;
			Spider s;
			
			boolean result;
			
			goi = gameObjectCollection.getIterator();
			
			while(goi.hasNext()) {
				go = goi.getNext();
				if(go instanceof Spider) {
					s = (Spider) go;
					result = this.myAnt.collidesWith(s);
					if(result) {
						if(s.getCollided() == false) {
							this.myAnt.handleCollision(s, this);
							s.setCollided(result);
							s.setCollisionTimer(0);
						}
						else if(s.getCollided() == true) {
							if(s.getCollisionTimer() == 50) {
								s.setCollided(false);
							}
						}
					}
				}
				else if(go instanceof Flag){
					Flag f = (Flag) go;
					result = this.myAnt.collidesWith(f);
					if(result) {
						this.myAnt.handleCollision(f, this);
					}
				}
				else if(go instanceof FoodStations) {
					FoodStations fd = (FoodStations) go;
					result = this.myAnt.collidesWith(fd);
					if(result) {
						this.myAnt.handleCollision(fd, this);
					}
				}
			}
			
			this.setChanged();
			this.notifyObservers(this);
			
		}
		
		public GameObjectCollection getCollection() {
			return this.gameObjectCollection;
		}

		public void setPosition(float x, float y) {
			// TODO Auto-generated method stub
			
			GameObjects go;
			
			goi = gameObjectCollection.getIterator();
			
			if(positionButtonPress) {
				if(selectedObj instanceof Flag) {
					Flag f = (Flag) selectedObj;
					f.setLocation(x, y);
				}
				else if(selectedObj instanceof FoodStations) {
					FoodStations fd = (FoodStations) selectedObj;
					fd.setLocation(x, y);
					
				}
				/*while (goi.hasNext()) {
					go = goi.getNext();
					if(go instanceof FixedGameObject) {
						FixedGameObject fgo = (FixedGameObject) go;
						if(fgo.isSelected()) {
							fgo.setLocation(x, y);
						}
					}
				}*/
			}
			
			selectedObj = null;
			this.positionButtonPress = false;
			
			goi = gameObjectCollection.getIterator();
			
			while(goi.hasNext()) {
				go = goi.getNext();
				if(go instanceof FoodStations) {
					((FoodStations) go).setSelected(false);
				}
				else if(go instanceof Flag) {
					((Flag) go).setSelected(false);
				}
			}
			
			this.setChanged();
			this.notifyObservers(this);
			
		}

		public void positionButtonPressed() {
			// TODO Auto-generated method stub
			this.positionButtonPress = !positionButtonPress;
			this.setChanged();
			this.notifyObservers(this);
		}
		
		public boolean getPositionButtonPressed() {
			return this.positionButtonPress;
		}
		
		public void objectSelected(Point p) {
			
			boolean objectSelection = false;

			goi = gameObjectCollection.getIterator();
			
			GameObjects go;
			
			while(goi.hasNext()) {
				go = goi.getNext();
				
					if(go instanceof FoodStations) {
						if(((FoodStations) go).contains(p)) {
							
							if(selectedObj instanceof Flag) {
								Flag f = (Flag) selectedObj;
								f.setSelected(false);
							}
							else if(selectedObj instanceof FoodStations) {
								FoodStations fd = (FoodStations) selectedObj;
								fd.setSelected(false);
								
							}
							selectedObj = (FoodStations) go;
							selectedCount++;
							objectSelection = true;
						
						}
					}
					else if(go instanceof Flag) {
						if(((Flag) go).contains(p)) {
						
							if(selectedObj instanceof Flag) {
								Flag f = (Flag) selectedObj;
								f.setSelected(false);
							}
							else if(selectedObj instanceof FoodStations) {
								FoodStations fd = (FoodStations) selectedObj;
								fd.setSelected(false);
								
							}
							selectedObj = (Flag) go;
							selectedCount++;
							objectSelection = true;
						
						}
				
					}
				
			}
			
			goi = gameObjectCollection.getIterator();
			
			if(objectSelection == false) {
				while(goi.hasNext()) {
					go = goi.getNext();
					if(go instanceof FoodStations) {
						((FoodStations) go).setSelected(false);
					}
					else if(go instanceof Flag) {
						((Flag) go).setSelected(false);
					}
				}
				selectedObj = null;
			}
			
			if(selectedObj instanceof Flag) {
				Flag f = (Flag) selectedObj;
				f.setSelected(true);
			}
			else if(selectedObj instanceof FoodStations) {
				FoodStations fd = (FoodStations) selectedObj;
				fd.setSelected(true);
				
			}
			
			
			this.setChanged();
			this.notifyObservers(this);
			
		}
		
		public int getSelectedCount() {
			return this.selectedCount;
		}
		
	}