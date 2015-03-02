import java.util.HashSet;
import java.util.Set;

public class Board {
	private int dimension;
	private int grid[][];
	private int goal[][];
	private String directions[] = { "UP", "DOWN", "LEFT", "RIGHT" };

	// construct a board from an N-by-N array of block
	public Board(int[][] blocks) {
		dimension = blocks.length;
		grid = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				grid[i][j] = blocks[i][j];

		goal = new int[dimension][dimension];
		int n = 1;
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++, n++)
				goal[i][j] = n;
		goal[dimension - 1][dimension - 1] = 0;
	}

	// board dimension N
	public int dimension() {
		return dimension;
	}

	// number of blocks out of place
	public int hamming() {
		int hammingDistence = 0;
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				if (goal[i][j] != grid[i][j])
					hammingDistence++;
		return hammingDistence;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhanttan() {
		int manhanttanDistance = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				int n = grid[i][j];
				// start from 0 to dimension-1
				int row = 0;
				int col = 0;
				if (n % dimension == 0) {
					col = dimension - 1;
					row = n / dimension - 1;
				} else {
					col = n % dimension - 1;
					row = n / dimension;
				}
				manhanttanDistance = manhanttanDistance + Math.abs(i - row)
						+ Math.abs(j - col);
			}
		}
		return manhanttanDistance;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				if (goal[i][j] != grid[i][j])
					return false;
		return true;
	}

	// board that is obtained by exchanging two adjacent blocks in the same row
	public Board twin() {
		if (dimension < 2)
			return new Board(grid);

		int[][] twinGrid = new int[dimension][dimension];
		// System.arraycopy(grid, 0, twinGrid, 0, grid.length);
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				twinGrid[i][j] = grid[i][j];
		int rdmRow = (int) Math.random() * grid.length;
		int rdmCol = Math.max(0, grid.length - 1) / 2;
		if (twinGrid[rdmRow][rdmCol] == 0 || twinGrid[rdmRow][rdmCol + 1] == 0) {
			if (rdmRow < grid.length - 1)
				rdmRow++;
			else if (rdmRow > 0)
				rdmRow--;
		}
		twinGrid[rdmRow][rdmCol] = grid[rdmRow][rdmCol + 1];
		twinGrid[rdmRow][rdmCol + 1] = grid[rdmRow][rdmCol];
		return new Board(twinGrid);
	}

	// does this board equal?
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Board) {
			Board that = (Board) obj;
			for (int i = 0; i < dimension; i++)
				for (int j = 0; j < dimension; j++)
					if (this.grid[i][j] != that.grid[i][j])
						return false;
			return true;
		}
		return false;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		// states after moving blank grid
		Set<Board> neighbors = new HashSet<Board>();
		for (String direct : directions) {
			int copyOfGrid[][] = new int[dimension][dimension];
			// System.arraycopy(grid, 0, copyOfGrid, 0, grid.length);
			for (int i = 0; i < dimension; i++)
				for (int j = 0; j < dimension; j++)
					copyOfGrid[i][j] = grid[i][j];
			int row = 0, col = 0;
			for (int i = 0; i < dimension; i++)
				for (int j = 0; j < dimension; j++)
					if (grid[i][j] == 0) {
						row = i;
						col = j;
						break;
					}
			switch (direct) {
			case "UP":
				if (row > 0) {
					int tmp = copyOfGrid[row][col];
					copyOfGrid[row][col] = copyOfGrid[row - 1][col];
					copyOfGrid[row - 1][col] = tmp;
					Board nghb = new Board(copyOfGrid);
					neighbors.add(nghb);
				}
				break;

			case "DOWN":
				if (row < dimension - 1) {
					int tmp = copyOfGrid[row][col];
					copyOfGrid[row][col] = copyOfGrid[row + 1][col];
					copyOfGrid[row + 1][col] = tmp;
					Board nghb = new Board(copyOfGrid);
					neighbors.add(nghb);
				}
				break;

			case "LEFT":
				if (col > 0) {
					int tmp = copyOfGrid[row][col];
					copyOfGrid[row][col] = copyOfGrid[row][col - 1];
					copyOfGrid[row][col - 1] = tmp;
					Board nghb = new Board(copyOfGrid);
					neighbors.add(nghb);
				}
				break;

			case "RIGHT":
				if (col < dimension - 1) {
					int tmp = copyOfGrid[row][col];
					copyOfGrid[row][col] = copyOfGrid[row][col + 1];
					copyOfGrid[row][col + 1] = tmp;
					Board nghb = new Board(copyOfGrid);
					neighbors.add(nghb);
				}
				break;

			default:
				break;
			}
		}
		return neighbors;
	}

	// string representation of this board (in the output format specified
	// below)
	public String toString() {
		String puzzleStr = new String();
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if ((j + 1) % dimension != 0)
					puzzleStr = puzzleStr + grid[i][j] + " ";
				else
					puzzleStr = puzzleStr + grid[i][j] + "\n";
			}
		}
		return puzzleStr;
	}
}