package client;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

/**
 * The Class Connection.
 */
public class Connection {
	
	/** The socket. */
	Socket socket;
	
	/** The output stream. */
	ObjectOutputStream out = null;
	
	/** The input stream. */
	ObjectInputStream in = null;
	
	/** The message to / from server. */
	JSONObject msg;
	
	/**
	 * Instantiates a new connection.
	 *
	 * @param host the host
	 * @param port the port
	 */
	public Connection(String host, int port){
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
	
	public boolean isConnected(){
		if(socket == null || socket.isClosed())
			return false;
		return socket.isConnected();
	}
	
	/**
	 * Send move.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void sendMove(int x, int y){
		try{
			msg = new JSONObject();
			msg.put("Type", "sendMove");
			msg.put("x", x);
			msg.put("y", y);
			
			out.writeObject(msg);
			out.flush();
			
			msg.clear();
		}catch(Exception e){
			
		}
	}

	/**
	 * Receive.
	 *
	 * @return the JSON object
	 */
	public JSONObject receive() {
		try {
			return (JSONObject) in.readObject();
		} catch (Exception e) {}
		return null;
	}

	/**
	 * Send nickname.
	 *
	 * @param nickname the nickname
	 */
	public void sendNickname(String nickname) {
		try{
			msg = new JSONObject();
			msg.put("Type", "sendNickname");
			msg.put("nickname", nickname);
			
			out.writeObject(msg);
			out.flush();
			
		}catch(Exception e){
			
		}
	}

	/**
	 * Send result of checking.
	 *
	 * @param result the result
	 */
	void sendResultOfChecking(int result) {
		try{
			msg = new JSONObject();
			msg.put("Type", "sendNickname");
			msg.put("result", result);
			
			out.writeObject(msg);
			out.flush();
			
		}catch(Exception e){
			
		}		
	}

	/**
	 * Send area.
	 *
	 * @param area the area
	 */
	public void sendArea(ArrayList<Point> area) {
		try{
			msg = new JSONObject();
			msg.put("Type", "sendNickname");
			msg.put("area", area);
			System.out.println(msg.get("area"));
			out.writeObject(msg);
			out.flush();
			
		}catch(Exception e){
			
		}
		
	}

	/**
	 * Send score.
	 *
	 * @param score the score
	 */
	public void sendScore(double score) {
		try{
			msg = new JSONObject();
			msg.put("Type", "sendScore");
			msg.put("score", score);
			out.writeObject(msg);
			out.flush();
		}catch(Exception e){
			
		}
	}

	/**
	 * Send decision about bot.
	 *
	 * @param true if player wants play with bot
	 */
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
