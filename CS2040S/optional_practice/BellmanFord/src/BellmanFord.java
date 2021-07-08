import java.util.ArrayList;
import java.util.Arrays;

public class BellmanFord {
    // DO NOT MODIFY THE TWO STATIC VARIABLES BELOW
    public static int INF = 20000000;
    public static int NEGINF = -20000000;

    // public class Edge {
    //     int start;
    //     IntPair pair;

    //     public Edge(int start, IntPair pair) {
    //         this.start = start;
    //         this.pair = pair;
    //     }
    // }

    ArrayList<ArrayList<IntPair>> adjList;
    int[] result;
    int[] parent;
    // Edge[] parent;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        this.adjList = adjList;
        this.result = new int[adjList.size()];
        this.parent = new int[adjList.size()];
        // this.parent = new Edge[adjList.size()];
    }

    public void computeShortestPaths(int source) {
        Arrays.fill(result, INF);
        result[source] = 0;
        parent[source] = -1;
        // run bellman ford
        bellmanford();
        // detect negative circle
        if (detectNegative()) {
            findNegative();
        }
        if (result[source] == NEGINF) {
            for (int i = 0; i < result.length; i++) {
                result[i] = result[i] == INF ? INF : NEGINF;
            }
        } else {
            for (int i = 0; i < result.length; i++) {
                if (result[i] != NEGINF) {
                    result[i] = INF;
                }
            }
            result[source] = 0;
            bellmanford();
        }
    }

    public void bellmanford() {
        for (int i = 0; i < adjList.size() - 1; i++) {
            for (int j = 0; j < adjList.size(); j++) {
                for (IntPair pair : adjList.get(j)) {
                    if (result[j] != INF && result[pair.first] != NEGINF) {
                        relax(j, pair.first, pair.second);
                        parent[pair.first] = j;
                    }
                }
            }
        }
    }

    public void relax(int source, int target, int newWeight) {
        if (result[target] > result[source] + newWeight) {
            if (result[source] != NEGINF) {
                result[target] = result[source] + newWeight;
            } else {
                result[target] = NEGINF;
            }
        }
    }

    public boolean detectNegative() {
        for (int j = 0; j < adjList.size(); j++) {
            for (IntPair pair : adjList.get(j)) {
                if (result[pair.first] > result[j] + pair.second && result[j] != INF) {
                    return true;
                }
            }
        }
        return false;
    }

    public void findNegative() {
        int temp[] = Arrays.copyOf(result, result.length);
        ArrayList<Integer> list = new ArrayList<>();
        for (int j = 0; j < adjList.size(); j++) {
            for (IntPair pair : adjList.get(j)) {
                if (temp[pair.first] > temp[j] + pair.second && temp[j] != INF) {
                    temp[pair.first] = temp[j] + pair.second;
                    parent[pair.first] = j;
                }
            }
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != result[i]) {
                result[i] = NEGINF;
                list.add(i);
            }
        }

        for (Integer node : list) {
            int p = parent[node];
            boolean[] visited = new boolean[adjList.size()];
            visited[node] = true;
            while (p != -1 && !visited[p]) {
                visited[p] = true;
                p = parent[p];
            }
            if (p != -1) {
                p = parent[node];
                Arrays.fill(visited, false);
                visited[node] = true;
                while (!visited[p]) {
                    result[p] = NEGINF;
                    visited[p] = true;
                    p = parent[p];
                }
            }
        }
    }

    public int getDistance(int node) { 
        return result[node];
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<IntPair>> adjList = new ArrayList<>();
        ArrayList<IntPair> edge0 = new ArrayList<>();
        edge0.add(new IntPair(3, 2));
        ArrayList<IntPair> edge1 = new ArrayList<>();
        edge1.add(new IntPair(2, -2));
        // edge1.add(new IntPair(3, 0));
        ArrayList<IntPair> edge2 = new ArrayList<>();
        edge2.add(new IntPair(1, 1));
        edge2.add(new IntPair(3, 0));
        ArrayList<IntPair> edge3 = new ArrayList<>();
        edge3.add(new IntPair(2, 1));
        ArrayList<IntPair> edge4 = new ArrayList<>();
        adjList.add(edge0);
        adjList.add(edge1);
        adjList.add(edge2);
        adjList.add(edge3);
        adjList.add(edge4);
        BellmanFord bf = new BellmanFord(adjList);
        bf.computeShortestPaths(0);
        for (int i = 0; i < bf.result.length; i++) {
            System.out.println(bf.getDistance(i));
        }
    }
}
