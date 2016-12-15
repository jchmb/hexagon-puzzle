package nl.jchmb.hexagon;

public enum Direction {
	TOP_RIGHT(new VectorXY(1, 1)),
	BOTTOM_RIGHT(new VectorXY(1, -1)),
	BOTTOM(new VectorXY(0, -2)),
	BOTTOM_LEFT(new VectorXY(-1, -1)),
	TOP_LEFT(new VectorXY(-1, 1)),
	TOP(new VectorXY(0, 2));

	private final VectorXY offset;

	private Direction(VectorXY offset) {
		this.offset = offset;
	}
	
	public VectorXY offset() {
		return offset;
	}
	
	public Direction rotate(int rotation) {
		int index = (ordinal() + rotation) % Direction.values().length;
		return Direction.values()[index];
	}
	
	public Direction negate() {
		return rotate(3);
	}
}
