import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MedA extends Asteroid {

	private Image pic;
	private static int SIZE = 60;
	
	public MedA(){
		int ast = (int) (Math.random()*4 +1);
		
		try {
            pic = ImageIO.read(this.getClass().getResourceAsStream("rocket " + ast + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
