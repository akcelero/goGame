package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.Client;
import server.Game;

public class MainServer {
	
	public static void main(String [ ] args){
		Game gameWithBot = null;
		Game gameWithPlayer = null;

		ServerSocket server = null;
		try {
			server = new ServerSocket(3513);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			while(true){
								
				Socket socket = server.accept();
				
				Client player = new Client(socket);
				boolean playWithPlayer = player.getDecisionAboutBot();
				
				if(player.getDecisionAboutBot()){
					if(gameWithPlayer == null){
						gameWithPlayer = new Game();
						gameWithPlayer.addPlayer(player);
					} else {
						gameWithPlayer.addPlayer(player);
						new Thread(gameWithPlayer).start();
						gameWithPlayer = null;
					}
					
				} else {
					gameWithBot = new Game();
					
					Client bot = new Client(server.accept());
					
					gameWithBot.addPlayer(player);
					gameWithBot.addPlayer(bot);
					
					new Thread(gameWithBot).start();
				}
			}
		}catch(Exception e){}
	}
}
