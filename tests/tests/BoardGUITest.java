package tests;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Point;
import java.text.Format.Field;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import client.BoardGUI;
import main.Board;
//@Ignore
public class BoardGUITest {

	BoardGUI boardGUI = null;
	
	
	@Before
	public void setUpBefore() throws Exception {
		boardGUI = new BoardGUI();
	}

	@Test
	public void testLock() {
		boardGUI.lock();
		java.lang.reflect.Field privateStringField = null;
		try {
			privateStringField = BoardGUI.class.getDeclaredField("GUIlocked");
			privateStringField.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals(true, (boolean)privateStringField.get(boardGUI));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUnlock() {
		boardGUI.lock();
		boardGUI.unlock();
		java.lang.reflect.Field privateStringField = null;
		try {
			privateStringField = BoardGUI.class.getDeclaredField("GUIlocked");
			privateStringField.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals(false, (boolean)privateStringField.get(boardGUI));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testChangeSelectingPoints() {
		boardGUI.changeSelectingPoints(1);
		java.lang.reflect.Field privateStringField = null;
		try {
			privateStringField = BoardGUI.class.getDeclaredField("GUIlocked");
			privateStringField.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals(false, (boolean)privateStringField.get(boardGUI));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckOpponentArea() {
		int result = boardGUI.checkOpponentArea(new ArrayList<Point>());
		if(result < -1 || 1 < result){
			fail("Unexpected option");
		}
		
	}

	@Ignore
	@Test
	public void testClearSelectedArea() {
		boardGUI.clearSelectedArea();
		java.lang.reflect.Field privateStringField = null;
		try {
			privateStringField = BoardGUI.class.getDeclaredField("selectedPoint");
			privateStringField.setAccessible(true);
		} catch (Exception e) {
		}
		
		try {
			assertEquals(0, ((ArrayList<Point>)privateStringField.get(boardGUI)).size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testSetSelectedPoints() {
		ArrayList<Point> test = new ArrayList<Point>();
		test.add(new Point(2,2));
		boardGUI.setSelectedPoints(test);
		assertEquals(test, boardGUI.getSelectedPoints());
	}

}
