package client.player;

import java.awt.Point;
import java.util.Scanner;

import main.Board;

public class PlayerHumanTerminal implements Player {
	
	private int color;
	private Scanner reader;
	private String nickname = null;
	private String opponentNickname;
	Board board;

	@Override
	public void setColor(int color){
		this.color = color;
		System.out.println("Setted color: " + this.color);
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Point getMove(){
		System.out.println("dawaj x");
		int x = reader.nextInt();
		System.out.println("dawaj y");
		int y = reader.nextInt();
		return new Point(x, y);
	}

	@Override
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
}
