package on_screen_elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game_operation.Game;

public class ChangeBallVelocityUpgrade extends Upgrade {

	private final int MAX_SPEED = 12;	
	private final int MIN_SPEED = 2;
	
	private Ball ball;
	private int speedDiff;
	
	//Constructor
	public ChangeBallVelocityUpgrade(int x, int y, Ball b, int speedDifference) {
		super(x, y);
		this.ball = b;
		this.speedDiff = speedDifference;
		
	}
	

	
	public int getSpeedDiff() {
		return speedDiff;
	}
	

	public void applyModification() {
		// change speed
		int newSpeed = this.ball.getSpeed()+this.getSpeedDiff();
		this.ball.setSpeed(Game.fixedVar(newSpeed, MIN_SPEED, MAX_SPEED));	
		
		// change direction
		this.ball.setVelx((int)(this.ball.getSpeed() * Math.random() + 3));
		this.ball.setVely((int)(this.ball.getSpeed() * Math.random() + 3));
		
		
		if(Math.random() > 0.5)
			this.ball.changeXDir();
		
		
		if(Math.random() > 0.5)
			this.ball.changeYDir();
		
	}

	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;	
		g2d.setStroke(new BasicStroke(2));
		

		g.setColor(Color.BLUE);	// draw circle
		g.fillOval(this.x, this.y, upgradeDiameter, upgradeDiameter);
		
		String star = "*";	// print '*' in the middle of the screen
		g.setColor(Color.WHITE);
		g.setFont(new Font("ariel",10, 40));
		g.drawString(star, this.x+this.getUpgradeDiameter()/4+2, this.y+this.getUpgradeDiameter()/2+14);
		
	}

	
	
}

