import static org.junit.Assert.*;

public class Test {
    @org.junit.Test
    public void test1() {
        MedianFinder soln = new MedianFinder();
        soln.insert(4);
        soln.insert(2);
        soln.insert(3);
        soln.insert(1);
        assertEquals(3, soln.getMedian());
        assertEquals(2, soln.getMedian());
        assertEquals(4, soln.getMedian());
        assertEquals(1, soln.getMedian());
    }

    @org.junit.Test
    public void test2() {
        MedianFinder soln = new MedianFinder();
        soln.insert(5);
        soln.insert(7);
        soln.insert(10);
        soln.insert(4);
        soln.insert(9);
        soln.insert(2);
        soln.insert(6);
        soln.insert(8);
        soln.insert(3);
        soln.insert(1);
        assertEquals(6, soln.getMedian());
        assertEquals(5, soln.getMedian());
        assertEquals(7, soln.getMedian());
        assertEquals(4, soln.getMedian());
        assertEquals(8, soln.getMedian());
        assertEquals(3, soln.getMedian());
        assertEquals(9, soln.getMedian());
        assertEquals(2, soln.getMedian());
        assertEquals(10, soln.getMedian());
        assertEquals(1, soln.getMedian());
    }

    @org.junit.Test
    public void test3() {
        MedianFinder soln = new MedianFinder();
        soln.insert(5);
        soln.insert(52);
        soln.insert(36);
        soln.insert(45);
        soln.insert(61);
        soln.insert(32);
        soln.insert(39);
        soln.insert(32);
        assertEquals(39, soln.getMedian());
        assertEquals(36, soln.getMedian());
        assertEquals(45, soln.getMedian());
        assertEquals(32, soln.getMedian());
        assertEquals(52, soln.getMedian());
        assertEquals(32, soln.getMedian());
        assertEquals(61, soln.getMedian());
        assertEquals(5, soln.getMedian());
    }

    @org.junit.Test
    public void test4() {
        MedianFinder soln = new MedianFinder();
        soln.insert(4);
        soln.insert(2);
        soln.insert(3);
        assertEquals(3, soln.getMedian());
        soln.insert(8);
        soln.insert(2);
        soln.insert(7);
        soln.insert(1);
        assertEquals(4, soln.getMedian());
        assertEquals(2, soln.getMedian());
        assertEquals(7, soln.getMedian());
        assertEquals(2, soln.getMedian());
        assertEquals(8, soln.getMedian());
        assertEquals(1, soln.getMedian());
    }

    @org.junit.Test
    public void test5() {
        MedianFinder soln = new MedianFinder();
        soln.insert(3);
        assertEquals(3, soln.getMedian());
        soln.insert(5);
        assertEquals(5, soln.getMedian());
        soln.insert(31);
        assertEquals(31, soln.getMedian());
    }
}
