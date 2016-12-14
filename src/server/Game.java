package server;

import java.awt.Point;
import java.util.ArrayList;

import main.Board;

public class Game implements Runnable {
	
	ArrayList<Client> players = null;
	private int turn;
	private Point move;
	private int resultCode;
	private Board board = null;
	
	public Game(){
		players = new ArrayList<Client>();
	}
	@Override
	public void run() {
		System.out.println("Starting new game");		
	}
	public void addPlayer(Client player) {
		players.get(0).setColor(1);
		players.get(1).setColor(0);
		String client0 = players.get(0).getNickname();
		String client1 = players.get(1).getNickname();
		players.get(0).setOpponentNickname(client1);
		players.get(1).setOpponentNickname(client0);
		while(resultCode != 4200){
			
			do{
				move = players.get(turn).getMove();
				if(move == null){
					break;
				}
				resultCode = board .commitMove(move, turn);
				players.get(turn).returnResultCode(resultCode);
			}while(resultCode < 0);
			if(move != null){
				System.out.println("User " + turn + " put at X:" + (int)move.getX() + " Y:" + (int)move.getY());
			}
			turn ^= 1;
			players.get(turn).sendOpponentMove(move);
		}
	}

}
