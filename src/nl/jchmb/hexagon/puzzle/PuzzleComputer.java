package nl.jchmb.hexagon.puzzle;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import nl.jchmb.hexagon.Direction;
import nl.jchmb.hexagon.VectorXY;

public class PuzzleComputer {
	private boolean useEV = false;
	
	public PuzzleComputer() {
		this(false);
	}
	
	public PuzzleComputer(boolean useEV) {
		this.useEV = useEV;
	}
	
	private VectorXY computeRadius(int index, int r) {
		if (index < r * 6) {
			return new VectorXY(index, r);
		}
		return computeRadius(index - r * 6, r + 1);
	}
	
	private VectorXY computeRadius(int index) {
		return index == 0 ? new VectorXY(0, 0) : computeRadius(index - 1, 1);
	}
	
	private VectorXY computeOffset(int index, int r) {
		return PuzzleSpace.EV.add(PuzzleSpace.EL.scale(r - 1));
	}
	
	private VectorXY computeWalk(int index, int r) {
//		for (int i = 0; i <= index; i++) {
//			Direction d;
//			if (i < r) {
//				d = Direction.TOP_RIGHT;
//			} else {
//				
//			}
//		}
		return Stream.of(Direction.values())
			.map(d -> d.offset())
			.flatMap(
					d -> IntStream.range(0, d.equals(Direction.TOP_RIGHT.offset()) ? (r - 1) : r)
							.mapToObj(i -> d)
			)
			.sequential()
			.limit(index)
//			.forEach(
//				d -> {
//					System.out.print("(" + d.x + "," + d.y + ") ");
//				}
//			);
//		System.out.println();
//		return VectorXY.E0;
			.reduce(VectorXY.E0, (a, b) -> a.add(b));
	}
	
	public VectorXY compute(int index) {
		if (index == 0) {
			return VectorXY.E0;
		}
		VectorXY radius = computeRadius(index);
		int i, r;
		i = radius.x;
		r = radius.y;
		VectorXY offset = computeOffset(i, r);
		VectorXY walk = computeWalk(i, r);
		return offset.add(walk);
	}
	
	public VectorXY computePath(int i, int j) {
		VectorXY a, b;
		a = compute(i);
		b = compute(j);
		return PuzzleSpace.toLR(a.subtract(b));
	}
	
	public Stream<VectorXY> computePathAsStream(int i, int j) {
		VectorXY v = computePath(i, j);
		if (useEV && Math.signum(v.x) == Math.signum(v.y)) {
			int l, r, y;
			y = Math.min(Math.abs(v.x), Math.abs(v.y));
			l = v.x - ((int) Math.signum(v.x)) * y;
			r = v.y - ((int) Math.signum(v.y)) * y;
			return Stream.concat(
				Stream.concat(
					IntStream.range(0, Math.abs(l)).mapToObj(k -> PuzzleSpace.ER.scale((int) -Math.signum(l))),
					IntStream.range(0, Math.abs(r)).mapToObj(k -> PuzzleSpace.EL.scale((int) -Math.signum(r)))
				),
				IntStream.range(0, Math.abs(y)).mapToObj(k -> PuzzleSpace.EV.scale((int) -Math.signum(y)))
			);
		} else {
			return Stream.concat(
				IntStream.range(0, Math.abs(v.x)).mapToObj(k -> PuzzleSpace.ER.scale((int) -Math.signum(v.x))),
				IntStream.range(0, Math.abs(v.y)).mapToObj(k -> PuzzleSpace.EL.scale((int) -Math.signum(v.y)))
			);
		}
	}
}
