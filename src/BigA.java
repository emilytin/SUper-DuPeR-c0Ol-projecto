import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BigA extends Asteroid {

	private Image pic;
	private static int SIZE = 120;
	private int speed;
	
	public BigA(){
		int ast = (int) (Math.random()*4 +1);
		speed = (int) (Math.random()*20 +10);
		
		try {
            pic = ImageIO.read(this.getClass().getResourceAsStream("asteroid " + ast + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(pic, 10, 10, SIZE, SIZE, null);
	}

}
