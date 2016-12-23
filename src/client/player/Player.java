package client.player;

import java.awt.Point;
import java.util.ArrayList;

public interface Player {
	public void setColor(int color);
	public int getColor();
	public Point getMove();
	public String getNickname();
	public void setOpponentNickname(String opponentNickname);
	public void getReturnCode(int code);
	public void getOpponentMove(int i, int j);
	public int checkOponentArea(ArrayList<Point> points);
	public ArrayList<Point> countArea();
	public double getScore();
	public void getResultOfGame(String result);
	public void getResultCodeForArea(int code);
	public void showDialogAboutArea();
	public String getOpponentNickname();
}
