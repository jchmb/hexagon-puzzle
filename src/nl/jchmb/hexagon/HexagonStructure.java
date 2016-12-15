package nl.jchmb.hexagon;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import nl.jchmb.hexagon.puzzle.PuzzleSpace;

public class HexagonStructure<T> {
	private Map<VectorXY, T> entities;
	private int size;
	
	public HexagonStructure(int size) {
		this.entities = new HashMap<>();
		this.size = size;
	}
	
	public int size() {
		return size;
	}
	
	public Optional<T> get(VectorXY position) {
		return Optional.of(entities.get(position));
	}
	
	public void set(VectorXY position, T entity) {
		entities.put(position, entity);
	}
	
	public boolean validate(VectorXY position) {
		int a0, b0, a, b;
		
		a0 = position.x + position.y;
		b0 = position.y - position.x;
		if (a0 % 2 != 0 || b0 % 2 != 0) {
			return false;
		}
		a = a0 / 2;
		b = b0 / 2;
		return Math.abs(a) <= size && Math.abs(b) < size ||
				Math.abs(a) < size && Math.abs(b) <= size;
	}
	
	public Stream<VectorXY> points() {
		int realSize = size * 2;
		return IntStream.range(0, realSize * realSize)
			.mapToObj(
				i -> PuzzleSpace.EL.scale(i / realSize - size)
						.add(PuzzleSpace.ER.scale(i % realSize - size))
			)
			.filter(this::validate);
	}
	
	public Stream<VectorXY> neighbours(VectorXY position) {
		return Stream.of(Direction.values())
			.map(d -> position.add(d.offset()))
			.filter(this::validate);
	}
}
