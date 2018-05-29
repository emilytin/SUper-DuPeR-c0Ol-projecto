
public class SmallA extends Asteroid{

	private static int SIZE = 45;
	
	public SmallA(){
		super(SIZE);
	}

	public SmallA(int x, int y, int angle,String sp) {
		super(x,y,angle,SIZE,sp);
	}

}
