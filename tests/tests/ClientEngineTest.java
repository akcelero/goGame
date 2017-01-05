package tests;
import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import client.ClientEngine;
import client.player.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientEngineTest.
 */
public class ClientEngineTest {
	
	/** The server. */
	ServerSocket server = null;
	
	/** The client socket. */
	private Socket clientSocket = null;
	
	/** The player. */
	@Mock
	Player player;
	
	/** The mockito rule. */
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	/** The global timeout. */
	@Rule
    public Timeout globalTimeout = Timeout.seconds(2);
	
	/** The obj out stream. */
	private ObjectOutputStream objOutStream;
	
	/** The obj in stream. */
	private ObjectInputStream objInStream;
	
	/** The json. */
	private JSONObject json;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		
		player = Mockito.mock(Player.class);
		
		server = new ServerSocket(3513);
		new Thread(new ClientEngine("127.0.0.1", 3513, player, false)).start();
		clientSocket = server.accept();
		objOutStream = new ObjectOutputStream(clientSocket.getOutputStream());
		objInStream = new ObjectInputStream(clientSocket.getInputStream());
		json = new JSONObject();
		System.out.println("test");
	}
	
	/**
	 * Send json.
	 */
	void sendJson(){
		try {
			objOutStream.writeObject(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recive json.
	 */
	void reciveJson(){
		try {
			json = (JSONObject)objInStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}

	/**
	 * Test get nickname.
	 */
	@Test
	public void testGetNickname() {
		Mockito.when(player.getNickname()).thenReturn("testNickname");
		json.put("Type", "getNickname");
		sendJson();
		reciveJson();
		assertEquals("testNickname", json.get("nickname"));
	}
	
	@Test
	public void testGetDecisionAboutBot() {
		json.put("Type", "getDecisionAboutBot");
		sendJson();
		reciveJson();
		assertEquals(false, json.get("decision"));
	}
	
	/**
	 * Test get move.
	 */
	@Test
	public void testGetMove() {
		Mockito.when(player.getMove()).thenReturn(new Point(1,5));
		json.put("Type", "getTurn");
		sendJson();
		reciveJson();
		assertEquals(1, json.get("x"));
		assertEquals(5, json.get("y"));
	}
	
	/**
	 * Test get score.
	 */
	@Test
	public void testGetScore() {
		Mockito.when(player.getScore()).thenReturn((double)42.0);
		json.put("Type", "getScore");
		sendJson();
		reciveJson();
		assertEquals(42.0, json.get("score"));
	}

	/**
	 * Clean up after.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void cleanUpAfter() throws Exception{
		while(!server.isClosed()){
			server.close();			
		}
	}
	
}
