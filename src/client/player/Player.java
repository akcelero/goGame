package client.player;

import java.awt.Point;
import java.util.ArrayList;

/**
 * The Interface Player.
 */
public interface Player {
	
	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(int color);
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public int getColor();
	
	/**
	 * Gets the move.
	 *
	 * @return the move
	 */
	public Point getMove();
	
	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname();
	
	/**
	 * Gets the opponent nickname.
	 *
	 * @return the opponent nickname
	 */
	public String getOpponentNickname();
	
	/**
	 * Sets the opponent nickname.
	 *
	 * @param oponentNickname the new oponent nickname
	 */
	public void setOpponentNickname(String opponentNickname);
	
	/**
	 * Gets the opponent move.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the opponent move
	 */
	public void getOpponentMove(int x, int y);
	
	/**
	 * Gets the return code.
	 *
	 * @param i the i
	 * @return the return code
	 */
	public void getReturnCode(int i);
	
	/**
	 * Check opponent area.
	 *
	 * @param points the points
	 * @return the int
	 */
	public int checkOponentArea(ArrayList<Point> points);
	
	/**
	 * Count area.
	 *
	 * @return the array list
	 */
	public ArrayList<Point> countArea();
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public double getScore();
	
	/**
	 * Gets the result of game.
	 *
	 * @param result the result
	 * @return the result of game
	 */
	public void getResultOfGame(String result);
	
	/**
	 * Gets the result code for area.
	 *
	 * @param code the code
	 * @return the result code for area
	 */
	public void getResultCodeForArea(int code);
	
	/**
	 * Show dialog about area.
	 */
	public void showDialogAboutArea();
}
