package input_listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game_operation.Game;
import game_operation.Game.STATE;


public class MouseListener extends MouseAdapter {
	
	

	private boolean isMouseInRect(int xpos, int ypos, int x, int y, int w, int h) {
		return x < xpos && xpos < x+w && y < ypos && ypos < y+h;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int xpos = e.getX();
		int ypos = e.getY();
		
		if(Game.gameState == STATE.OpeningScreen) {
			if(isMouseInRect(xpos, ypos,330, 250, 380, 70)) {
				Game.gameChoice = 1;
			}else if(isMouseInRect(xpos, ypos,330, 350, 380, 70)) {
				Game.gameChoice = 2;
			}
			//********************** New *************************
			System.out.println(Game.gameChoice);
		}
		
	}
}
