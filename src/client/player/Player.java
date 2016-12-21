package client.player;

import java.awt.Point;

public interface Player {
	public void setColor(int color);
	public int getColor();
	public Point getMove();
	public String getNickname();
	public void setOpponentNickname(String opponentNickname);
	public void getReturnCode(int code);
	public void getOpponentMove(int i, int j);

}
