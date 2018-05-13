import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Rocket {

	private int x;
	private int y;
	private static int acceleration = 8;
	private int speed;
	private int direction;
	private int lives;
	private Image pic;
	private static int width = 80;
	private static int height = 90;
	private int score;
	private Font customFont;
	
	public Rocket(){
		x= 475;
		y=475;
		direction =90;
		lives = 3;
		speed=0;
		score = 0;
		
		try {
            pic = ImageIO.read(this.getClass().getResourceAsStream("rocket final.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		try {
		    //create the font to use. Specify the size!
		   customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Hyperdpace Bold Italic.oft")).deriveFont(12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Hyperdpace Bold Italic.oft")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
	}
	
	
	public void loseLife(){
		lives--;
	}
	
	public void draw (Graphics g){
		
		//g.drawImage(pic, x, y, width, height, null);
		
		
		  AffineTransform oldtrans = new AffineTransform();
		  AffineTransform trans = new AffineTransform();

		    trans.setToIdentity();
		    trans.rotate(Math.toRadians(90-direction), width/2, height/2); 
		    trans.translate(x-(width/2), y-(height/2));
		    g.setTransform(trans);
		    g.drawImage(pic, x, y, width,height, null);
		    trans.setToIdentity();
		    g.setTransform(oldtrans);
		    
		
	}
	

	public void drawStats(Graphics g) {
		g.setFont(customFont);
		g.drawString(Integer.toString(score), 5, 5);
		
		int width = 40;
		int height =50;
		int place = 10;
		for(int l= lives; l>0; l--){
			g.drawImage(pic, place, width, height, null);
			place+=10;
		}
		
	}
	
	public int getLives(){
		return lives;
	}
	
	public void changeDirection(int i){
		direction+= i;
		if (direction ==-1){
			direction =359;
		}
	}



	public void move(int time) {
		
		
	}


	public void slowDown() {
		speed--;
		
	}


	public int getx() {
		return x;
	}


	public int getdirection() {
		return direction;
	}


	public int gety() {
		int umm;
		return y;
		
	}




	
	
}
