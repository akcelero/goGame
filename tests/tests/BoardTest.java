package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import main.Board;

public class BoardTest {

	/** The board. */
	Board board;
	
	@Before
	public void beforTests(){
		board = new Board();
	}
	
	/**
	 * Test output.
	 */
	@Test
	public void testOutput() {
		assertEquals(0, board.commitMove(new Point(0,2), 1));
		assertNotEquals(0, board.commitMove(new Point(0,2), 1));
	}
	
	/**
	 * Test marking.
	 */
	@Test
	public void testMarking() {
		board.commitMove(new Point(0,2), 1);
		assertNotEquals(0, board.commitMove(new Point(0,2), 1));
	}

	/**
	 * Test if forbridden self kill.
	 */
	@Test
	public void testIfForbriddenSelfKill() {
		board.commitMove(new Point(0,2), 1);
		board.commitMove(new Point(1,1), 1);
		board.commitMove(new Point(2,2), 1);
		board.commitMove(new Point(1,3), 1);
		assertNotEquals(0, board.commitMove(new Point(1,2), 0));
	}
	
	/**
	 * Test if forbridden self kill advanced.
	 */
	@Test
	public void testIfForbriddenSelfKillAdvanced(){
		board.commitMove(new Point(2,1), 1);
		board.commitMove(new Point(3,1), 1);
		board.commitMove(new Point(4,2), 1);
		board.commitMove(new Point(3,3), 1);
		board.commitMove(new Point(3,4), 1);
		board.commitMove(new Point(2,5), 1);
		board.commitMove(new Point(1,4), 1);
		board.commitMove(new Point(1,3), 1);
		board.commitMove(new Point(1,2), 1);
		
		board.commitMove(new Point(2,4), 0);
		board.commitMove(new Point(2,3), 0);
		assertEquals(0, board.commitMove(new Point(2,2), 0));
		assertNotEquals(0,board.commitMove(new Point(3,2), 0));
//		board.printBoard();
	}
	
	@Test
	public void testEresingStones(){
		board.commitMove(new Point(1,2), 1);
		board.commitMove(new Point(2,3), 1);
		board.commitMove(new Point(3,2), 1);
		board.commitMove(new Point(2,1), 1);
		board.commitMove(new Point(3,1), 0);
		board.commitMove(new Point(4,2), 0);
		board.commitMove(new Point(3,3), 0);
		board.commitMove(new Point(2,2), 0);
		assertEquals(0,board.commitMove(new Point(3,2), 0));
	}
	
	/**
	 * Test breath counting.
	 */
	@Test
	public void testBreathCounting(){
		board.commitMove(new Point(2,1), 1);
		assertEquals(4, board.numberOfBreath(new Point(2,1), 1, new HashSet<Point>()));
		board.commitMove(new Point(2,2), 1);
		assertEquals(6, board.numberOfBreath(new Point(2,1), 1, new HashSet<Point>()));
		board.commitMove(new Point(1,1), 0);
		assertEquals(5, board.numberOfBreath(new Point(2,1), 1, new HashSet<Point>()));
	}

}
