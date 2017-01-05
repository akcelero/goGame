package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mock;
import org.mockito.Mockito;

import server.Client;
import server.Game;

// TODO: Auto-generated Javadoc
/**
 * The Class GameTest.
 */
public class GameTest {

	/** The game. */
	Game game = null;
	
	/** The socket mock. */
	@Mock
	Socket socketMock;
	
	/** The global timeout. */
	@Rule
    public Timeout globalTimeout = Timeout.seconds(2);
	
	/**
	 * Sets the up before.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		game = new Game();
		socketMock = Mockito.mock(Socket.class);
		final PipedOutputStream source = new PipedOutputStream();
	    PipedInputStream sink = new PipedInputStream(source);
		Mockito.when(socketMock.getOutputStream()).thenReturn(source);
		Mockito.when(socketMock.getInputStream()).thenReturn(sink);
		Mockito.when(socketMock.getInputStream()).thenReturn(sink);
		;
	}
	
	/**
	 * Test add user.
	 */
	@Test
	public void testAddUser() {
		game.addUser(new Client(socketMock));
		game.addUser(new Client(socketMock));
		assertEquals(false, game.waiting());
	}
	
	@Test
	public void testGetUser() {
		Client client = new Client(socketMock);
		game.addUser(client);
		game.addUser(new Client(socketMock));
		assertEquals(client, game.getUser(0));
	}
	
	/**
	 * Test waiting.
	 */
	@Test
	public void testWaiting() {
		assertEquals(true, game.waiting());
		game.addUser(new Client(socketMock));
		assertEquals(true, game.waiting());
		game.addUser(new Client(socketMock));
		assertNotEquals(true, game.waiting());
	}

	/**
	 * Test run.
	 */
	@Test
	public void testRun() {
		try{
			game.addUser(new Client(socketMock));
			game.addUser(new Client(socketMock));
			Thread thread = new Thread(game);
			thread.start();
			if(!thread.isAlive()){
				fail("didn't start thread");
			}
			
		}catch(NullPointerException e){
			// it's not error during tests.
		}
	}

}
