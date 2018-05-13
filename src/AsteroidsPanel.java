import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

public class AsteroidsPanel extends JPanel{

	Rocket r = new Rocket();
	private ArrayList<Bullet> bullets= new ArrayList();;
	private ArrayList<Asteroid> roids = new ArrayList();
	int time = 0;
	int score=0;
	
	Timer timer = new Timer(5,null);
	
	
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		new AsteroidsPanel().start();
		
	}
	
	private void start() {
		JFrame frame = new JFrame("Asteroids!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AsteroidsPanel ap = new AsteroidsPanel();
		frame.add(ap);
		ap.setBackground(Color.BLACK);
		ap.setPreferredSize(new Dimension(950,950));
		frame.pack();
		frame.setVisible(true);
		ap.setUpKeyMappings();
		ap.startGame();
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		r.draw(g);
		for (int b = 0; b<bullets.size(); b++){
			bullets.get(b).draw(g);
		}
		for (int a = 0; a<roids.size(); a++){
			roids.get(a).draw(g);
		}
		
		for(int l=r.getLives();l>0;l--){
			r.drawStats(g);
		}
	}
	
	
	private void setUpKeyMappings() {

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"left");
		this.getActionMap().put("left",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				r.changeDirection(-1);
			}
		});
			
			this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"right");
			this.getActionMap().put("right",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println("Hit right arrow!!");
					r.changeDirection(1);
				}
			
		});
			this.getInputMap().put(KeyStroke.getKeyStroke("UP"),"up");
			this.getActionMap().put("up",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println("Hit right arrow!!");
					r.move(time);
				}
			
		});
			this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"space");
			this.getActionMap().put("space",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println("Hit right arrow!!");
					int x = r.getx();
					int y = r.gety();
					int direction = r.getdirection();
					Bullet b  = new Bullet(x+40, y, direction);
					bullets.add(b);
				}
			
		});
		this.requestFocusInWindow();
		
	}
	
	private void startGame() {
		timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tick();
				
			}
			
		});
		timer.start();
	}
	
	
	protected void tick() {
		time += 5;
		r.slowDown();
		if (time%2000 == 0){
			roids.add(new BigA());
		}
		for (int b = 0; b<bullets.size(); b++){
			bullets.get(b).move();
		}
		for (int a =0; a<roids.size();a++){
			roids.get(a).move();
		}
		
		if(youLose() == true){
			endGame();
		}
		else{
			
			
		}

		repaint();
	}

	
	private void endGame() {
		
		
	}

	private boolean youLose() {
		if(r.getLives() == 0){
			return true;
		}
		return false;
		
	}
	
	
}
