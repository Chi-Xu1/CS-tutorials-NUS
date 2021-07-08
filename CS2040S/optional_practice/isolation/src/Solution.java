import java.util.HashMap;

public class Solution {
    public static int solve(int[] arr) {
        int maxLength = -1;
        // int curLength = 0;
        int start = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                start = map.get(arr[i]) > start ? map.get(arr[i]) : start;
                map.remove(arr[i]);
            }
            map.put(arr[i], i + 1);
            maxLength = i + 1 - start > maxLength ? i + 1 - start : maxLength;
        }

        return maxLength;
    }

    // public static void main(String[] args) {
    //     int[] arr = {1, 2, 3, 4, 5, 6, 2, 9, 10, 2, 1};
    //     System.out.println(solve(arr));
    // }
}