class Solution {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;
        
        //calculate  total sum;
        int total = 0;

        for(int num: nums) {
            total += num;
        }

        //We need to keep subarray with this sum
        int target = total - x;

        //if target is negative, impossible
        if(target < 0) {
            return -1;
        }

        //if target == 0, remove all elements
        if(target == 0) {
            return n;
        }

        int left = 0;
        int sum = 0;
        int maxLength = -1;

        for(int right=0; right<n; right++) {

            sum += nums[right];

            //Shrink window if sum becomes too large
            while(sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            //Found required subarray
            if(sum == target) {
                maxLength = Math.max(maxLength, right-left+1);
            }
        }

        //No valid subarray
        if(maxLength == -1) {
            return -1;
        }

        //Remove everything outside the subarray
        return n - maxLength;
    }
}