package on_screen_elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game_operation.Game;


public class Player implements Drawable{
	
	// ********************** New *************************
	public static final int VELOCITY = 5;
	
	static final int REGULAR_WIDTH = 20;
	static final int REGULAR_HEIGHT = 170;
	
	private int x,y;
	private int vel;
	
	private int width, height;
	private int playerNum;
	
	private Color color;
	private int score;
	
	public Player(int playerNumber) {
		this.playerNum = playerNumber;
		
		this.initPlayer();
		
		if(playerNumber == 1) {
			this.x = 0;
			this.color = new Color(184, 77, 255);
		}else {
			this.x = Game.WIDTH-this.width;
			this.color = Color.cyan;
		}
		
		this.score = 0;
	}
	
	
	public void initPlayer() {
		this.vel = 0;
		this.width = Player.REGULAR_WIDTH;
		this.height = Player.REGULAR_HEIGHT;
		this.y = Game.HEIGHT/2-this.height/2;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.x, this.y, this.width, this.height);
		this.drawScore(g);
	}
	
	public void drawScore(Graphics g) {
		String scoreStr;
		scoreStr = ""+this.score;
		g.setFont(new Font("ariel",10,30));
		if(this.playerNum == 1)
			g.drawString(scoreStr, Game.WIDTH/2-50, 40);
		else
			g.drawString(scoreStr, Game.WIDTH/2+50, 40);
	}
	
	public void update() {
		this.y += this.vel;
		this.y = Game.fixedVar(this.y, 0, Game.HEIGHT-this.height);
	}
	
	public void incScore() {
		this.score++;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public void setVel(int vel) {
		this.vel = vel;
	}


	public int getScore() {
		return score;
	}


	public int getPlayerNum() {
		return playerNum;
	}


	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	
	
	
}




