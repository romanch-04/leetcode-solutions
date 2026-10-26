class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 0) {
                    continue;
                }

                long sum = 0;

                // Start exploring a new island
                stack.push(i * n + j);
                sum += grid[i][j];
                grid[i][j] = 0;

                while (!stack.isEmpty()) {
                    int cell = stack.pop();

                    int r = cell / n;
                    int c = cell % n;

                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];

                        if (nr >= 0 && nr < m &&
                            nc >= 0 && nc < n &&
                            grid[nr][nc] > 0) {

                            sum += grid[nr][nc];

                            stack.push(nr * n + nc);

                            // Mark visited
                            grid[nr][nc] = 0;
                        }
                    }
                }

                // Check the total value of this island
                if (sum % k == 0) {
                    count++;
                }
            }
        }

        return count;
    }
}