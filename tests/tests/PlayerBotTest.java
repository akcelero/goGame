package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.player.PlayerBot;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerBotTest.
 */
public class PlayerBotTest {
	
	/** The bot. */
	PlayerBot bot = null;

	/**
	 * Sets the up before.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		bot = new PlayerBot();
		bot.setColor(0);
		bot.setOpponentNickname("test");
	}

	/**
	 * Test set color.
	 */
	@Test
	public void testSetColor() {
		bot.setColor(0);
	}

	/**
	 * Test get move.
	 */
	@Test
	public void testGetMove() {
		assertNotNull(bot.getMove());
	}

	/**
	 * Test get nickname.
	 */
	@Test
	public void testGetNickname() {
		assertNotEquals("", bot.getNickname());
	}

	/**
	 * Test get color.
	 */
	@Test
	public void testGetColor() {
		assertEquals(0, bot.getColor());
	}

	/**
	 * Test set oponent nickname.
	 */
	@Test
	public void testSetOponentNickname() {
		bot.setOpponentNickname("test");
	}

	/**
	 * Test get oponent nickname.
	 */
	@Test
	public void testGetOponentNickname() {
		assertEquals("test", bot.getOpponentNickname());
	}

	/**
	 * Test get oponent move.
	 */
	@Test
	public void testGetOponentMove() {
		bot.getOpponentMove(5,5);
	}

	/**
	 * Test get score.
	 */
	@Test
	public void testGetScore() {
		assertNotNull(bot.getScore());
	}

}
