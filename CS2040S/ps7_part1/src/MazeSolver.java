import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] { { -1, 0 }, // North
			{ 1, 0 }, // South
			{ 0, 1 }, // East
			{ 0, -1 } // West
	};

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private int endRow, endCol;
	private HashMap<Integer, Integer> reachableRooms;

	private class Node {
		public int row;
		public int column;
		public Node parent;
		public int steps;

		public Node(int row, int column, Node parent, int steps) {
			this.row = row;
			this.column = column;
			this.parent = parent;
			this.steps = steps;
		}
	}

	public MazeSolver() {
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];
		solved = false;
		reachableRooms = null;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() || endRow < 0
				|| endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		reachableRooms = new HashMap<>();
		this.endRow = endRow;
		this.endCol = endCol;
		solved = true;

		return solve(startRow, startCol);
	}

	public Integer solve(int startRow, int startCol) {
		Queue<Node> queue = new LinkedList<Node>();
		Node start = new Node(startRow, startCol, null, 0);
		visited[startRow][startCol] = true;
		Node end = null;
		queue.offer(start);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			updateReachableRooms(cur);
			if (cur.row == endRow && cur.column == endCol) {
				end = cur;
			}
			for (int direction = 0; direction < 4; ++direction) {
				if (canGo(cur.row, cur.column, direction)) {
					int nextRow = cur.row + DELTAS[direction][0];
					int nextCol = cur.column + DELTAS[direction][1];
					if (!visited[nextRow][nextCol]) {
						visited[nextRow][nextCol] = true;
						queue.offer(new Node(nextRow, nextCol, cur, cur.steps + 1));
					}
				}
			}
		}
		if (end == null) {
			// can not find the path.
			return null;
		} else {
			Node p = end;
			while (p != null) {
				maze.getRoom(p.row, p.column).onPath = true;
				p = p.parent;
			}
			return end.steps;
		}
	}

	private void updateReachableRooms(Node node) {
		if (reachableRooms.get(node.steps) == null) {
			reachableRooms.put((Integer) (node.steps), 1);
		} else {
			Integer temp = reachableRooms.get(node.steps) + 1;
			reachableRooms.put((Integer) (node.steps), temp);
		}
	}

	private boolean canGo(int row, int col, int dir) {
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows())
			return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns())
			return false;

		switch (dir) {
		case NORTH:
			return !maze.getRoom(row, col).hasNorthWall();
		case SOUTH:
			return !maze.getRoom(row, col).hasSouthWall();
		case EAST:
			return !maze.getRoom(row, col).hasEastWall();
		case WEST:
			return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (reachableRooms.get(k) == null) {
			return 0;
		} else {
			return reachableRooms.get(k);
		}
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 3, 3));
			ImprovedMazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
