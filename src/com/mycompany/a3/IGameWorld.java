package com.mycompany.a3;

public interface IGameWorld {

	boolean getSound();
	void setSound();
	void accelerate();
	void brake();
	void flag(int x);
	void foodCollision(FoodStations obj);
	void spiderCollision();
	void left();
	void right();
	void clock();
	void display();
	String map();
	void exit();
	void yes();
	void no();
	int getLives();
	int getClock();
	int getFlag();
	int getFoodLevel();
	int getHealthLevel();
	GameObjectCollection getCollection();
	
	
	
}
