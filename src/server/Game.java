package server;

import java.util.ArrayList;

public class Game implements Runnable {
	
	ArrayList<Client> clients = null;
	
	public Game(){
		clients = new ArrayList<Client>();
	}
	@Override
	public void run() {
		System.out.println("Starting new game");		
	}
	public void addClient(Client client) {
		clients.get(0).setColor(1);
		clients.get(1).setColor(0);
		String client0 = clients.get(0).getNickname();
		String client1 = clients.get(1).getNickname();
		clients.get(0).setOpponentNickname(client1);
		clients.get(1).setOpponentNickname(client0);
	}

}
