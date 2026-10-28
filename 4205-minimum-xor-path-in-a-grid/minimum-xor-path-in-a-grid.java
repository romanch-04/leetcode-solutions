
class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        java.util.Set<Integer>[][] dp = new java.util.HashSet[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = new java.util.HashSet<>();

                if (i == 0 && j == 0) {
                    dp[i][j].add(grid[i][j]);
                    continue;
                }

                if (i > 0) {
                    for (int x : dp[i - 1][j]) {
                        dp[i][j].add(x ^ grid[i][j]);
                    }
                }

                if (j > 0) {
                    for (int x : dp[i][j - 1]) {
                        dp[i][j].add(x ^ grid[i][j]);
                    }
                }
            }
        }

        int ans = Integer.MAX_VALUE;

        for (int x : dp[m - 1][n - 1]) {
            ans = Math.min(ans, x);
        }

        return ans;
    }
}
