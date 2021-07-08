import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] { 
			{ -1, 0 }, // North
			{ 1, 0 }, // South
			{ 0, 1 }, // East
			{ 0, -1 } // West
	};

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	// 3D array to record the state(room and super power) 
	private boolean[][][] visitedWithPower;
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

	// Node with superpowers
	private class SuperNode {
		public Node node;
		public int superPowers = 0;

		public SuperNode(int row, int column, Node parent, int steps, int superPowers) {
			this.node = new Node(row, column, parent, steps);
			this.superPowers = superPowers;
		}
	}

	public MazeSolverWithPower() {
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

	// path search without super power
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

	// solve without super power
	public Integer solve(int startRow, int startCol) {
		Queue<Node> queue = new LinkedList<Node>();
		Node start = new Node(startRow, startCol, null, 0);
		visited[startRow][startCol] = true;
		Node end = null;
		// BFS
		queue.offer(start);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			updateReachableRooms(cur);
			// find the destination room
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
			// update the onPath attribute to form a path
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

	// check whether the wall can be breaked
	private boolean canBreak(int row, int col, int dir) {
		// the outer wall
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows())
			return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns())
			return false;

		switch (dir) {
		case NORTH:
			return maze.getRoom(row, col).hasNorthWall();
		case SOUTH:
			return maze.getRoom(row, col).hasSouthWall();
		case EAST:
			return maze.getRoom(row, col).hasEastWall();
		case WEST:
			return maze.getRoom(row, col).hasWestWall();
		}

		return true;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (reachableRooms.get(k) == null) {
			return 0;
		} else {
			return reachableRooms.get(k);
		}
	}

	// Find shortest path with powers allowed.
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol, int superpowers) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() || endRow < 0
				|| endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// set all visited flag to false
		// before we begin our search
		visitedWithPower = new boolean[maze.getRows()][maze.getColumns()][superpowers + 1];
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
				for (int k = 0; k <= superpowers; ++k) {
					this.visitedWithPower[i][j][k] = false;
				}
			}
		}
		reachableRooms = new HashMap<>();
		this.endRow = endRow;
		this.endCol = endCol;
		this.solved = false;
		return solve(startRow, startCol, superpowers);
	}

	// solve by using BFS with super powers allowed.
	public Integer solve(int startRow, int startCol, int superPowers) {
		Queue<SuperNode> queue = new LinkedList<>();
		// construct the start node
		SuperNode start = new SuperNode(startRow, startCol, null, 0, superPowers);
		visitedWithPower[startRow][startCol][superPowers] = true;
		Node end = null;
		queue.offer(start);
		updateReachableRooms(start);

		while (!queue.isEmpty()) {
			SuperNode cur = queue.poll();
			// record the first time when reaching the destination
			if (cur.node.row == endRow && cur.node.column == endCol && !this.solved) {
				end = cur.node;
				this.solved = true;
			}
			// cross the room without using super power
			for (int direction = 0; direction < 4; ++direction) {
				if (canGo(cur.node.row, cur.node.column, direction)) {
					int nextRow = cur.node.row + DELTAS[direction][0];
					int nextCol = cur.node.column + DELTAS[direction][1];
					if (!visitedWithPower[nextRow][nextCol][cur.superPowers]) {
						// mark this state as visited
						visitedWithPower[nextRow][nextCol][cur.superPowers] = true;
						// enqueue next state
						SuperNode next = new SuperNode(nextRow, nextCol, cur.node, cur.node.steps + 1, cur.superPowers);
						queue.offer(next);
						// update the reachable room
						updateReachableRooms(next);
					}
				}
			}
			// cross the room by using super power
			if (cur.superPowers > 0) {
				for (int direction = 0; direction < 4; ++direction) {
					if (canBreak(cur.node.row, cur.node.column, direction)) {
						int nextRow = cur.node.row + DELTAS[direction][0];
						int nextCol = cur.node.column + DELTAS[direction][1];
						if (!visitedWithPower[nextRow][nextCol][cur.superPowers - 1]) {
							// mark this state as visited.
							visitedWithPower[nextRow][nextCol][cur.superPowers - 1] = true;
							// enqueue next state
							SuperNode next = new SuperNode(nextRow, nextCol, cur.node, cur.node.steps + 1, cur.superPowers - 1);
							queue.offer(next);
							// update the reachable room
							updateReachableRooms(next);
						}
					}
				}
			}
		}

		// mark the room on the shortest path as onPath
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

	private void updateReachableRooms(SuperNode snode) {
		int count = 0;
		// length is the number of super power + 1
		int length = visitedWithPower[0][0].length;
		// use count to check if the room is visited twice(with different number of super powers)
		for (int i = 0; i < length; ++i) {
			if (visitedWithPower[snode.node.row][snode.node.column][i]) {
				count++;
			}
		}
		
		if (count == 1) {
			if (reachableRooms.get(snode.node.steps) == null) {
				reachableRooms.put((Integer) (snode.node.steps), 1);
			} else {
				Integer temp = reachableRooms.get(snode.node.steps) + 1;
				reachableRooms.put((Integer) (snode.node.steps), temp);
			}
		}
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			// System.out.println(solver.pathSearch(0, 0, 3, 3));
			// ImprovedMazePrinter.printMaze(maze);
			// for (int i = 0; i <= 9; ++i) {
			// 	System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			// }
			System.out.println(solver.pathSearch(0, 0, 4, 3, 2));
			ImprovedMazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
