package server;

import java.net.Socket;

public class Client {
	Socket socket = null;
	public Client(Socket socket) {
		this.socket = socket;
	}
}
