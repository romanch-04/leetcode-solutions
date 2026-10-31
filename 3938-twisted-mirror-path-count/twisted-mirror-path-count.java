class Solution {
    static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Destination when a mirror is entered from the left (moving right).
        int[][] rightIn = new int[m][n];

        // Destination when a mirror is entered from above (moving down).
        int[][] downIn = new int[m][n];

        for (int i = 0; i < m; i++) {
            java.util.Arrays.fill(rightIn[i], -1);
            java.util.Arrays.fill(downIn[i], -1);
        }

        // Precompute mirror-chain destinations.
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 0) {
                    continue;
                }

                // Entered moving right: reflection sends us down.
                if (i + 1 < m) {
                    if (grid[i + 1][j] == 0) {
                        rightIn[i][j] = (i + 1) * n + j;
                    } else {
                        rightIn[i][j] = downIn[i + 1][j];
                    }
                }

                // Entered moving down: reflection sends us right.
                if (j + 1 < n) {
                    if (grid[i][j + 1] == 0) {
                        downIn[i][j] = i * n + j + 1;
                    } else {
                        downIn[i][j] = rightIn[i][j + 1];
                    }
                }
            }
        }

        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        // Process reachable empty cells in row-major order.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 || dp[i][j] == 0) {
                    continue;
                }

                if (i == m - 1 && j == n - 1) {
                    continue;
                }

                // Try moving right.
                if (j + 1 < n) {
                    int dest;

                    if (grid[i][j + 1] == 0) {
                        dest = i * n + j + 1;
                    } else {
                        dest = rightIn[i][j + 1];
                    }

                    if (dest != -1) {
                        int r = dest / n;
                        int c = dest % n;
                        dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                    }
                }

                // Try moving down.
                if (i + 1 < m) {
                    int dest;

                    if (grid[i + 1][j] == 0) {
                        dest = (i + 1) * n + j;
                    } else {
                        dest = downIn[i + 1][j];
                    }

                    if (dest != -1) {
                        int r = dest / n;
                        int c = dest % n;
                        dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}