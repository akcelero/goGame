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
		clients.add(client);
	}

}
