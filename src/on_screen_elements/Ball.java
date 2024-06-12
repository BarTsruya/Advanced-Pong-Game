package on_screen_elements;

import java.awt.Color;
import java.awt.Graphics;

import game_operation.Game;


public class Ball implements Drawable{

	
	static final int REGULAR_DIAMETER = 25;
	static final int REGULAR_SPEED = 8;
	
	private int diameter;
	private int x,y;
	private int velx, vely;
	private int speed;
	
	public Ball() {
		
		this.initBall();
		
	}
	
	public void initBall() {
		this.speed = Ball.REGULAR_SPEED;
		this.diameter = Ball.REGULAR_DIAMETER;
		
		this.x = Game.WIDTH/2-this.diameter/2;
		this.y = Game.HEIGHT/2-this.diameter/2;
		
		this.velx = (int)(speed * Math.random() + 1);
		this.vely = (int)(speed * Math.random() + 1);
		
		if(Math.random() > 0.5)
			this.changeXDir();
		
		if(Math.random() > 0.5)
			this.changeYDir();
		
		//System.out.println("VelX = "+this.velx+", VelY = "+this.vely);
	}
	
	public void changeXDir() {
		this.velx *= -1;
	}
	
	public void changeYDir() {
		this.vely *= -1;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);	// draw a orange circle
		g.fillOval(this.x, this.y, this.diameter, this.diameter);
		g.setColor(new Color(100,100,100));
		g.drawOval(this.x, this.y, this.diameter, this.diameter);
	}
	
	
	public int update(Player p1, Player p2) {
		// moving the ball
		this.x += this.velx;
		this.y += this.vely;
//		checkBoundryCollisions();
		
		// checks if someone scored
		if (this.x < 0) {
			p2.incScore();
			return 1;
		}else if(this.x>Game.WIDTH - this.diameter) {
			p1.incScore();
			return 1;
		}
		
		// checks for collisions with players
		if((this.x <= p1.getWidth()) && (p1.getY() <= this.y + this.diameter/2) && (this.y + this.diameter/2 <= p1.getY()+p1.getHeight())) {
			this.changeXDir();
			return 0;
		}
		
		if((p2.getX() <= this.x + this.diameter) && (p2.getY() <= this.y + this.diameter/2) && (this.y + this.diameter/2 <= p2.getY()+p2.getHeight())) {
			this.changeXDir();
			return 0;
		}
		
		if(this.y <= 0 || this.y+this.diameter >= Game.HEIGHT) {
			this.changeYDir();
			return 0;
		}
		
		// another (important!) collisions. avoid bugs.
		if((this.x <= p1.getWidth()) && (this.y <= p1.getY() + p1.getHeight()) && (this.y + this.diameter >= p1.getY())) {
			this.changeYDir();
			return 0;
		}
		
		if((p2.getX() <= this.x + this.diameter) && (this.y <= p2.getY() + p2.getHeight()) && (this.y + this.diameter >= p2.getY())) {
			this.changeYDir();
			return 0;
		}
		
		return 0;
		
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		this.vely = vely;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	
	
	
	
}
