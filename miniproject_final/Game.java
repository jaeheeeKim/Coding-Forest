package miniproject_final;

import java.io.*;

public class Game implements Serializable{
	private String game;
	private String id;
	private int tot;
	
	public Game(String game, String id, int tot) {
		this.game = game;
		this.id = id;
		this.tot = tot;
	}
	
	public String getGame() {
		return game;
	}
	
	public void setGame(String game) {
		this.game = game;
	}
	
	public String getID() {
		return id;
	}
	
	/*
	public void setID(String id) {
		this.id = id;
	}
	*/
	
	public int getTot() {
		return tot;
	}
	
	public void setTot(int tot) {
		this.tot = tot;
	}
	
}
