class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2,
                                 int k1, int k2) {

        long k = (long) k1 + k2;
        int n = nums1.length;
        int[] diff = new int[n];

        long total = 0;
        int max = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            total += diff[i];
            max = Math.max(max, diff[i]);
        }

        if (k >= total) {
            return 0;
        }

        int left = 0;
        int right = max;

        // Find the smallest possible maximum difference.
        while (left < right) {
            int mid = left + (right - left) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int limit = left;
        long remaining = k;
        long ans = 0;

        // Reduce all differences above the limit.
        for (int d : diff) {
            if (d > limit) {
                remaining -= d - limit;
                d = limit;
            }
            ans += (long) d * d;
        }

        // Use remaining operations to reduce limit-level differences.
        // Each reduction from limit to limit - 1 saves 2*limit - 1.
        if (limit > 0 && remaining > 0) {
            ans -= remaining * (2L * limit - 1);
        }

        return ans;
    }
}