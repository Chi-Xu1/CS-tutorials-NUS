/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = { { 1, 3, 5, 7, 9, 11, 10, 8, 6, 4 }, { 67, 65, 43, 42, 23, 17, 9, 100 },
            { 4, -100, -80, 15, 20, 25, 30 },
            { 2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83 } };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) throws IndexOutOfBoundsException {
        int l = dataArray.length;
        int start = 0;
        int end = l - 1;
        int mid = 0;
        int result = 0;
        // when the array is empty
        if (l == 0) {
            throw new IndexOutOfBoundsException();
        }
        // when the size of the array is 1
        if (l == 1) {
            return dataArray[0];
        }
        if (dataArray[0] < dataArray[1]) {
            if (dataArray[l - 2] < dataArray[l - 1]) {
                // when the array is increasing
                result = dataArray[l - 1];
            } else {
                // when the array is first increasing and then decreasing
                while (start < end) {
                    mid = start + (end - start) / 2;
                    if (mid == l - 1 || mid == 0) {
                        // when mid is the start of the array or the end of the array
                        result = dataArray[mid];
                        break;
                    } else {
                        if (dataArray[mid] > dataArray[mid + 1]) {
                            if (dataArray[mid] > dataArray[mid - 1]) {
                                result = dataArray[mid];
                                break;
                            } else {
                                end = mid;
                            }
                        } else {
                            start = mid + 1;
                        }
                    }
                }
            }
        } else {
            if (dataArray[l - 2] < dataArray[l - 1]) {
                // when the array is first decreasing and then increasing
                result = dataArray[0] > dataArray[l - 1] ? dataArray[0] : dataArray[l - 1];
            } else {
                // when the array is decreasing
                result = dataArray[0];
            }
        }
        return result;
    }

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
