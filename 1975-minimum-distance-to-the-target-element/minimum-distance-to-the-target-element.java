class Solution {
    public int getMinDistance(int[] nums, int target, int start) {
        int ans = nums.length;
        for(int i=0; i<nums.length; i++) {
            if((nums[i])==target) {
                int distance;
                if(i>start) {
                    distance = i-start;
                } else {
                    distance = start-i;
                }

                if (distance < ans) {
                    ans = distance;
                }
            }
        }
        return ans;
    }
}