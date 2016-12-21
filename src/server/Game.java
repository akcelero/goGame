package server;

import java.awt.Point;
import java.net.Socket;
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
		players.get(0).sendNicknameOfOponent(players.get(1).getNickname());
		players.get(1).sendNicknameOfOponent(players.get(0).getNickname());
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
		if(!players.get(0).isConnected() || players.get(1).getScore() > players.get(0).getScore()){
			players.get(1).win();
			players.get(0).lose();
		} else {
			players.get(0).win();
			players.get(1).lose();
		}
		players.remove(1);
		players.remove(0);
		return;
	}
	public Client getUser(int i) {
		return players.get(i);
	}

}
