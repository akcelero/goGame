package client.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import main.Board;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerHumanTerminal.
 */
public class PlayerHumanTerminal implements Player {
	
	/** The color. */
	private int color;
	
	/** The reader. */
	private Scanner reader;
	
	/** The nickname. */
	private String nickname = null;
	
	/** The opponent nickname. */
	private String opponentNickname;
	
	/** The board. */
	Board board;
	
	/**
	 * Instantiates a new player human terminal.
	 *
	 * @param color the color
	 */
	public PlayerHumanTerminal(int color){
		this.color  = color;
		nickname = null;
		reader = new Scanner(System.in);
		board = new Board();
	}
	
	/* (non-Javadoc)
	 * @see client.player.Player#setColor(int)
	 */
	public void setColor(int color){
		this.color = color;
		System.out.println("Setted color: " + this.color);
	}
	
	/* (non-Javadoc)
	 * @see client.player.Player#getMove()
	 */
	public Point getMove(){
		System.out.println("dawaj x");
		int x = reader.nextInt();
		System.out.println("dawaj y");
		int y = reader.nextInt();
		return new Point(x, y);
	}
	
	/* (non-Javadoc)
	 * @see client.player.Player#getNickname()
	 */
	public String getNickname(){
		if(nickname == null){
			System.out.println("Wyskakuj z pseudonimu");
			nickname = reader.nextLine();
		}
		return nickname;
	}
	
	/* (non-Javadoc)
	 * @see client.player.Player#setOponentNickname(java.lang.String)
	 */
	@Override
	public void setOpponentNickname(String opponentNickname){
		this.opponentNickname = opponentNickname;
		System.out.println("Your oponen is " + opponentNickname);
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getColor()
	 */
	@Override
	public int getColor() {return color;}

	/* (non-Javadoc)
	 * @see client.player.Player#getOponentNickname()
	 */
	@Override
	public String getOpponentNickname() {return opponentNickname;}

	/* (non-Javadoc)
	 * @see client.player.Player#getOponentMove(int, int)
	 */
	@Override
	public void getOpponentMove(int x, int y) {}

	/* (non-Javadoc)
	 * @see client.player.Player#getReturnCode(int)
	 */
	@Override
	public void getReturnCode(int i) {}

	/* (non-Javadoc)
	 * @see client.player.Player#checkOponentArea(java.util.ArrayList)
	 */
	@Override
	public int checkOponentArea(ArrayList<Point> points) {
		for(int i = 0; i < points.size(); i++){
			System.out.println(points.get(i));
		}
		System.out.println("this points belong to oponent? (y)");
		return 0;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#countArea()
	 */
	@Override
	public ArrayList<Point> countArea() {
		System.out.println("Are you seriously want to put here every Point in your Area? Hope nope...");
		return board.getPropositionOfArea(color);
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getResultOfGame(java.lang.String)
	 */
	@Override
	public void getResultOfGame(String result) {
		if(result.equals("win")){
			System.out.println("You won!");
		} else {
			System.out.println("You lose!");
		}
		
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getScore()
	 */
	@Override
	public double getScore() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getResultCodeForArea(int)
	 */
	@Override
	public void getResultCodeForArea(int code) {
	}

	/* (non-Javadoc)
	 * @see client.player.Player#showDialogAboutArea()
	 */
	@Override
	public void showDialogAboutArea() {
		// TODO Auto-generated method stub
		
	}
}