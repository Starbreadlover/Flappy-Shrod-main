import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.transaction.xa.XAException;
import javax.swing.JOptionPane;

public class GameFile extends JPanel implements ActionListener, KeyListener {
	int uiWidth = 360;
	int uiHeight = 680;
	
	//Images declare
	Image back;
	Image Bird;
	Image top;
	Image bottom;
	
	//bird
	int birdx = uiWidth/8;
	int birdy = uiHeight/2;
	int birdW = 70;
	int birdH = 60;
	
	int pipex = uiWidth;
	int pipey = 0;
	int pipeW = 64;
	int pipeH = 512;
	
	class Pipe {
		int x = pipex;
		int y = pipey;
		int Width = pipeW;
		int Height = pipeH;
		Image img;
		
		boolean passed = false;
		Pipe (Image img) {
			this.img = img;
			
			
			
		}
		
	}
 	
	class Bird {
		int x = birdx;
		int y = birdy;
		int width = birdW;
		int height = birdH;
		Image img;
		
		Bird(Image img) {
			this.img = img;	
		}	
	}
	
	//game logic
		
	Timer gameLoop;
	Timer placePipesTimer;
	ArrayList <Pipe> pipes;
	
	Random random = new Random();
	Bird bird; 
	boolean gameOver = false;
	double score = 0;
	int velocityX = -4;
	int velocityY = 0;
	int gravity = 1;
	
	

		GameFile() {
			setPreferredSize(new Dimension (uiWidth, uiHeight));
			setBackground(Color.pink);
			
			setFocusable(true); //take key events
			addKeyListener (this);
			
			
			back = new ImageIcon(getClass().getResource("./Background.png")).getImage();
			Bird = new ImageIcon(getClass().getResource("./Bird.png")).getImage();
			top = new ImageIcon(getClass().getResource("./top.png")).getImage();
			bottom = new ImageIcon(getClass().getResource("./bottom.png")).getImage();

			
			bird = new Bird (Bird); // Initialize it
			pipes = new ArrayList <Pipe>();
		
			
			placePipesTimer = new Timer(1650, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					placePipes();
					
				}			
			});
			
			  
			
			gameLoop = new Timer(1000/60, this);
			gameLoop.start();
			placePipesTimer.start();
			
		
			

	}
		
		
			
			public void Play() {
			    // Reset bird position
			    bird.x = birdx;
			    bird.y = birdy;
			    velocityY = 0;
			    score = 0;

			    // Clear all pipes
			    pipes.clear();

			    // Reset game state
			    gameOver = false;

			    // Restart timers
			    gameLoop.start();
			    placePipesTimer.start();
			}


			
		
		
		
	public void paintComponent (Graphics g) {	
		super.paintComponent(g);
		draw(g);
	}
	
	public void placePipes () {
		int randomPipeY = (int) (pipey - pipeH/4 - Math.random()*(pipeH/2));
		int openingSpace = uiHeight/5;
		
		Pipe toppipe = new Pipe (top);
		toppipe.y = randomPipeY;
		pipes.add(toppipe);
		
		Pipe bottompipe = new Pipe (bottom);
		bottompipe.y = toppipe.y + pipeH + openingSpace;
		pipes.add(bottompipe);
		
		
		}
		

	
	public void draw (Graphics g) {
		g.drawImage(back, 0, 0, uiWidth, uiHeight, null);
		g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
		
		for (int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get (i);
			pipe.x += velocityX;
			g.drawImage(pipe.img, pipe.x, pipe.y, pipe.Width, pipe.Height,null);
		}
		
		
		if (gameOver != true) {
			g.setColor(Color.pink);
			g.setFont(new Font ("Arial", Font.BOLD, 30));
			g.drawString(String.valueOf((int) score), 15,35);
		}
		
		if (gameOver == true) {
			
			
			int choice = JOptionPane.showConfirmDialog(
	                null,
	                "U DIED BITCH!! UR SCORE IS: " + score,
	                "Do you want to PLAY AGAIN?",
	                JOptionPane.YES_NO_OPTION
	        );
			
			   if (choice == JOptionPane.YES_OPTION) {
				  Play(); // Restart the game
		        } else {
		            System.exit(0); // Exit the game
		        }
		    } 
		}
	public void move() {
		//update x and y position
		velocityY += gravity;
		bird.y += velocityY;
		bird.y= Math.max(bird.y, 0); 
		bird.x= Math.max(bird.x , 0);
		
		if (bird.y > uiHeight) {
            gameOver = true;
        }
		
		for (Pipe pipe : pipes) {
		    if (hit(bird, pipe)) {
		        gameOver = true;
		        
		    }
		}
		
		for (Pipe pipe : pipes) {
		    if (!pipe.passed && bird.x > pipe.x + pipe.Width) {
		        pipe.passed = true;
		        score += 0.5; // Increment score for passed pipes
		    }
		}
			
	}
	
	public boolean hit (Bird a, Pipe b) {
		return 	   a.x < b.x + b.Width &&   //a's top left corner doesn't reach b's top right corner
	               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
	               a.y < b.y + b.Height &&  //a's top left corner doesn't reach b's bottom left corner
	               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
	    }
	
				
		

	
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if (gameOver) {
			placePipesTimer.stop();
			gameLoop.stop();
		}
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE); 
		velocityY = -9;
		
		
	}
	
	
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
