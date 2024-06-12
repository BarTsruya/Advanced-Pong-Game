package on_screen_elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game_operation.Game;

public class ResizePlayerHeightUpgrade extends Upgrade{

	private final int MAX_HEIGHT = 350;	
	private final int MIN_HEIGHT = 75;
	
	private Player player;
	private int heightDiff;
	
	//Constructor
	public ResizePlayerHeightUpgrade(int x, int y, Player player, int heightDifference) {
		super(x, y);
		this.player = player;
		this.heightDiff = heightDifference;
	}
	
	
	// Getters
	public Player getPlayer() {
		return player;
	}


	public int getHeightDiff() {
		return heightDiff;
	}


	public void applyModification() {
		int newHeight = this.player.getHeight()+this.getHeightDiff();
		this.player.setHeight(Game.fixedVar(newHeight, MIN_HEIGHT, MAX_HEIGHT));
	}
	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;	
		g2d.setStroke(new BasicStroke(2));
		
		
		String heightSignStr;
		
		if(this.getHeightDiff() > 0) {
			g.setColor(new Color(0, 128, 43));
			heightSignStr = "+";
			g.fillOval(this.x, this.y, upgradeDiameter, upgradeDiameter);
			
			g.setColor(Color.white);
			g.setFont(new Font("ariel",10,30));
			g.drawString(heightSignStr, this.x+this.getUpgradeDiameter()/4, this.y+this.getUpgradeDiameter()/2+5);
		}else {
			g.setColor(new Color(220,0,0));
			heightSignStr = "-";
			g.fillOval(this.x, this.y, upgradeDiameter, upgradeDiameter);
			
			g.setColor(Color.white);
			g.setFont(new Font("ariel",10,30));
			g.drawString(heightSignStr, this.x+this.getUpgradeDiameter()/4+5, this.y+this.getUpgradeDiameter()/2+8);
		}
		
		
		
		
		
		
	}

}

