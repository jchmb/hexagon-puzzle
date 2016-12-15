package nl.jchmb.hexagon.puzzle;

import javax.swing.JFrame;

import nl.jchmb.hexagon.HexagonStructure;

public class Main {
	public static void main(String[] args) {
		int size = 10;
		
		HexagonStructure<?> structure = new HexagonStructure<>(size);
		PuzzleView<?> view;
		
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view = new PuzzleView<>(structure);
		
		frame.getContentPane().add(view);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		
//		new Thread(
//			() -> {
//				while (true) {
//					try {
//						Thread.sleep(1000L);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						break;
//					}
//					view.index++;
//					view.invalidate();
//					view.repaint();
////					System.out.println(view.index + "");
//				}
//			}
//		).start();
	}
}
