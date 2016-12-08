package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
	
	public static void main(String [ ] args){
		Game gameWithBot = null;
		Game gameWithPlayer = null;
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(3513);
		} catch(Exception e){
			e.printStackTrace();
		}
		while(true){
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Client client = new Client(socket);
			
			if(client.playWithBot()){
				gameWithBot = new Game();
				gameWithBot.addClient(client);
				try {
					gameWithBot.addClient(new Client(server.accept()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Thread(gameWithBot).start();
			} else {
				if(gameWithPlayer == null){
					gameWithPlayer = new Game();
					gameWithPlayer.addClient(client);
				} else {
					gameWithPlayer.addClient(client);
					new Thread(gameWithPlayer).start();
					gameWithPlayer = null;
				}
			}
			
		}
	}
}
