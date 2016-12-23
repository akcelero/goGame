package client.player;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private boolean passClicked;
	private String nickname;
	private JLabel playerInfo;
	private String opponentNickname;
	private int color;
	private JLabel opponentInfo;
	private double score;
	private double oponentScore;
	private Component areaIsCounted;
	private int countingArea;

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
		this.color = color;
		boardGUI.setColor(color);
		System.out.println("Set color: " + this.color);
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public Point getMove() {
		Point result = null;
		boardGUI.unlock();
		boardGUI.repaint();
		do{
			result = boardGUI.getClicked();
			if(passClicked){
				result = new Point(-1, -1);
				passClicked = false;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(result == null);
		lastMove = result;
		boardGUI.lock();
		boardGUI.repaint();
		return result;
	}

	@Override
	public String getNickname() {
		if(nickname == null){
			JPanel panel = new JPanel();
	        panel.add(new JLabel("Wanna play with bot?"));
	        nickname = JOptionPane.showInputDialog("Type your nickname");
	        refreshInfo();
		}
		return nickname;
	}
	private void refreshInfo() {
		playerInfo.setText(nickname + " " + score);
		if(opponentNickname != null){
			opponentInfo.setText(opponentNickname + " " + oponentScore);
		} else {
			opponentInfo.setText("Waiting for oponent...");
		}
        pane.repaint();
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
	public void getOpponentMove(int x, int y) {
		if(x == -1 && y == -1){
			JOptionPane.showMessageDialog(frame,
				    "Your oponent pass move.");
		}
		int scoretmp = board.commitMove(new Point(x,y), color ^ 1);
		if(scoretmp < 4200){
			oponentScore +=scoretmp;
		}
		refreshInfo();
		boardGUI.repaint();
	}
	
	@Override
	public void getResultCodeForArea(int code) {

		switch(code){
		case -1:
			JOptionPane.showMessageDialog(frame,
				    "Your oponent decided to back to game.");
			boardGUI.clearSelectedArea();
			boardGUI.changeSelectingPoints(0);
		break;
		case 1:
			JOptionPane.showMessageDialog(frame,
				    "Your oponent don't agree with you. Select your area again.");
		break;
		case 0:
			JOptionPane.showMessageDialog(frame,
				    "Your oponent accept your area.");
			boardGUI.clearSelectedArea();
			boardGUI.changeSelectingPoints(0);
		break;
		}
		
	}
	
	@Override
	public void showDialogAboutArea() {
		JOptionPane.showMessageDialog(frame,
			    "Your oponent is selecting area, please wait.");
	}

	@Override
	public int checkOponentArea(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Point> countArea() {
		JOptionPane.showMessageDialog(frame,
			    "Please select your area.");
		countingArea = 1;
		boardGUI.changeSelectingPoints(1);
		
		areaIsCounted.setVisible(true);
		areaIsCounted.repaint();
		boardGUI.setSelectedPoints(board.getPropositionOfArea(color));
	    
		while(countingArea == 1){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		areaIsCounted.setVisible(false);
		ArrayList<Point> result = new ArrayList<Point>(boardGUI.getSelectedPoints());
		boardGUI.repaint();
		JOptionPane.showMessageDialog(frame,
			    "Wait till oponent decide about selected area.");
		return result;
	}

	@Override
	public double getScore() {
		return score;
	}

	@Override
	public void getResultOfGame(String result) {
		JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, "You " + result + ".");
        frame.dispose();
	}

}
