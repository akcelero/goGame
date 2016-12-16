package server;

import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.json.simple.JSONObject;

public class Client {
	Socket socket = null;
	int color;
	private String opponentNickname;
	private JSONObject msg;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String nickname; 
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
	
	public boolean isConnected(){
		return !socket.isClosed();
	}
	
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
	
	public String getNickname(){
		if(nickname == null){
			establishNickname();
		}
		return nickname;
	}

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
}
