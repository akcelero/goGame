package client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainClient {
	
	/**
	 * The main method.
	 * Start new thread for game.
	 * Check if want play with bot.
	 * If yes, start new thread for bot.
	 * @param args the arguments
	 */
	public static void main(String [ ] args){
		JPanel panel = new JPanel();
        panel.add(new JLabel("Wanna play with bot?"));
        int boot = JOptionPane.showConfirmDialog(null, panel, "Wybor", JOptionPane.YES_NO_OPTION);
        if(boot == 0){
        	
        }
	}

}
