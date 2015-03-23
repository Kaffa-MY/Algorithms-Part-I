import java.util.HashSet;
import java.util.Set;

public class Board {
	private int dimension;
	private int grid[][];
	private String directions[] = { "UP", "DOWN", "LEFT", "RIGHT" };

	// construct a board from an N-by-N array of block
	public Board(int[][] blocks) {
		dimension = blocks.length;
		grid = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				grid[i][j] = blocks[i][j];
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
				if (grid[i][j] != goalValueAt(i, j) && grid[i][j] != 0)
					hammingDistence++;
		return hammingDistence;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int manhanttanDistance = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				int n = grid[i][j];
				// excluding grid 0
				if (n == 0)
					continue;
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
				if (grid[i][j] != goalValueAt(i, j))
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
			Board nextBoard = move(direct);
			if (nextBoard != null)
				neighbors.add(nextBoard);
		}
		return neighbors;
	}

	// move 0 grid
	private Board move(String direct) {
		int row = 0, col = 0;
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				if (grid[i][j] == 0) {
					row = i;
					col = j;
					break;
				}

		int copyOfGrid[][] = new int[dimension][dimension];
		// System.arraycopy(grid, 0, copyOfGrid, 0, grid.length);
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				copyOfGrid[i][j] = grid[i][j];
		boolean check = false;
		switch (direct) {
		case "UP":
			if (row > 0) {
				check = true;
				int tmp = copyOfGrid[row][col];
				copyOfGrid[row][col] = copyOfGrid[row - 1][col];
				copyOfGrid[row - 1][col] = tmp;
			}
			break;

		case "DOWN":
			if (row < dimension - 1) {
				check = true;
				int tmp = copyOfGrid[row][col];
				copyOfGrid[row][col] = copyOfGrid[row + 1][col];
				copyOfGrid[row + 1][col] = tmp;
			}
			break;

		case "LEFT":
			if (col > 0) {
				check = true;
				int tmp = copyOfGrid[row][col];
				copyOfGrid[row][col] = copyOfGrid[row][col - 1];
				copyOfGrid[row][col - 1] = tmp;
			}
			break;

		case "RIGHT":
			if (col < dimension - 1) {
				check = true;
				int tmp = copyOfGrid[row][col];
				copyOfGrid[row][col] = copyOfGrid[row][col + 1];
				copyOfGrid[row][col + 1] = tmp;
			}
			break;
		}
		if (check)
			return new Board(copyOfGrid);
		else
			return null;
	}

	// string representation of this board (in the output format specified
	// below)
	public String toString() {
		String puzzleStr = new String(dimension + "\n");
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if ((j + 1) % dimension != 0)
					puzzleStr = puzzleStr + String.format("%2d ", grid[i][j]);

				else
					puzzleStr = puzzleStr + String.format("%2d\n", grid[i][j]);
			}
		}
		return puzzleStr;
	}

	private int goalValueAt(int row, int col) {
		if (row == dimension - 1 && col == dimension - 1)
			return 0;
		else
			return row * dimension + (col + 1);
	}
}