class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;

        int[] best = new int[n + 1];

        for(int i=0; i<=n; i++) {
            best[i] = INF;
        } 

        int answer = INF;

        int left = 0;
        int sum = 0;

        for(int right=0; right<n;  right++) {
            sum += arr[right];

            while(sum>target) {
                sum-= arr[left];
                left++;
            }

            if(sum == target) {
                int len = right  - left + 1;

                if(best[left] != INF) {
                    answer = Math.min(answer, best[left] + len);
                }

                best[right + 1] = Math.min(best[right], len);
            } else {
                best[right + 1] = best[right];
            }
        }
        return answer == INF ? -1 : answer;
    }
}