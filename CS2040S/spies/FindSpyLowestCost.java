public class FindSpyLowestCost implements IFindSpies {
    @Override
    public int[] findSpies(int N, int k, IMissionControl missionControl) {
        // Problem 2 -- Implement strategy to find spies with minimum cost (least total students sent)
        int[] map = new int[N];
        int index = 0;
        for(int i = 1; i < N; ++i) {
            map[i] = 1;
        }
        while(k > 0 && index < N - 1) {
            if(!missionControl.sendForMission(map)) {
                map[index] = 1;
                --k;
            }
            if(N - index - 1 <= k) {
                return map;
            } else {
                map[++index] = 0;
            }
        }
        if(index == N - 1 && k == 1) {
            map[index] = 1;
        } else {
            for(int i = index; i < N; ++i) {
                map[i] = 0;
            }
        }
        return map;
    }
}
