package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Board;
import main.Stone;

public class BoardGUI extends JPanel implements MouseListener, MouseMotionListener{
	private int paddingX;
	private int paddingY;
	private Point mouseClickedPoint;
	private ArrayList<Point> selectedPoints;
	private Integer color;
	private int fieldSizeX;
	private int height;
	private int width;
	private int deltaY;
	private int deltaX;
	private int fieldSizeY;
	private Point mouseMovedPoint;
	private Board board;

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
		if(mouseMovedPoint != null){
			g.setColor(Color.RED);
			int x = (int)((mouseMovedPoint.getX() - deltaX - paddingX + fieldSizeX/2) / fieldSizeX);
			int y = (int)((mouseMovedPoint.getY() - deltaY - paddingY + fieldSizeY/2) / fieldSizeY);
			if(0 <= x && x <= 18 && 0 <= y && y <= 18 && color != null
					&& 0 != board.checkTurn(new Point(x, y), color)){
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
				Stone stone = board.getStone(new Point(i,j));
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
	public Point getClicked(){
		Point result = mouseClickedPoint;
		mouseClickedPoint = null;
		return result;		
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClickedPoint = new Point(
        		(e.getX() - (deltaX + paddingX - fieldSizeX/2)) / fieldSizeX,
        		(e.getY() - (deltaY + paddingY - fieldSizeX/2)) / fieldSizeY
        	);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
