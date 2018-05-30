
public class MedA extends Asteroid {

	private static final int SIZE = 70;

	public MedA(int x, int y, int angle, String sp) {
		super(x,y,angle,SIZE,sp);
	}
	
	public int getSize(){
		return SIZE;
	}

}
