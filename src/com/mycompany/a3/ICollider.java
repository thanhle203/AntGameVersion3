package com.mycompany.a3;

public interface ICollider {
	public boolean collidesWith(GameObjects obj);
	public void handleCollision(GameObjects obj, GameWorld gw);
}