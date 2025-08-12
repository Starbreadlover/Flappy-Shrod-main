
import javax.swing.*; 

public class FlappyMain {

	public static void main(String[] args) {
		int uiWidth = 360;
		int uiHeight = 680;
		
		JFrame xiane = new JFrame ("Flappy Shrod");
		xiane.setSize(uiWidth, uiHeight);
		xiane.setLocationRelativeTo(null);
		xiane.setResizable(false);
		xiane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameFile gamefile = new GameFile ();
		xiane.add(gamefile);
		xiane.setVisible(true);
		xiane.pack();
		gamefile.requestFocus(); //focus 
		
		
		
	}

}
