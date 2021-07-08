import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class TSPGraph implements IApproximateTSP {

    HashMap<Integer, ArrayList<Integer>> outgoingEdges;

    @Override
    public void MST(TSPMap map) {
        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        HashSet<Integer> set = new HashSet<>();
        outgoingEdges = new HashMap<>();

        int size = map.getCount();
        // Initialize the priority queue
        for (int i = 0; i < size; ++i) {
            pq.add(i, Double.POSITIVE_INFINITY);
        }
        pq.decreasePriority(0, 0D);
        while (!pq.isEmpty()) {
            Integer cur = pq.extractMin();
            Integer parent = map.getLink(cur);
            // record the outgoing edges of each node
            if (parent != -1) {
                if (outgoingEdges.containsKey(parent)) {
                    outgoingEdges.get(parent).add(cur);
                } else {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(cur);
                    outgoingEdges.put(parent, temp);
                }
            }
            set.add(cur);
            for (int i = 0; i < size; ++i) {
                if (!set.contains(i)) {
                    double distance = map.pointDistance(cur, i);
                    if (pq.lookup(i) > distance) {
                        pq.decreasePriority(i, distance);
                        map.setLink(i, cur, false);
                    }
                }
            }
        }
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        ArrayList<Integer> trace = new ArrayList<>();
        ArrayList<Integer> skippedTrace = new ArrayList<>();
        boolean[] visited = new boolean[map.getCount()];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }

        // DFS and remember every time we visit a node
        // also need to erase the link from MST
        DFS(0, trace, map);
        // for (Integer i : trace) {
        //     System.out.println(i);
        // }
        // skip the uncessary visits
        for (int i = 0; i < trace.size(); ++i) {
            if (!visited[trace.get(i)]) {
                skippedTrace.add(trace.get(i));
                visited[trace.get(i)] = true;
            }
        }
        // add link
        for (int i = 0; i < skippedTrace.size() - 1; ++i) {
            map.setLink(skippedTrace.get(i), skippedTrace.get(i + 1), false);
        }
        map.setLink(skippedTrace.get(skippedTrace.size() - 1), 0, false);
    }

    public void DFS(Integer cur, ArrayList<Integer> trace, TSPMap map) {
        trace.add(cur);
        if (outgoingEdges.get(cur) != null) {
            for (Integer end: outgoingEdges.get(cur)) {
                map.eraseLink(end, false);
                DFS(end, trace, map);
                trace.add(cur);
            }
        }
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        boolean[] visited = new boolean[map.getCount()];
        Integer p = map.getLink(0);
        while (!visited[0]) {
            if (p == -1 || visited[p]) {
                return false;
            } else {
                visited[p] = true;
                p = map.getLink(p);
            }
        }
        boolean flag = true;
        for (boolean b : visited) {
            flag = flag && b;
        }
        return flag;
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        if (!isValidTour(map)) {
            return -1;
        } else {
            double result = 0;
            int i = 0;
            int j = map.getLink(i);
            while (j != 0) {
                result += map.pointDistance(i, j);
                i = j;
                j = map.getLink(j);
            }
            result += map.pointDistance(i, 0);
            return result;
        }
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "D:/java_code/CS2040S/ps8/twentypoints.txt");
        TSPGraph graph = new TSPGraph();

        graph.MST(map);
        graph.TSP(map);
        System.out.println(graph.isValidTour(map));
        System.out.println(graph.tourDistance(map));
        map.redraw();
    }
}
