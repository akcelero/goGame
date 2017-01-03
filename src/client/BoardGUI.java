package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Board;
import main.Stone;

public class BoardGUI extends JPanel implements MouseListener, MouseMotionListener{

	private Point mouseClickedPoint;
	private Point mouseMovedPoint;
	int width;
	int height;
	int deltaX;
	int deltaY;
	int fieldSizeX;
	int fieldSizeY;
	private int paddingX;
	private int paddingY;
	private Board board;
	private Integer color = null;
	ArrayList<Point> selectedPoints = null;
	int selectingPoints = 0;
	int checkingOponentArea = 0;
	private boolean GUIlocked = true;
	
	
	public BoardGUI(){
		paddingX = 30;
		paddingY = 30;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		mouseClickedPoint = null;
		selectedPoints = new ArrayList<Point>();
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public void lock(){ GUIlocked = true;}
	
	public void unlock(){ GUIlocked = false;}
	
	@Override
	public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked " + e.getX() + " " + e.getY());
        if(GUIlocked == true){
        	mouseClickedPoint = null;
        	return;
        }
        mouseClickedPoint = new Point(
        		(e.getX() - (deltaX + paddingX - fieldSizeX/2)) / fieldSizeX,
        		(e.getY() - (deltaY + paddingY - fieldSizeX/2)) / fieldSizeY
        	);

    }
	
	public Point getClicked(){
		Point result = mouseClickedPoint;
		mouseClickedPoint = null;
		return result;		
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	
	@Override
	public void repaint(){
		int size = Math.min(this.getHeight(), this.getWidth());
		this.setSize(size, size);
		super.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.revalidate();
		this.setBackground(new Color(195,188,168));
		
		width = getWidth() - paddingX * 2;
		height = getHeight() - paddingY * 2;
		
		deltaX = (width % 18)/2;
		deltaY = (height % 18)/2;
		
		width = width - width%18;
		height = height - height%18;
		
		fieldSizeX = width/18;
		fieldSizeY = height/18;
		
		for(int i = 0; i < 19; i++){
			g.drawLine(
					paddingX + deltaX,
					paddingY + deltaY + i * height/18,
					paddingX + deltaX + 18 * width/18,
					paddingY + deltaY + i * height/18
				);
		}
		for(int i = 0; i < 19; i++){
			g.drawLine(
					paddingX + deltaX + i * width/18,
					paddingY + deltaY,
					paddingX + deltaX + i * width/18,
					paddingY + deltaY + 18 * height/18
				);
		}
		if(selectingPoints == 1 || checkingOponentArea == 1){
			g.setColor(Color.DARK_GRAY);
			for(Point p : selectedPoints){
				int x = p.x;
				int y = p.y;
			
				g.fillOval(
						paddingY + deltaX + x * width/18 - (fieldSizeX * 4)/10,
						paddingY + deltaY + y * height/18 - (fieldSizeY * 4)/10,
						(fieldSizeX * 8)/10,
						(fieldSizeY * 8)/10
					);
			}
		}
		if(GUIlocked == false && mouseMovedPoint != null){
			g.setColor(Color.RED);
			int x = (int)((mouseMovedPoint.getX() - deltaX - paddingX + fieldSizeX/2) / fieldSizeX);
			int y = (int)((mouseMovedPoint.getY() - deltaY - paddingY + fieldSizeY/2) / fieldSizeY);
			if(0 <= x && x <= 18 && 0 <= y && y <= 18 && color != null
					&& 0 != board.checkTurn(new Point(x, y), color)
					&& (selectingPoints == 0 || !selectedPoints.contains(new Point(x,y)))){
				g.fillOval(
						paddingY + deltaX + x * width/18 - (fieldSizeX * 3)/10,
						paddingY + deltaY + y * height/18 - (fieldSizeY * 3)/10,
						(fieldSizeX * 6)/10,
						(fieldSizeY * 6)/10
					);
			}
		}
		for(int i = 0; i < 19; i++){
			for(int j = 0; j < 19; j++){
				Stone stone = board.getStone(i,j);
				if(stone == null){
					continue;
				}
				if(stone.getColor() == 1){
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.BLACK);
				}
				
				g.fillOval(
						paddingY + deltaX + i * width/18 - (fieldSizeX * 4)/10,
						paddingY + deltaY + j * height/18 - (fieldSizeY * 4)/10,
						(fieldSizeX * 8)/10,
						(fieldSizeY * 8)/10
					);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(selectingPoints == 1){
			int x = (int)((e.getX() - deltaX - paddingX + fieldSizeX/2) / fieldSizeX);
			int y = (int)((e.getY() - deltaY - paddingY + fieldSizeY/2) / fieldSizeY);
			if(0 <= x && x <= 18 && 0 <= y && y <= 18 && board.getStone(x, y) == null){
				if(selectedPoints.contains(new Point(x,y))){
					selectedPoints.remove(new Point(x,y));
				} else {
					selectedPoints.add(new Point(x, y));
				}
			}
		}
		this.repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	
	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(GUIlocked == true){
			mouseMovedPoint = null;
		}
		mouseMovedPoint = new Point(arg0.getX(), arg0.getY());
		this.repaint();
	}
	
	@Override
    public Dimension getPreferredSize() {
        Dimension d = this.getParent().getSize();
        int newSize = d.width > d.height ? d.height : d.width;
        newSize = newSize == 0 ? 100 : newSize;
        return new Dimension(newSize, newSize);
    }
	
	public void changeSelectingPoints(int choice){
		selectingPoints = choice;
		GUIlocked = (choice == 0);
	}
	
	public ArrayList<Point> getSelectedPoints(){
		ArrayList<Point> result = new ArrayList<Point>(selectedPoints);
		return result;
	}
	
	public int checkOpponentArea(ArrayList<Point> selected){
		selectingPoints = 1;
		selectedPoints = selected;
		this.repaint();
		JPanel panel = new JPanel();
		panel.add(new JLabel("This oponent's area is ok?"));
        int correct = JOptionPane.showConfirmDialog(null, panel, "Area", JOptionPane.YES_NO_OPTION);
        if(correct == 1){
        	panel = new JPanel();
        	panel.add(new JLabel("Want continue game?"));
        	if( 0 == JOptionPane.showConfirmDialog(null, panel, "Back to game", JOptionPane.YES_NO_OPTION)){
        		correct = -1;
        	}
        }
        selectingPoints = 0;
		selectedPoints = new ArrayList<Point>();
		this.repaint();
        return correct;
	}
	
	public void clearSelectedArea() {
		selectedPoints = new ArrayList<Point>();
	}

	public void setSelectedPoints(ArrayList<Point> selected) {
		selectedPoints = selected;
	}

}
