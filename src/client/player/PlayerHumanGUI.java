package client.player;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import client.BoardGUI;
import main.Board;

public class PlayerHumanGUI implements Player {
	
	private Board board;
	private Object lastMove;
	private BoardGUI boardGUI;
	private JFrame frame;
	private Container pane;
	private JButton exit;

	public PlayerHumanGUI(){
		board = new Board();
		lastMove = null;
		boardGUI = new BoardGUI();
		
		frame = new JFrame("Game");
	    pane = frame.getContentPane();
	    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
	    exit = new JButton("Exit");
	    exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit( 0 );
			}
	    	
	    });
	    exit.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    boardGUI.setMaximumSize(new Dimension(1500,1500));
	    boardGUI.setMinimumSize(new Dimension(500,500));

	    pane.add(boardGUI, frame.CENTER_ALIGNMENT);
	    pane.add(exit);
	    frame.pack();
	    frame.setMinimumSize(new Dimension(600,600));
	    frame.setVisible(true);
	}

	@Override
	public void setColor(int color) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Point getMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNickname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOpponentNickname(String opponentNickname) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getReturnCode(int code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOpponentMove(int i, int j) {
		// TODO Auto-generated method stub
		
	}

}
