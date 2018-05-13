import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SmallA extends Asteroid{

	private Image pic;
	private static int SIZE = 30;
	
	public SmallA(){
		int ast = (int) (Math.random()*4 +1);
		
		try {
            pic = ImageIO.read(this.getClass().getResourceAsStream("rocket " + ast + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(pic, 2, 4, SIZE, SIZE, null);
		
	}

}
