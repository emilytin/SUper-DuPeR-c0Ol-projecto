
public class SmallA extends Asteroid{

	private static final int SIZE = 45;
	
	public SmallA(int x, int y, int angle,String sp) {
		super(x,y,angle,SIZE,sp);
	}
	
	public int getSize(){
		return SIZE;
	}

}
