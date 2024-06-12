package game_operation;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import input_listeners.KeyListener;
import input_listeners.MouseListener;
import on_screen_elements.Ball;
import on_screen_elements.ChangeBallVelocityUpgrade;
import on_screen_elements.Player;
import on_screen_elements.ResizeBallUpgrade;
import on_screen_elements.ResizePlayerHeightUpgrade;
import on_screen_elements.Upgrade;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH * 9 / 16;

	public boolean running = false;
	private Thread gameThread;

	private static Ball ball;
	private static Player player1, player2;

	private boolean exchange = false;
	private long startWait;

	static final int MAX_SCORE = 5;
	private Player winner;
	
	public static int gameChoice;
	
	private static ArrayList<Upgrade> upgrades;
	
	//********************** New *************************
	private int counterTillUpgrade;

	
	public static enum STATE { // comfortable way to arrange the game process
		OpeningScreen(), Game(), EndingScreen();
	}
	
	

	public static STATE gameState;

	public Game() {
		canvasSetup();
		initialize();
		new Window("PongGame", this);
		this.addKeyListener(new KeyListener(this.player1, this.player2));
		this.addMouseListener(new MouseListener());
		// this.setFocusable(true);
		
	}

	public void canvasSetup() { // sets the size of the window
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
	}

	private void initialize() {
		// requestFocus();
		Game.gameState = STATE.OpeningScreen;

		ball = new Ball();
		this.player1 = new Player(1);
		this.player2 = new Player(2);
		
		gameChoice = 0;
		
		upgrades = new ArrayList<Upgrade>();	// upgrades array
		
		this.counterTillUpgrade = 3*60;	//3 seconds for the first upgrade.
		
	}
	
	//********************** New *************************
	private void addUpgrades() {
		
		Upgrade u = null;
		Random randLoc = new Random();
		Random randSize = new Random();
		Random randChoice = new Random();
		
		
		// random location
		int ux = (int)((1/7+randLoc.nextFloat()*5/7)*WIDTH);
		int uy = (int)((1/7+randLoc.nextFloat()*5/7)*HEIGHT);
		
		
		// random type
		int upgradeChoise = 1 + randChoice.nextInt(3);
		switch(upgradeChoise) {
			case 1:	//ResizeBallUpgrade
				
				int sizeDifference = 5+randSize.nextInt(11);// values between 5 to 10
				if(randChoice.nextFloat() > 0.5)
					sizeDifference = -1*sizeDifference;
				
				u = new ResizeBallUpgrade(ux, uy,ball, sizeDifference);
				break;
			case 2://ResizePlayerHeightUpgrade
				
				Player p;
				if(randChoice.nextFloat() > 0.5)// choosing player
					p = player1;
				else
					p = player2;
				
				int heightDifference = 30+randSize.nextInt(21);// values between 30 to 50
				if(randChoice.nextFloat() > 0.5)
					heightDifference = -1*heightDifference;
				
				u = new ResizePlayerHeightUpgrade(ux, uy, p, heightDifference);
				break;
			case 3://ChangeBallVelocityUpgrade
				
				int speedDifference  = 2;
				
				if(randChoice.nextFloat() > 0.5)
					speedDifference = -1*speedDifference;
				
				u = new ChangeBallVelocityUpgrade(ux, uy,ball, speedDifference);
				break;
		}
		
		
		upgrades.add(u);
	}
	
	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running = true;
	}

	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOFTicks = 60.0;
		double ns = 1000000000 / amountOFTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			if (running)
				draw();

			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	public void draw() {
		// Initialize drawing tools
		BufferStrategy buffer = this.getBufferStrategy();

		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		
		Graphics g = buffer.getDrawGraphics();
		if(Game.gameState == STATE.OpeningScreen) {
			
			g.setColor(Color.BLACK);	// black background color
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			g.setFont(new Font("ariel",10,80));	// prints the name on screen
			g.setColor(Color.ORANGE);
			g.drawString("Pong - Game", 275,140);
			
			
			g.setFont(new Font("ariel",10,50));	// enter to start the game
			
			
			if(gameChoice == 0) {
				g.setColor(Color.WHITE);
				g.drawRect(330, 250, 380, 70);
				g.drawString("Boring Game", 375,300);
				g.drawRect(330, 350, 380, 70);
				g.drawString("Featured Game", 350,400);
				
			}else if(gameChoice == 1) {
				
				g.setColor(Color.gray);
				g.fillRect(330, 250, 380, 70);
				g.setColor(new Color(128, 255, 0));
				g.drawString("Boring Game", 375,300);
				g.setColor(Color.WHITE);
				g.drawRect(330, 250, 380, 70);
				g.drawRect(330, 350, 380, 70);
				g.drawString("Featured Game", 350,400);
				
			}else if(gameChoice == 2) {
				g.setColor(Color.gray);
				g.fillRect(330, 350, 380, 70);
				g.setColor(new Color(128, 255, 0));
				g.drawString("Featured Game", 350,400);
				
				g.setColor(Color.WHITE);
				g.drawRect(330, 250, 380, 70);
				g.drawRect(330, 350, 380, 70);
				g.drawString("Boring Game", 375,300);
			}
			
			g.drawString("Press Enter to start!", 300,500);
			
			
		} else if(Game.gameState == STATE.Game) {
			
	
			drawBackground(g);
	
			ball.draw(g);
	
			player1.draw(g);
			player2.draw(g);
			
			//********************** New *************************
			if(gameChoice == 2) {
				for(Upgrade u: upgrades)
					u.draw(g);
			}
			
		}else if(Game.gameState == STATE.EndingScreen) {
			
			g.setColor(Color.BLACK);	// black background color
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			g.setFont(new Font("ariel",10,80));	// prints the name on screen
			g.setColor(Color.ORANGE);
			g.drawString("Pong - Game", 275,140);
			
			g.setFont(new Font("ariel",10,50));	// prints the 'Game Over' on screen
			g.setColor(Color.lightGray);
			g.drawString("~The Game is Over~", 280,210);
			
			g.setFont(new Font("ariel",10,40));	// prints the players' scores
			g.setColor(Color.WHITE);
			String score1, score2;
			score1 = "Player1's score: " + this.player1.getScore();
			score2 = "Player2's score: " + this.player2.getScore();
			
			g.drawString(score1, 350,300);
			g.drawString(score2, 350,380);
			
			g.setFont(new Font("ariel",10,50));	// announcement  of the winner
			g.setColor(Color.RED);
			String winner;
			if(this.winner == this.player1)
				winner = "The Winner is Player1!";
			else
				winner = "The Winner is Player2!";
			
			g.drawString(winner, 270,480);
			
			
			g.setFont(new Font("ariel",10,35));	// quit by pressing on Escape
			g.setColor(Color.darkGray);
			g.drawString("(Press Escape to quit)", 340,530);
		}
		
		
		// dispose
		g.dispose();
		buffer.show();
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawLine(Game.WIDTH / 2, 0, Game.WIDTH / 2, Game.HEIGHT);

		// circle in the middle
		int r = 120;
		g2d.drawOval(Game.WIDTH / 2 - r, Game.HEIGHT / 2 - r, 2 * r, 2 * r);
		g2d.setStroke(new BasicStroke(2));

	}

	public void update() {
		if(Game.gameState == STATE.Game){
			if (this.exchange) {
				if (System.currentTimeMillis() - this.startWait > 2 * 1000) { // begin new round
					this.exchange = false;
	
				}
			} else {
				
				int retValue = ball.update(player1, player2);
				if (retValue == 1) { // new round
					this.startWait = System.currentTimeMillis();
					this.exchange = true;
					this.initRound();
				}
			}
	
			player1.update();
			player2.update();
			
			//********************** New *************************
			if(gameChoice == 2) {
				this.handleUpgrades();
				
				this.counterTillUpgrade = this.counterTillUpgrade - 1;
				if(this.counterTillUpgrade == 0) {
					this.addUpgrades();
					Random randChoice = new Random();
					this.counterTillUpgrade = (2+randChoice.nextInt(4))*60;	// random seconds number between 2 to 5
				}
				
			}
	
			// checks if the game is over
			int gameOver = isGameOver();
	
			if (gameOver != 0) {
				// set the winner player reference
				if (gameOver == 1)
					this.winner = player1;
				else if (gameOver == 2)
					this.winner = player2;
	
				Game.gameState = STATE.EndingScreen;
				System.out.println("Winner: " + this.winner.getPlayerNum());
			}
		}
	}

	// This function return the winner index if the game over and 0 else
	private int isGameOver() {

		if (this.player1.getScore() == Game.MAX_SCORE)
			return 1;
		if (this.player2.getScore() == Game.MAX_SCORE)
			return 2;
		
		return 0;
	}

	private void initRound() {
		this.ball.initBall();
		this.player1.initPlayer();
		this.player2.initPlayer();
	}

	// this function is responsible for saving the var value in the given range
	// between min and max.
	public static int fixedVar(int var, int min, int max) {
		if (var >= max)
			var = max;
		if (min >= var)
			var = min;
		return var;
	}
	
	//********************** New *************************
	private void handleUpgrades() {
		
		double dist;
		int upgradeX,upgradeY;
		
		int ballCenterX = ball.getX() + ball.getDiameter()/2;
		int ballCenterY = ball.getY() + ball.getDiameter()/2;
		
		
		
		for(Upgrade u: upgrades) {
			upgradeX = u.getX() + u.getUpgradeDiameter()/2;	//gets the upgrade coordinates
			upgradeY = u.getY() + u.getUpgradeDiameter()/2;
			
			// calculate the distance from the ball
			dist = Math.sqrt(Math.pow(ballCenterX-upgradeX, 2)+Math.pow(ballCenterY-upgradeY, 2));
			
			if(dist <= ball.getDiameter()/2 + u.getUpgradeDiameter()/2) {	// check collision
				u.applyModification();// Polymorphism
				
				upgrades.remove(u);
				break;
			}
			
			
		}
		
		
	}


	public static void main(String[] args) {
		new Game();
		
	}
	
	public Ball getBall() {
		return this.ball;
	}
	
	public Player getPlayer1() {
		return this.player1;
	}
	
	public Player getPlayer2() {
		return this.player2;
	}
	
}
