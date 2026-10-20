import java.util.Arrays;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            if (k == 1) {
                if (Arrays.equals(nums, sorted)) {
                    ans += 1;
                }
                continue;
            }

            for (int start = 0; start < n && valid; start += k) {
                if (!isRotation(nums, sorted, start, k)) {
                    valid = false;
                }
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }

    private boolean isRotation(int[] nums, int[] sorted,
                               int start, int k) {
        int[] lps = new int[k];

        // Build the KMP LPS array for the original block.
        for (int i = 1, len = 0; i < k;) {
            if (nums[start + i] == nums[start + len]) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }

        // Search for the original block in the sorted block
        // repeated twice. This detects every cyclic rotation.
        int j = 0;

        for (int i = 0; i < 2 * k - 1; i++) {
            int value = sorted[start + (i % k)];

            while (j > 0 && value != nums[start + j]) {
                j = lps[j - 1];
            }

            if (value == nums[start + j]) {
                j++;
            }

            if (j == k) {
                return true;
            }
        }

        return false;
    }
}