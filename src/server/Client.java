package server;

import java.awt.Point;
import java.net.Socket;

public class Client {
	Socket socket = null;
	int color;
	private String opponentNickname; 
	public Client(Socket socket) {
		this.socket = socket;
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public boolean getDecisionAboutBot() {
		return true;
	}
	
	public String getNickname(){
		return "takietam";
	}

	public void setOpponentNickname(String opponentNickname) {
		this.opponentNickname = opponentNickname;
	}

	public Point getMove() {
		
		return null;
	}

	public void returnResultCode(int resultCode) {
		
	}

	public void sendOpponentMove(Point move) {
		
	}
}
