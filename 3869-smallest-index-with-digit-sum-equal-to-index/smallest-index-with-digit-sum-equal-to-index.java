class Solution {
    public int smallestIndex(int[] nums) {

        int n = nums.length;
        
        for(int i=0; i<n; i++) {

            int sum = 0;
            int num = nums[i];

            while(num > 0) {

                sum = sum + (num%10);
                num = num/10;
            }

            if(sum == i) {

                return i;
            }
        }

        return -1;
    }
}