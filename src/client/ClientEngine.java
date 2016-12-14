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
				switch((String)data.get("Type")){
					case "getDecisionAboutBot":
						con.sendDecisionAboutBot(playWithBot);
					break;
					case "setColor":
					break;
					case "getTurn":
					break;
					case "getNickname":
					break;
					case "returnCode":
					break;
					case "opponentMove":
					break;
					case "opponentNickname":
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