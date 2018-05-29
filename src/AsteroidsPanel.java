import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

public class AsteroidsPanel extends JPanel{

	Rocket r = new Rocket();
	private ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	private ArrayList<Asteroid> roids = new ArrayList<Asteroid>();
	int time = 0;
	private ArrayList<int[]> oldDirections = new ArrayList<int[]>();
	private boolean LEFT = false;
	private boolean RIGHT = false;
	private boolean UP = false;
	
	String operating = "start page";
	
	Timer timer = new Timer(5,null);
	
	
	public static void main(String[] args) {
		try {
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
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if( operating.equals("game page")){
			r.draw(g,UP);
			for (int b = 0; b<bullets.size(); b++){
				bullets.get(b).draw(g);
			}
			for (int a = 0; a<roids.size(); a++){
				roids.get(a).draw(g);
			}
		}
		else if(operating.equals("start page")){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 950, 950);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 160));
			g.drawString("Asteroids", 150, 300);
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
			g.drawString("PLAY GAME", 275, 600);
			g.drawRect(270, 550, 350, 60);
		}
		else if (operating.equals("end page")){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 950, 950);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN,160 ));
			g.drawString("GAME", 225, 300);
			g.drawString("OVER", 250, 410);
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
			g.drawString("play again", 350, 700);
			g.drawRect(350, 650, 250, 70);
			
			g.drawString("Final Score:" +r.getScore(), 280,500);
		}
		
	}
	
	
	private void setUpKeyMappings() {

			this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"left");
			this.getActionMap().put("left",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					LEFT = true;
				}
		});
		
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true),"left released");
			this.getActionMap().put("left released",new AbstractAction(){
	
				@Override
				public void actionPerformed(ActionEvent e) {
					LEFT = false;
				}
		});
			
			this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"right");
			this.getActionMap().put("right",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					RIGHT = true;
				}
		});
			
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true),"right released");
			this.getActionMap().put("right released",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					RIGHT = false;
				}
		});
			
			this.getInputMap().put(KeyStroke.getKeyStroke("UP"),"up");
			this.getActionMap().put("up",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					UP = true;
				}			
		});

			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,true),"up released");
			this.getActionMap().put("up released",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					UP = false;
				}
		});
			
			this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"space");
			this.getActionMap().put("space",new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent e) {
					bullets.add(r.shoot());
		
					try {
				    File yourFile= new File("src/gunshot.wav");
				    AudioInputStream stream;
				    AudioFormat format;
				    DataLine.Info info;
				    Clip clip;

				    stream = AudioSystem.getAudioInputStream(yourFile);
				    format = stream.getFormat();
				    info = new DataLine.Info(Clip.class, format);
				    clip = (Clip) AudioSystem.getLine(info);
				    clip.open(stream);
				    clip.start();
				}
				catch (Exception e1) {
				    System.out.println("you suck");
				}
					
					
				   }
		});

		this.requestFocusInWindow();
	}
	
	public AsteroidsPanel() {
		this.setPreferredSize(new Dimension(950,950));
		this.addMouseListener(new MouseListener() {

			public void mousePressed(MouseEvent arg0) {
				
				if(operating.equals("start page")&& arg0.getX()> 270 && arg0.getX() < 270+350 
						&& arg0.getY()>550 && arg0.getY()<550+60){
					operating = "game page";
					startGame();
				}
				else if(operating.equals("end page")&& arg0.getX()> 350 && arg0.getX() < 300+250 
						&& arg0.getY()>650 && arg0.getY()<650+70){
					operating = "start page";
					time=0;
					r = new Rocket();
					bullets= new ArrayList<Bullet>();
					roids = new ArrayList<Asteroid>();
					oldDirections = new ArrayList<int[]>();
					LEFT = false;
					RIGHT = false;
					UP = false;
					timer = new Timer(5,null);
					
					startGame();
				} 
				
				repaint();
			}

			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
			}
			});
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
		if (time%1000 == 0){
			roids.add(new BigA());
		}
		for (Bullet b:bullets){
			b.move();
		}
		for (Asteroid ast: roids){
			ast.move();
		}
		
		double[] oldD = new double[2];
		oldD[0] = r.getdirection();
		oldD[1] = (int) r.getSpeed();
		
		
		if(RIGHT == true){
			r.changeDirection(2);
		}
		if(LEFT == true){
			r.changeDirection(-2);
		}
		if(UP == true){
			r.slide(r.getdirection());
			if(time%100==0){
			r.move(90-r.getdirection());
			}
			int actualDirection = (int) (((oldD[0])*(oldD[1]/(oldD[1]*r.getSpeed()))+(r.getdirection())*(r.getSpeed()/(oldD[1]*r.getSpeed())))/2);
			if(time%50 ==0){
			r.move(actualDirection);
			}
		}
		if(time%50 ==0){
			r.slowDown();
		}
		
		
		
		int score = hit();
		r.addScore(score);
		
		for (int b = bullets.size()-1; b>=0; b--){
			if(bullets.get(b).getx()>950 || bullets.get(b).getx()<0 || bullets.get(b).gety()>950 || bullets.get(b).gety()<0){
				bullets.remove(b);
			}
		}
		for (int a = roids.size()-1; a>=0; a--){
			if(roids.get(a).getx()>950 || roids.get(a).getx()+ roids.get(a).getSIZE()<0 || roids.get(a).gety()>950 
					|| roids.get(a).gety()+ roids.get(a).getSIZE()<0){
				roids.remove(a);
			}
		}
		
		if(youLose() == true){
			endGame();
		}

		repaint();
	}

	private void endGame() {
		operating= "end page";
		timer.stop();
	}

	private boolean youLose() {
		if(r.getLives()<= 0){
			return true;
		}
		return false;	
	}
	
	private int hit(){
		ArrayList <Asteroid> hit= new ArrayList<Asteroid>();
		
		for(int ai = roids.size()-1; ai>=0; ai--){
			Asteroid ast = roids.get(ai);
			if( (ast.getx()>= r.getx() && ast.getx()<= r.getx()+r.width 
					&& ast.gety()>=r.gety() && ast.gety()<= r.gety()+r.height) || 
					(ast.getx()+ast.getSIZE()>= r.getx() && ast.getx()+ast.getSIZE()<= r.getx()+r.width 
					&& ast.gety()+ ast.getSIZE()>=r.gety() && ast.gety()+ast.getSIZE()<= r.gety()+r.height) || 
					(ast.getx()+(ast.getSIZE()/2)>= r.getx() && ast.getx()+(ast.getSIZE()/2)<= r.getx()+r.width 
					&& ast.gety()+ (ast.getSIZE()/2)>=r.gety() && ast.gety()+(ast.getSIZE()/2)<= r.gety()+r.height)){
				r.loseLife();
				hit.add(ast);
				roids.remove(ai);
				
				if( ast.getSIZE()== 130){
					roids.add(new MedA(ast.getx(),ast.gety(),ast.getAngle()+30,"left"));
					roids.add(new MedA(ast.getx(),ast.gety(),ast.getAngle()-30,"right"));
				}
				else if( ast.getSIZE() == 70){
					roids.add(new SmallA(ast.getx(),ast.gety(),ast.getAngle()+30,"left"));
					roids.add(new SmallA(ast.getx(),ast.gety(),ast.getAngle()-30,"right"));
				}
				
			}
		}
		
		
		for(int ia= roids.size()-1; ia>=0; ia--){
			Asteroid ast = roids.get(ia);
			for(int bi= bullets.size()-1; bi>=0; bi--){
				Bullet b= bullets.get(bi);
				if((b.getx()>=ast.getx() && b.getx()<=ast.getx()+ast.getSIZE() && b.gety()>=ast.gety() && b.gety()<=ast.gety()+ast.getSIZE()) 
						||(b.getx()+2>=ast.getx() && b.getx()+2<=ast.getx()+ast.getSIZE() && b.gety()+2>=ast.gety() && b.gety()+2<=ast.gety()+ast.getSIZE())){
					
					hit.add(ast);
					roids.remove(ia);
					bullets.remove(bi);
					
					if( ast.getSIZE()== 130){
						roids.add(new MedA(ast.getx()+1,ast.gety()+1,ast.getAngle()+30, "left"));
						roids.add(new MedA(ast.getx()+1,ast.gety()+1,ast.getAngle()-30,"right"));
					}
					else if( ast.getSIZE() == 70){
						roids.add(new SmallA(ast.getx(),ast.gety()+1,ast.getAngle()+30,"left"));
						roids.add(new SmallA(ast.getx(),ast.gety()+1,ast.getAngle()-30,"right"));
					}
				}
			}
		}
	
		
		int score=0;
		for(int index=hit.size()-1;index>=0; index--){
			score+= hit.get(index).getScore();
			hit.remove(index);
		}
		
		repaint();
		return score;
	}
	
	
}
