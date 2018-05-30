import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

	private Rocket r= new Rocket();
	private ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	private ArrayList<Asteroid> roids = new ArrayList<Asteroid>();
	
	int time = 0;
	int hitTime=0;
	private boolean hit= false;
	private ArrayList<int[]> oldDirections = new ArrayList<int[]>();
	private boolean LEFT = false;
	private boolean RIGHT = false;
	private boolean UP = false;
	private int score;
	private int lives=3;
	
	private static Font customFontLarge;
	private static Font customFontsmall;
	private static Font customFontmed;
	private static Clip clip;
	
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
		
		
		try {
		     customFontLarge = Font.createFont(Font.TRUETYPE_FONT, new File("src\\Hyperspace Bold Italic.otf")).deriveFont(120f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src\\Hyperspace Bold Italic.otf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}

		try {
		     customFontsmall = Font.createFont(Font.TRUETYPE_FONT, new File("src\\Hyperspace.otf")).deriveFont(60f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src\\Hyperspace.otf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		frame.setFont(customFontsmall);
		
		try {
		     customFontmed = Font.createFont(Font.TRUETYPE_FONT, new File("src\\Hyperspace Italic.otf")).deriveFont(50f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src\\Hyperspace Bold.otf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	 
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if( operating.equals("game page")){
			
			g.setColor(Color.WHITE);
			g.setFont(customFontmed);
			g.drawString(Integer.toString(score), 10, 47);
			
			int width = 40;
			int height =50;
			int place = 10;
			for(int l= lives; l>0; l--){
				g.drawImage(r.getPic(), place, 60, width, height, null);
				place = place + 25;
			}
			
			for (int b = 0; b<bullets.size(); b++){
				bullets.get(b).draw(g);
			}
			for (int a = 0; a<roids.size(); a++){
				roids.get(a).draw(g,roids.get(a).getSize());
			}
			
			if(hit ==false ) {
				r.draw(g,UP);
			}
		}
		else if(operating.equals("start page")){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 950, 950);
			g.setColor(Color.WHITE);
			
			g.setFont(customFontsmall);
			g.drawString("PLAY GAME", 275, 600);
			g.drawRect(270, 550, 350, 60);
			
			g.setFont(customFontLarge);
			g.drawString("Asteroids", 150, 300);
		
		}
		else if (operating.equals("end page")){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 950, 950);
			
			g.setColor(Color.WHITE);
			g.setFont(customFontLarge);
			g.drawString("GAME OVER", 150, 300);
	
			g.setFont(customFontsmall);
			g.drawString("play again", 300, 700);
			g.drawRect(290, 640, 380, 70);
			
			g.drawString("Final Score: "+score, 180,500);
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
					if( hit == false){
						bullets.add(r.shoot());
						playSound();
					}
					
				}
		});

		this.requestFocusInWindow();
	}


	protected void playSound() {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/gunshot3.wav"));
	         clip = AudioSystem.getClip();  

	        clip.open(ais);
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		clip.start();
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
				else if(operating.equals("end page")&& arg0.getX()> 290 && arg0.getX() < 290+380 
						&& arg0.getY()>640 && arg0.getY()<640+70){
					operating = "start page";
					time=0;
					r = new Rocket();
					lives = 3;
					score=0;
					bullets= new ArrayList<Bullet>();
					roids = new ArrayList<Asteroid>();
					oldDirections = new ArrayList<int[]>();
					LEFT = false;
					RIGHT = false;
					UP = false;
					timer = new Timer(5,null);
					
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
			b.changeTime(5);
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
			
			r.move(90-r.getdirection());
			
//			int actualDirection = (int) (((oldD[0])*(oldD[1]/(oldD[1]*r.getSpeed()))+(r.getdirection())*(r.getSpeed()/(oldD[1]*r.getSpeed())))/2);
		}
			
			r.slowDown();
			
		
		
		hit();
		
		if (hit == true){
			hitTime+= 5;
		}
		
		if (hitTime>2000){
			hit = false;
			hitTime=0;
			r = new Rocket();
		}
		
		for (int b = bullets.size()-1; b>=0; b--){
			if(bullets.get(b).getTime()>300){
				bullets.remove(b);
			}
		}
		for (int a = roids.size()-1; a>=0; a--){
			if(roids.get(a).getx()>950 || roids.get(a).getx()+ roids.get(a).getSize()<0 || roids.get(a).gety()>950 
					|| roids.get(a).gety()+ roids.get(a).getSize()<0){
				roids.remove(a);
			}
		}
		
		if(lives<=0){
			endGame();
		}

		repaint();
	}

	private void endGame() {
		operating= "end page";
		timer.stop();
	}
	
	private void hit(){
		ArrayList <Asteroid> hit= new ArrayList<Asteroid>();
		
			for(int ai = roids.size()-1; ai>=0; ai--){
				Asteroid ast = roids.get(ai);
				if( (ast.getx()>= r.getx() && ast.getx()<= r.getx()+r.width 
						&& ast.gety()>=r.gety() && ast.gety()<= r.gety()+r.height) || 
						(ast.getx()+ast.getSize()>= r.getx() && ast.getx()+ast.getSize()<= r.getx()+r.width 
						&& ast.gety()+ ast.getSize()>=r.gety() && ast.gety()+ast.getSize()<= r.gety()+r.height) || 
						(ast.getx()+(ast.getSize()/2)>= r.getx() && ast.getx()+(ast.getSize()/2)<= r.getx()+r.width 
						&& ast.gety()+ (ast.getSize()/2)>=r.gety() && ast.gety()+(ast.getSize()/2)<= r.gety()+r.height)){
					if( this.hit == false){
						lives--;
						this.hit = true;
						hit.add(ast);
						roids.remove(ai);
						
						if( ast.getSize()== 130){
							roids.add(new MedA(ast.getx(),ast.gety(),ast.getAngle()+30,"left"));
							roids.add(new MedA(ast.getx(),ast.gety(),ast.getAngle()-30,"right"));
						}
						else if( ast.getSize() == 70){
							roids.add(new SmallA(ast.getx(),ast.gety(),ast.getAngle()+30,"left"));
							roids.add(new SmallA(ast.getx(),ast.gety(),ast.getAngle()-30,"right"));
						}
					
					}
				}
			}
		
		
		for(int ia= roids.size()-1; ia>=0; ia--){
			Asteroid ast = roids.get(ia);
			for(int bi= bullets.size()-1; bi>=0; bi--){
				Bullet b= bullets.get(bi);
				if((b.getx()>=ast.getx() && b.getx()<=ast.getx()+ast.getSize() && b.gety()>=ast.gety() && b.gety()<=ast.gety()+ast.getSize()) 
						||(b.getx()+2>=ast.getx() && b.getx()+2<=ast.getx()+ast.getSize() && b.gety()+2>=ast.gety() && b.gety()+2<=ast.gety()+ast.getSize())){
					
					hit.add(ast);
					roids.remove(ia);
					bullets.remove(bi);
					
					if( ast.getSize()== 130){
						roids.add(new MedA(ast.getx()+1,ast.gety()+1,ast.getAngle()+30, "left"));
						roids.add(new MedA(ast.getx()+1,ast.gety()+1,ast.getAngle()-30,"right"));
					}
					else if( ast.getSize() == 70){
						roids.add(new SmallA(ast.getx(),ast.gety()+1,ast.getAngle()+30,"left"));
						roids.add(new SmallA(ast.getx(),ast.gety()+1,ast.getAngle()-30,"right"));
					}
				}
			}
		}
	
	
		for(int index=hit.size()-1;index>=0; index--){
			score+= hit.get(index).getScore();
			hit.remove(index);
		}
		
		repaint();
	}
	
}
