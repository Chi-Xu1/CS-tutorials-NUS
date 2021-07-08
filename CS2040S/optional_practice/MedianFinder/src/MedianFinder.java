// import java.util.TreeMap;
// import java.util.TreeSet;
import java.util.Scanner;

public class MedianFinder {

    AVLTree<Integer> tree;

    public MedianFinder() {
        tree = new AVLTree<>();
    }

    public void insert(int x) {
        tree.insert(x);
    }

    public int getMedian() {
        int size = tree.size();
        // if (size == 0) {
        //     return null;
        // }
        int medianIndex = size % 2 == 0 ? size / 2 + 1 : (size + 1) / 2;
        Integer median = tree.getKeyByRank(medianIndex);
        tree.remove(median);
        return median;
    }

    public static void main(String[] args) {
        MedianFinder m = new MedianFinder();
        // m.insert(4);
        // m.insert(2);
        // m.insert(3);
        // System.out.println(m.getMedian());
        // m.insert(8);
        // m.insert(2);
        // m.insert(7);
        // m.insert(1);
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        // m.insert(4);
        // m.insert(2);
        // m.insert(3);
        // m.insert(1);
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        // System.out.println(m.getMedian());
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int n = sc.nextInt();
        while (k-- > 0) {
            m.insert(sc.nextInt());
        }
        // m.getMedian();
        while(n-- > 0) {
            System.out.println(m.getMedian());
        }
        sc.close();
    }
}
