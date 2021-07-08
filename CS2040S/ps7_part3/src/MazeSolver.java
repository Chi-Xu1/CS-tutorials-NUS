import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final int FEAR_OF_STEP = 1;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(Room::getNorthWall,
			Room::getEastWall, Room::getWestWall, Room::getSouthWall);
	private static final int[][] DELTAS = new int[][] { { -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;
	private int[][] distance;
	private int endRow = 0;
	private int endCol = 0;
	private PriorityQueue<Node> heap;
	// coordinate of the safe room
	private int safeRow = 0;
	private int safeCol = 0;

	public class Node implements Comparable<Node> {
		public int row;
		public int column;
		public int dist;

		public Node(int row, int column, int dist) {
			this.row = row;
			this.column = column;
			this.dist = dist;
		}

		public int compareTo(Node other) {
			int d1 = this.dist;
			int d2 = other.dist;
			return d1 < d2 ? -1 : d1 > d2 ? 1 : 0;
		}

		public int getDistance() {
			return distance[row][column];
		}

		public void updateDist(int d) {
			distance[row][column] = d;
			this.dist = d;
		}
	}

	public MazeSolver() {
		maze = null;
		distance = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		distance = new int[maze.getRows()][maze.getColumns()];
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

		// fill the distance array with INF
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.distance[i][j] = Integer.MAX_VALUE;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		this.heap = new PriorityQueue<Node>();

		return solve(startRow, startCol);
	}

	public Integer solve(int row, int col) {
		distance[row][col] = 0;
		heap.offer(new Node(row, col, 0));
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			if (cur.row == endRow && cur.column == endCol) {
				return cur.getDistance();
			}
			for (int dir = 0; dir < 4; dir++) {
				int nextRow = cur.row + DELTAS[dir][0];
				int nextCol = cur.column + DELTAS[dir][1];
				Node next = new Node(nextRow, nextCol, 0);
				if (WALL_FUNCTIONS.get(dir).apply(maze.getRoom(cur.row, cur.column)) != TRUE_WALL) {
					relax(cur, next, WALL_FUNCTIONS.get(dir).apply(maze.getRoom(cur.row, cur.column)));
				}
			}
		}
		return distance[endRow][endCol] == Integer.MAX_VALUE ? null : distance[endRow][endCol];
	}

	public void relax(Node cur, Node next, int weight) {
		weight = weight == EMPTY_SPACE ? FEAR_OF_STEP : weight;
		if (cur.getDistance() + weight < next.getDistance()) {
			// next step is an empty room
			next.updateDist(cur.getDistance() + weight);
			heap.offer(next);
		}
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() || endRow < 0
				|| endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// fill the distance array with INF
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.distance[i][j] = Integer.MAX_VALUE;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		this.heap = new PriorityQueue<Node>();
		return bonusSolve(startRow, startCol);
	}

	public Integer bonusSolve(int row, int col) {
		distance[row][col] = 0;
		heap.offer(new Node(row, col, 0));
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			if (cur.row == endRow && cur.column == endCol) {
				return cur.getDistance();
			}
			for (int dir = 0; dir < 4; dir++) {
				int nextRow = cur.row + DELTAS[dir][0];
				int nextCol = cur.column + DELTAS[dir][1];
				Node next = new Node(nextRow, nextCol, 0);
				if (WALL_FUNCTIONS.get(dir).apply(maze.getRoom(cur.row, cur.column)) != TRUE_WALL) {
					bonusRelax(cur, next, WALL_FUNCTIONS.get(dir).apply(maze.getRoom(cur.row, cur.column)));
				}
			}
		}
		return distance[endRow][endCol] == Integer.MAX_VALUE ? null : distance[endRow][endCol];
	}

	public void bonusRelax(Node cur, Node next, int weight) {
		if (weight < cur.dist && weight != EMPTY_SPACE) {
			weight = 0;
		} else {
			if (weight == EMPTY_SPACE) {
				weight = FEAR_OF_STEP;
			} else {
				weight = weight - cur.dist;
			}
		}
		if (cur.getDistance() + weight < next.getDistance()) {
			next.updateDist(cur.getDistance() + weight);
			heap.offer(next);
		}
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol)
			throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() || endRow < 0
				|| endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// fill the distance array with INF
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.distance[i][j] = Integer.MAX_VALUE;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		this.safeRow = sRow;
		this.safeCol = sCol;
		return bonusSolveSafe(startRow, startCol);
	}

	public Integer bonusSolveSafe(int row, int col) {
		distance[row][col] = 0;
		for (int k = 0; k < maze.getRows() * maze.getColumns(); k++) {
			for (int i = 0; i < maze.getRows(); i++) {
				for (int j = 0; j < maze.getColumns(); j++) {
					Room cur = maze.getRoom(i, j);
					for (int dir = 0; dir < 4; dir++) {
						if (WALL_FUNCTIONS.get(dir).apply(cur) != TRUE_WALL) {
							int nextRow = i + DELTAS[dir][0];
							int nextCol = j + DELTAS[dir][1];
							bonusRelax(i, j, nextRow, nextCol, WALL_FUNCTIONS.get(dir).apply(cur));
						}
					}
				}
			}
		}
		return distance[endRow][endCol] == Integer.MAX_VALUE ? null : distance[endRow][endCol];
	}

	public void bonusRelax(int r1, int c1, int r2, int c2, int weight) {
		// no enough information to relax
		if (distance[r1][c1] == Integer.MAX_VALUE) {
			return;
		}
		if (r2 == safeRow && c2 == safeCol) {
			distance[r2][c2] = -1;
			return;
		}
		if (weight < distance[r1][c1] && weight != EMPTY_SPACE) {
			weight = 0;
		} else {
			if (weight == EMPTY_SPACE) {
				weight = FEAR_OF_STEP;
			} else {
				weight = weight - distance[r1][c1];
			}
		}
		if (distance[r1][c1] + weight < distance[r2][c2]) {
			distance[r2][c2] = distance[r1][c1] + weight;
		}
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-simple.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			// System.out.println(solver.pathSearch(1, 0, 1, 4));
			// System.out.println(solver.bonusSearch(0, 0, 0, 4));
			// System.out.println(solver.bonusSearch(0, 5, 0, 3, 0, 4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
