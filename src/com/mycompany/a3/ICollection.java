package com.mycompany.a3;

public interface ICollection {

	public void add(GameObjects newGameObject);
	public void remove(GameObjects gameObject);
	public IIterator getIterator();
	
}
