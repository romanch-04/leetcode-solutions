class Solution {

    public int closedIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Step 1: Remove land connected to the boundary
        for (int i = 0; i < m; i++) {
            dfs(grid, i, 0);
            dfs(grid, i, n - 1);
        }

        for (int j = 0; j < n; j++) {
            dfs(grid, 0, j);
            dfs(grid, m - 1, j);
        }

        // Step 2: Count the remaining islands
        int count = 0;

        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {

                if (grid[i][j] == 0) {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }

        return count;
    }

    private void dfs(int[][] grid, int r, int c) {

        // Stop if outside the grid
        if (r < 0 || r >= grid.length ||
            c < 0 || c >= grid[0].length) {
            return;
        }

        // Stop if water or already visited
        if (grid[r][c] == 1) {
            return;
        }

        // Mark land as visited
        grid[r][c] = 1;

        // Explore four directions
        dfs(grid, r - 1, c); // Up
        dfs(grid, r + 1, c); // Down
        dfs(grid, r, c - 1); // Left
        dfs(grid, r, c + 1); // Right
    }
}