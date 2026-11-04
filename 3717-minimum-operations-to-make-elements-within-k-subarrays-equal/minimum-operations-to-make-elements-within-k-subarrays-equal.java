
import java.util.Arrays;

class Solution {

    static class Fenwick {
        int n;
        long[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new long[n + 1];
        }

        void add(int index, long value) {
            index++;

            while (index <= n) {
                tree[index] += value;
                index += index & -index;
            }
        }

        long sum(int index) {
            long result = 0;
            index++;

            while (index > 0) {
                result += tree[index];
                index -= index & -index;
            }

            return result;
        }

        // Find the index of the kth smallest element.
        int kth(long k) {
            int index = 0;

            for (int step = Integer.highestOneBit(n);
                 step > 0; step >>= 1) {

                int next = index + step;

                if (next <= n && tree[next] < k) {
                    index = next;
                    k -= tree[next];
                }
            }

            return index;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int value : sorted) {
            if (m == 0 || sorted[m - 1] != value) {
                sorted[m++] = value;
            }
        }

        Fenwick countTree = new Fenwick(m);
        Fenwick sumTree = new Fenwick(m);

        int[] id = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = Arrays.binarySearch(
                sorted, 0, m, nums[i]
            );
        }

        // Store the cost of every window of size x.
        int windows = n - x + 1;
        long[] cost = new long[windows];

        long totalSum = 0;

        // Add the first window.
        for (int i = 0; i < x; i++) {
            countTree.add(id[i], 1);
            sumTree.add(id[i], nums[i]);
            totalSum += nums[i];
        }

        for (int start = 0; start < windows; start++) {

            // Find median.
            int medianIndex = countTree.kth((x + 1L) / 2);
            long median = sorted[medianIndex];

            // Calculate left and right sums.
            long leftCount = countTree.sum(medianIndex);
            long leftSum = sumTree.sum(medianIndex);

            long rightCount = x - leftCount;
            long rightSum = totalSum - leftSum;

            cost[start] =
                median * leftCount - leftSum
                + rightSum - median * rightCount;

            // Slide the window.
            if (start + 1 < windows) {

                int removeIndex = id[start];
                int addIndex = id[start + x];

                countTree.add(removeIndex, -1);
                sumTree.add(removeIndex, -nums[start]);
                totalSum -= nums[start];

                countTree.add(addIndex, 1);
                sumTree.add(addIndex, nums[start + x]);
                totalSum += nums[start + x];
            }
        }

        // DP: select exactly k non-overlapping windows.
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];

        for (int j = 1; j <= k; j++) {

            long[] curr = new long[n + 1];
            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Option 1: Skip current element.
                curr[i] = curr[i - 1];

                // Option 2: Select a window ending at i-1.
                if (i >= x && prev[i - x] != INF) {

                    long take =
                        prev[i - x] + cost[i - x];

                    curr[i] = Math.min(curr[i], take);
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}
