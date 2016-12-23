package client.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import main.Board;

public class PlayerHumanTerminal implements Player {
	
	private int color;
	private Scanner reader;
	private String nickname = null;
	private String opponentNickname;
	Board board;

	public PlayerHumanTerminal(){
		nickname = null;
		reader = new Scanner(System.in);
		board = new Board();
	}
	
	public void setColor(int color){
		this.color = color;
		System.out.println("Setted color: " + this.color);
	}
	
	public Point getMove(){
		System.out.println("dawaj x");
		int x = reader.nextInt();
		System.out.println("dawaj y");
		int y = reader.nextInt();
		return new Point(x, y);
	}
	
	public String getNickname(){
		if(nickname == null){
			System.out.println("Wyskakuj z pseudonimu");
			nickname = reader.nextLine();
		}
		return nickname;
	}
	
	@Override
	public void setOpponentNickname(String opponentNickname){
		this.opponentNickname = opponentNickname;
		System.out.println("Your oponen is " + opponentNickname);
	}
	
	@Override
	public int getColor() {return color;}
	
	@Override
	public String getOpponentNickname() {return opponentNickname;}

	/* (non-Javadoc)
	 * @see client.player.Player#getOponentMove(int, int)
	 */
	@Override
	public void getOpponentMove(int x, int y) {}
	
	@Override
	public void getReturnCode(int i) {}
	
	@Override
	public int checkOponentArea(ArrayList<Point> points) {
		for(int i = 0; i < points.size(); i++){
			System.out.println(points.get(i));
		}
		System.out.println("this points belong to oponent? (y)");
		return 0;
	}
	
	@Override
	public ArrayList<Point> countArea() {
		System.out.println("Are you seriously want to put here every Point in your Area? Hope nope...");
		return board.getPropositionOfArea(color);
	}
	
	@Override
	public void getResultOfGame(String result) {
		if(result.equals("win")){
			System.out.println("You won!");
		} else {
			System.out.println("You lose!");
		}
		
	}
	
	@Override
	public double getScore() {
		return 0;
	}
	
	@Override
	public void getResultCodeForArea(int code) {
	}
	
	@Override
	public void showDialogAboutArea() {
		// TODO Auto-generated method stub
		
	}
}
