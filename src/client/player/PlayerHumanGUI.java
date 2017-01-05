package client.player;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.BoardGUI;
import main.Board;
import main.Stone;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerHumanGUI.
 */
public class PlayerHumanGUI implements Player {

	/** The color. */
	private int color;
	
	/** The nickname. */
	private String nickname = null;
	
	/** The opponent nickname. */
	private String opponentNickname;
	
	/** The opponent info. */
	private JLabel opponentInfo;
	
	/** The player info. */
	private JLabel playerInfo;
	
	/** The pass. */
	private JButton pass;
	
	/** The exit. */
	private JButton exit;
	
	/** The pass clicked. */
	boolean passClicked;
	
	/** The board. */
	Board board;
	
	/** The board GUI. */
	BoardGUI boardGUI;
	
	/** The frame. */
	JFrame frame = null;
	
	/** The area is counted. */
	JButton areaIsCounted;
	
	/** The last move. */
	Point lastMove;
	
	/** The counting area. */
	int countingArea = 0;
	
	/** The box. */
//	Box box = null;
	
	/** The score. */
	double score = 0;
	
	/** The oponent score. */
	double oponentScore = 0; 
	
	Container pane;
	
	/**
	 * Instantiates a new player human GUI.
	 */
	public PlayerHumanGUI(){
		board = new Board();
		lastMove = null;
		boardGUI = new BoardGUI();
		passClicked = false;
		
		frame = new JFrame("Let's play Go!");
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pane = frame.getContentPane();
	    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
	    
		boardGUI.setBoard(board);
		boardGUI.setBorder(new EmptyBorder(400, 400, 400, 400));
	    boardGUI.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    opponentInfo = new JLabel("---------");
	    opponentInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    playerInfo = new JLabel("---------");
	    playerInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    pass = new JButton("Pass");
	    pass.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				passClicked = true;
			}
	    });
	    pass.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    exit = new JButton("Exit");
	    exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit( 0 );
			}
	    	
	    });
	    exit.setAlignmentX(Component.CENTER_ALIGNMENT);
	    areaIsCounted = new JButton("send area");
	    areaIsCounted.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				countingArea = 0;
			}
	    	
	    });
	    areaIsCounted.setAlignmentX(Component.CENTER_ALIGNMENT);
	    areaIsCounted.setVisible(false); 
	    
//	    boardGUI.setMaximumSize(1500, 1500);
	    boardGUI.setMaximumSize(new Dimension(1500,1500));
	    boardGUI.setMinimumSize(new Dimension(500,500));

	    pane.add(boardGUI, frame.CENTER_ALIGNMENT);
	    
	    pane.add(playerInfo);
	    pane.add(opponentInfo);
	    
	    pane.add(pass);
	    pane.add(exit);
	    pane.add(areaIsCounted);
	    frame.pack();
	    frame.setMinimumSize(new Dimension(600,600));
	    frame.setVisible(true);
	    
//	    frame.add(box);
//	    frame.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see client.player.Player#setColor(int)
	 */
	public void setColor(int color){
		this.color = color;
		boardGUI.setColor(color);
		System.out.println("Set color: " + this.color);
	}
	
	/* (non-Javadoc)
	 * @see client.player.Player#getMove()
	 */
	public Point getMove(){
		pass.setVisible(true);
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
	
	/* (non-Javadoc)
	 * @see client.player.Player#getNickname()
	 */
	public String getNickname(){
		if(nickname == null){
			JPanel panel = new JPanel();
	        panel.add(new JLabel("Wanna play with bot?"));
	        nickname = JOptionPane.showInputDialog("Type your nickname");
	        refreshInfo();
		}
		return nickname;
	}
	
	/**
	 * Refresh info.
	 */
	private void refreshInfo() {
		playerInfo.setText(nickname + " " + score);
		if(opponentNickname != null){
			opponentInfo.setText(opponentNickname + " " + oponentScore);
		} else {
			opponentInfo.setText("Waiting for oponent...");
		}
        pane.repaint();
	}

	/* (non-Javadoc)
	 * @see client.player.Player#setOponentNickname(java.lang.String)
	 */
	@Override
	public void setOpponentNickname(String opponentNickname){
		this.opponentNickname = opponentNickname;
		opponentInfo.setText(opponentNickname + " " + oponentScore);
		pane.repaint();
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getColor()
	 */
	@Override
	public int getColor() {return color;}

	/* (non-Javadoc)
	 * @see client.player.Player#getOponentNickname()
	 */
	@Override
	public String getOpponentNickname() {return opponentNickname;}

	/* (non-Javadoc)
	 * @see client.player.Player#getOponentMove(int, int)
	 */
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

	/* (non-Javadoc)
	 * @see client.player.Player#getReturnCode(int)
	 */
	@Override
	public void getReturnCode(int i) {
		System.out.println("Code for move: " + i);
		if(i >= 0){
			int scoretmp = board.commitMove(lastMove, color);
			if(scoretmp < 4200){
				score += scoretmp; 
			}
			refreshInfo();
			boardGUI.repaint();
		}
	}

	/* (non-Javadoc)
	 * @see client.player.Player#checkOponentArea(java.util.ArrayList)
	 */
	@Override
	public int checkOponentArea(ArrayList<Point> points) {
//		JOptionPane.showMessageDialog(frame,
//			    "Check your oponent area.");
		pass.setVisible(false);
		for(Point i : points){
			System.out.println(i);
		}
		return boardGUI.checkOpponentArea(points);
	}

	/* (non-Javadoc)
	 * @see client.player.Player#countArea()
	 */
	@Override
	public ArrayList<Point> countArea() {
		JOptionPane.showMessageDialog(frame,
			    "Please select your area.");
		pass.setVisible(false);
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

	/* (non-Javadoc)
	 * @see client.player.Player#getResultOfGame(java.lang.String)
	 */
	@Override
	public void getResultOfGame(String result) {
		JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, "You " + result + ".");
        frame.dispose();
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getScore()
	 */
	@Override
	public double getScore() {
		return score;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getResultCodeForArea(int)
	 */
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

	/* (non-Javadoc)
	 * @see client.player.Player#showDialogAboutArea()
	 */
	@Override
	public void showDialogAboutArea() {
		JOptionPane.showMessageDialog(frame,
			    "Your oponent is selecting area, please wait.");
	}
	
}
