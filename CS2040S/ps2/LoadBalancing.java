// import jdk.javadoc.internal.doclets.formats.html.resources.standard;

/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean feasibleLoad(int[] jobSize, int queryLoad, int p) {
        int temp = 0;
        int i = 0;
        while(i < jobSize.length) {
            temp += jobSize[i];
            if(temp > queryLoad) {
                temp = jobSize[i];
                --p;
                if(temp > queryLoad) {
                    p = 0;
                    break;
                }
            }
            ++i;
        }
        return p > 0;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSize, int p) {
        int end = findTotalLoad(jobSize);
        int m = findMaxLoad(jobSize);
        int start = m >= end/p ? m : end/p;
        int mid;
        while(start < end) {
            mid = start + (end - start) / 2;
            if(feasibleLoad(jobSize, mid, p)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    /**
     * Return the maximum individual load among the jobSize
     * 
     * @param jobSize the sizes of the jobs to be performed
     * @return the maximum individual load among the jobSize
     */
    public static int findMaxLoad(int[] jobSize) {
        int result = 0;
        for(int i = 0; i < jobSize.length; ++i) {
            if(result < jobSize[i]) {
                result = jobSize[i];
            }
        }
        return result;
    }

    /**
     * Return the total load of the jobSize
     * 
     * @param jobSize the sizes of the jobs to be performed
     * @return the total load of the jobSize
     */
    public static int findTotalLoad(int[] jobSize){
        int result = 0;
        for(int size : jobSize) {
            result += size;
        }
        return result;
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
