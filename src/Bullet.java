import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	private int speed;
	private int x;
	private int y;
	private int d;
	private int time;
	
	public Bullet(int xint,int yint, int dint){
		speed = 8;
		x = xint;
		y = yint;
		d = dint;
		time = 0;
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
		
		if(x>950){
			x=0;
		}
		if(y>950){
			y=0;
		}
		if (x<0){
			x=950;
		}
		if (y<0){
			y=950;
		}
		
	}
	
	public int getx(){
		return x;
	}
	
	public int gety(){
		return y;
	}
	
	public int getTime(){
		return time;
	}
	
	public void changeTime(int t){
		time+= t;
	}
}
