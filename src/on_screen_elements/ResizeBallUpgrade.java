package on_screen_elements;

import java.awt.Color;
import java.awt.Graphics;

import game_operation.Game;

public class ResizeBallUpgrade extends Upgrade {

	private final int MAX_SIZE = 50;	
	private final int MIN_SIZE = 10;
	
	private Ball ball;
	private int sizeDiff;
	
	//Constructor
	public ResizeBallUpgrade(int x, int y, Ball b, int sizeDifference) {
		super(x, y);
		this.ball = b;
		this.sizeDiff = sizeDifference;
	}
	
	// This method change the ball's diameter.
	public void applyModification() {
		int size = this.ball.getDiameter()+this.getSizeDiff();
		this.ball.setDiameter(Game.fixedVar(size, MIN_SIZE, MAX_SIZE));
		
	}
	
	
	
	public int getSizeDiff() {
		return sizeDiff;
	}

	
	@Override
	public void draw(Graphics g) {	// Here we draw the upgrade
		
		if(this.getSizeDiff() > 0)
			g.setColor(new Color(0, 128, 43));	// green color
		else
			g.setColor(new Color(220,0,0));	// red color
		
		
		// white circle in the bigger filled circle
		g.fillOval(this.x, this.y, upgradeDiameter, upgradeDiameter);
		g.setColor(Color.white);
		g.drawOval(this.x+upgradeDiameter/4, this.y+upgradeDiameter/4, upgradeDiameter/2, upgradeDiameter/2);
		
	}
	
	
}
