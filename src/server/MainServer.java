package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
	
	public static void main(String [ ] args){
		ArrayList<Game> games = null;
		ServerSocket server = null;
		try {
			server = new ServerSocket(3513);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Socket socket = null;
		try {
			socket = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		games.get(0).addClient(new Client(socket));
	}
}
