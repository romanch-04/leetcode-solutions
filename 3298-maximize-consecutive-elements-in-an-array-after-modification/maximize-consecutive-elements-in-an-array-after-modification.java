class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        int maxVal = nums[nums.length - 1];

        int[] dp = new int[maxVal + 2];

        int ans = 0;

        for (int num : nums) {

            // Option 1: Increase num to num + 1
            dp[num + 1] = Math.max(dp[num + 1], dp[num] + 1);

            // Option 2: Keep num unchanged
            dp[num] = Math.max(dp[num], dp[num - 1] + 1);

            ans = Math.max(ans, Math.max(dp[num], dp[num + 1]));
        }

        return ans;
    }
}