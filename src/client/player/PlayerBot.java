package client.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import main.Board;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerBot.
 */
public class PlayerBot implements Player {
	
	/** The board. */
	Board board;
	
	/** The color. */
	int color;
	
	/** The x. */
	int x = 10;
	
	/** The y. */
	int y = 10;
	
	/** The opponent nickname. */
	String opponentNickname;
	
	/** The nickname. */
	String nickname = "Bot Mieczysław";
	
	/** The template. */
	int[][] template = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
	
	/**
	 * Instantiates a new player bot.
	 */
	public PlayerBot(){
		board = new Board();
		color = -1;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#setColor(int)
	 */
	@Override
	public void setColor(int color) {
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getMove()
	 */
	@Override
	public Point getMove() {
		Point bestMove = new Point(-1,-1);
		int bestScore = 0;
		int result = 0;
		
		for(int x = 0; x < 19; x++){
			for(int y = 0; y < 19; y++){
				// check if point is at board 
				if(board.getStone(x, y) != null || board.checkTurn(new Point(x,y), color) == 0){
					continue;
				}
				
				result = getValueOfPoint(new Point(x,y), color, 0); 
				if(result > bestScore){
					bestScore = result;
					bestMove = new Point(x,y);
				}
				result = getValueOfPoint(new Point(x,y), color^1, 0); 
				if(result > bestScore){
					bestScore = result;
					bestMove = new Point(x,y);
				}
			}
		}
		board.commitMove(bestMove, color);
		return bestMove;
	}
	
	/**
	 * Gets the value of point.
	 *
	 * @param point the point
	 * @param color the color
	 * @param time the time
	 * @return the value of point
	 */
	int getValueOfPoint(Point point, int color, int time){
		int result = 0;
//		result += ((checkOponentAround(point, color)) + board.checkTurn(point, color^1))*100;
//		result += board.checkTurn(point,color);
//		result += board.checkTurnPoint(point, color).x;// + board.checkTurnPoint(point, (color^1)).x;
		for(int i= 0; i < 4; i++){
			int x = point.x + template[i][0];
			int y = point.y + template[i][1];
			if((0 <= x && x <= 18 && 0 <= y && y <= 18) && board.getStone(x, y) != null /*&&
					board.checkTurn(new Point(x,y), color) != 0*/){
//				result += (board.numberOfStonesInGroup(new Point(x,y), color^1, history1)*1000)
//				result += Math.max(0, board.numberOfBreath(new Point(x,y), color ^ 1, new HashSet()) - time);
				result += (board.numberOfStonesInGroup(new Point(x,y), color ^ 1, new HashSet<Point>())*100)
						/Math.max(1, board.numberOfBreath(new Point(x,y), color ^ 1, new HashSet<Point>()));
				result += board.numberOfStonesInGroup(new Point(x,y), color, new HashSet<Point>());
				
			}
		}
		
		return result;
	}
	
	/**
	 * Check oponent around.
	 *
	 * @param point the point
	 * @param color the color
	 * @return the int
	 */
	int checkOponentAround(Point point, int color){
		int[][] template = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
		Point newPoint;
		int result = 0;
		for(int i = 0; i < 4; i++){
			newPoint = new Point((int)point.getX() + template[i][0], (int)point.getY() + template[i][1]);
			if(
					18 < (int)newPoint.getX() || (int)newPoint.getX() < 0 ||
					18 < (int)newPoint.getY() || (int)newPoint.getY() < 0 ||
					board.getStone(newPoint) == null ||
					board.getStone(newPoint).getColor() == color
				)continue;
//			if(board.numberOfBreath(newPoint, color ^ 1, new HashSet<Point>()) > 0){
				result ++;
//			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getNickname()
	 */
	@Override
	public String getNickname() {return nickname;}

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
	 * @see client.player.Player#setOponentNickname(java.lang.String)
	 */
	@Override
	public void setOpponentNickname(String oponentNickname) {this.opponentNickname = oponentNickname;}

	/* (non-Javadoc)
	 * @see client.player.Player#getOponentMove(int, int)
	 */
	@Override
	public void getOpponentMove(int x, int y) {
		board.commitMove(new Point(x,y), color ^ 1);
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getReturnCode(int)
	 */
	@Override
	public void getReturnCode(int i) {}

/* (non-Javadoc)
 * @see client.player.Player#checkOponentArea(java.util.ArrayList)
 */
// --------------------------------------------------------------
	@Override
	public int checkOponentArea(ArrayList<Point> points) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#countArea()
	 */
	@Override
	public ArrayList<Point> countArea() {
		board.getPropositionOfArea(color);
		return null;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getResultOfGame(java.lang.String)
	 */
	@Override
	public void getResultOfGame(String result) {}

	/* (non-Javadoc)
	 * @see client.player.Player#getScore()
	 */
	@Override
	public double getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see client.player.Player#getResultCodeForArea(int)
	 */
	@Override
	public void getResultCodeForArea(int code) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.player.Player#showDialogAboutArea()
	 */
	@Override
	public void showDialogAboutArea() {
		// TODO Auto-generated method stub
		
	}
	
}
