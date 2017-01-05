package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import client.ClientEngine;
import client.player.PlayerBot;
import main.MainServer;

// TODO: Auto-generated Javadoc
/**
 * The Class MainServerTest.
 */
@Ignore
public class MainServerTest extends RunListener {

	/** The out. */
	java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
	
	/** The thread. */
	private Thread thread = null;
	
	/** The thread 2. */
	private Thread thread2 = null;
	
	/** The client engine. */
	private ClientEngine clientEngine = null;

	/**
	 * Sets the up test.
	 */
	@Before
	public void setUpTest() {
	    (thread = new Thread(){
	    	@Override
	    	public void run(){
	    	    MainServer.main(new String[0]);
	    	}
	    }).start();
	    System.setOut(new java.io.PrintStream(out));
	    clientEngine = new ClientEngine("127.0.0.1", 3513, new PlayerBot(), false);
		(thread2 = new Thread(clientEngine)).start();
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test run finished.
	 */
	@After
	public void testRunFinished(){
		clientEngine = null;
		try {
			out.close();
			while(thread.isAlive()){
				thread.interrupt();
			}
			while(thread2.isAlive()){
				thread2.interrupt();
			}
			thread.join();
			thread2.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test main.
	 */
	@Test(timeout=3000)
	public void testMain() {
		if(out.size()>0){
			String str = out.toString();
			assertTrue(str.contains("New User"));
		}
		System.setOut(new java.io.PrintStream(System.out));
	}

}
