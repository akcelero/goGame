package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import server.Client;
// TODO: Auto-generated Javadoc

/**
 * The Class ClientTest.
 */
//@Ignore
public class ClientTest {

	/** The server. */
	private ServerSocket server = null;
	
	/** The socket client. */
	private Socket socketClient = null;
	
	/** The socket server. */
	private Socket socketServer = null;
	
	/** The in. */
	private ObjectInputStream in;
	
	/** The out. */
	private ObjectOutputStream out;
	
	/** The client. */
	private Client client = null;
	
	/** The json. */
	JSONObject json = null;

	/** The global timeout. */
	@Rule
    public Timeout globalTimeout = Timeout.seconds(2);
	
	/** The thread. */
	private Thread thread;
	
	/**
	 * Sets the up before.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		server = new ServerSocket(3513);
		socketClient = new Socket("localhost", 3513);
		socketServer = server.accept();
		(thread = new Thread(
				new Runnable(){

					public void run(){
						try {
							in = new ObjectInputStream(socketServer.getInputStream());
							out = new ObjectOutputStream(socketServer.getOutputStream());
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				}
			)).start();
		client = new Client(socketClient);
	}
	
	/**
	 * Clean up after.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void cleanUpAfter() throws Exception {
		while(!server.isClosed()){
			server.close();
		}
		out.close();
		in.close();
		while(!socketClient.isClosed()){
			socketClient.close();
		}
		while(!socketServer.isClosed()){
			socketServer.close();
		}
		while(thread.isAlive()){
			thread.join();
		}
	}

	/**
	 * Test is connected.
	 */
	@Test
	public void testIsConnected() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(socketClient.isConnected(), true);
	}
	
	/**
	 * Test send nickname of oponent.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSendNicknameOfOponent() throws Exception {
		client.sendNicknameOfOponent("abcde");
		json = (JSONObject) in.readObject();
		assertEquals("opponentNickname", (String)json.get("Type"));
		assertEquals("abcde",(String)json.get("nickname"));
	}
	
	@Test
	public void testResultCodeForArea() throws Exception {
		client.sendResultCodeForArea(11);
		json = (JSONObject) in.readObject();
		assertEquals("resultCodeForArea", (String)json.get("Type"));
		assertEquals(11,(int)json.get("result"));
	}
	
	@Test
	public void testGetDecisionAboutBot(){
		(new Thread(){
			@Override
			public void run(){
				try {
					json = (JSONObject) in.readObject();
//					assertEquals("getDecisionAboutBot", (String)json.get("Type"));
					json = new JSONObject();
					json.put("decision", true);
					out.writeObject(json);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
		assertEquals(true, client.getDecisionAboutBot());
	}

	
	@Test
	public void testCountArea(){

		ArrayList<Point> test = new ArrayList<Point>();
		test.add(new Point(3,4));
		(new Thread(){
			@Override
			public void run(){
				try {
					json = (JSONObject) in.readObject();
					json = new JSONObject();
					json.put("area", test);
					out.writeObject(json);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
		assertEquals(test, client.countArea());
	}
	
	@Test
	public void testCheckOpponentArea(){

		ArrayList<Point> test = new ArrayList<Point>();
		test.add(new Point(3,4));
		(new Thread(){
			@Override
			public void run(){
				try {
					json = (JSONObject) in.readObject();
					assertEquals(test, (ArrayList<Point>)json.get("area"));
					json = new JSONObject();
					json.put("result", 67);
					out.writeObject(json);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
		assertEquals(67, client.checkOpponentArea(test));
	}

	/**
	 * Test win.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testWin() throws Exception{
		client.win();
		Thread.sleep(100);
		json = (JSONObject) in.readObject();
		assertEquals("resultOfGame", (String)json.get("Type"));
		assertEquals("win",(String)json.get("status"));
	}
	
	
	/**
	 * Test win.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testLose() throws Exception{
		client.lose();
		Thread.sleep(100);
		json = (JSONObject) in.readObject();
		assertEquals("resultOfGame", (String)json.get("Type"));
		assertEquals("lose",(String)json.get("status"));
	}
	
	@Test
	public void testChangeToAreaSelecting() throws Exception{
		client.changeToAreaSelecting();
		json = (JSONObject) in.readObject();
		assertEquals("selectingArea", (String)json.get("Type"));
	}
	
	@Test
	public void getScore() throws Exception{
		(new Thread(){
			@Override
			public void run(){
				try {
					json = (JSONObject) in.readObject();
					json = new JSONObject();
					json.put("score", 29.0);
					System.out.println("test");
					out.writeObject(json);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		assertEquals(29.0, client.getScore(), 0.1);
	}

}
