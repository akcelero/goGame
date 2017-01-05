package tests;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import main.MainClient;

// TODO: Auto-generated Javadoc
/**
 * The Class MainClientTest.
 */
public class MainClientTest {

	/** The server socket. */
	private ServerSocket serverSocket = null;
	
	/** The global timeout. */
	@Rule
    public Timeout globalTimeout = Timeout.seconds(20);

	/** The thread. */
	private Thread thread;

	/**
	 * Sets the up before.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		serverSocket = new ServerSocket(3513);
		(thread = new Thread(){
			@Override
			public void run(){
				try {
					serverSocket.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Test main.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testMain() throws Exception{
		MainClient.main(new String[0]);		
		System.out.println("connected");
	}
	
	/**
	 * Clean up after.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void cleanUpAfter() throws Exception {
		while(thread.isAlive()){
			thread.interrupt();
		}
		while(!serverSocket.isClosed()){
			serverSocket.close();
		}
	}
}
