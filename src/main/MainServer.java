package main;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.Client;
import server.Game;

/**
 * The Class MainServer.
 */
public class MainServer {

	/**
	 * The main method.
	 * Inside Server has instance of game for player with bot and without.
	 * If somebody wants play with bot, then he is inserting into gameWithBot and server insert next player(bot) in same game.
	 * Then game is starting new thread and variable gameWithBot gets new instance
	 * If somebody wants play with other player he is inserting into gameWithoutBot, if this game is not waiting any more,
	 * then game is starting new thread and variable gameWithoutBot gets new instance 
	 *
	 * @param args the arguments - doesn't matter
	 */
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
				System.out.println("New User!");
				
				Client player = new Client(socket);
				boolean playWithPlayer = player.getDecisionAboutBot();
				
				if(playWithPlayer){
					if(gameWithPlayer != null){
						if(!gameWithPlayer.getUser(0).isConnected()){
							gameWithPlayer = null;
						}
					}
					if(gameWithPlayer == null){
						gameWithPlayer = new Game();
						gameWithPlayer.addUser(player);
					} else {
						gameWithPlayer.addUser(player);
						new Thread(gameWithPlayer).start();
						gameWithPlayer = null;
					}
					
				} else {
					gameWithBot = new Game();
					
					Client bot = new Client(server.accept());
					
					gameWithBot.addUser(player);
					gameWithBot.addUser(bot);
					
					new Thread(gameWithBot).start();
				}
			}
		}catch(Exception e){
			System.out.println("Error " + e.getMessage());
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
