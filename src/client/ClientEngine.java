package client;

import java.awt.Point;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import client.player.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientEngine.
 */
public class ClientEngine implements Runnable {
	
	String host;
	int port;
	Player client;
	private boolean playWithBot;
	Connection con;
	
	public ClientEngine(String host, int port, Player client, boolean playWithBot){
		this.host = host;
		this.port = port;
		this.client = client;
		this.playWithBot = playWithBot; 
	}
	
	@Override
	public void run() {
		JSONObject data = new JSONObject();
		
		con = new Connection(host, port);
		try{
			while(data != null){
				data = con.receive();
				switch((String)data.get("Type")){
					case "getDecisionAboutBot":
						con.sendDecisionAboutBot(playWithBot);
					break;
					case "setColor":
						client.setColor((int)data.get("color"));
					break;
					case "getTurn":
						Point move = client.getMove();
						con.sendMove(move.x, move.y);
					break;
					case "getNickname":
						con.sendNickname(client.getNickname());
					break;
					case "returnCode":
						client.getReturnCode((int)data.get("code"));
					break;
					case "opponentMove":
						client.getOpponentMove( (int) data.get("x"), (int) data.get("y"));
					break;
					case "opponentNickname":
						client.setOpponentNickname((String) data.get("nickname"));
					break;
					case "resultOfGame":
						client.getResultOfGame((String) data.get("status"));
						return;
//					break;
					default:
						System.out.println("Recived undefined package! " + (String)data.get("Type"));
					break;
					case "checkOpponentArea":
						con.sendResultOfChecking(client.checkOponentArea((ArrayList<Point>) data.get("area")));
					break;
					case "getArea":
						con.sendArea(client.countArea());
					break;
					case "getScore":
						con.sendScore(client.getScore());
					break;
					case "resultCodeForArea":
						client.getResultCodeForArea((int)data.get("result"));
					break;
					case "selectingArea":
						client.showDialogAboutArea();
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Faild connection with server");
			e.printStackTrace();
		}
	}
}