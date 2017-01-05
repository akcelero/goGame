package server;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONObject;

/**
 * The Class Client.
 */
public class Client {
	
	/** Nickname of player. */
	public String nickname = null;
	
	/** Score. */
	int score = 0;
	
	/** Socket for communication with client. */
	Socket socket;
	
	/** The output stream to client. */
	ObjectOutputStream out;
	
	/** The input stream from client. */
	ObjectInputStream in;
	
	/** The message to / from client. */
	JSONObject msg;
	
	/**
	 * Instantiates a new client.
	 *
	 * @param socket connected with player.
	 */
	public Client(Socket socket) {
		this.socket = socket;
		msg = new JSONObject();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected(){
		return !socket.isClosed();
	}
	
	/**
	 * Establish nickname of player.
	 */
	public void establishNickname(){
		try {
			msg = new JSONObject();
			msg.put("Type", "getNickname");
			out.writeObject(msg);
			out.flush();
			
			msg = (JSONObject) in.readObject();
			this.nickname = (String)msg.get("nickname");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname(){
		if(nickname == null){
			establishNickname();
		}
		return nickname;
	}
	
	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(int color){
		try {
			msg = new JSONObject();
			
			msg.put("Type", "setColor");
			msg.put("color", color);
			
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the move.
	 *
	 * @return the move
	 */
	public Point getMove() {
		Point point = null;
		try {
			msg = new JSONObject();
			
			msg.put("Type", "getTurn");
			out.writeObject(msg);
			out.flush();
			
			msg = (JSONObject) in.readObject();
			point = new Point((int)msg.get("x"), (int)msg.get("y"));
			
		} catch (Exception e){
			return null;
		}
		return point;
	}

	/**
	 * Return result code (when ask about area of opponent).
	 *
	 * @param resultCode the result code
	 */
	public void returnResultCode(int resultCode) {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "returnCode");
			msg.put("code", resultCode);
			
			out.writeObject(msg);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send opponent move.
	 *
	 * @param move the move
	 */
	public void sendOpponentMove(Point move){
		try {
			msg = new JSONObject();
			
			msg.put("Type", "opponentMove");
			msg.put("x", (int)move.getX());
			msg.put("y", (int)move.getY());
			
			out.writeObject(msg);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send nickname of oponent.
	 *
	 * @param nicknameOfOponent the nickname of opponent
	 */
	public void sendNicknameOfOponent(String nicknameOfOpponent) {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "opponentNickname");
			msg.put("nickname", nicknameOfOpponent);
			
			out.writeObject(msg);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the score counted by user.
	 *
	 * @return the score
	 */
	public double getScore() {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "getScore");
			
			out.writeObject(msg);
			out.flush();
			
			msg = (JSONObject) in.readObject();
			return (double)msg.get("score");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (double)-1.0;
	}

	/**
	 * Send info about win.
	 */
	public void win() {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "resultOfGame");
			msg.put("status", "win");
			
			out.writeObject(msg);
			out.flush();
			out.close();
			in.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * send info about lose.
	 */
	public void lose() {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "resultOfGame");
			msg.put("status", "lose");
			
			out.writeObject(msg);
			out.flush();
			out.close();
			in.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ask about arrea of player.
	 *
	 * @return the array list
	 */
	public ArrayList<Point> countArea() {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "getArea");
			
			out.writeObject(msg);
			out.flush();
			
			msg = (JSONObject) in.readObject();
			return (ArrayList<Point>)msg.get("area");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check opponent area.
	 *
	 * @param countArea the count area
	 * @return the int
	 */
	public int checkOpponentArea(ArrayList<Point> countArea) {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "checkOpponentArea");
			msg.put("area", countArea);
			
			out.writeObject(msg);
			out.flush();
			
			msg = (JSONObject) in.readObject();
			return (int)msg.get("result");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Gets the decision about bot.
	 *
	 * @return the decision about bot
	 */
	public boolean getDecisionAboutBot() {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "getDecisionAboutBot");
			
			out.writeObject(msg);
			out.flush();
			
			msg = (JSONObject) in.readObject();
			return (boolean)msg.get("decision");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Send result code for area of oponent.
	 *
	 * @param result code for result
	 */
	public void sendResultCodeForArea(int result) {
		
		try {
			msg = new JSONObject();
			
			msg.put("Type", "resultCodeForArea");
			msg.put("result", result);
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Change GUI to area selecting.
	 */
	public void changeToAreaSelecting() {
		try {
			msg = new JSONObject();
			
			msg.put("Type", "selectingArea");
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
