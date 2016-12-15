package nl.jchmb.hexagon.puzzle;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		PuzzleView view;
		
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view = new PuzzleView();
		
		frame.setTitle("HoneyComb Puzzle Solver");
		frame.getContentPane().add(view);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
	}
}
