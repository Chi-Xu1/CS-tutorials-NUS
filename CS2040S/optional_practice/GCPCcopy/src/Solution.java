import java.util.Scanner;

public class Solution {
    public class Node implements Comparable<Node>{
        int team;
        long count;
        long penalty;

        public Node(int team, long count, long penalty) {
            this.team = team;
            this.count = count;
            this.penalty = penalty;
        }

        @Override
        public int compareTo(Node other) {
            if (this.count == other.count) {
                if (this.penalty < other.penalty) {
                    return -1;
                } else {
                    if (this.penalty == other.penalty) {
                        if (this.team < other.team) {
                            return -1;
                        } else {
                            if (this.team == other.team) {
                                return 0;
                            } else {
                                return 1;
                            }
                        }
                    } else {
                        return 1;
                    }
                }
            } else {
                if (this.count > other.count) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node)) {
                return false;
            } else {
                Node other = (Node)o;
                return this.team == other.team;
            }
        }
    }

    private AVLTree<Node> tree = new AVLTree<>();
    private Node[] teams = null;


    public Solution(int numTeams) {
        this.teams = new Node[numTeams + 1];
        for (int i = 1; i <= numTeams; ++i) {
            teams[i] = new Node(i, 0, 0);
            tree.insert(teams[i]);
        }
    }

    public int update(int team, long newPenalty){
        Node cur = teams[team];
        tree.remove(cur);
        cur.count++;
        cur.penalty += newPenalty;
        tree.insert(cur);
        return tree.getRank(teams[1]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Solution solution = new Solution(n);
        while(m-- > 0) {
            int team = sc.nextInt();
            long newPenalty = sc.nextLong();
            System.out.println(solution.update(team, newPenalty));
        }
        sc.close();
    }
}
