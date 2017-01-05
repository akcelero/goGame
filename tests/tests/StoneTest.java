package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Stone;

// TODO: Auto-generated Javadoc
/**
 * The Class StoneTest.
 */
public class StoneTest {
	
	/** The stone. */
	private Stone stone = null;
	
	/**
	 * Sets the up before.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUpBefore() throws Exception {
		stone = new Stone(0);
	}

	/**
	 * Test get color.
	 */
	@Test
	public void testGetColor() {
		assertEquals(stone.getColor(), 0);
	}

}
