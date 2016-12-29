package server;

import java.awt.Point;
import java.net.Socket;
import java.util.ArrayList;

import main.Board;

public class Game implements Runnable {
	
	ArrayList<Client> players = null;
	Board board = null;
	
	public Game(){
		players = new ArrayList<Client>();
		board = new Board();
	}

	public Boolean waiting(){
		return players.size() < 2;
	}

	public void addUser(Client player){
		players.add(player);
	}
	
	public Client getUser(int number){
		return players.get(number);
	}

	@Override
	public void run() {
		System.out.println("Starting new game");
		System.out.flush();
		Point move;
		int resultCode = 0;
		int turn = 1;
		
		players.get(0).setColor(1);
		players.get(1).setColor(0);
		
		System.out.println("User 0 nickname: " + players.get(0).getNickname());
		System.out.println("User 1 nickname: " + players.get(1).getNickname());

		players.get(0).sendNicknameOfOponent(players.get(1).getNickname());
		players.get(1).sendNicknameOfOponent(players.get(0).getNickname());
		
		while(resultCode != 4200 && players.get(0).isConnected() && players.get(1).isConnected()){
			
			System.out.println("Waiting for user " + turn);
			do{
				move = players.get(turn).getMove();
				System.out.println("recived move");
				if(move == null){
					System.out.println("move is not valid! aborting...");
					break;
				}
				resultCode = board.commitMove(move, turn);
				System.out.println("X:" + (int)move.getX() + " Y:" + (int)move.getY());
				players.get(turn).returnResultCode(resultCode);
			}while(resultCode < 0);
			if(move != null){
				System.out.println("User " + turn + " put at X:" + (int)move.getX() + " Y:" + (int)move.getY());
			}
			turn ^= 1;
			if(move == null || !players.get(turn^1).isConnected() || !players.get(turn).isConnected()){
				break;
			}
			players.get(turn).sendOpponentMove(move);
			if(resultCode == 4200){
				System.out.println("Started counting points");
				players.get(1).changeToAreaSelecting();
				int result;
				do{
					ArrayList<Point> area = players.get(0).countArea();
					result = players.get(1).checkOpponentArea(area);
					players.get(0).sendResultCodeForArea(result);
				}while(result == 1);
				
				if(result != -1){
					players.get(0).changeToAreaSelecting();
					do{
						ArrayList<Point> area = players.get(1).countArea();
						result = players.get(0).checkOpponentArea(area);
						players.get(1).sendResultCodeForArea(result);
					}while(result == 1);
				} else {
					resultCode = 0;
				}
				if(result == -1){
					resultCode = 0;
				}
			}
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
}
