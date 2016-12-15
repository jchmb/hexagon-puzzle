package nl.jchmb.hexagon;

public class VectorXY {
	public static final VectorXY E0 = new VectorXY(0, 0);
	public static final VectorXY EX = new VectorXY(1, 0);
	public static final VectorXY EY = new VectorXY(0, 1);
	public static final VectorXY EXY = new VectorXY(1, 1);
	
	public final int x, y;
	
	public VectorXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public VectorXY add(VectorXY v) {
		return new VectorXY(x + v.x, y + v.y);
	}
	
	public VectorXY subtract(VectorXY v) {
		return new VectorXY(x - v.x, y - v.y);
	}
	
	public VectorXY negate() {
		return scale(-1);
	}
	
	public VectorXY scale(int mult) {
		return new VectorXY(mult * x, mult * y);
	}
	
	public int contract(VectorXY v) {
		return x * v.x + y * v.y;
	}
	
	@Override
	public int hashCode() {
		return 7 * x + 13 * y;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof VectorXY)) {
			return false;
		}
		VectorXY obj = (VectorXY) other;
		return x == obj.x && y == obj.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
