class Solution {
    public boolean uniformArray(int[] nums1) {
        boolean hasOdd = false;

        for (int num : nums1) {
            if (num % 2 != 0) {
                hasOdd = true;
                break;
            }
        }

        if (!hasOdd) {
            return true;
        }

        int minOdd = Integer.MAX_VALUE;

        for (int num : nums1) {
            if (num % 2 != 0) {
                minOdd = Math.min(minOdd, num);
            }
        }

        for (int num : nums1) {
            if (num % 2 == 0 && num < minOdd) {
                return false;
            }
        }

        return true;
    }
}