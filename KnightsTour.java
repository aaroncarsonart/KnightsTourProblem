import java.awt.Color;
import java.util.Stack;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * CS 345 - Lab2
 * <p>
 * {@code KnightsTour} implements a backtracking algorithm solution to the
 * knight's tour problem.
 * 
 * @author Aaron Carson
 * @version May 12, 2014
 */
public class KnightsTour {

	// *********************************************************************
	// fields
	// *********************************************************************

	private static int N;
	private static int M;
	private boolean[][] visited;
	private String[] solution; // holds the solution steps (example: {"0, 0"})
	private Stack<KPanel> stack;
	private Color animationColor;
	private Thread thread;

	// *********************************************************************
	// constructors
	// *********************************************************************

	/**
	 * Creates an N x N board to find a solution for the Knights Tour on, where
	 * N = size.
	 * 
	 * @param size The height and width of the board to solve over.
	 */
	public KnightsTour(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Size must be at least 1.");
		}
		N = size;
		M = N * N - 1;
		visited = new boolean[size][size];
		solution = new String[M + 1];
	}

	/**
	 * Initialize a default size board of 5 for the KnightsTour (used for
	 * testing).
	 */
	public KnightsTour() {
		this(5);
	}

	// *********************************************************************
	// methods
	// *********************************************************************

	/**
	 * This implements the pseudocode logic as given in the lab2 printout. It
	 * uses a backtracking, depth-first search to find a solution for the
	 * Knight's Tour Problem given the x & y positions for the knight.
	 * 
	 * @param x a horizontal coordinate on the board.
	 * @param y a vertical coordinate on the board.
	 * @param m the move number
	 * @return true or false
	 */
	public boolean move(int x, int y, int m) {
		// check if coordinate has passed off the board
		if (x < 0 || x >= N || y < 0 || y >= N) {
			return false;
		}

		// check if coordinate has already been visited
		if (visited[x][y] == true) {
			return false;
		}

		// valid move and knight has now made M moves; finished!
		if (m == M) {
			// System.out.println("A solution has been found");
			solution[m] = x + ", " + y;
			// System.out.println(solution[m]);

			visited[x][y] = true;
			return true;
		}
		// this is a valid move, but a tour has not been completed. So, try all
		// moves that can be made from this location recursively.
		else {
			visited[x][y] = true;

			boolean result = false;

			// iterate over all possible knight moves
			result = result || move(x + 2, y + 1, m + 1);
			result = result || move(x + 2, y - 1, m + 1);
			result = result || move(x - 2, y + 1, m + 1);
			result = result || move(x - 2, y - 1, m + 1);
			result = result || move(x + 1, y + 2, m + 1);
			result = result || move(x + 1, y - 2, m + 1);
			result = result || move(x - 1, y + 2, m + 1);
			result = result || move(x - 1, y - 2, m + 1);

			// one of the 8 moves led to a completed tour. so, this position is
			// part of a successful tour.
			if (result == true) {
				solution[m] = x + ", " + y;
				// System.out.println(solution[m]);
				return true;
			}

			// none of the moves from this position led to a successful tour. We
			// must backtrack and try a different path
			else {
				visited[x][y] = false;
				return false;
			}

		}
	}

	/**
	 * moveWithAnimation(int,int,int,Lab2GUI) is identical to move(int,int,int)
	 * except that it also displays each recursive call to move() in the GUI
	 * view. This is just for fun and to better understand how the backtracking
	 * algorithm works.
	 * <p>
	 * This implements the pseudocode logic as given in the lab2 printout. It
	 * uses a backtracking, depth-first search to find a solution for the
	 * Knight's Tour Problem given the x & y positions for the knight.
	 * 
	 * * @param x a horizontal coordinate on the board.
	 * @param y a vertical coordinate on the board.
	 * @param m the move number
	 * @return true or false
	 */
	public boolean moveWithAnimation(int x, int y, int m, Lab2GUI gui) {
		// check if coordinate has passed off the board
		if (x < 0 || x >= N || y < 0 || y >= N) {
			return false;
		}

		// check if coordinate has already been visited
		if (visited[x][y] == true) {
			return false;
		}

		// valid move and knight has now made M moves; finished!
		if (m == M) {
			solution[m] = x + ", " + y;
			visited[x][y] = true;
			addPanel(gui.getPanels()[x + y * N], String.valueOf(m), gui);
			finishAnimation();
			return true;
		}
		// this is a valid move, but a tour has not been completed. So, try all
		// moves that can be made from this location recursively.
		else {
			visited[x][y] = true;
			addPanel(gui.getPanels()[x + y * N], String.valueOf(m), gui);
			boolean result = false;

			// iterate over all possible knight moves
			result = result || moveWithAnimation(x + 2, y + 1, m + 1, gui);
			result = result || moveWithAnimation(x + 2, y - 1, m + 1, gui);
			result = result || moveWithAnimation(x - 2, y + 1, m + 1, gui);
			result = result || moveWithAnimation(x - 2, y - 1, m + 1, gui);
			result = result || moveWithAnimation(x + 1, y + 2, m + 1, gui);
			result = result || moveWithAnimation(x + 1, y - 2, m + 1, gui);
			result = result || moveWithAnimation(x - 1, y + 2, m + 1, gui);
			result = result || moveWithAnimation(x - 1, y - 2, m + 1, gui);

			// one of the 8 moves led to a completed tour. so, this position is
			// part of a successful tour.
			if (result == true) {
				solution[m] = x + ", " + y;
				return true;
			}

			// none of the moves from this position led to a successful tour. We
			// must backtrack and try a different path
			else {
				visited[x][y] = false;
				removePanel(gui.getPanels()[x + y * N], "", gui);
				return false;
			}
		}
	}

	/**
	 * Processes any swing updates for when another panel needs to be added to
	 * the stack and the colors updated.
	 * @param p the KPanel to push on the stack.
	 * @param s the string to update the KPanel with.
	 * @param gui the Lab2GUI associated with the current animation.
	 */
	private void addPanel(final KPanel p, final String s, Lab2GUI gui) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// update previous panel to normal
				if (!stack.isEmpty()) {
					KPanel temp = stack.peek();
					temp.setBackground(temp.getDefaultColor());
					temp.paintAll(temp.getGraphics());
				}
				p.setBackground(animationColor);
				p.repaint(s);
				stack.push(p);
			}
		});
		wait(gui.getWaitTime());
	}

	/**
	 * Processes any swing updates for when the current panel needs to be
	 * removed from the stack (during backtracking) and the colors updated.
	 * @param p the KPanel to push on the stack.
	 * @param s the string to update the KPanel with.
	 * @param gui the Lab2GUI associated with the current animation.
	 */
	private void removePanel(final KPanel p, final String s, Lab2GUI gui) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// update prev panel to normal, and remove it.
				if (!stack.isEmpty()) {
					KPanel temp = stack.pop();
					temp.setBackground(temp.getDefaultColor());
					temp.paintAll(temp.getGraphics());
				}
				stack.peek().setBackground(animationColor);
				stack.peek().repaint(null, 0);
				p.repaint(s);
			}
		});
		wait(gui.getWaitTime());
	}

	/**
	 * Provides one final update to the GUI to finish the animation.
	 */
	private void finishAnimation() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				stack.peek().setBackground(stack.peek().getDefaultColor());
				stack.peek().paintAll(stack.peek().getGraphics());
			}
		});
	}

	/**
	 * Wait for the specified # of milliseconds.
	 * @param waitTime how long to wait.
	 */
	private void wait(int waitTime) {
		if (waitTime > 0) {
			try {
				Thread.sleep(waitTime);
			}
			catch (Exception e) {

			}
		}
	}

	/**
	 * Solves the KnightsTour while updating the associated GUI.
	 * @param x The starting x value for the knight.
	 * @param y The starting y value for the knight.
	 * @param gui The GUI that is displaying the results.
	 */
	public void solveWithAnimation(final int x, final int y,
	        final Lab2GUI gui, final JTextArea t) {
		// setup fields
		stack = new Stack<KPanel>();
		animationColor = gui.getAnimationColor();

		// append info
		t.append("board size:\t" + N + " x " + N + "\nstart position:\t(" + x
		        + ", " + y + ")\nAnimating algorithm ...\n");

		// run animation in separate thread to allow scheduling.
		thread = new Thread() {
			public void run() {
				boolean solved = moveWithAnimation(x, y, 0, gui);
				if(solved){
					t.append("a solution was found.");
				}
				else{
					t.append("no solution was found.");
				}
			}
		};
		thread.start();
	}

	/**
	 * Returns the animation thread.
	 * @return the animation thread.
	 */
	public Thread getAnimationThread() {
		return thread;
	}

	/**
	 * Assigns a new value to the animation thread.
	 * @param t the new Thread to assign.
	 */
	@SuppressWarnings("deprecation")
	public void setAnimationThread(Thread t) {
		if (thread != null) {
			thread.stop();
		}
		thread = t;
	}

	/**
	 * Solves the given board state and prints the results to the terminal. The
	 * knight's starting position is specified by (x, y).
	 */
	public void solve(int x, int y, boolean print) {
		if (print) {
			System.out.print("board size:\t" + N + " x " + N
			        + "\nstart position:\t(" + x + ", " + y
			        + ")\nSolving Knight's Tour ...");
			if (move(x, y, 0)) {
				System.out.println("a solution was found.");
			}
			else {
				System.out.println("no solution was found.");
			}
		}
		else {
			move(x, y, 0);
		}
	}

	/**
	 * Call this solve method from the GUI, to streamline output to the
	 * textArea.
	 * @param x The knight's starting x position.
	 * @param y The knight's starting y position.
	 * @param textArea The JTextArea to update with this method.
	 */
	public boolean solve(int x, int y, JTextArea textArea) {
		textArea.append("board size:\t" + N + " x " + N
		        + "\nstart position:\t(" + x + ", " + y
		        + ")\nSolving Knight's Tour ... ");
		if (move(x, y, 0)) {
			textArea.append("a solution was found.\n");
			return true;
		}
		else {
			textArea.append("no solution was found.\n");
			return false;
		}
	}

	/**
	 * Prints the board in the order it visited each vertex.
	 */
	public void printSteps() {
		if (solution[0] == null) {
			System.out.println("(no solution)");
		}
		else {
			for (int m = 0; m <= M; m++) {
				System.out.println((m + 1) + ":\t" + solution[m]);
			}
		}
	}

	/**
	 * Prints the board in the order it visited each vertex to the given
	 * JTextArea, for convenience in displaying in a GUI.
	 */
	public void printSteps(JTextArea textArea) {
		if (solution[0] == null) {
			textArea.append("(no solution)\n");
		}
		else {
			for (int m = 0; m <= M; m++) {
				textArea.append((m + 1) + ":\t" + solution[m] + "\n");
			}
			textArea.append("\n");
		}
	}

	/**
	 * Access the solution array.
	 * @return The String[] representing the solution.
	 */
	public String[] getSolution() {
		return solution;
	}

	/**
	 * Generate a 2 dimensional integer array that represents the knight's tour.
	 * Each index of int[x][y] stores the number of the step during which the
	 * knight visits that position of the grid.
	 * 
	 * @return a 2-dimensional int array. This is of size 0x0 if there is no
	 *         solution.
	 */
	public int[][] getTour() {
		try {
			// build a temp array to generate solution grid
			int[][] tour = new int[N][N];
			for (int m = 0; m <= M; m++) {
				String[] a = solution[m].split(", ");
				int x = Integer.parseInt(a[0]);
				int y = Integer.parseInt(a[1]);
				tour[x][y] = m;
			}
			return tour;
		}

		// if a null pointer exception occurred, then there is no solution, so
		// an empty matrix is returned.
		catch (NullPointerException e) {
			return new int[0][0];
		}

	}

	/**
	 * Prints the board in the order it visited each vertex.
	 */
	public void printGrid() {
		int[][] tour = getTour();
		// iterate and print it to the terminal.
		for (int y = 0; y < tour.length; y++) {
			for (int x = 0; x < tour[y].length; x++) {
				if (tour[x][y] < 10) {
					System.out.print(" ");
				}
				System.out.print(tour[x][y] + "   ");

			}
			System.out.println("\n");
		}

	}

	/**
	 * Prints the board in the order it visited each vertex.
	 */
	public void printGrid(JTextArea textArea) {
		int[][] tour = getTour();
		// iterate and print it to the terminal.
		for (int y = 0; y < tour.length; y++) {
			for (int x = 0; x < tour[y].length; x++) {
				if (tour[x][y] < 10) {
					textArea.append(" ");
				}
				textArea.append(tour[x][y] + "   ");

			}
			textArea.append("\n\n");
		}

	}

	// *********************************************************************
	// MAIN
	// *********************************************************************

	/**
	 * Note: run code for 5x5 to 7x7 board sizes (maximum) to prevent running
	 * time from being very long as the growth rate is exponential.
	 * 
	 * @param args If a single parameter is given, it is interpreted as a single
	 *            int value to be the size of the grid. If three parameters are
	 *            given, it is interpreted as 3 int values: size, x, and y;
	 *            otherwise, it runs on a default size of 5 x 5, and a starting
	 *            (x, y) position of (0, 0).
	 */
	public static void main(String[] args) {
		KnightsTour tour;
		int size, x, y;
		if (args.length == 1) {
			size = Integer.parseInt(args[0]);
			x = 0;
			y = 0;
		}
		else if (args.length == 3) {
			size = Integer.parseInt(args[0]);
			x = Integer.parseInt(args[1]);
			y = Integer.parseInt(args[2]);
		}
		else {
			size = 5;
			x = 0;
			y = 0;
		}
		tour = new KnightsTour(size);
		tour.solve(x, y, true);
		tour.printSteps();
		tour.printGrid();
	}
}
