package server;

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
	
	public boolean playWithBot() {
		return false;
	}
	
	public String getNickname(){
		return "takietam";
	}

	public void setOpponentNickname(String opponentNickname) {
		this.opponentNickname = opponentNickname;
	}
}
