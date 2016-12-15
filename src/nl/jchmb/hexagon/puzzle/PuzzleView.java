package nl.jchmb.hexagon.puzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import nl.jchmb.hexagon.HexagonStructure;
import nl.jchmb.hexagon.VectorXY;

public class PuzzleView<T> extends JPanel {
	private final int LIMIT = 320;
	private PuzzleComputer computer;
	private HexagonStructure<T> structure;
	private int width = 1000, height = 800;
	private int cellSize = 25;
	public volatile int index = 0;
//	public int i = 12, j = 49;
	public int i = 8, j = 123;
	private int toggle = 0;
	private final int h = (int) (Math.cos((1.0d/3.0d) * Math.PI) * ((double) cellSize));
	private final int w = (int) (Math.sin((1.0d/3.0d) * Math.PI) * ((double) cellSize));
	
	public PuzzleView(HexagonStructure<T> structure) {
		setPreferredSize(new Dimension(width, height));
		this.structure = structure;
		computer = new PuzzleComputer(true);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int originX = x - width / 2;
				int originY = y - height / 2;
				x = (originX - w) / (w * 2);
				y = -((originY - h) / (h * 2));
				VectorXY v = new VectorXY(x, y);
				
//				System.out.println(v.x + "," + v.y);
				Optional<PuzzlePoint> optional = PuzzleSpace.points(LIMIT).filter(z -> z.getVector().equals(v)).findAny();
				
				if (!optional.isPresent()) {
					return;
				}
				
				int index = optional.get().getIndex();
//				System.out.println(index);
				if (toggle == 0) {
					i = index;
				} else {
					j = index;
				}
				toggle = (toggle + 1) % 2;
				repaint();
			}
		});
		setFocusable(true);
	}
	
	private void paintCell(Graphics g, VectorXY p) {
//		g.fillOval(p.x * cellSize + width / 2, p.y * cellSize + height / 2, cellSize, cellSize);
		double angle = 0.0d;
		int originX = p.x * (w * 2) + width / 2;
		int originY = -p.y * h * 2 + height / 2;
		int x = originX;
		int y = originY;
		int x2, y2;
		int[] xx = new int[6];
		int[] yy = new int[6];
		for (int i = 0; i < 6; i++) {
			x2 = x + ((int) (((double) cellSize) * Math.cos(angle)));
			y2 = y - ((int) (((double) cellSize) * Math.sin(angle)));
			xx[i]= x;
			yy[i]= y; 
//			g.drawLine(x, y, x2, y2);
			x = x2;
			y = y2;
			angle -= (1.0d/3.0d) * Math.PI;
		}
		g.fillPolygon(xx, yy, 6);
		
//		Color temp = g.getColor();
//		g.setColor(Color.YELLOW);
//		g.drawString("(" + p.x + "," + p.y + ")", originX - w / 4, originY + h * 2);
//		g.setColor(temp);
	}
	
	@Override
	public void paint(Graphics g) {
		int limit = LIMIT;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
//		structure.points()
//			.forEach(p -> paintCell(g, p));
		PuzzleSpace.points(limit)
			.forEach(z -> paintCell(g, z.getVector()));
		
		g.setColor(Color.RED);
		paintCell(g, VectorXY.E0);
		
		g.setColor(Color.BLUE);
		VectorXY p = computer.compute(i);
		List<VectorXY> list = computer.computePathAsStream(i, j).collect(Collectors.toList());
		g.setColor(Color.GREEN);
		for (VectorXY v : list) {
			paintCell(g, p);
			p = p.add(v);
			g.setColor(Color.BLUE);
		}
		g.setColor(Color.GREEN);
		paintCell(g, p);
		
		PuzzleSpace.points(limit)
			.forEach(z -> {
				int originX = z.getVector().x * (w * 2) + width / 2;
				int originY = -z.getVector().y * h * 2 + height / 2;
				g.setColor(Color.YELLOW);
//				g.drawString("(" + p.x + "," + p.y + ")", originX - w / 4, originY + h * 2);
				g.drawString(Integer.toString(z.getIndex()), originX - w / 4, originY + h * 2);
			});
		
		g.setColor(Color.RED);
		g.drawString("Distance: " + list.size(), 30, 30);
		
//		paintCell(g, computer.compute(index));
	}
}
