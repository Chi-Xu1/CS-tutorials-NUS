import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        Arrays.sort(houses);
        double low = 0;
        double high = (houses[houses.length - 1] - houses[0]) / numOfAccessPoints;
        double mid;
        while(high - low > 0.1) {
            mid = low + (high - low) / 2;
            if(coverable(houses, numOfAccessPoints, mid)) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return low;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        // Arrays.sort(houses);
        double curRange = houses[0] + 2 * distance;
        --numOfAccessPoints;
        for(int i = 1; i < houses.length; ++i) {
            if(houses[i] > curRange) {
                curRange = houses[i] + 2 * distance;
                --numOfAccessPoints;
            }
        }
        return numOfAccessPoints >= 0;
    }
}
