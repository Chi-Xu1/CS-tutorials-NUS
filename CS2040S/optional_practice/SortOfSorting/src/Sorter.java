class Sorter {

    public static void sortStrings(String[] arr) {
        sortStrings(arr, 0, arr.length - 1);
    }

    public static boolean isGreaterThan(String s1, String s2) {
        if (s1.charAt(0) > s2.charAt(0)) {
            return true;
        } else {
            if (s1.charAt(0) < s2.charAt(0)) {
                return false;
            } else {
                return s1.charAt(1) > s2.charAt(1);
            }
        }
    }

    public static void sortStrings(String arr[], int left, int right) {
        if (left == right) {
            
        } else {
            int mid = left + (right - left) / 2;
            sortStrings(arr, left, mid);
            sortStrings(arr, mid + 1, right);
            merge(arr, left, mid, mid + 1, right);
        }
    }

    public static void merge(String[] arr, int left1, int right1, int left2, int right2) {
        int origin = left1;
        String[] tempArray = new String[right2 - left1 + 1];
        int index = 0;
        while (left1 <= right1 && left2 <= right2) {
            if (!isGreaterThan(arr[left1], arr[left2])) {
                tempArray[index++] = arr[left1++];
            } else {
                tempArray[index++] = arr[left2++];
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
    }
}
