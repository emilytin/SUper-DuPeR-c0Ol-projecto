import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	private int speed;
	private int x;
	private int y;
	private int d;
	
	public Bullet(int xint,int yint, int dint){
		speed = 8;
		x = xint;
		y = yint;
		d = dint;
	}
	
	public void draw (Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(x, y, 2, 2);
	}
	public void move(){
		int changey = (int) (speed*(Math.sin(Math.toRadians(d))));
		int changex = (int) (speed*(Math.cos(Math.toRadians(d))));
		
		x = x+changex;
		y = y+changey;
	}
	
	public int getx(){
		return x;
	}
	
	public int gety(){
		return y;
	}
	
}
