class Solution {
    public int minimumDeletions(int[] nums) {
         int n = nums.length;

        // Find the positions of minimum and maximum
        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 1; i < n; i++) {

            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }

            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Put the smaller index in minIndex
        // and the larger index in maxIndex
        if (minIndex > maxIndex) {
            int temp = minIndex;
            minIndex = maxIndex;
            maxIndex = temp;
        }

        // 1. Remove both from the front
        int front = maxIndex + 1;

        // 2. Remove both from the back
        int back = n - minIndex;

        // 3. Remove smaller index from front
        //    and larger index from back
        int both = (minIndex + 1) + (n - maxIndex);

        // Return the smallest number of deletions
        return Math.min(front, Math.min(back, both));
    }
}