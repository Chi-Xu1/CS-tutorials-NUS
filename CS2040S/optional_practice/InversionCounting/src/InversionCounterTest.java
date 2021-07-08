import org.junit.Test;

import static org.junit.Assert.*;

public class InversionCounterTest {

    @Test
    public void countSwapsTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest2() {
        int[] arr = {2, 3, 4, 1};
        assertEquals(3L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest3() {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        assertEquals(36L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest4() {
        int[] arr = {1,2,3,4,5,9,0,10};
        assertEquals(6L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest5() {
        int[] arr = {8,2,1,9,4,0,7,3};
        assertEquals(16L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void mergeAndCountTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.mergeAndCount(arr, 0, 0, 1, 2));
    }

    @Test
    public void mergeAndCountTest2() {
        int[] arr = {2, 3, 4, 1};
        assertEquals(3L, InversionCounter.mergeAndCount(arr, 0, 2, 3, 3));
    }

    @Test
    public void mergeAndCountTest3() {
        int[] arr = {7,8,9,10,11,12,1,2};
        // long expected = InversionCounter.countSwaps(arr);
        // assertEquals(InversionCounter.countSwaps(arr), InversionCounter.mergeAndCount(arr, 0, 1, 2, 7));
        assertEquals(12L, InversionCounter.mergeAndCount(arr, 0, 5, 6, 7));
    }

    @Test
    public void mergeAndCountTest4() {
        int[] arr = {7,9,10,13,15,1,8,14};
        // long expected = InversionCounter.countSwaps(arr);
        // assertEquals(InversionCounter.countSwaps(arr), InversionCounter.mergeAndCount(arr, 0, 4, 5, 7));
        assertEquals(10L, InversionCounter.mergeAndCount(arr, 0, 4, 5, 7));
    }

    @Test
    public void mergeAndCountTest5() {
        int[] arr = {1,8,11,7,9,10,13,15};
        // long expected = InversionCounter.countSwaps(arr);
        // assertEquals(InversionCounter.countSwaps(arr), InversionCounter.mergeAndCount(arr, 0, 4, 5, 7));
        assertEquals(4L, InversionCounter.mergeAndCount(arr, 0, 2, 3, 7));
    }

    @Test
    public void mergeAndCountTest6() {
        int[] arr = {2,1};
        // long expected = InversionCounter.countSwaps(arr);
        // assertEquals(InversionCounter.countSwaps(arr), InversionCounter.mergeAndCount(arr, 0, 4, 5, 7));
        assertEquals(1L, InversionCounter.mergeAndCount(arr, 0, 0, 1, 1));
    }
}