class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int LOG = 1;

        while ((1 << LOG) <= n) {
            LOG++;
        }

        // Prefix sum
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Next greater element to the right
        int[] nge = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() &&
                   nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }

            nge[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // Binary lifting tables
        int[][] jump = new int[LOG][n];
        long[][] cost = new long[LOG][n];

        // Base level
        for (int i = 0; i < n; i++) {
            jump[0][i] = nge[i];

            int end = nge[i];

            cost[0][i] =
                (long) nums[i] * (end - i)
                - (prefix[end] - prefix[i]);
        }

        // Build higher levels
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                int mid = jump[j - 1][i];

                if (mid < n) {
                    jump[j][i] = jump[j - 1][mid];

                    cost[j][i] =
                        cost[j - 1][i] + cost[j - 1][mid];
                } else {
                    jump[j][i] = n;
                    cost[j][i] = cost[j - 1][i];
                }
            }
        }

        // Calculate cost of subarray [l, r]
        // using binary lifting
        long answer = 0;
        int right = 0;

        for (int left = 0; left < n; left++) {

            if (right < left + 1) {
                right = left + 1;
            }

            while (right < n &&
                   getCost(left, right, nums, prefix,
                           jump, cost, LOG) <= k) {
                right++;
            }

            answer += right - left;
        }

        return answer;
    }

    private long getCost(
        int left,
        int right,
        int[] nums,
        long[] prefix,
        int[][] jump,
        long[][] cost,
        int LOG
    ) {
        int pos = left;
        long result = 0;

        // Skip complete prefix-maximum segments
        for (int j = LOG - 1; j >= 0; j--) {
            int next = jump[j][pos];

            if (next <= right) {
                result += cost[j][pos];
                pos = next;
            }
        }

        // Handle the remaining partial segment
        result += (long) nums[pos] * (right - pos + 1)
                  - (prefix[right + 1] - prefix[pos]);

        return result;
    }
}