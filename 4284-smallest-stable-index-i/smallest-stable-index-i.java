class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {

            int max = nums[0];
            int min = nums[i];

            // Find maximum from index 0 to i
            for (int j = 0; j <= i; j++) {
                if (nums[j] > max) {
                    max = nums[j];
                }
            }

            // Find minimum from index i to n-1
            for (int j = i; j < n; j++) {
                if (nums[j] < min) {
                    min = nums[j];
                }
            }

            // Check instability score
            if (max - min <= k) {
                return i;
            }
        }

        return -1;
    }
}