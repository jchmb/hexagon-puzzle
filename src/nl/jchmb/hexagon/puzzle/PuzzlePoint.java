package nl.jchmb.hexagon.puzzle;

import nl.jchmb.hexagon.VectorXY;

public class PuzzlePoint {
	private int index;
	private VectorXY vector;
	
	public PuzzlePoint(int index, VectorXY vector) {
		this.index = index;
		this.vector = vector;
	}
	
	public int getIndex() {
		return index;
	}
	
	public VectorXY getVector() {
		return vector;
	}
}
