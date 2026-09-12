class Solution {

    static class Result {
        long score;
        int[] indices;

        Result(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(java.util.List<java.util.List<Integer>> intervals) {

        int n = intervals.size();

        // {left, right, weight, originalIndex}
        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        // Sort by left
        java.util.Arrays.sort(a, (x, y) -> {
            return Integer.compare(x[0], y[0]);
        });

        // Find next non-overlapping interval
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = findNext(a, i + 1, a[i][1]);
        }

        /*
         * dp[k][i]
         *
         * k = number of intervals we are still allowed to take
         * i = current interval
         */
        Result[][] dp = new Result[5][n + 1];

        // IMPORTANT:
        // Initialize ALL states to empty Result.
        for (int k = 0; k <= 4; k++) {
            for (int i = 0; i <= n; i++) {
                dp[k][i] = new Result(0, new int[0]);
            }
        }

        // Fill DP from right to left
        for (int k = 1; k <= 4; k++) {

            for (int i = n - 1; i >= 0; i--) {

                // 1. Skip current interval
                Result skip = dp[k][i + 1];

                // 2. Take current interval
                Result after = dp[k - 1][next[i]];

                int[] takeIndices =
                    new int[after.indices.length + 1];

                takeIndices[0] = a[i][3];

                for (int j = 0; j < after.indices.length; j++) {
                    takeIndices[j + 1] = after.indices[j];
                }

                // Final answer must be sorted by original index
                java.util.Arrays.sort(takeIndices);

                Result take = new Result(
                    a[i][2] + after.score,
                    takeIndices
                );

                // Choose better score
                if (take.score > skip.score) {
                    dp[k][i] = take;
                }
                else if (take.score < skip.score) {
                    dp[k][i] = skip;
                }
                else {
                    // Same score -> lexicographically smaller
                    if (isSmaller(take.indices, skip.indices)) {
                        dp[k][i] = take;
                    }
                    else {
                        dp[k][i] = skip;
                    }
                }
            }
        }

        return dp[4][0].indices;
    }

    // Find first interval whose left > current right
    private int findNext(int[][] a, int start, int right) {

        int low = start;
        int high = a.length;

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (a[mid][0] > right) {
                high = mid;
            }
            else {
                low = mid + 1;
            }
        }

        return low;
    }

    // Check lexicographical order
    private boolean isSmaller(int[] a, int[] b) {

        int n = Math.min(a.length, b.length);

        for (int i = 0; i < n; i++) {

            if (a[i] < b[i]) {
                return true;
            }

            if (a[i] > b[i]) {
                return false;
            }
        }

        return a.length < b.length;
    }
}