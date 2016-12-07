package client;

import java.awt.Point;

public interface Player {
	public void setColor(int color);
	public int getColor();
	public Point getMove();
	public String getNickname();
	public void setOpponentNickname(String opponentNickname);

}
