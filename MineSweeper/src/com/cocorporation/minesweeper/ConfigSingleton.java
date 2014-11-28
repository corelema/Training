package com.cocorporation.minesweeper;

public class ConfigSingleton {
	private static ConfigSingleton instance;
	private int mNbOfMines;
	private int mNbMaxMinesAsides;
	private int mWidth;
	private int mHeight;
	
	private ConfigSingleton(){
		mNbOfMines = 10;
		mNbMaxMinesAsides = 4;
		mWidth = 10;
		mHeight = 15;
	}
	
	static public ConfigSingleton getInstance(){
		if (instance == null)
			instance = new ConfigSingleton();
		return instance;
	}

	public int getNbOfMines() {
		return mNbOfMines;
	}

	public void setNbOfMines(int nbOfMines) {
		this.mNbOfMines = nbOfMines;
		System.out.println("Number of mines in the config file = " + this.mNbOfMines);
	}

	public int getNbMaxMinesAsides() {
		return mNbMaxMinesAsides;
	}

	public void setNbMaxMinesAsides(int nbMaxMinesAsides) {
		this.mNbMaxMinesAsides = nbMaxMinesAsides;
	}

	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int width) {
		this.mWidth = width;
	}

	public int getHeight() {
		return mHeight;
	}

	public void setHeight(int height) {
		this.mHeight = height;
	}

	public static void setInstance(ConfigSingleton instance) {
		ConfigSingleton.instance = instance;
	}
}
