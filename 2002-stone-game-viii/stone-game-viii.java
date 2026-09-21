class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        //Build prefix sums
        int[]  prefix = new int[n];

        prefix[0] = stones[0];

        for(int i=1; i<n; i++) {
            prefix[i] = prefix[i-1] + stones[i];
        }

        //If alice takes all stones initially
        int answer = prefix[n - 1];

        //Try every possible first stopping position
        for(int i=n-2; i>=1; i--)  {
            answer = Math.max(answer, prefix[i] - answer);
        }
        return answer;
    }
}