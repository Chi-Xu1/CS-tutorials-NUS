import java.util.Random;

public class SortingTester {

    final static int checkSortTimes = 20;

    public static boolean checkSort(ISort sorter, int size) {
        KeyValuePair[] array = new KeyValuePair[size];
        for (int j = 0; j < checkSortTimes; ++j) {
            Random random = new Random();
            for (int i = 0; i < size; ++i) {
                array[i] = new KeyValuePair(random.nextInt(), i);
            }
            sorter.sort(array);
            for (int i = 0; i < size - 1; ++i) {
                if (array[i].getKey() > array[i + 1].getKey()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        KeyValuePair[] array = new KeyValuePair[size];
        Random random = new Random();
        for (int i = 0; i < size; ++i) {
            array[i] = new KeyValuePair(random.nextInt(5), i);
        }
        sorter.sort(array);
        for (int i = 0; i < size - 1; i++) {
            if (array[i].getKey() == array[i + 1].getKey() && array[i].getValue() > array[i + 1].getValue()) {
                return false;
            }
        }
        
        return true;
    }

    public static char toLetter(int i) {
        char result = 'A';
        switch (i) {
            case 0:
                result = 'A';
                break;
            case 1:
                result = 'B';
                break;
            case 2:
                result = 'C';
                break;
            case 3:
                result = 'D';
                break;
            case 4:
                result = 'E';
                break;
            case 5:
                result = 'F';
                break;
        }
        return result;
    }

    public static float getRunTime(ISort sorter, KeyValuePair[] array) {
        StopWatch watch = new StopWatch();
        watch.start();
        sorter.sort(array);
        watch.stop();
        return watch.getTime();
    }

    public static void testIncreasingArray(ISort[] sortingObjects, int size) {
        System.out.printf("**Size: %d**\n", size);
        KeyValuePair[] testArray1 = new KeyValuePair[size];
        System.out.println("**Increasing order array**");
        for (int i = 0; i < 6; ++i) {
            double totalTime = 0;
            for (int k = 0; k < 10; ++k) {
                for (int j = 0; j < testArray1.length; j++) {
                    testArray1[j] = new KeyValuePair(j, j);
                }
                totalTime += getRunTime(sortingObjects[i], testArray1);
            }
            System.out.printf("Sorter%s's run time: %f\n", toLetter(i), totalTime / 10);
        }
    }

    public static void testDecreasingArray(ISort[] sortingObjects, int size) {
        System.out.printf("**Size: %d**\n", size);
        KeyValuePair[] testArray2 = new KeyValuePair[size];
        System.out.println("**Decreasing order array**");
        for (int i = 0; i < 6; ++i) {
            double totalTime = 0;
            for (int k = 0; k < 10; ++k) {
                for (int j = 0; j < testArray2.length; j++) {
                    testArray2[j] = new KeyValuePair(testArray2.length - j, j);
                }
                totalTime += getRunTime(sortingObjects[i], testArray2);
            }
            System.out.printf("Sorter%s's run time: %f\n", toLetter(i), totalTime / 10);
        }
    }

    public static void testRandomArray(ISort[] sortingObjects, int size) {
        System.out.printf("**Size: %d**\n", size);
        Random random = new Random();
        KeyValuePair[] testArray3 = new KeyValuePair[size];
        System.out.println("**Random order array**");
        for (int i = 0; i < testArray3.length; i++) {
            testArray3[i] = new KeyValuePair(random.nextInt(), i);
        }
        for (int i = 0; i < 6; ++i) {
            KeyValuePair[] temp = new KeyValuePair[size];
            double totalTime = 0;
            for (int k = 0; k < 10; ++k) {
                for (int j = 0; j < testArray3.length; j++) {
                    temp[j] = testArray3[j];
                }
                totalTime += getRunTime(sortingObjects[i], temp);
            }
            System.out.printf("Sorter%s's run time: %f\n", toLetter(i), totalTime / 10);
        }
    }

    public static void testHomogeneousArray(ISort[] sortingObjects, int size) {
        System.out.printf("**Size: %d**\n", size);
        KeyValuePair[] testArray4 = new KeyValuePair[size];
        System.out.println("**Homogeneous array**");
        for (int i = 0; i < 6; ++i) {
            double totalTime = 0;
            for (int k = 0; k < 10; ++k) {
                for (int j = 0; j < testArray4.length; j++) {
                    testArray4[j] = new KeyValuePair(0, j);
                }
                totalTime += getRunTime(sortingObjects[i], testArray4);
            }
            System.out.printf("Sorter%s's run time: %f\n", toLetter(i), totalTime / 10);
        }
    }

    public static void testNearlySortedIncreasingArray(ISort[] sortingObjects, int size) {
        System.out.printf("**Size: %d**\n", size);
        KeyValuePair[] testArray5 = new KeyValuePair[size];
        System.out.println("**Nearly sorted increasing array**");
        for (int i = 0; i < 6; ++i) {
            double totalTime = 0;
            for (int k = 0; k < 10; ++k) {
                for (int j = 0; j < testArray5.length - 1; j++) {
                    testArray5[j] = new KeyValuePair(j + 1, j);
                }
                testArray5[testArray5.length - 1] = new KeyValuePair(0, testArray5.length - 1);
                totalTime += getRunTime(sortingObjects[i], testArray5);
            }
            System.out.printf("Sorter%s's run time: %f\n", toLetter(i), totalTime / 10);
        }
    }

    public static void main(String[] args) {
        ISort[] sortingObjects = new ISort[] { new SorterA(), new SorterB(), new SorterC(), new SorterD(),
                new SorterE(), new SorterF() };
        final int size1 = 10000;
        final int size2 = 100;
        final int size3 = 100;
        final int size4 = 1000;
        final int size5 = 10000;

        // // check if all the sorter are correct
        // for (int i = 0; i < 6; i++) {
        //     if (checkSort(sortingObjects[i], size1)) {
        //         System.out.printf("Sorter%s is correct\n", toLetter(i));
        //     } else {
        //         System.out.printf("Sorter%s is wrong\n", toLetter(i));
        //     }
        // }

        // // check if the sorters are stable or not
        // for (int i = 0; i < 6; i++) {
        //     if (isStable(sortingObjects[i], size2)) {
        //         System.out.printf("Sorter%s is stable\n", toLetter(i));
        //     } else {
        //         System.out.printf("Sorter%s is instable\n", toLetter(i));
        //     }
        // }

        // check increasing order array
        testIncreasingArray(sortingObjects, size3);
        testIncreasingArray(sortingObjects, size4);
        testIncreasingArray(sortingObjects, size5);

        // check decreasing array
        testDecreasingArray(sortingObjects, size3);
        testDecreasingArray(sortingObjects, size4);
        testDecreasingArray(sortingObjects, size5);

        // check random array
        testRandomArray(sortingObjects, size3);
        testRandomArray(sortingObjects, size4);
        testRandomArray(sortingObjects, size5);

        // check homogeneous array
        testHomogeneousArray(sortingObjects, size3);
        testHomogeneousArray(sortingObjects, size4);
        testHomogeneousArray(sortingObjects, size5);

        // check nearly sorted increasing order array
        testNearlySortedIncreasingArray(sortingObjects, size3);
        testNearlySortedIncreasingArray(sortingObjects, size4);
        testNearlySortedIncreasingArray(sortingObjects, size5);
    }
}
