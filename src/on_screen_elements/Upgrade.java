package on_screen_elements;


public abstract class Upgrade implements Drawable {
	
	protected static final int upgradeDiameter = 40;
	protected int x,y;
	
	
	public Upgrade(int x, int y) {
		this.x = x;
		this.y =y;
	}
	
	public abstract void applyModification();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getUpgradeDiameter() {
		return upgradeDiameter;
	}
	
	
	
	
}