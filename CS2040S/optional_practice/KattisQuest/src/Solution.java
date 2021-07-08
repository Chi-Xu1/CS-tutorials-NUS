import java.util.TreeSet;
import java.util.Scanner;

public class Solution {
    class Quest implements Comparable<Quest> {
        public long energy;
        public long reward;
        public int times;

        public Quest(long energy, long reward) {
            this.energy = energy;
            this.reward = reward;
            this.times = 1;
        }

        public int compareTo(Quest q) {
            if (this.energy < q.energy) {
                return -1;
            } else {
                if (this.energy > q.energy) {
                    return 1;
                } else {
                    return this.reward < q.reward ? -1 : this.reward > q.reward ? 1 : 0;
                }
            }
        }
    }

    TreeSet<Quest> set = new TreeSet<>();

    public Solution() {
    }

    void add(long energy, long value) {
        Quest cur = new Quest(energy, value);
        if (!set.add(cur)) {
            Quest tmp = set.floor(cur);
            set.remove(tmp);
            cur.times += tmp.times;
            set.add(cur);
        }
    }

    long query(long remainingEnergy) {
        long totalReward = 0;
        while (remainingEnergy > 0) {
            Quest cur = new Quest(remainingEnergy, Integer.MAX_VALUE);
            Quest tmp = set.floor(cur);
            if (tmp != null && remainingEnergy >= tmp.energy) {
                totalReward += tmp.reward;
                remainingEnergy -= tmp.energy;
                if (tmp.times > 1) {
                    tmp.times -= 1;
                } else {
                    set.remove(tmp);
                }
            } else {
                break;
            }
        }
        return totalReward;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        while (n -- > 0) {
            String command = sc.next();
            if (command.equals("add")) {
                long energy = sc.nextLong();
                long reward = sc.nextLong();
                solution.add(energy, reward);
            } else {
                long query = sc.nextLong();
                System.out.println(solution.query(query));
            }
        }
        sc.close();
    }
}
