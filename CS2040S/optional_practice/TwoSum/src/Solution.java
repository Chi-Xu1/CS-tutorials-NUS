import java.util.HashMap;

public class Solution {
    public static int solve(int[] arr, int target) {
        // TODO: Implement your solution here
        HashMap<Integer, Integer> map = new HashMap<>();
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            if (map.get(target - arr[i]) == null) {
                if (!map.containsKey(arr[i])) {
                    map.put(arr[i], 1);
                } else {
                    Integer temp = map.get(arr[i]);
                    map.remove(arr[i]);
                    map.put(arr[i], temp + 1);
                }
            } else {
                result++;
                Integer temp = map.get(target - arr[i]);
                map.remove(target - arr[i]);
                if (temp > 1) {
                    map.put(target - arr[i], temp - 1);
                }
            }
        }
        return result;
    }
}
