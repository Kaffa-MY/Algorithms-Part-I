import java.util.ArrayList;

public class Solver {
	private MinPQ<SearchNode> searchNodeMinPQ = null;
	private MinPQ<SearchNode> twinSearchNodeMinPQ = null;
	private SearchNode goalNode = null;
	private Board initBoard;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		// run the A* algorithm simultaneously on two puzzle instances—one with
		// the initial board and one with the initial board modified by swapping
		// a pair of adjacent blocks in the same row.
		initBoard = initial;
		
		searchNodeMinPQ = new MinPQ<Solver.SearchNode>();
		twinSearchNodeMinPQ = new MinPQ<Solver.SearchNode>();

		ArrayList<String> stepList = new ArrayList<String>();
		SearchNode searchNode = new SearchNode(initial, 0, stepList);

		ArrayList<String> twinStepList = new ArrayList<String>();
		SearchNode twinSearchNode = new SearchNode(initial.twin(), 0, twinStepList);
		
		searchNodeMinPQ.insert(searchNode);
		twinSearchNodeMinPQ.insert(twinSearchNode);
		
		goalNode = aStarSearch(searchNodeMinPQ, twinSearchNodeMinPQ);
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		if (goalNode == null)
			return false;
		return true;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		if (goalNode == null)
			return -1;
		return goalNode.numOfMoves;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (goalNode == null)
			return null;
		//return goalNode.boardList;
		ArrayList<Board> boards = new ArrayList<Board>();
		Board board = initBoard;
		for (int i = 0; i < goalNode.numOfMoves; i++) {
			Board nextBoard = board.move(goalNode.steps.get(i));
			boards.add(nextBoard);
			board = nextBoard;
		}
		return boards;
	}

	// A* search method
	private SearchNode aStarSearch(MinPQ<SearchNode> queue,
			MinPQ<SearchNode> twinQueue) {
		while (!queue.isEmpty()) {
			SearchNode nodeToSearch = queue.delMin();
			ArrayList<String> visted = new ArrayList<String>();
			if (nodeToSearch.board.isGoal())
				return nodeToSearch;

			for (Board nextBoard : nodeToSearch.board.neighbors()) {
				if (visted.contains(nextBoard.toString()))
					continue;
				else
					visted.add(nextBoard.toString());

				ArrayList<String> steps = new ArrayList<String>(
						nodeToSearch.steps);
				steps.add(nextBoard.getFrmMov());
				SearchNode nextNode = new SearchNode(nextBoard,
						nodeToSearch.numOfMoves + 1, steps);
				queue.insert(nextNode);
			}
		}

		// while (!twinQueue.isEmpty()) {
		// SearchNode nodeToSearch = twinQueue.delMin();
		// ArrayList<String> visited = new ArrayList<String>();
		// if (nodeToSearch.board.isGoal()) {
		// StdOut.println("solution does not exist");
		// return null;
		// }
		//
		// for (Board nextBoard : nodeToSearch.board.neighbors()) {
		// if (visited.contains(nextBoard.toString()))
		// continue;
		// else
		// visited.add(nextBoard.toString());
		// ArrayList<Board> steps = new ArrayList<Board>(
		// nodeToSearch.boardList);
		// steps.add(nextBoard);
		// SearchNode nextNode = new SearchNode(nextBoard,
		// nodeToSearch.numOfMoves + 1, steps);
		// twinQueue.insert(nextNode);
		// }
		// }
		return null;
	}

	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private int numOfMoves;
		// list to store statuses
		private ArrayList<String> steps;

		public SearchNode(Board board, int move, ArrayList<String> steps) {
			// TODO Auto-generated constructor stub
			this.board = board;
			this.numOfMoves = move;
			this.steps = steps;
		}

		@Override
		public int compareTo(SearchNode that) {
			// TODO Auto-generated method stub
			// A-star alg
			if (that == null)
				return -1;
			return board.manhattan() + numOfMoves
					- (that.board.manhattan() + that.numOfMoves);
		}
	}
}