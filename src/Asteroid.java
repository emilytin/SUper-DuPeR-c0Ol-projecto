import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asteroid {
	
	private Image pic;
	private static int SIZE;
	private double speed;
	private int angle;
	private int score;
	private int x;
	private int y;
	private String spin;
	private int shpin;
	
	
	public Asteroid(int SIZE){
		
		int droll = (int) (Math.random() *4);
		if (droll ==0){
			x = (int) (Math.random()*950);
			y= 950;
			if( x > 950/2){
				angle = (int) (Math.random()*90+180);
				spin ="left";
			}
			else{
				angle = (int) (Math.random()*90+270);
				spin = "right";
			}
		}
		else if( droll ==1){
			x = (int) (Math.random()*950);
			y= 0;
			
			if( x > 950/2){
				angle = (int) (Math.random()*90 +90);
				spin = "left";
			}
			else{
				angle = (int) (Math.random()*90);
				spin = "right";
			}
		}
		else if( droll ==2){
			y = (int) (Math.random()*950);
			x= 0;
			if( y > 950/2){
				angle = (int) (Math.random()*90 +270);
			}
			else{
				angle = (int) (Math.random()*90);
			}
			spin = "right";
		}
		else if( droll ==3){
			y = (int) (Math.random()*950);
			x= 950;
			if( y > 950/2){
				angle = (int) (Math.random()*90 +180);
			}
			else{
				angle = (int) (Math.random()*90 +90);
			}
			spin = "left";
		}
		
		
		int ast = (int) (Math.random()*4 +1);
		try {
            pic = ImageIO.read(this.getClass().getResourceAsStream("asteroid " + ast + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		this.SIZE=SIZE;
		if(this.SIZE == 130 ){
			score= 20;
			speed = (Math.random()*1.5+1.5);
		}
		else if (this.SIZE== 70){
			score= 50;
			speed = (Math.random()*2 +1.5);
		}
		else if (this.SIZE== 45){
			score= 100;
			speed = (Math.random()*3 +2);
		}
	
		speed = (Math.random()*1.5+1.5);
	}
	
	public Asteroid(int x2, int y2, int angle2, int SIZE, String sp) {
		x=x2;
		y=y2;
		angle=angle2;
		shpin= 0;
		spin = sp;
		
		int ast = (int) (Math.random()*4 +1);
		try {
            pic = ImageIO.read(this.getClass().getResourceAsStream("asteroid " + ast + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		this.SIZE=SIZE;
		if(this.SIZE == 130 ){
			score= 20;
		}
		else if (this.SIZE== 70){
			score= 50;
		}
		else if (this.SIZE== 45){
			score= 100;
		}
		speed=(Math.random()*1.5+1.5);
	}

	public void draw (Graphics g){
		//g.drawImage(pic, x, y, SIZE, SIZE, null);
		Graphics2D g2d=(Graphics2D)g;
	    AffineTransform backup = g2d.getTransform();
	    if( spin.equals("left")){
	    	shpin--;
	    }
	    else if( spin.equals("right")){
	    	shpin++;
	    }
	    double radangle= Math.toRadians(shpin);
	    AffineTransform a = AffineTransform.getRotateInstance(radangle, x+ (SIZE/2), y+ (SIZE/2));
	    g2d.setTransform(a);
	    g2d.drawImage(pic, x, y , SIZE, SIZE, null);
	    g2d.setTransform(backup);
	}

	public void move() {
		int changey = (int) (speed*(Math.sin(Math.toRadians(angle))));
		int changex = (int) (speed*(Math.cos(Math.toRadians(angle))));
		
		x = x+changex;
		y = y+changey;
	}
	
	
	public int getx(){
		return x;
	}
	
	public int gety(){
		return y;
	}
	
	public int getSIZE(){
		return SIZE;
	}
	
	public int getAngle(){
		return angle;
	}
	
	public int getScore(){
		return score;
	}
}
