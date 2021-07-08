import java.util.TreeMap;
import java.util.Scanner;

public class MedianFinder {
    public class Node implements Comparable<Node> {
        int x;
        int count;

        public Node(int x, int count) {
            this.x = x;
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Node) {
                Node temp = (Node) o;
                return this.x == temp.x;
            } else {
                return false;
            }
        }

        @Override
        public int compareTo(Node n) {
            if (this.x < n.x) {
                return -1;
            } else {
                return this.x > n.x ? 1 : 0;
            }
        }
    }

    // private Comparator<Integer> comparator = (x, y) -> x <= y ? -1 : 1;
    public TreeMap<Integer, Integer> lessThanSet = new TreeMap<>();
    public TreeMap<Integer, Integer> moreThanSet = new TreeMap<>();
    public int size1 = 0;
    public int size2 = 0;

    public void insert(int x) {
        if (size1 == 0 && size2 == 0) {
            lessThanSet.put(x, 0);
            size1++;
            return ;
        }
        if (size1 == 0 || size2 == 0) {
            if (size2 == 0) {
                Integer last = lessThanSet.lastKey();
                if (x >= last) {
                    moreThanSet.put(x, 0);
                } else {
                    lessThanSet.remove(last);
                    moreThanSet.put(last, 0);
                    lessThanSet.put(x, 0);
                }
                ++size2;
            } else {
                Integer first = moreThanSet.firstKey();
                if (x <= first) {
                    lessThanSet.put(x, 0);
                } else {
                    moreThanSet.remove(first);
                    lessThanSet.put(first, 0);
                    moreThanSet.put(x, 0);
                }
                ++size1;
            }
            return;
        }
        Integer last = lessThanSet.lastKey();
        Integer first = moreThanSet.firstKey();
        
        if (x <= last) {
            if (size1 > size2) {
                delete(lessThanSet, last);
                size1--;
                add(moreThanSet, last);
                size2++;
            }
            size1++;
            add(lessThanSet, x);
        } else {
            if (x >= first) {
                if (size2 > size1) {
                    delete(moreThanSet, first);
                    size2--;
                    add(lessThanSet, first);
                    size1++;
                }
                add(moreThanSet, x);
                size2++;
            } else {
                if (size2 > size1) {
                    add(lessThanSet, x);
                    size1++;
                } else {
                    add(moreThanSet, x);
                    size2++;
                }
            }
        }
    }

    public void add(TreeMap<Integer, Integer> map, int x) {
        if (map.containsKey(x)) {
            Integer count = map.get(x);
            map.remove(x);
            map.put(x, count + 1);
        } else {
            map.put(x, 0);
        }
    }

    public void delete(TreeMap<Integer, Integer> map, int x) {
        Integer count = map.get(x);
        map.remove(x);
        if (count != 0) {
            map.put(x, count - 1);
        }
    }

    public int getMedian() {
        int size = size1 + size2;
        if (size % 2 == 0) {
            Integer result = moreThanSet.firstKey();
            delete(moreThanSet, result);
            size2--;
            return result;
        } else {
            if (size1 < size2) {
                Integer result = moreThanSet.firstKey();
                delete(moreThanSet, result);
                size2--;
                return result;
            } else {
                Integer result = lessThanSet.lastKey();
                delete(lessThanSet, result);
                size1--;
                return result;
            }
        }
    }

    // public static void main(String[] args) {
    //     MedianFinder m = new MedianFinder();
    //     // m.insert(4);
    //     // m.insert(2);
    //     // m.insert(3);
    //     // System.out.println(m.getMedian());
    //     // m.insert(8);
    //     // m.insert(2);
    //     // m.insert(7);
    //     // m.insert(1);
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     // m.insert(4);
    //     // m.insert(2);
    //     // m.insert(3);
    //     // m.insert(1);
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     // System.out.println(m.getMedian());
    //     Scanner sc = new Scanner(System.in);
    //     int k = sc.nextInt();
    //     int n = sc.nextInt();
    //     while (k-- > 0) {
    //         m.insert(sc.nextInt());
    //     }
    //     // m.getMedian();
    //     while (n-- > 0) {
    //         System.out.println(m.getMedian());
    //     }
    //     sc.close();
    // }
}