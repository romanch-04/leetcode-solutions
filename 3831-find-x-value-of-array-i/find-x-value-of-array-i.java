class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] res = new long[k];

        long[] dp = new long[k];

        for(int num: nums) {
            long[] next = new long[k];
            int value = num % k;
            next[value]++;

            for(int r=0; r<k; r++)  {
                if(dp[r] > 0) {
                    int newRemainder = (r * value) % k;
                    next[newRemainder] += dp[r];
                }
            }
            dp = next;
            for(int r=0; r<k; r++) {
                res[r] += dp[r];
            }
        }
        return res;
    }
}