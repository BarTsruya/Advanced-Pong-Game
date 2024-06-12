package input_listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game_operation.Game;
import game_operation.Game.STATE;
import on_screen_elements.Player;

public class KeyListener extends KeyAdapter{

	
	private Player player1, player2;
	
	
	public KeyListener(Player p1, Player p2) {
		
		this.player1 = p1;
		this.player2 = p2;
		
		
	}
	

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(Game.gameState == STATE.OpeningScreen){
			//********************** New *************************
			if(key == KeyEvent.VK_ENTER && Game.gameChoice != 0) {	// starting the game by pressing on enter
				Game.gameState = STATE.Game;
			}
		} else if(Game.gameState == STATE.Game) {
			
			switch(key) {	// The role of each key 
			case KeyEvent.VK_W:
				this.player1.setVel(-Player.VELOCITY);
				break;
			case KeyEvent.VK_S:
				this.player1.setVel(Player.VELOCITY);
				break;
			case KeyEvent.VK_UP:
				this.player2.setVel(-Player.VELOCITY);
				break;
			case KeyEvent.VK_DOWN:
				this.player2.setVel(Player.VELOCITY);
				break;
			default:
				break;
			}
			
		}
		else if(Game.gameState == STATE.EndingScreen) {
			
			if(key == KeyEvent.VK_ESCAPE)	// close the window
				System.exit(1);
			
			
		}
		
		
	}
	
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(Game.gameState == STATE.Game) {
			
			switch(key) {
			case KeyEvent.VK_W:
				this.player1.setVel(0);
				break;
			case KeyEvent.VK_S:
				this.player1.setVel(0);
				break;
			case KeyEvent.VK_UP:
				this.player2.setVel(0);
				break;
			case KeyEvent.VK_DOWN:
				this.player2.setVel(0);
				break;
			default:
				break;
			}
			
		}
		
	}
}