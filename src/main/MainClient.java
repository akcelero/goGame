package main;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.ClientEngine;
import client.player.PlayerBot;
import client.player.PlayerHumanGUI;

public class MainClient {
	
	public static void main(String [ ] args){
		
		
        JPanel panel = new JPanel();
        panel.add(new JLabel("Wanna play with bot?"));
        int boot = JOptionPane.showConfirmDialog(null, panel, "Wybor", JOptionPane.YES_NO_OPTION);
        
		Thread threadPlayer = new Thread(
				new ClientEngine(
						"127.0.0.1",
						3513,
						new PlayerHumanGUI(),
						(boot==0)
				));
		threadPlayer.start();
		if(boot == 0){
			Thread threadBot = new Thread(
					new ClientEngine(
							"127.0.0.1",
							3513,
							new PlayerBot(),
							false
					));
			threadBot.start();
		}
		try {
			threadPlayer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
