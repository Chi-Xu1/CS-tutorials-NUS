class InversionCounter {

    public static long countSwaps(int[] arr) {
        return countSwapsInRange(arr, 0, arr.length - 1);
        // long r = countSwapsInRange(arr, 0, arr.length - 1);
        // for (int i : arr) {
        //     System.out.printf("%d ", i);
        // }
        // System.out.println("");
        // return r;
    }

    public static long countSwapsInRange(int arr[], int left, int right) {
        if (left == right) {
            return 0;
        } else {
            long result = 0;
            int mid = left + (right - left) / 2;
            result = countSwapsInRange(arr, left, mid) + countSwapsInRange(arr, mid + 1, right);
            result += mergeAndCount(arr, left, mid, mid + 1, right);
            return result;
        }
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int origin = left1;
        long result = 0;
        int[] tempArray = new int[right2 - left1 + 1];
        int index = 0;
        while (left1 <= right1 && left2 <= right2) {
            if (arr[left1] <= arr[left2]) {
                tempArray[index++] = arr[left1++];
            } else {
                tempArray[index++] = arr[left2++];
                result += (left2 - index - origin); 
            }
        }
        if (left1 <= right1) {
            while (left1 <= right1) {
                tempArray[index++] = arr[left1++];
            }
        } else {
            while (left2 <= right2) {
                tempArray[index++] = arr[left2++];
            }
        }
        for (int i = 0; i < tempArray.length; i++) {
            arr[origin + i] = tempArray[i];
        }
        return result;
    }
}
