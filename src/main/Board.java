package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

public class Board {
	int width = 19;
	int height = 19;
	
	private Stone[][] stones;
	private Point lastMove;
	
	int[][] template = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
	
	public Board(){
		stones = new Stone[19][19];
		for(int i=0;i<19;i++){
			for(int j=0;j<19;j++){
				stones[i][j] = null;
				stones[i][j] = null;
			}
		}
	}
	
	public int commitMove(Point point, int color) {
		// check if pass
		// check if point is at board 
		if(18 < point.getX() || point.getX() < 0 ||
				18 < point.getY() || point.getY() < 0)
			return -3;
		// check if place is empty
		if(stones[(int)point.getX()][(int)point.getY()] != null){
			return -2;
		}
		// check if turn is possible because of logic
		if(checkTurn(point, color) == 0){
			return -1;
		}
		
		lastMove = new Point(point.x, point.y);
		for(int i=0;i<19;i++){
			for(int j=0;j<19;j++){
				if(stones[i][j] != null){
					stones[i][j] = new Stone(stones[i][j].getColor());
				} else {
					stones[i][j] = null;
				}
			}
		}
		return putStone(point, color);
		
	}
	
	public int checkTurn(Point point, int color){
		Point result = checkTurnPoint(point, color);
		return Math.max(result.x, result.y);
	}

	private Point checkTurnPoint(Point point, int color) {
		Point newPoint;
		int x, y;
		int nrOfOpponentBreath = 0;
		int nrOfPlayerBreath = 0;

		
		HashSet<Point> setPlayer = new HashSet<Point>();

		HashSet<Point> setOponent = new HashSet<Point>();
		for(int i = 0; i < 4; i++){
			newPoint = new Point(point.x + template[i][0], point.y + template[i][1]);
			x = newPoint.x;
			y = newPoint.y;
			if((0 <= x && x <= 18 && 0 <= y && y <= 18)){
				if(getStone(newPoint) == null || getStone(newPoint).getColor() == color){
					setPlayer.add(point);
					nrOfPlayerBreath += numberOfBreath(newPoint, color, setPlayer);
				} else if(getStone(newPoint).getColor() == (color ^ 1)){
					if(numberOfBreath(newPoint, color ^ 1, setOponent) == 1){
						nrOfOpponentBreath++;
					}
				}
			}
		}
		
		return new Point(nrOfOpponentBreath, nrOfPlayerBreath);
	}
	
	public Stone getStone(Point point){
		if(
				width - 1 < (int)point.getX() || (int)point.getX() < 0 ||
				height - 1 < (int)point.getY() || (int)point.getY() < 0
			)
			return null;
		return stones[(int)point.getX()][(int)point.getY()];
	}

	public int numberOfBreath(Point point, int color, HashSet<Point> history){
		int x, y, result = 0;
		x = (int) point.getX();
		y = (int) point.getY();
		if(history.contains(point) || !(0 <= x && x <= 18 && 0 <= y && y <= 18)){
			return 0;
		}
		if(stones[x][y] == null){
			return 1;
		}
		history.add(point);
		if(stones[x][y].getColor() != color){
			return 0;
		}
		for(int i = 0; i < 4; i++){
			Point newPoint = new Point( x + template[i][0], y + template[i][1]);
			result += numberOfBreath(newPoint, color, history);
		}
		return result;
	}
	
	private int putStone(Point point, int color) {
		stones[(int)point.getX()][(int)point.getY()] = new Stone(color);
		Point newPoint;
		int x, y;
		int result = 0;
		
		for(int i = 0; i < 4; i++){
			newPoint = new Point((int)point.getX() + template[i][0], (int)point.getY() + template[i][1]);
			x = (int)newPoint.getX();
			y = (int)newPoint.getY();
			if((0 <= x && x < 19 && 0 <= y && y < 19)
					&& stones[x][y] != null && stones[x][y].getColor() == (color ^ 1)
					&& numberOfBreath(newPoint, color ^ 1, new HashSet<Point>()) == 0){
				result += eraseGroupOfStones(newPoint, color ^ 1);
			}
		}
		return result;
	}
	
	public int numberOfStonesInGroup(Point point, int color, HashSet<Point> history){
		System.out.println(point);
		int x, y, result = 0; 
		x = point.x;
		y = point.y;
		if(history.contains(point) || x < 0 || y < 0 || 19 <= x || 19 <= y
				|| stones[x][y] == null || stones[x][y].getColor() != color){
			return 0;
		}
		history.add(point);
		for(int i = 0; i < 4; i++){
			result += numberOfStonesInGroup(
					new Point( x + template[i][0], y + template[i][1])
					, color, history);
		}
		return result + 1;
	}
	private int eraseGroupOfStones(Point point, int color){
		int x = (int) point.getX(); 
		int y = (int) point.getY();
		int result = 0;
		if(x < 0 || y < 0 || 19 <= x || 19 <= y || (stones[x][y] != null && stones[x][y].getColor()!=color)){
			return 0;
		}
		stones[x][y] = null;
		for(int i = 0; i < 4; i++){
			result += eraseGroupOfStones(new Point( x + template[i][0], y + template[i][1]), color);
		}
		return result + 1;
	}

	public ArrayList<Point> getPropositionOfArea(int color) {
		ArrayList<Point> result = new ArrayList<Point>();
		for(int i=0;i<19;i++){
			for(int j=0;j<19;j++){
				if(
						checkIfCanContactWithColor(i, j, color, new HashSet<Point>()) &&
						!checkIfCanContactWithColor(i, j, color^1, new HashSet<Point>()) &&
						stones[i][j] == null){
					result.add(new Point(i,j));
				}
			}
		}
		return result;
	}
	
	private boolean checkIfCanContactWithColor(int x, int y, int color, HashSet<Point> visited) {
		if(x < 0 || y < 0 || 19 <= x || 19 <= y || visited.contains(new Point(x,y))){
			return false;
		}
		visited.add(new Point(x,y));
		if(stones[x][y] != null){
			if(stones[x][y].getColor() == color){
				return true;
			}
			return false;
		}
		
		return this.checkIfCanContactWithColor(x+1, y, color, visited) ||
				this.checkIfCanContactWithColor(x-1, y, color, visited) ||
				this.checkIfCanContactWithColor(x, y+1, color, visited) ||
				this.checkIfCanContactWithColor(x, y-1, color, visited);
		
	}
}
