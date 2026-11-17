class Solution {
    public int minimumChanges(String s, int k) {

        int n = s.length();
        int INF = 1_000_000;

        // Step 1: Precompute minimum changes
        // for every substring.
        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {

            for (int start = 0; start + len <= n; start++) {

                int end = start + len - 1;
                cost[start][end] = INF;

                // Try every proper divisor
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Check each group
                    for (int p = 0; p < d; p++) {

                        int groupLength = (len - 1 - p) / d + 1;

                        // Compare opposite characters
                        for (int q = 0; q < groupLength / 2; q++) {

                            int left = start + p + q * d;

                            int right = start + p
                                      + (groupLength - 1 - q) * d;

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                        }
                    }

                    cost[start][end] =
                        Math.min(cost[start][end], changes);
                }
            }
        }

        // Step 2: Dynamic programming
        int[][] dp = new int[k + 1][n + 1];

        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        // g = number of substrings
        for (int g = 1; g <= k; g++) {

            // i = number of characters considered
            for (int i = 2 * g; i <= n; i++) {

                // j = starting index of last substring
                for (int j = 2 * (g - 1); j <= i - 2; j++) {

                    if (dp[g - 1][j] == INF) {
                        continue;
                    }

                    dp[g][i] = Math.min(
                        dp[g][i],
                        dp[g - 1][j] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
