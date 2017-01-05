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
	
	/** The host. */
	String host;
	
	/** The port. */
	int port;
	
	/** The client. */
	Player client;
	
	/** The play with bot. */
	private boolean playWithBot;
	
	/** The con. */
	Connection con;
	
	/**
	 * Instantiates a new client engine.
	 *
	 * @param host the host
	 * @param port the port
	 * @param client the client
	 * @param playWithBot the play with bot
	 */
	public ClientEngine(String host, int port, Player client, boolean playWithBot){
		this.host = host;
		this.port = port;
		this.client = client;
		this.playWithBot = playWithBot; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		JSONObject data = new JSONObject();
		
		con = new Connection(host, port);
		try{
			while(data != null){
				data = con.receive();
				if(!con.isConnected() || data == null){
					break;
				}
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
						System.out.println("Sended: X:" + move.x + " Y:" + move.y);
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
					case "checkOpponentArea":
						con.sendResultOfChecking(
								client.checkOponentArea((ArrayList<Point>) data.get("area"))
							);
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
					default:
						System.out.println("Recived undefined package! " + (String)data.get("Type"));
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Faild connection with server");
			e.printStackTrace();
		}
	}
}