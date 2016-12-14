package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.json.simple.JSONObject;

public class Connection {

	private JSONObject msg;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	private Socket socket;

	public Connection(String host, int port) {
		msg = new JSONObject();
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.out.println("Nie ma serwera na podanym hoscie / porcie");
		} catch (IOException e) {
			System.out.println("Error with opening socket");
		}		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {}
	}

	public JSONObject receive() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendDecisionAboutBot(boolean playWithBot) {
		try{
			msg = new JSONObject();
			msg.put("Type", "getDecisionAboutBot");
			msg.put("decision", playWithBot);
			out.writeObject(msg);
			out.flush();
		}catch(Exception e){}
	}

}