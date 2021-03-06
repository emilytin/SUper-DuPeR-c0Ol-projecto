import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private int tipx;
	private int tipy;
	private int direction;
	private Image pic;
	private Image flame;
	public static int width = 80;
	public static int height = 90;
	private double speed;
	private double maxSpeed;
	private double acceleration;
	private double friction;
	
	public Rocket(){
		x= 475;
		y=475;
		tipx = (int)(x + ( height/2 *Math.cos(Math.toRadians(90-direction))));
		tipy= (int) (y- (height/2 *Math.sin(Math.toRadians(90-direction))));
		direction =0;
		speed=0;
		friction = 0.95;
		maxSpeed = 4.0;
		acceleration = .2;
	
		
		try {
            pic = ImageIO.read(this.getClass().getResourceAsStream("rocket final.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		try {
            flame = ImageIO.read(this.getClass().getResourceAsStream("flame rocket.png"));
        } catch (IOException e) {
            e.printStackTrace();
        	}
		
		
	}
	
	
	
	
	public void draw (Graphics g, boolean up){
		
		if (up==true){
			Graphics2D g2d=(Graphics2D)g;
		    AffineTransform backup = g2d.getTransform();
		    double radangle= Math.toRadians(direction);
		    AffineTransform a = AffineTransform.getRotateInstance(radangle, x, y);
		    g2d.setTransform(a);
		    g2d.drawImage(flame, x - (60/2), y - (75/2) , 60, 75, null);
		    g2d.setTransform(backup);
		}
		else{
		
		Graphics2D g2d=(Graphics2D)g;
		    AffineTransform backup = g2d.getTransform();
		    double radangle= Math.toRadians(direction);
		    AffineTransform a = AffineTransform.getRotateInstance(radangle, x, y);
		    g2d.setTransform(a);
		    g2d.drawImage(pic, x - (width/2), y - (height/2) , width, height, null);
		    g2d.setTransform(backup);
		}
	}

	
	public void changeDirection(int i){
		direction+= i;
		if (direction <=-1){
			direction = 360+direction;
		}
		else if (direction >=360){
			direction = direction%360;
		}
		
		int changety= (int)(height/2 * Math.sin(Math.toRadians(direction-90)));
		int changetx= (int)(height/2 * Math.cos(Math.toRadians(direction-90)));
		tipx= x + changetx;
		tipy = y+ changety;
	}



	public void move(int oldDirection) {
				if(speed < maxSpeed){
					double changeY = (speed+acceleration)*(Math.sin(Math.toRadians(oldDirection)));
					double changeX = (speed+acceleration)*(Math.cos(Math.toRadians(oldDirection)));
					
					x = (int) (x+changeX);
					y = (int) (y-changeY);
					tipx+= changeX;
					tipy-= changeY;
					
					speed = speed+(acceleration);
					if(x>950){
						x=0;
						int changex = (int)(height/2 * Math.cos(Math.toRadians(direction-90)));
						tipx = x+changex;
					}
					if(y>950){
						y=0;
						int changey = (int)(height/2 * Math.sin(Math.toRadians(direction-90)));
						tipy = y+changey;
					}
					if (x<0){
						x=950;
						int changex = (int)(height/2 * Math.cos(Math.toRadians(direction-90)));
						tipx = x+changex;
					}
					if (y<0){
						y=950;
						int changey = (int)(height/2 * Math.sin(Math.toRadians(direction-90)));
						tipy = y+changey;
					}
				}
					else{
						double changeY = (maxSpeed*(Math.sin(Math.toRadians(oldDirection))));
						double changeX = (maxSpeed*(Math.cos(Math.toRadians(oldDirection))));
						x = (int) (x+changeX);
						y = (int) (y-changeY);
						tipx+= changeX;
						tipy-= changeY;
						if(x>950){
							x=0;
							int changex = (int)(height/2 * Math.cos(Math.toRadians(direction-90)));
							tipx = x+changex;
						}
						if(y>950){
							y=0;
							int changey = (int)(height/2 * Math.sin(Math.toRadians(direction-90)));
							tipy = y+changey;
						}
						if (x<0){
							x=950;
							int changex = (int)(height/2 * Math.cos(Math.toRadians(direction-90)));
							tipx = x+changex;
						}
						if (y<0){
							y=950;
							int changey = (int)(height/2 * Math.sin(Math.toRadians(direction-90)));
							tipy = y+changey;
						}
						
						
				}
		}
	
	public void slide(){
		double changeY = ((speed/2)*(Math.sin(Math.toRadians(90-direction))));
		double changeX = (speed*(Math.cos(Math.toRadians(90-direction))));
		x = (int) (x+changeX);
		y = (int) (y-changeY);
		tipx+= changeX;
		tipy-= changeY;
		if(x>950){
			x=0;
			int changex = (int)(height/2 * Math.cos(Math.toRadians(direction-90)));
			tipx = x+changex;
		}
		if(y>950){
			y=0;
			int changey = (int)(height/2 * Math.sin(Math.toRadians(direction-90)));
			tipy = y+changey;
		}
		if (x<0){
			x=950;
			int changex = (int)(height/2 * Math.cos(Math.toRadians(direction-90)));
			tipx = x+changex;
		}
		if (y<0){
			y=950;
			int changey = (int)(height/2 * Math.sin(Math.toRadians(direction-90)));
			tipy = y+changey;
		}
		
	}

	public void slowDown() {
		speed= speed*friction;
		if (speed<.05){
			speed = 0;
		}
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getAcceleration() {
		return acceleration;
	}

	public int getx() {
		return x;
	}

	public int getdirection() {
		return direction;
	}

	public int gety() {
		return y;
	}

	public Bullet shoot() {
		int bx = tipx;
		int by = tipy;
		int bd = direction-90 ;
		
		Bullet b = new Bullet(bx,by,bd);
		return b;
	}

	public Image getPic() {
		return pic;
	}
}
