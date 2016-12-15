package nl.jchmb.hexagon.puzzle;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import nl.jchmb.hexagon.VectorXY;

public class PuzzleSpace {
	public static final VectorXY EL = new VectorXY(-1, 1);
	public static final VectorXY ER = new VectorXY(1, 1);
	public static final VectorXY EV = new VectorXY(0, 2);
	
	private static final PuzzleComputer COMPUTER = new PuzzleComputer(true);
	
	public static VectorXY toLR(VectorXY position) {
		int a0, b0, a, b;
		
		a0 = position.x + position.y;
		b0 = position.y - position.x;
		a = a0 / 2;
		b = b0 / 2;
		return new VectorXY(a, b);
	}
	
	public static <T> Stream<PuzzlePoint> points(int limit) {
		return IntStream.range(0, limit)
			.mapToObj(i -> new PuzzlePoint(i, COMPUTER.compute(i)));
	}
}
