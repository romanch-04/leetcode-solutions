class Solution {
    public int maximumWealth(int[][] accounts) {
        int ans = Integer.MIN_VALUE;
        for(int[] person: accounts) {
            int rowSum = 0;
            for(int acc: person) {
                rowSum += acc;
            }
            if(ans < rowSum) {
                ans = rowSum;
            }
        }
        return ans;
    }
}